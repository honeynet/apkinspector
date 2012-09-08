package polyglot.visit;

import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

import java.util.*;

/**
 * Visitor which performs dead code elimination.
 */
public class DeadCodeEliminator extends DataFlow {
    public DeadCodeEliminator(Job job, TypeSystem ts, NodeFactory nf) {
	super(job, ts, nf,
	      false /* backward analysis */,
	      true  /* perform dataflow on entry to CodeDecls */);
    }

    protected static class DataFlowItem extends Item {
	// Set of LocalInstances of live variables.
	private Set liveVars;

	// Set of LocalInstances of live declarations.  A LocalDecl is live if
	// the declared local is ever live.
	private Set liveDecls;

	/**
	 * Constructor for creating an empty set.
	 */
	protected DataFlowItem() {
	    this.liveVars = new HashSet();
	    this.liveDecls = new HashSet();
	}

	/**
	 * Deep copy constructor.
	 */
	protected DataFlowItem(DataFlowItem dfi) {
	    liveVars = new HashSet(dfi.liveVars);
	    liveDecls = new HashSet(dfi.liveDecls);
	}

	public void add(LocalInstance li) {
	    liveVars.add(li);
	    liveDecls.add(li);
	}

	public void addAll(Set lis) {
	    liveVars.addAll(lis);
	    liveDecls.addAll(lis);
	}

	public void remove(LocalInstance li) {
	    liveVars.remove(li);
	}

	public void removeAll(Set lis) {
	    liveVars.removeAll(lis);
	}

	public void removeDecl(LocalInstance li) {
	    liveVars.remove(li);
	    liveDecls.remove(li);
	}

	public void union(DataFlowItem dfi) {
	    liveVars.addAll(dfi.liveVars);
	    liveDecls.addAll(dfi.liveDecls);
	}

	protected boolean needDecl(LocalInstance li) {
	    return liveDecls.contains(li);
	}

	protected boolean needDef(LocalInstance li) {
	    return liveVars.contains(li);
	}

	public int hashCode() {
	    int result = 0;
	    for (Iterator it = liveVars.iterator(); it.hasNext(); ) {
		result = 31*result + it.next().hashCode();
	    }

	    for (Iterator it = liveDecls.iterator(); it.hasNext(); ) {
		result = 31*result + it.next().hashCode();
	    }

	    return result;
	}

	public boolean equals(Object o) {
	    if (!(o instanceof DataFlowItem)) return false;

	    DataFlowItem dfi = (DataFlowItem)o;
	    return liveVars.equals(dfi.liveVars)
		&& liveDecls.equals(dfi.liveDecls);
	}

	public String toString() {
	    return "<vars=" + liveVars + " ; decls=" + liveDecls + ">";
	}
    }

    public Item createInitialItem(FlowGraph graph, Term node) {
	return new DataFlowItem();
    }

    public Item confluence(List inItems, Term node, FlowGraph graph) {
	DataFlowItem result = null;
	for (Iterator it = inItems.iterator(); it.hasNext(); ) {
	    DataFlowItem inItem = (DataFlowItem)it.next();
	    if (result == null) {
		result = new DataFlowItem(inItem);
	    } else {
		result.union(inItem);
	    }
	}

	return result;
    }

    public Map flow(Item in, FlowGraph graph, Term t, Set succEdgeKeys) {
	return itemToMap(flow(in, graph, t), succEdgeKeys);
    }

    protected DataFlowItem flow(Item in, FlowGraph graph, Term t) {
	DataFlowItem result = new DataFlowItem((DataFlowItem)in);

	Set[] du = null;

	if (t instanceof LocalDecl) {
	    LocalDecl n = (LocalDecl)t;

	    LocalInstance to = n.localInstance();
	    result.removeDecl(to);

	    du = getDefUse(n.init());
	} else if (t instanceof Stmt && !(t instanceof CompoundStmt)) {
	    du = getDefUse((Stmt)t);
	} else if (t instanceof CompoundStmt) {
	    if (t instanceof If) {
		du = getDefUse(((If)t).cond());
	    } else if (t instanceof Switch) {
		du = getDefUse(((Switch)t).expr());
	    } else if (t instanceof Do) {
		du = getDefUse(((Do)t).cond());
	    } else if (t instanceof For) {
		du = getDefUse(((For)t).cond());
	    } else if (t instanceof While) {
		du = getDefUse(((While)t).cond());
	    }
	}

	if (du != null) {
	    result.removeAll(du[0]);
	    result.addAll(du[1]);
	}

	return result;
    }

    public void post(FlowGraph graph, Term root) throws SemanticException {
	// No need to do any checking.
	if (Report.should_report(Report.cfg, 2)) {
	    dumpFlowGraph(graph, root);
	}
    }

    public void check(FlowGraph graph, Term n, Item inItem, Map outItems)
	throws SemanticException {

	throw new InternalCompilerError("DeadCodeEliminator.check should "
	    + "never be called.");
    }

    private DataFlowItem getItem(Term n) {
	FlowGraph g = currentFlowGraph();
	if (g == null) return null;

	Collection peers = g.peers(n);
	if (peers == null || peers.isEmpty()) return null;

	List items = new ArrayList();
	for (Iterator it = peers.iterator(); it.hasNext(); ) {
	    FlowGraph.Peer p = (FlowGraph.Peer)it.next();
	    if (p.inItem() != null) items.add(p.inItem());
	}

	return (DataFlowItem)confluence(items, n, g);
    }

    public Node leaveCall(Node old, Node n, NodeVisitor v)
	throws SemanticException {

	if (n instanceof LocalDecl) {
	    LocalDecl ld = (LocalDecl)n;
	    DataFlowItem in = getItem(ld);
	    if (in == null || in.needDecl(ld.localInstance())) return n;
	    return getEffects(ld.init());
	}

	if (n instanceof Eval) {
	    Eval eval = (Eval)n;
	    Expr expr = eval.expr();
	    Local local;
	    Expr right = null;

	    if (expr instanceof Assign) {
		Assign assign = (Assign)expr;
		Expr left = assign.left();
		right = assign.right();

		if (!(left instanceof Local)) return n;
		local = (Local)left;
	    } else if (expr instanceof Unary) {
		Unary unary = (Unary)expr;
		expr = unary.expr();
		if (!(expr instanceof Local)) return n;
		local = (Local)expr;
	    } else {
		return n;
	    }

	    DataFlowItem in = getItem(eval);
	    if (in == null || in.needDef(local.localInstance())) return n;

	    if (right != null) {
		return getEffects(right);
	    }

	    return nf.Empty(Position.COMPILER_GENERATED);
	}

	if (n instanceof Block) {
	    // Get rid of empty statements.
	    Block b = (Block)n;
	    List stmts = new ArrayList(b.statements());
	    for (Iterator it = stmts.iterator(); it.hasNext(); ) {
		if (it.next() instanceof Empty) it.remove();
	    }

	    return b.statements(stmts);
	}

	return n;
    }

    /**
     * Returns array of sets of local instances.
     * Element 0 is the set of local instances DEFined by the node.
     * Element 1 is the set of local instances USEd by the node.
     */
    protected Set[] getDefUse(Node n) {
	final Set def = new HashSet();
	final Set use = new HashSet();

	if (n != null) {
	    n.visit(createDefUseFinder(def, use));
	}

	return new Set[] {def, use};
    }

    protected NodeVisitor createDefUseFinder(Set def, Set use) {
	return new DefUseFinder(def, use);
    }

    protected static class DefUseFinder extends HaltingVisitor {
	protected Set def;
	protected Set use;

	public DefUseFinder(Set def, Set use) {
	    this.def = def;
	    this.use = use;
	}

	public NodeVisitor enter(Node n) {
	    if (n instanceof LocalAssign) {
		return bypass(((Assign)n).left());
	    }

	    return super.enter(n);
	}

	public Node leave(Node old, Node n, NodeVisitor v) {
	    if (n instanceof Local) {
		use.add(((Local)n).localInstance());
	    } else if (n instanceof Assign) {
		Expr left = ((Assign)n).left();
		if (left instanceof Local) {
		    def.add(((Local)left).localInstance());
		}
	    }

	    return n;
	}
    }

    /**
     * Returns a statement that is side-effect-equivalent to the given
     * expression.
     */
    protected Stmt getEffects(Expr expr) {
	Stmt empty = nf.Empty(Position.COMPILER_GENERATED);
	if (expr == null) return empty;

	final List result = new LinkedList();
	final Position pos = Position.COMPILER_GENERATED;

	NodeVisitor v = new HaltingVisitor() {
	    public NodeVisitor enter(Node n) {
		if (n instanceof Assign || n instanceof ProcedureCall) {
		    return bypassChildren(n);
		}

		// XXX Cast

		if (n instanceof Unary) {
		    Unary.Operator op = ((Unary)n).operator();
		    if (op == Unary.POST_INC || op == Unary.POST_DEC
			|| op == Unary.PRE_INC || op == Unary.PRE_INC) {

			return bypassChildren(n);
		    }
		}

		return this;
	    }

	    public Node leave(Node old, Node n, NodeVisitor v) {
		if (n instanceof Assign || n instanceof ProcedureCall) {
		    result.add(nf.Eval(pos, (Expr)n));
		} else if (n instanceof Unary) {
		    Unary.Operator op = ((Unary)n).operator();
		    if (op == Unary.POST_INC || op == Unary.POST_DEC
			|| op == Unary.PRE_INC || op == Unary.PRE_INC) {

			result.add(nf.Eval(pos, (Expr)n));
		    }
		}

		// XXX Cast

		return n;
	    }
	};

	expr.visit(v);

	if (result.isEmpty()) return empty;
	if (result.size() == 1) return (Stmt)result.get(0);
	return nf.Block(Position.COMPILER_GENERATED, result);
    }
}

