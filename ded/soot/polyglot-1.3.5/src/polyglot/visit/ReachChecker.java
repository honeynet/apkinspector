package polyglot.visit;

import java.util.*;

import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;

/**
 * Visitor which checks that all statements must be reachable
 */
public class ReachChecker extends DataFlow
{
    public ReachChecker(Job job, TypeSystem ts, NodeFactory nf) {
	super(job, ts, nf, 
              true /* forward analysis */, 
              true /* perform dataflow on entry to CodeDecls */);
    }

    protected static class DataFlowItem extends Item {
        final boolean reachable;
        final boolean normalReachable;

        protected DataFlowItem(boolean reachable, boolean normalReachable) {
            this.reachable = reachable;
            this.normalReachable = normalReachable;
        }
        
        // terms that are reachable through normal control flow
        public static final DataFlowItem REACHABLE = new DataFlowItem(true, true);
        
        // terms that are reachable only through exception control flow, but
        // not by normal control flow. 
        public static final DataFlowItem REACHABLE_EX_ONLY = new DataFlowItem(true, false);

        // terms that are not reachable 
        public static final DataFlowItem NOT_REACHABLE = new DataFlowItem(false, false);

        public String toString() {
            return (reachable?"":"not ") + "reachable" +
                   (normalReachable?"":" by exceptions only");
        }
        
        public boolean equals(Object o) {
            if (o instanceof DataFlowItem) {
                return this.reachable == ((DataFlowItem)o).reachable &&
                       this.normalReachable == ((DataFlowItem)o).normalReachable;
            }
            return false;
        }
        public int hashCode() {
            return (reachable ? 5423 : 5753) + (normalReachable ? 31 : -2);
        }
    }

    public Item createInitialItem(FlowGraph graph, Term node) {
        if (node == graph.entryNode()) {
            return DataFlowItem.REACHABLE;
        }
        else {
            return DataFlowItem.NOT_REACHABLE;
        }
    }
    public Map flow(Item in, FlowGraph graph, Term n, Set succEdgeKeys) {
        if (in == DataFlowItem.NOT_REACHABLE) {
            return itemToMap(in, succEdgeKeys);
        }
        
        // in is either REACHABLE or REACHABLE_EX_ONLY.
        // return a map where all exception edges are REACHABLE_EX_ONLY,
        // and all non-exception edges are REACHABLE.
        Map m = itemToMap(DataFlowItem.REACHABLE_EX_ONLY, succEdgeKeys);

        if (succEdgeKeys.contains(FlowGraph.EDGE_KEY_OTHER)) {
            m.put(FlowGraph.EDGE_KEY_OTHER, DataFlowItem.REACHABLE);
        }
        if (succEdgeKeys.contains(FlowGraph.EDGE_KEY_TRUE)) {
            m.put(FlowGraph.EDGE_KEY_TRUE, DataFlowItem.REACHABLE);
        }
        if (succEdgeKeys.contains(FlowGraph.EDGE_KEY_FALSE)) {
            m.put(FlowGraph.EDGE_KEY_FALSE, DataFlowItem.REACHABLE);
        }
        
        return m;
    }

    public Item confluence(List inItems, Term node, FlowGraph graph) {
        throw new InternalCompilerError("Should never be called.");
    }

    public Item confluence(List inItems, List itemKeys, Term node, FlowGraph graph) {
        // if any predecessor is reachable, so is this one, and if any
        // predecessor is normal reachable, and the edge key is not an 
        // exception edge key, then so is this one.
        
        
        List l = this.filterItemsNonException(inItems, itemKeys);
        for (Iterator i = l.iterator(); i.hasNext(); ) {
            if (i.next() == DataFlowItem.REACHABLE) {
                // this term is reachable via a non-exception edge
                return DataFlowItem.REACHABLE;
            }
        }

        // If we fall through to here, then there were
        // no non-exception edges that were normally reachable.        
        // We now need to determine if this node is
        // reachable via an exception edge key, or if 
        // it is not reachable at all.
        for (Iterator i = inItems.iterator(); i.hasNext(); ) {
            if (((DataFlowItem)i.next()).reachable) {
                // this term is reachable, but only through an
                // exception edge.
                return DataFlowItem.REACHABLE_EX_ONLY;
            }
        }

        return DataFlowItem.NOT_REACHABLE;
    }

    public Node leaveCall(Node old, Node n, NodeVisitor v) throws SemanticException {
        // check for reachability.
        if (n instanceof Term) {
           n = checkReachability((Term)n);
        }
         
        return super.leaveCall(old, n, v);
    }

    protected Node checkReachability(Term n) throws SemanticException {
        FlowGraph g = currentFlowGraph();
        if (g != null) {   
            Collection peers = g.peers(n);
            if (peers != null && !peers.isEmpty()) {
                boolean isInitializer = (n instanceof Initializer);
                
                for (Iterator iter = peers.iterator(); iter.hasNext(); ) {
                    FlowGraph.Peer p = (FlowGraph.Peer) iter.next();
        
                    // the peer is reachable if at least one of its out items
                    // is reachable. This would cover all cases, except that some
                    // peers may have no successors (e.g. peers that throw an
                    // an exception that is not caught by the method). So we need 
                    // to also check the inItem.
                    if (p.inItem() != null) {
                        DataFlowItem dfi = (DataFlowItem)p.inItem();
                        // there will only be one peer for an initializer,
                        // as it cannot occur in a finally block.
                        if (isInitializer && !dfi.normalReachable) {
                            throw new SemanticException("Initializers must be able to complete normally.",
                                                        n.position());
                        }

                        if (dfi.reachable) {
                            return n.reachable(true);
                        }
                    }
                    
                    if (p.outItems != null) {
                        for (Iterator k = p.outItems.values().iterator(); k.hasNext(); ) {
                            DataFlowItem item = (DataFlowItem) k.next();
                        
                            if (item != null && item.reachable) {
                                // n is reachable.
                                return n.reachable(true);
                            }                    
                        }
                    }
                }
                
                // if we fall through to here, then no peer for n was reachable.
                n = n.reachable(false);
                
                // Compound statements are allowed to be unreachable
                // (e.g., "{ // return; }" or "while (true) S").  If a compound
                // statement is truly unreachable, one of its sub-statements will
                // be also and we will report an error there.
    
                if ((n instanceof Block && ((Block) n).statements().isEmpty()) ||
                    (n instanceof Stmt && ! (n instanceof CompoundStmt))) {
                    throw new SemanticException("Unreachable statement.",
                                                n.position());
                }
            }
        }        
        return n;
    }
    
    public void post(FlowGraph graph, Term root) throws SemanticException {
        // There is no need to do any checking in this method, as this will
        // be handled by leaveCall and checkReachability.
        if (Report.should_report(Report.cfg, 2)) {
            dumpFlowGraph(graph, root);
        }
    } 

    public void check(FlowGraph graph, Term n, Item inItem, Map outItems) throws SemanticException {
        throw new InternalCompilerError("ReachChecker.check should " +
                "never be called.");
    }
    
}
