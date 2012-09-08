package polyglot.visit;

import java.util.*;

import polyglot.ast.*;
import polyglot.main.Report;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.*;

/**
 * Class used to construct a CFG.
 */
public class CFGBuilder implements Copy
{
    /** The flowgraph under construction. */
    protected FlowGraph graph;

    /** The type system. */
    protected TypeSystem ts;

    /**
     * The outer CFGBuilder.  We create a new inner CFGBuilder when entering a
     * loop or try-block and when entering a finally block.
     */
    protected CFGBuilder outer;

    /**
     * The innermost loop or try-block in lexical scope.  We maintain a stack
     * of loops and try-blocks in order to add edges for break and continue
     * statements and for exception throws.  When such a jump is encountered we
     * traverse the stack, searching for the target of the jump.
     */
    protected Stmt innermostTarget;

    /**
     * List of terms on the path to the innermost finally block.  If we are
     * constructing a CFG for a finally block, this is the sequence of terms
     * that caused entry into this and lexically enclosing finally blocks.
     * We construct a unique subgraph for each such path. The list
     * is empty if this CFGBuilder is not constructing the CFG for a finally
     * block.
     */
    protected List path_to_finally;

    /** The data flow analysis for which we are constructing the graph. */
    protected DataFlow df;

    /**
     * True if we should skip the catch blocks for the innermost try when
     * building edges for an exception throw.
     */
    protected boolean skipInnermostCatches;
    
    /**
     * True if we should add edges for uncaught Errors to the exit node of the
     * graph.  By default, we do not, but subclasses can change this behavior
     * if needed.
     */
    protected boolean errorEdgesToExitNode;

    public CFGBuilder(TypeSystem ts, FlowGraph graph, DataFlow df) {
        this.ts = ts;
        this.graph = graph;
        this.df = df;
        this.path_to_finally = Collections.EMPTY_LIST;
        this.outer = null;
        this.innermostTarget = null;
        this.skipInnermostCatches = false;
        this.errorEdgesToExitNode = false;
    }

    /** Get the type system. */
    public TypeSystem typeSystem() {
        return ts;
    }

    /** Copy the CFGBuilder. */
    public Object copy() {
        try {
            return (CFGBuilder) super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalCompilerError("Java clone() weirdness.");
        }
    }

    /**
     * Construct a new CFGBuilder with the a new innermost loop or
     * try-block <code>n</code>.
     */
    public CFGBuilder push(Stmt n) {
        return push(n, false);
    }

    /**
     * Construct a new CFGBuilder with the a new innermost loop or
     * try-block <code>n</code>, optionally skipping innermost catch blocks.
     */
    public CFGBuilder push(Stmt n, boolean skipInnermostCatches) {
        CFGBuilder v = (CFGBuilder) copy();
        v.outer = this;
        v.innermostTarget = n;
        v.skipInnermostCatches = skipInnermostCatches;
        return v;
    }

    /**
     * Visit edges from a branch.  Simulate breaking/continuing out of
     * the loop, visiting any finally blocks encountered.
     */
    public void visitBranchTarget(Branch b) {
      Term last = b;
      CFGBuilder last_visitor = this;

      for (CFGBuilder v = this; v != null; v = v.outer) {
        Term c = v.innermostTarget;

        if (c instanceof Try) {
          Try tr = (Try) c;
          if (tr.finallyBlock() != null) {
            last_visitor = tryFinally(v, last, last_visitor, tr.finallyBlock());
            last = tr.finallyBlock();
          }
        }

        if (b.label() != null) {
          if (c instanceof Labeled) {
            Labeled l = (Labeled) c;
            if (l.label().equals(b.label())) {
              if (b.kind() == Branch.BREAK) {
                edge(last_visitor, last, l, FlowGraph.EDGE_KEY_OTHER);
              }
              else {
                Stmt s = l.statement();
                if (s instanceof Loop) {
                  Loop loop = (Loop) s;
                  edge(last_visitor, last, loop.continueTarget(), FlowGraph.EDGE_KEY_OTHER);
                }
                else {
                  throw new CFGBuildError("Target of continue statement must " +
                                          "be a loop.", l.position());
                }
              }

              return;
            }
          }
        }
        else {
          if (c instanceof Loop) {
            Loop l = (Loop) c;
            if (b.kind() == Branch.CONTINUE) {
              edge(last_visitor, last, l.continueTarget(), FlowGraph.EDGE_KEY_OTHER);
            }
            else {
              edge(last_visitor, last, l, FlowGraph.EDGE_KEY_OTHER);
            }

            return;
          }
          else if (c instanceof Switch && b.kind() == Branch.BREAK) {
            edge(last_visitor, last, c, FlowGraph.EDGE_KEY_OTHER);
            return;
          }
        }
      }

      throw new CFGBuildError("Target of branch statement not found.",
                              b.position());
    }

    /**
     * Visit edges for a return statement.  Simulate the return, visiting any
     * finally blocks encountered.
     */
    public void visitReturn(Return r) {
      Term last = r;
      CFGBuilder last_visitor = this;

      for (CFGBuilder v = this; v != null; v = v.outer) {
        Term c = v.innermostTarget;

        if (c instanceof Try) {
          Try tr = (Try) c;
          if (tr.finallyBlock() != null) {
            last_visitor = tryFinally(v, last, last_visitor, tr.finallyBlock());
            last = tr.finallyBlock();
          }
        }
      }

      // Add an edge to the exit node.
      edge(last_visitor, last, graph.exitNode(), FlowGraph.EDGE_KEY_OTHER);
    }

    static int counter = 0;

    /** Visit the AST, constructing the CFG. */
    public void visitGraph() {
        String name = StringUtil.getShortNameComponent(df.getClass().getName());
        name += counter++;

	if (Report.should_report(Report.cfg, 2)) {
            String rootName = "";
            if (graph.root() instanceof CodeDecl) {
                CodeDecl cd = (CodeDecl)graph.root();
                rootName = cd.codeInstance().toString() + " in " + 
                            cd.codeInstance().container().toString();
            }

            Report.report(2, "digraph CFGBuild" + name + " {");
            Report.report(2, "  label=\"CFGBuilder: " + name + "\\n" + rootName +
                "\"; fontsize=20; center=true; ratio=auto; size = \"8.5,11\";");
        }

        // create peers for the entry and exit nodes.
        graph.peer(graph.entryNode(), Collections.EMPTY_LIST, df);
        graph.peer(graph.exitNode(), Collections.EMPTY_LIST, df);

        this.visitCFG(graph.root(), Collections.EMPTY_LIST);

	if (Report.should_report(Report.cfg, 2))
	    Report.report(2, "}");
    }

    /** Utility function to visit all edges in a list. */
    public void visitCFGList(List elements, Term after) {
        Term prev = null;

        for (Iterator i = elements.iterator(); i.hasNext(); ) {
            Term c = (Term) i.next();

            if (prev != null) {
                visitCFG(prev, c.entry());
            }

            prev = c;
        }

        if (prev != null) {
            visitCFG(prev, after);
        }
    }

    /**
     * Create an edge for a node <code>a</code> with a single successor
     * <code>succ</code>.
     * 
     * The EdgeKey used for the edge from <code>a</code> to <code>succ</code>
     * will be FlowGraph.EDGE_KEY_OTHER
     */
    public void visitCFG(Term a, Term succ) {
        visitCFG(a, FlowGraph.EDGE_KEY_OTHER, succ);
    }

    /**
     * Create an edge for a node <code>a</code> with a single successor
     * <code>succ</code>, and EdgeKey <code>edgeKey</code>
     */
    public void visitCFG(Term a, FlowGraph.EdgeKey edgeKey, Term succ) {
        visitCFG(a, CollectionUtil.list(new EdgeKeyTermPair(edgeKey, succ)));
    }

    /**
     * Create edges from node <code>a</code> to successors <code>succ1</code> 
     * and <code>succ2</code> with EdgeKeys <code>edgeKey1</code> and
     * <code>edgeKey2</code> respecitvely.
     */
    public void visitCFG(Term a, FlowGraph.EdgeKey edgeKey1, Term succ1, 
                                 FlowGraph.EdgeKey edgeKey2, Term succ2) {
        visitCFG(a, CollectionUtil.list(new EdgeKeyTermPair(edgeKey1, succ1), 
                                        new EdgeKeyTermPair(edgeKey2, succ2)));
    }

    /**
     * Create edges from node <code>a</code> to all successors <code>succ</code> 
     * with the EdgeKey <code>edgeKey</code> for all edges created.
     */
    public void visitCFG(Term a, FlowGraph.EdgeKey edgeKey, List succ) {
        List l = new ArrayList(2*succ.size());
        for (Iterator iter = succ.iterator(); iter.hasNext(); ) {
            l.add(new EdgeKeyTermPair(edgeKey, (Term)iter.next()));
        }
        visitCFG(a, l);
    }

    protected static class EdgeKeyTermPair {
        public EdgeKeyTermPair(FlowGraph.EdgeKey edgeKey, Term term) {
            this.edgeKey = edgeKey;
            this.term = term;
        }
        FlowGraph.EdgeKey edgeKey;
        Term term;        

	public String toString() {
	    return "{edgeKey=" + edgeKey + ",term=" + term + "}";
	}
    }
    /**
     * Create edges for a node <code>a</code> with successors
     * <code>succs</code>.
     * 
     * @param a the source node for the edges.
     * @param succs a list of <code>EdgeKeyTermPair</code>s
     */
    protected void visitCFG(Term a, List succs) {
        if (Report.should_report(Report.cfg, 2))
            Report.report(2, "// node " + a + " -> " + succs);

        succs = a.acceptCFG(this, succs);

        for (Iterator i = succs.iterator(); i.hasNext(); ) {
            EdgeKeyTermPair pair = (EdgeKeyTermPair)i.next();
            edge(a, pair.term, pair.edgeKey);
        }

        visitThrow(a);
    }

    public void visitThrow(Term a) {
        for (Iterator i = a.del().throwTypes(ts).iterator(); i.hasNext(); ) {
            Type type = (Type) i.next();
            visitThrow(a, type);
        }

        // Every statement can throw an error.
        // This is probably too inefficient.
        if ((a instanceof Stmt && ! (a instanceof CompoundStmt)) ||
            (a instanceof Block && ((Block) a).statements().isEmpty())) {
            
            visitThrow(a, ts.Error());
        }
    }
    
    /**
     * Create edges for an exception thrown from term <code>t</code>.
     */
    public void visitThrow(Term t, Type type) {
        Term last = t;
        CFGBuilder last_visitor = this;

        for (CFGBuilder v = this; v != null; v = v.outer) {
            Term c = v.innermostTarget;

            if (c instanceof Try) {
                Try tr = (Try) c;

                if (! v.skipInnermostCatches) {                    
                    boolean definiteCatch = false;
                    
                    for (Iterator i = tr.catchBlocks().iterator(); i.hasNext(); ) {
                        Catch cb = (Catch) i.next();

                        // definite catch
                        if (type.isImplicitCastValid(cb.catchType())) {
                            edge(last_visitor, last, cb.entry(), new FlowGraph.ExceptionEdgeKey(type));
                            definiteCatch = true;
                        }
                        // possible catch
                        else if (cb.catchType().isImplicitCastValid(type)) { 
                            edge(last_visitor, last, cb.entry(), new FlowGraph.ExceptionEdgeKey(cb.catchType()));
                        }
                    }
                    if (definiteCatch) {
                        // the exception has definitely been caught.
                        // we can stop recursing to outer try-catch blocks
                        return; 
                    }
                }

                if (tr.finallyBlock() != null) {
                    last_visitor = tryFinally(v, last, last_visitor, tr.finallyBlock());
                    last = tr.finallyBlock();
                }
            }
        }

        // If not caught, insert a node from the thrower to exit.
        if (errorEdgesToExitNode || !type.isSubtype(ts.Error())) {
            edge(last_visitor, last, graph.exitNode(), new FlowGraph.ExceptionEdgeKey(type));
        }
    }

    /**
     * Create edges for the finally block of a try-finally construct. 
     * 
     * @param v v.innermostTarget is the Try term that the finallyBlock is assoicated with. @@@XXX
     * @param last the last term visited before the finally block is entered.
     * @param last_visitor @@@XXX
     * @param finallyBlock the finally block associated with a try finally block.
     */
    protected static CFGBuilder tryFinally(CFGBuilder v, Term last,
                                 CFGBuilder last_visitor, Block finallyBlock) {
        //###@@@ I think that we may be using the wrong visitor to perform the
        // enterFinally on; should it maybe be last_visitor? we want to make 
        // sure that the path_to_finally list grows correctly.
        CFGBuilder v_ = v.outer.enterFinally(last);
        
        // @@@XXX
        v_.edge(last_visitor, last, finallyBlock.entry(), FlowGraph.EDGE_KEY_OTHER);
        
        // visit the finally block.  
        v_.visitCFG(finallyBlock, Collections.EMPTY_LIST);
        return v_;
    }

    /** 
     * Enter a finally block. This method returns a new CFGBuilder
     * with the path_to_finally field pointing to a list that has the
     * Term <code>from</code> appended.
     */
    protected CFGBuilder enterFinally(Term from) {
      CFGBuilder v = (CFGBuilder) this.copy();
      v.path_to_finally = new ArrayList(path_to_finally.size()+1);
      v.path_to_finally.addAll(path_to_finally);
      v.path_to_finally.add(from);
      return v;
    }

    /**
     * Add an edge to the CFG from <code>p</code> to <code>q</code>.
     */
    public void edge(Term p, Term q) {
      edge(this, p, q, FlowGraph.EDGE_KEY_OTHER);
    }

    /**
     * Add an edge to the CFG from <code>p</code> to <code>q</code>.
     */
    public void edge(Term p, Term q, FlowGraph.EdgeKey edgeKey) {
      edge(this, p, q, edgeKey);
    }
    
    /**
     * @param p_visitor The visitor used to create p ("this" is the visitor
     *                  that created q) 
     * @param p The predecessor node in the forward graph
     * @param q The successor node in the forward graph
     */
    public void edge(CFGBuilder p_visitor, Term p, Term q, FlowGraph.EdgeKey edgeKey) {
        if (Report.should_report(Report.cfg, 2))
            Report.report(2, "//     edge " + p + " -> " + q);
        
        FlowGraph.Peer pp = graph.peer(p, p_visitor.path_to_finally, df);
        FlowGraph.Peer pq = graph.peer(q, path_to_finally, df);
        
        if (Report.should_report(Report.cfg, 3)) {
            // at level 3, use Peer.toString() as the label for the nodes
            Report.report(2,
                          pp.hashCode() + " [ label = \"" +
                          StringUtil.escape(pp.toString()) + "\" ];");
            Report.report(2,
                          pq.hashCode() + " [ label = \"" +
                          StringUtil.escape(pq.toString()) + "\" ];");
        }
        else if (Report.should_report(Report.cfg, 2)) {
            // at level 2, use Node.toString() as the label for the nodes
            // which is more readable than Peer.toString(), but not as unique.
            Report.report(2,
                          pp.hashCode() + " [ label = \"" +
                          StringUtil.escape(pp.node.toString()) + "\" ];");
            Report.report(2,
                          pq.hashCode() + " [ label = \"" +
                          StringUtil.escape(pq.node.toString()) + "\" ];");
        }
        
        if (graph.forward()) {
            if (Report.should_report(Report.cfg, 2)) {
                Report.report(2, pp.hashCode() + " -> " + pq.hashCode() + " [label=\"" + edgeKey + "\"];");
            }
            pp.succs.add(new FlowGraph.Edge(edgeKey, pq));
            pq.preds.add(new FlowGraph.Edge(edgeKey, pp));
        }
        else {
            if (Report.should_report(Report.cfg, 2)) {
                Report.report(2, pq.hashCode() + " -> " + pp.hashCode() + " [label=\"" + edgeKey + "\"];");
            }
            pq.succs.add(new FlowGraph.Edge(edgeKey, pp));
            pp.preds.add(new FlowGraph.Edge(edgeKey, pq));
        }
    }
}
