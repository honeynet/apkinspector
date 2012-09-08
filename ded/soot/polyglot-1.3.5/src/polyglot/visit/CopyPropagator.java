package polyglot.visit;

import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;

import java.util.*;

/**
 * Visitor which performs copy propagation.
 */
public class CopyPropagator extends DataFlow {
    public CopyPropagator(Job job, TypeSystem ts, NodeFactory nf) {
	super(job, ts, nf,
	      true /* forward analysis */,
	      true /* perform dataflow on entry to CodeDecls */);
    }

    protected static class DataFlowItem extends Item {
	// Map of LocalInstance -> CopyInfo.  The CopyInfo nodes form a forest
	// to represent copy information.
	private Map map;  

	/**
	 * Constructor for creating an empty set.
	 */
	protected DataFlowItem() {
	    this.map = new HashMap();
	}

	/**
	 * Deep copy constructor.
	 */
	protected DataFlowItem(DataFlowItem dfi) {
	    map = new HashMap(dfi.map.size());
	    for (Iterator it = dfi.map.entrySet().iterator(); it.hasNext(); ) {
		Map.Entry e = (Map.Entry)it.next();

		LocalInstance li = (LocalInstance)e.getKey();
		CopyInfo ci = (CopyInfo)e.getValue();
		if (ci.from != null) add(ci.from.li, li);
	    }
	}

	protected static class CopyInfo {
	    final LocalInstance li; // Local instance this node pertains to.
	    CopyInfo from;    // In edge.
	    Set to;	      // Out edges.
	    CopyInfo root;    // Root CopyInfo node for this tree.

	    protected CopyInfo(LocalInstance li) {
		if (li == null) {
		    throw new InternalCompilerError("Null local instance "
			+ "encountered during copy propagation.");
		}

		this.li = li;
		this.from = null;
		this.to = new HashSet();
		this.root = this;
	    }

	    protected void setRoot(CopyInfo root) {
		List worklist = new ArrayList();
		worklist.add(this);
		while (worklist.size() > 0) {
		    CopyInfo ci = (CopyInfo)worklist.remove(worklist.size()-1);
		    worklist.addAll(ci.to);
		    ci.root = root;
		}
	    }

	    public boolean equals(Object o) {
		if (!(o instanceof CopyInfo)) return false;
		CopyInfo ci = (CopyInfo)o;

		// Assume both are in consistent data structures, so only check
		// up pointers.  Also check root pointers because we can.
		return li == ci.li
		    && (from == null ? ci.from == null
			: (ci.from != null && from.li == ci.from.li))
		    && root.li == ci.root.li;
	    }

	    public int hashCode() {
		return li.hashCode()
		    + 31*(from == null ? 0 : from.li.hashCode()
			+ 31*root.li.hashCode());
	    }
	}

	protected void add(LocalInstance from, LocalInstance to) {
	    // Get the 'to' node.
	    boolean newTo = !map.containsKey(to);
	    CopyInfo ciTo;
	    if (newTo) {
		ciTo = new CopyInfo(to);
		map.put(to, ciTo);
	    } else {
		ciTo = (CopyInfo)map.get(to);
	    }

	    // Get the 'from' node.
	    CopyInfo ciFrom;
	    if (map.containsKey(from)) {
		ciFrom = (CopyInfo)map.get(from);
	    } else {
		ciFrom = new CopyInfo(from);
		map.put(from, ciFrom);
		ciFrom.root = ciFrom;
	    }

	    // Make sure ciTo doesn't already have a 'from' node.
	    if (ciTo.from != null) {
		throw new InternalCompilerError("Error while copying dataflow "
		    + "item during copy propagation.");
	    }

	    // Link up.
	    ciFrom.to.add(ciTo);
	    ciTo.from = ciFrom;

	    // Consistency fix-up.
	    if (newTo) {
		ciTo.root = ciFrom.root;
	    } else {
		ciTo.setRoot(ciFrom.root);
	    }
	}

	protected void intersect(DataFlowItem dfi) {
	    boolean modified = false;

	    for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
		Map.Entry e = (Map.Entry)it.next();
		LocalInstance li = (LocalInstance)e.getKey();
		CopyInfo ci = (CopyInfo)e.getValue();

		if (!dfi.map.containsKey(li)) {
		    modified = true;

		    it.remove();

		    // Surgery.  Bypass and remove the node.  We'll fix
		    // consistency later.
		    if (ci.from != null) ci.from.to.remove(ci);
		    for (Iterator i = ci.to.iterator(); i.hasNext(); ) {
			CopyInfo toCI = (CopyInfo)i.next();
			toCI.from = null;
		    }

		    continue;
		}

		if (ci.from == null) continue;

		// Other DFI contains this key.
		// Make sure that ci and ci.from are also in the same tree in
		// the other DFI.  If not, break the link in the intersection
		// result.
		CopyInfo otherCI = (CopyInfo)dfi.map.get(li);
		CopyInfo otherCIfrom = (CopyInfo)dfi.map.get(ci.from.li);

		if (otherCIfrom == null || otherCI.root != otherCIfrom.root) {
		    modified = true;

		    // Remove the uplink.
		    ci.from.to.remove(ci);
		    ci.from = null;
		}
	    }

	    if (!modified) return;

	    // Fix consistency.
	    for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
		Map.Entry e = (Map.Entry)it.next();
		CopyInfo ci = (CopyInfo)e.getValue();

		// Only work on roots.
		if (ci.from != null) continue;

		// Cut out singleton nodes.
		if (ci.to.isEmpty()) {
		    it.remove();
		    continue;
		}

		// Fix root.
		ci.setRoot(ci);
	    }
	}

	public void kill(LocalInstance var) {
	    if (!map.containsKey(var)) return;

	    CopyInfo ci = (CopyInfo)map.get(var);
	    map.remove(var);

	    // Splice out 'ci' and fix consistency.
	    if (ci.from != null) ci.from.to.remove(ci);
	    for (Iterator it = ci.to.iterator(); it.hasNext(); ) {
		CopyInfo toCI = (CopyInfo)it.next();
		toCI.from = ci.from;
		if (ci.from == null) {
		    toCI.setRoot(toCI);
		} else {
		    ci.from.to.add(toCI);
		}
	    }
	}

	public LocalInstance getRoot(LocalInstance var) {
	    if (!map.containsKey(var)) return null;
	    return ((CopyInfo)map.get(var)).root.li;
	}

	private void die() {
	    throw new InternalCompilerError("Copy propagation dataflow item "
		+ "consistency error.");
	}

	private void consistencyCheck() {
	    for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
		Map.Entry e = (Map.Entry)it.next();

		LocalInstance li = (LocalInstance)e.getKey();
		CopyInfo ci = (CopyInfo)e.getValue();

		if (li != ci.li) die();
		if (!map.containsKey(ci.root.li)) die();
		if (map.get(ci.root.li) != ci.root) die();

		if (ci.from == null) {
		    if (ci.root != ci) die();
		} else {
		    if (!map.containsKey(ci.from.li)) die();
		    if (map.get(ci.from.li) != ci.from) die();
		    if (ci.from.root != ci.root) die();
		    if (!ci.from.to.contains(ci)) die();
		}

		for (Iterator i = ci.to.iterator(); i.hasNext(); ) {
		    CopyInfo toCI = (CopyInfo)i.next();
		    if (!map.containsKey(toCI.li)) die();
		    if (map.get(toCI.li) != toCI) die();
		    if (toCI.root != ci.root) die();
		    if (toCI.from != ci) die();
		}
	    }
	}

	public int hashCode() {
	    int result = 0;
	    for (Iterator it = map.entrySet().iterator(); it.hasNext(); ) {
		Map.Entry e = (Map.Entry)it.next();
		result = 31*result + e.getKey().hashCode();
		result = 31*result + e.getValue().hashCode();
	    }

	    return result;
	}

	public boolean equals(Object o) {
	    if (!(o instanceof DataFlowItem)) return false;

	    DataFlowItem dfi = (DataFlowItem)o;
	    return map.equals(dfi.map);
	}

	public String toString() {
	    String result = "";
	    boolean first = true;

	    for (Iterator it = map.values().iterator(); it.hasNext(); ) {
		CopyInfo ci = (CopyInfo)it.next();
		if (ci.from != null) {
		    if (!first) result += ", ";
		    if (ci.root != ci.from) result += ci.root.li + " ->* ";
		    result += ci.from.li + " -> " + ci.li;
		    first = false;
		}
	    }
	    return "[" + result + "]";
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
		result.intersect(inItem);
	    }
	}

	return result;
    }

    private void killDecl(DataFlowItem dfi, Stmt stmt) {
	if (stmt instanceof LocalDecl) {
	    dfi.kill(((LocalDecl)stmt).localInstance());
	}
    }

    protected DataFlowItem flow(Item in, FlowGraph graph, Term t) {
	DataFlowItem result = new DataFlowItem((DataFlowItem)in);

	if (t instanceof Assign) {
	    Assign n = (Assign)t;
	    Assign.Operator op = n.operator();
	    Expr left = n.left();
	    Expr right = n.right();

	    if (left instanceof Local) {
		LocalInstance to = ((Local)left).localInstance();
		result.kill(to);

		if (right instanceof Local && op == Assign.ASSIGN) {
		    LocalInstance from = ((Local)right).localInstance();
		    result.add(from, to);
		}
	    }
	} else if (t instanceof Unary) {
	    Unary n = (Unary)t;
	    Unary.Operator op = n.operator();
	    Expr expr = n.expr();

	    if (expr instanceof Local && (op == Unary.POST_INC
		|| op == Unary.POST_DEC || op == Unary.PRE_INC
		|| op == Unary.PRE_DEC)) {

		result.kill(((Local)expr).localInstance());
	    }
	} else if (t instanceof LocalDecl) {
	    LocalDecl n = (LocalDecl)t;

	    LocalInstance to = n.localInstance();
	    result.kill(to);

	    // It's a copy if we're initializing a non-final local declaration
	    // with a value from a local variable.  We only care about
	    // non-final local declarations because final locals have special
	    // use in local classes.
	    if (!n.flags().isFinal() && n.init() instanceof Local) {
		LocalInstance from = ((Local)n.init()).localInstance();
		result.add(from, to);
	    }
	} else if (t instanceof Block) {
	    // Kill locals that were declared in the block.
	    Block n = (Block)t;
	    for (Iterator it = n.statements().iterator(); it.hasNext(); ) {
		killDecl(result, (Stmt)it.next());
	    }
	} else if (t instanceof Loop) {
	    if (t instanceof For) {
		// Kill locals that were declared in the initializers.
		For n = (For)t;
		for (Iterator it = n.inits().iterator(); it.hasNext(); ) {
		    killDecl(result, (Stmt)it.next());
		}
	    }

	    // Kill locals that were declared in the body.
	    killDecl(result, ((Loop)t).body());
	} else if (t instanceof Catch) {
	    // Kill catch's formal.
	    result.kill(((Catch)t).formal().localInstance());
	} else if (t instanceof If) {
	    // Kill locals declared in consequent and alternative.
	    If n = (If)t;
	    killDecl(result, n.consequent());
	    killDecl(result, n.alternative());
	}

	return result;
    }

    public Map flow(Item in, FlowGraph graph, Term t, Set succEdgeKeys) {
	return itemToMap(flow(in, graph, t), succEdgeKeys);
    }

    public void post(FlowGraph graph, Term root) throws SemanticException {
	// No need to do any checking.
	if (Report.should_report(Report.cfg, 2)) {
	    dumpFlowGraph(graph, root);
	}
    }

    public void check(FlowGraph graph, Term n, Item inItem, Map outItems)
	throws SemanticException {

	throw new InternalCompilerError("CopyPropagator.check should never be "
	    + "called.");
    }

    public Node leaveCall(Node old, Node n, NodeVisitor v)
	throws SemanticException {

	if (n instanceof Local) {
	    FlowGraph g = currentFlowGraph();
	    if (g == null) return n;

	    Local l = (Local)n;
	    Collection peers = g.peers(l);
	    if (peers == null || peers.isEmpty()) return n;

	    List items = new ArrayList();
	    for (Iterator it = peers.iterator(); it.hasNext(); ) {
		FlowGraph.Peer p = (FlowGraph.Peer)it.next();
		if (p.inItem() != null) items.add(p.inItem());
	    }

	    DataFlowItem in = (DataFlowItem)confluence(items, l, g);
	    if (in == null) return n;

	    LocalInstance root = in.getRoot(l.localInstance());
	    if (root == null) return n;
	    return l.name(root.name()).localInstance(root);
	}

	if (n instanceof Unary) {
	    return old;
	}

	if (n instanceof Assign) {
	    Assign oldAssign = (Assign)old;
	    Assign newAssign = (Assign)n;
	    return newAssign.left(oldAssign.left());
	}

	return n;
    }
}

