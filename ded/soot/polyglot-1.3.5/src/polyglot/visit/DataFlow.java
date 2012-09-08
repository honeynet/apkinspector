package polyglot.visit;

import java.util.*;

import polyglot.ast.*;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.IdentityKey;
import polyglot.util.InternalCompilerError;
import polyglot.util.StringUtil;
import polyglot.visit.FlowGraph.Edge;
import polyglot.visit.FlowGraph.EdgeKey;
import polyglot.visit.FlowGraph.ExceptionEdgeKey;
import polyglot.visit.FlowGraph.Peer;

/**
 * Abstract dataflow Visitor, to allow simple dataflow equations to be easily
 * implemented.
 */
public abstract class DataFlow extends ErrorHandlingVisitor
{
    /**
     * Indicates whether this dataflow is a forward analysis.
     */
    protected final boolean forward;
    
    /**
     * Indicates whether the dataflow should be performed on entering a
     * <code>CodeDecl</code>, or on leaving a <code>CodeDecl</code>.
     * If dataflow is performed on entry, then the control flow graph
     * will be available when visiting children of the
     * <code>CodeDecl</code>, via the <code>currentFlowGraph</code>
     * method. If dataflow is performed on leaving, then the control
     * flow graph will not be available, but nested
     * <code>CodeDecl</code>s will have already been processed.
     */
    protected final boolean dataflowOnEntry;
    
    /**
     * A stack of <code>FlowGraphSource</code>. The flow graph is constructed 
     * upon entering a CodeDecl AST node, and dataflow performed on that flow 
     * graph immediately. The flow graph is available during the visiting of 
     * children of the CodeDecl, if subclasses want to use this information
     * to update AST nodes. The stack is only maintained if 
     * <code>dataflowOnEntry</code> is true.
     */
    protected LinkedList flowgraphStack;
    
    protected static class FlowGraphSource {
        FlowGraphSource(FlowGraph g, CodeDecl s) {
            flowgraph = g;
            source = s;
        }
        FlowGraph flowgraph;
        CodeDecl source;
        public FlowGraph flowGraph() { return flowgraph; }
        public CodeDecl source() { return source; }
    }
    
    /**
     * Constructor.
     */
    public DataFlow(Job job, TypeSystem ts, NodeFactory nf, boolean forward) {
        this(job, ts, nf, forward, false);
    }

    /**
     * Constructor.
     */
    public DataFlow(Job job, 
                    TypeSystem ts, 
                    NodeFactory nf, 
                    boolean forward, 
                    boolean dataflowOnEntry) {
        super(job, ts, nf);
        this.forward = forward;
        this.dataflowOnEntry = dataflowOnEntry;
        if (dataflowOnEntry)
            this.flowgraphStack = new LinkedList();
        else 
            this.flowgraphStack = null;
    }

    /**
     * An <code>Item</code> contains the data which flows during the dataflow
     * analysis. Each
     * node in the flow graph will have two items associated with it: the input
     * item, and the output item, which results from calling flow with the
     * input item. The input item may itself be the result of a call to the 
     * confluence method, if many paths flow into the same node.
     * 
     * NOTE: the <code>equals(Item)</code> method and <code>hashCode()</code>
     * method must be implemented to ensure that the dataflow algorithm works
     * correctly.
     */
    public static abstract class Item {
        public abstract boolean equals(Object i);
        public abstract int hashCode();
    }

    /**
     * Create an initial Item for the term node. This is generally how the Item that will be given
     * to the start node of a graph is created, although this method may also be called for other
     * (non-start) nodes.
     * 
     * @return a (possibly null) Item.
     */
    protected abstract Item createInitialItem(FlowGraph graph, Term node);
    
    /**
     * Produce new <code>Item</code>s as appropriate for the
     * <code>Term n</code> and the input <code>Item in</code>. 
     * 
     * @param in the Item flowing into the node. Note that if the Term n 
     *           has many flows going into it, the Item in may be the result 
     *           of a call to confluence(List, List, Term)
     * @param graph the FlowGraph which the dataflow is operating on
     * @param n the Term which this method must calculate the flow for.
     * @param edgeKeys a set of FlowGraph.EdgeKeys, being all the 
     *          EdgeKeys of the edges leaving this node. The 
     *          returned Map must have mappings for all objects in this set.
     * @return a Map from FlowGraph.EdgeKeys to Items. The map must have 
     *          entries for all EdgeKeys in edgeKeys. 
     */
    protected Map flow(Item in, FlowGraph graph, Term n, Set edgeKeys) {
        throw new InternalCompilerError("Unimplemented: should be " +
                                        "implemented by subclasses if " +
                                        "needed");
    }
    
    /**
     * Produce new <code>Item</code>s as appropriate for the
     * <code>Term n</code> and the input <code>Item</code>s. The default
     * implementation of this method is simply to call <code>confluence</code> 
     * for the list of inItems, and pass the result to flow(Item, FlowGraph,
     * Term, Set). Subclasses may want to override this method if a finer grain
     * dataflow is required. Some subclasses may wish to override this method
     * to call <code>flowToBooleanFlow</code>.
     * 
     * @param inItems all the Items flowing into the node. 
     * @param inItemKeys the FlowGraph.EdgeKeys for the items in the list inItems 
     * @param graph the FlowGraph which the dataflow is operating on
     * @param n the Term which this method must calculate the flow for.
     * @param edgeKeys a set of FlowGraph.EdgeKeys, being all the 
     *          EdgeKeys of the edges leaving this node. The 
     *          returned Map must have mappings for all objects in this set.
     * @return a Map from FlowGraph.EdgeKeys to Items. The map must have 
     *          entries for all EdgeKeys in edgeKeys. 
     */
    protected Map flow(List inItems, List inItemKeys, FlowGraph graph, Term n, Set edgeKeys) {
        Item inItem = this.safeConfluence(inItems, inItemKeys, n, graph);
        
        return this.flow(inItem, graph, n, edgeKeys);
    }

        
    

    /**
     * A utility method that simply collects together all the 
     * TRUE items, FALSE items, and all other items (including ExceptionEdgeKey
     * items), calls <code>confluence</code> on each of these three collections
     * as neccessary, and passes the results to 
     * flow(Item, Item, Item, FlowGraph, Term, Set). It is expected that 
     * this method will typically be called by subclasses overriding the
     * flow(List, List, FlowGraph, Term, Set) method, due to the need for
     * a finer grain dataflow analysis.
     * 
     * @param inItems all the Items flowing into the node. 
     * @param inItemKeys the FlowGraph.EdgeKeys for the items in the list inItems 
     * @param graph the FlowGraph which the dataflow is operating on
     * @param n the Term which this method must calculate the flow for.
     * @param edgeKeys a set of FlowGraph.EdgeKeys, being all the 
     *          EdgeKeys of the edges leaving this node. The 
     *          returned Map must have mappings for all objects in this set.
     * @return a Map from FlowGraph.EdgeKeys to Items. The map must have 
     *          entries for all EdgeKeys in edgeKeys. 
     */
    protected final Map flowToBooleanFlow(List inItems, List inItemKeys, FlowGraph graph, Term n, Set edgeKeys) {
        List trueItems = new ArrayList();
        List trueItemKeys = new ArrayList();
        List falseItems = new ArrayList();
        List falseItemKeys = new ArrayList();
        List otherItems = new ArrayList();
        List otherItemKeys = new ArrayList();
        
        Iterator i = inItems.iterator();
        Iterator j = inItemKeys.iterator();
        while (i.hasNext() || j.hasNext()) {
            Item item = (Item)i.next();
            EdgeKey key = (EdgeKey)j.next();
            
            if (FlowGraph.EDGE_KEY_TRUE.equals(key)) {
                trueItems.add(item);
                trueItemKeys.add(key);
            }
            else if (FlowGraph.EDGE_KEY_FALSE.equals(key)) {
                falseItems.add(item);
                falseItemKeys.add(key);
            }
            else {
                otherItems.add(item);
                otherItemKeys.add(key);
            }
        }
        
        Item trueItem = trueItems.isEmpty() ? null : this.safeConfluence(trueItems, trueItemKeys, n, graph);
        Item falseItem = falseItems.isEmpty() ? null : this.safeConfluence(falseItems, falseItemKeys, n, graph);
        Item otherItem = otherItems.isEmpty() ? null : this.safeConfluence(otherItems, otherItemKeys, n, graph);

        return this.flow(trueItem, falseItem, otherItem, graph, n, edgeKeys);
    }

    protected Map flow(Item trueItem, Item falseItem, Item otherItem, 
                       FlowGraph graph, Term n, Set edgeKeys) {
       throw new InternalCompilerError("Unimplemented: should be " +
                                       "implemented by subclasses if " +
                                       "needed");        
    }
    
    /**
     * 
     * @param trueItem The item for flows coming into n for true conditions. Cannot be null.
     * @param falseItem The item for flows coming into n for false conditions. Cannot be null.
     * @param otherItem The item for all other flows coming into n 
     * @param n The boolean expression.
     * @param edgeKeys The outgoing edges 
     */
    protected Map flowBooleanConditions(Item trueItem, Item falseItem, Item otherItem, 
                                        FlowGraph graph, Expr n, Set edgeKeys) {
        if (!n.type().isBoolean() || !(n instanceof Binary || n instanceof Unary)) {
            throw new InternalCompilerError("This method only takes binary " +
                      "or unary operators of boolean type");
        }
        
        if (trueItem == null || falseItem == null) {
            throw new IllegalArgumentException("The trueItem and falseItem " +
                                  "for flowBooleanConditions must be non-null.");
        }
        
        if (n instanceof Unary) {
            Unary u = (Unary)n;
            if (u.operator() == Unary.NOT) {
                return itemsToMap(falseItem, trueItem, otherItem, edgeKeys);                
            }
        }
        else {
            Binary b = (Binary)n;
            if (b.operator() == Binary.COND_AND) {
                // the only true item coming into this node should be
                // if the second operand was true.
                return itemsToMap(trueItem, falseItem, otherItem, edgeKeys);                
            }
            else if (b.operator() == Binary.COND_OR) {
                // the only false item coming into this node should be
                // if the second operand was false.
                return itemsToMap(trueItem, falseItem, otherItem, edgeKeys);                
            }
            else if (b.operator() == Binary.BIT_AND) {
                // there is both a true and a false item coming into this node, 
                // from the second operand. However, this operator could be false
                // if either the first or the second argument returned false.
                Item bitANDFalse = 
                     this.safeConfluence(trueItem, FlowGraph.EDGE_KEY_TRUE,
                                         falseItem, FlowGraph.EDGE_KEY_FALSE, 
                                         n, graph);
                return itemsToMap(trueItem, bitANDFalse, otherItem, edgeKeys);                
            }
            else if (b.operator() == Binary.BIT_OR) {
                // there is both a true and a false item coming into this node, 
                // from the second operand. However, this operator could be true
                // if either the first or the second argument returned true.
                Item bitORTrue = 
                    this.safeConfluence(trueItem, FlowGraph.EDGE_KEY_TRUE,
                                        falseItem, FlowGraph.EDGE_KEY_FALSE, 
                                        n, graph);
                return itemsToMap(bitORTrue, falseItem, otherItem, edgeKeys);                
            }
        }
        return null;
    }
    
    /**
     * The confluence operator for many flows. This method produces a single
     * Item from a List of Items, for the confluence just before flow enters 
     * node.
     * 
     * @param items List of <code>Item</code>s that flow into <code>node</code>.
     *            this method will only be called if the list has at least 2
     *            elements.
     * @param node <code>Term</code> for which the <code>items</code> are 
     *          flowing into.
     * @return a non-null Item.
     */
    protected abstract Item confluence(List items, Term node, FlowGraph graph);
    
    /**
     * The confluence operator for many flows. This method produces a single
     * Item from a List of Items, for the confluence just before flow enters 
     * node.
     * 
     * @param items List of <code>Item</code>s that flow into <code>node</code>.
     *               This method will only be called if the list has at least 2
     *               elements.
     * @param itemKeys List of <code>FlowGraph.ExceptionEdgeKey</code>s for
     *              the edges that the corresponding <code>Item</code>s in
     *              <code>items</code> flowed from.
     * @param node <code>Term</code> for which the <code>items</code> are 
     *          flowing into.
     * @return a non-null Item.
     */
    protected Item confluence(List items, List itemKeys, Term node, FlowGraph graph) {
        return confluence(items, node, graph); 
    }
    
    /**
     * The confluence operator for many flows. This method produces a single
     * Item from a List of Items, for the confluence just before flow enters 
     * node.
     * 
     * @param items List of <code>Item</code>s that flow into <code>node</code>.
     *               This method will only be called if the list has at least 2
     *               elements.
     * @param itemKeys List of <code>FlowGraph.ExceptionEdgeKey</code>s for
     *              the edges that the corresponding <code>Item</code>s in
     *              <code>items</code> flowed from.
     * @param node <code>Term</code> for which the <code>items</code> are 
     *          flowing into.
     * @return a non-null Item.
     */
    protected Item safeConfluence(List items, List itemKeys, Term node, FlowGraph graph) {
        if (items.isEmpty()) {
            return this.createInitialItem(graph, node);
        }
        if (items.size() == 1) {
            return (Item)items.get(0);
        }
        return confluence(items, itemKeys, node, graph); 
    }

    protected Item safeConfluence(Item item1, FlowGraph.EdgeKey key1,
                                  Item item2, FlowGraph.EdgeKey key2,
                                  Term node, FlowGraph graph) {
        return safeConfluence(item1, key1, item2, key2, null, null, node, graph);
    }
                                  
    protected Item safeConfluence(Item item1, FlowGraph.EdgeKey key1,
                                  Item item2, FlowGraph.EdgeKey key2,
                                  Item item3, FlowGraph.EdgeKey key3,
                                  Term node, FlowGraph graph) {
        List items = new ArrayList(3);
        List itemKeys = new ArrayList(3);
        
        if (item1 != null) {
            items.add(item1);
            itemKeys.add(key1);
        }
        if (item2 != null) {
            items.add(item2);
            itemKeys.add(key2);
        }
        if (item3 != null) {
            items.add(item3);
            itemKeys.add(key3);
        }
        return safeConfluence(items, itemKeys, node, graph); 
    }
    
    /**
     * Check that the term n satisfies whatever properties this
     * dataflow is checking for. This method is called for each term
     * in a code declaration block after the dataflow for that block of code 
     * has been performed.
     * 
     * @throws <code>SemanticException</code> if the properties this dataflow
     *         analysis is checking for is not satisfied.
     */
    protected abstract void check(FlowGraph graph, Term n, Item inItem, Map outItems) throws SemanticException;

    /**
     * Construct a flow graph for the <code>CodeDecl</code> provided, and call 
     * <code>dataflow(FlowGraph)</code>. Is also responsible for calling 
     * <code>post(FlowGraph, Block)</code> after
     * <code>dataflow(FlowGraph)</code> has been called, and for pushing
     * the <code>FlowGraph</code> onto the stack of <code>FlowGraph</code>s if
     * dataflow analysis is performed on entry to <code>CodeDecl</code> nodes.
     */
    protected void dataflow(CodeDecl cd) throws SemanticException {
        // only bother to do the flow analysis if the body is not null...
        if (cd.body() != null) {
            // Compute the successor of each child node.
            FlowGraph g = initGraph(cd, cd);

            if (g != null) {
                // Build the control flow graph.
                CFGBuilder v = createCFGBuilder(ts, g);

                try {
                    v.visitGraph();
                }
                catch (CFGBuildError e) {
                    throw new SemanticException(e.message(), e.position());
                }

                dataflow(g);

                post(g, cd);

                // push the CFG onto the stack if we are dataflowing on entry
                if (dataflowOnEntry)
                    flowgraphStack.addFirst(new FlowGraphSource(g, cd));
            }
        }
    }

    /** A "stack frame" for recursive DFS */
    static private class Frame {
	Peer peer;
	Iterator edges;
	Frame(Peer p, boolean forward) {
	    peer = p;
	    if (forward) edges = p.succs().iterator();
	    else edges = p.preds().iterator();
	}
    }

    /** Returns the linked list [by_scc, scc_head] where
     *  by_scc is an array in which SCCs occur in topologically
     *  order. 
     *  scc_head[n] where n is the first peer in an SCC is set to -1.
     *  scc_head[n] where n is the last peer in a (non-singleton) SCC is set
     *  to the index of the first peer. Otherwise it is -2. */
    protected LinkedList findSCCs(FlowGraph graph) {
	Collection peers = graph.peers();
	Peer[] sorted = new Peer[peers.size()];
        Collection start = graph.peers(graph.startNode());
	  // if start == peers, making all nodes reachable,
	  // the problem still arises.

	//System.out.println("scc: npeers = " + peers.size());

// First, topologically sort the nodes (put in postorder)
	int n = 0;
	LinkedList stack = new LinkedList();
	Set reachable = new HashSet();
	for (Iterator i = start.iterator(); i.hasNext(); ) {
	  Peer peer = (Peer)i.next();
	  if (!reachable.contains(peer)) {
	    reachable.add(peer);
	    stack.addFirst(new Frame(peer, true));
	    while (stack.size() != 0) {
		Frame top = (Frame)stack.getFirst();
		if (top.edges.hasNext()) {
		    Edge e = (Edge)top.edges.next();
		    Peer q = e.getTarget();
		    if (!reachable.contains(q)) {
			reachable.add(q);
			stack.addFirst(new Frame(q, true));
		    }
		} else {
		    stack.removeFirst();
		    sorted[n++] = top.peer;
		}
	    }
	  }
	}
	//System.out.println("scc: reached " + n);
// Now, walk the transposed graph picking nodes in reverse
// postorder, thus picking out one SCC at a time and
// appending it to "by_scc".
	Peer[] by_scc = new Peer[n];
	int[] scc_head = new int[n];
	Set visited = new HashSet();
	int head = 0;
	for (int i=n-1; i>=0; i--) {
	    if (!visited.contains(sorted[i])) {
		// First, find all the nodes in the SCC
		Set SCC = new HashSet();
		visited.add(sorted[i]);
		stack.add(new Frame(sorted[i], false));
		while (stack.size() != 0) {
		    Frame top = (Frame)stack.getFirst();
		    if (top.edges.hasNext()) {
			Edge e = (Edge)top.edges.next();
			Peer q = e.getTarget();
			if (reachable.contains(q) && !visited.contains(q)) {
			    visited.add(q);
			    Frame f = new Frame(q, false);
			    stack.addFirst(f);
			}
		    } else {
			stack.removeFirst();
			SCC.add(top.peer);
		    }
		}
		// Now, topologically sort the SCC (as much as possible)
		// and place into by_scc[head..head+scc_size-1]
		stack.add(new Frame(sorted[i], true));
		Set revisited = new HashSet();
		revisited.add(sorted[i]);
		int scc_size = SCC.size();
		int nsorted = 0;
		while (stack.size() != 0) {
		    Frame top = (Frame)stack.getFirst();
		    if (top.edges.hasNext()) {
			Edge e = (Edge)top.edges.next();
			Peer q = e.getTarget();
			if (SCC.contains(q) && !revisited.contains(q)) {
			    revisited.add(q);
			    Frame f = new Frame(q, true);
			    stack.addFirst(f);
			}
		    } else {
			stack.removeFirst();
			int n3 = head + scc_size - nsorted - 1;
			scc_head[n3] = -2;
			by_scc[n3] = top.peer;
			nsorted++;
		    }
		}
		scc_head[head+scc_size-1] = head;
		scc_head[head] = -1;
		head = head + scc_size;
	    }
	}
	if (Report.should_report(Report.dataflow, 2)) {
	    for (int j = 0; j < n; j++) {
		switch(scc_head[j]) {
		    case -1: Report.report(2, j + "[HEAD] : " + by_scc[j]); break;
		    case -2: Report.report(2, j + "       : " + by_scc[j]); break;
		    default: Report.report(2, j + " ->"+ scc_head[j] + " : " + by_scc[j]);
		}
		for (Iterator i = by_scc[j].succs().iterator(); i.hasNext(); ) {
		    Report.report(3, "     successor: " + ((Edge)i.next()).getTarget());
		}
	    }
	}
	LinkedList ret = new LinkedList();
	ret.addFirst(scc_head);
	ret.addFirst(by_scc);
	return ret;
    }

    /**
     * Perform the dataflow on the flowgraph provided.
     */
    protected void dataflow(FlowGraph graph) {
	if (Report.should_report(Report.dataflow, 1)) {
	    Report.report(1, "Finding strongly connected components");
	}
	LinkedList pair = findSCCs(graph);
	Peer[] by_scc = (Peer[])pair.getFirst();
	int[] scc_head = (int[])pair.getLast();
	int npeers = by_scc.length;

	/* by_scc contains the peers grouped by SCC.
	   scc_head marks where the SCCs are. The SCC
	   begins with a -1 and ends with the index of
	   the beginning of the SCC.
	*/
	if (Report.should_report(Report.dataflow, 1)) {
	    Report.report(1, "Iterating dataflow equations");
	}

	int current = 0;
	boolean change = false;

	while (current < npeers) {
            Peer p = by_scc[current];
	    if (scc_head[current] == -1) {
		change = false; // just started working on a new SCC
	    }

            // get the in items by examining the out items of all
            // the predecessors of p
            List inItems = new ArrayList(p.preds.size());
            List inItemKeys = new ArrayList(p.preds.size());
            for (Iterator i = p.preds.iterator(); i.hasNext(); ) {
                Edge e = (Edge)i.next();
                Peer o = e.getTarget();
                if (o.outItems != null) {
                    if (!o.outItems.keySet().contains(e.getKey())) {
                        throw new InternalCompilerError("There should have " +
                                "an out Item with edge key " + e.getKey() +
                                "; instead there were only " + 
                                o.outItems.keySet());
                    }
                    Item it = (Item)o.outItems.get(e.getKey());
                    if (it != null) {
                        inItems.add(it);
                        inItemKeys.add(e.getKey());
                    }
                }
            }
                
            // calculate the out item
            Map oldOutItems = p.outItems;
            p.inItem = this.safeConfluence(inItems, inItemKeys, p.node, graph);
            p.outItems = this.flow(inItems, inItemKeys, graph, p.node, p.succEdgeKeys());
                                
            if (!p.succEdgeKeys().equals(p.outItems.keySet())) {
                // This check is more for developers to ensure that they
                // have implemented their dataflow correctly. If performance
                // is an issue, maybe we should remove this check.
                throw new InternalCompilerError("The flow only defined " +
                        "outputs for " + p.outItems.keySet() + "; needs to " +
                        "define outputs for all of: " + p.succEdgeKeys());
            }

            if (oldOutItems != p.outItems &&
                 (oldOutItems == null || !oldOutItems.equals(p.outItems))) {
                // the outItems of p has changed, so we will
                // loop when we get to the end of the current SCC.
		change = true;
            }
	    if (change && scc_head[current] >= 0) {
		current = scc_head[current]; // loop!
		/* now scc_head[current] == -1 */
	    } else {
		current++;
	    }
        }
	if (Report.should_report(Report.dataflow, 1)) {
	    Report.report(1, "Done.");
	}
    }

    /**
     * Initialise the <code>FlowGraph</code> to be used in the dataflow
     * analysis.
     *
     * @return null if no dataflow analysis should be performed for this
     *         code declaration; otherwise, an apropriately initialized
     *         <code>FlowGraph.</code>
     */
    protected FlowGraph initGraph(CodeDecl code, Term root) {
        return new FlowGraph(root, forward);
    }

    /**
     * Construct a CFGBuilder.
     * 
     * @param ts The type system
     * @param g The flow graph to that the CFGBuilder will construct.
     * @return a new CFGBuilder
     */
    protected CFGBuilder createCFGBuilder(TypeSystem ts, FlowGraph g) {
        return new CFGBuilder(ts, g, this);
    }

    /**
     * Overridden superclass method, to build the flow graph, perform dataflow
     * analysis, and check the analysis for CodeDecl nodes.
     */
    protected NodeVisitor enterCall(Node n) throws SemanticException {
        if (dataflowOnEntry && n instanceof CodeDecl) {
            dataflow((CodeDecl)n);
        }
        
        return this;
    }

    /**
     * Overridden superclass method, to make sure that if a subclass has changed
     * a Term, that we update the peermaps appropriately, since they are based
     * on <code>IdentityKey</code>s.
     */
    public Node leave(Node parent, Node old, Node n, NodeVisitor v) {
        if (old != n) {            
            if (dataflowOnEntry && currentFlowGraph() != null) {
                // We currently only update the key in the peerMap.
                // We DO NOT update the Terms inside the peers, nor the
                // List of Terms that are the path maps. 
                Object o = currentFlowGraph().peerMap.get(new IdentityKey(old));
                if (o != null) {
                    currentFlowGraph().peerMap.put(new IdentityKey(n), o);
                }
            }
        }
        return super.leave(parent, old, n, v);
    }

    /**
     * Overridden superclass method, to pop from the stack of
     * <code>FlowGraph</code>s if necessary.
     */
    protected Node leaveCall(Node old, Node n, NodeVisitor v) throws SemanticException {
        if (n instanceof CodeDecl) {
            if (!dataflowOnEntry) {
                dataflow((CodeDecl)n);
            }
            else if (dataflowOnEntry && !flowgraphStack.isEmpty()) {
                FlowGraphSource fgs = (FlowGraphSource)flowgraphStack.getFirst();
                if (fgs.source.equals(old)) {
                    // we are leaving the code decl that pushed this flowgraph 
                    // on the stack. pop tbe stack.
                    flowgraphStack.removeFirst();
                }
            }
        }        
        return n;
    }

    /**
     * Check all of the Peers in the graph, after the dataflow analysis has
     * been performed.
     */
    protected void post(FlowGraph graph, Term root) throws SemanticException {
        if (Report.should_report(Report.cfg, 2)) {
            dumpFlowGraph(graph, root);
        }
        
        // Check the nodes in approximately flow order.
        Set uncheckedPeers = new HashSet(graph.peers());
        LinkedList peersToCheck = new LinkedList(graph.peers(graph.startNode()));
        while (!peersToCheck.isEmpty()) {
            Peer p = (Peer) peersToCheck.removeFirst();
            uncheckedPeers.remove(p);

            this.check(graph, p.node, p.inItem, p.outItems);
            
            for (Iterator iter = p.succs.iterator(); iter.hasNext(); ) {
                Peer q = ((Edge)iter.next()).getTarget();
                if (uncheckedPeers.contains(q) && !peersToCheck.contains(q)) {
                    // q hasn't been checked yet.
                    peersToCheck.addLast(q);
                }
            }
            
            if (peersToCheck.isEmpty() && !uncheckedPeers.isEmpty()) {
                // done all the we can reach...
                Iterator i = uncheckedPeers.iterator();                
                peersToCheck.add(i.next());
                i.remove();
            }
            
        }
    }
    
    /**
     * Return the <code>FlowGraph</code> at the top of the stack. This method
     * should not be called if dataflow is not being performed on entry to
     * the <code>CodeDecl</code>s, as the stack is not maintained in that case.
     * If this 
     * method is called by a subclass from the <code>enterCall</code> 
     * or <code>leaveCall</code> methods, for an AST node that is a child
     * of a <code>CodeDecl</code>, then the <code>FlowGraph</code> returned 
     * should be the <code>FlowGraph</code> for the dataflow for innermost
     * <code>CodeDecl</code>.
     */
    protected FlowGraph currentFlowGraph() {
        if (!dataflowOnEntry) {
            throw new InternalCompilerError("currentFlowGraph() cannot be" +
                " called when dataflow is not performed on entry");
        }
        if (flowgraphStack.isEmpty()) {
            return null;
        }
        return ((FlowGraphSource)flowgraphStack.getFirst()).flowgraph;
    }
    
    /**
     * This utility methods is for subclasses to convert a single Item into
     * a <code>Map</code>, to return from the
     * <code>flow</code> methods. This
     * method should be used when the same output <code>Item</code> from the
     * flow is to be used for all edges leaving the node.
     * 
     * @param i the <code>Item</code> to be placed in the returned
     *          <code>Map</code> as the value for every <code>EdgeKey</code> in
     *          <code>edgeKeys.</code>
     * @param edgeKeys the <code>Set</code> of <code>EdgeKey</code>s to be used
     *           as keys in the returned <code>Map</code>.
     * @return a <code>Map</code> containing a mapping from every
     *           <code>EdgeKey</code> in <code>edgeKeys</code> to the
     *           <code>Item i</code>.
     */
    protected static final Map itemToMap(Item i, Set edgeKeys) {
        Map m = new HashMap();
        for (Iterator iter = edgeKeys.iterator(); iter.hasNext(); ) {
            Object o = iter.next();
            m.put(o, i);
        }
        return m;
    }

    /**
     * This utility methods is for subclasses to convert a Items into
     * a <code>Map</code>, to return from the
     * <code>flow</code> methods. 
     * 
     * @param trueItem the <code>Item</code> to be placed in the returned
     *          <code>Map</code> as the value for the 
     *          <code>FlowGraph.EDGE_KEY_TRUE</code>, if that key is present in
     *          <code>edgeKeys.</code>
     * @param falseItem the <code>Item</code> to be placed in the returned
     *          <code>Map</code> as the value for the 
     *          <code>FlowGraph.EDGE_KEY_FALSE</code>, if that key is present in
     *          <code>edgeKeys.</code>
     * @param remainingItem the <code>Item</code> to be placed in the returned
     *          <code>Map</code> as the value for any edge key other than 
     *          <code>FlowGraph.EDGE_KEY_TRUE</code> or 
     *          <code>FlowGraph.EDGE_KEY_FALSE</code>, if any happen to be 
     *          present in
     *          <code>edgeKeys.</code>
     * @param edgeKeys the <code>Set</code> of <code>EdgeKey</code>s to be used
     *           as keys in the returned <code>Map</code>.
     * @return a <code>Map</code> containing a mapping from every
     *           <code>EdgeKey</code> in <code>edgeKeys</code> to the
     *           <code>Item i</code>.
     */
    protected static final Map itemsToMap(Item trueItem, Item falseItem, Item remainingItem, Set edgeKeys) {
        Map m = new HashMap();
        
        for (Iterator iter = edgeKeys.iterator(); iter.hasNext(); ) {
            FlowGraph.EdgeKey k = (EdgeKey)iter.next();
            if (FlowGraph.EDGE_KEY_TRUE.equals(k)) {
                m.put(k, trueItem);
            }
            else if (FlowGraph.EDGE_KEY_FALSE.equals(k)) {
                m.put(k, falseItem);
            }
            else { 
                m.put(k, remainingItem);
            }
        }
        return m;
    }

    /**
     * Filter a list of <code>Item</code>s to contain only <code>Item</code>s
     * that are not associated with error flows, that is, only 
     * <code>Item</code>s whose associated <code>EdgeKey</code>s are not 
     * <code>FlowGraph.ExceptionEdgeKey</code>s with a type that is a subclass
     * of <code>TypeSystem.Error()</code>.
     * 
     * @param items List of Items to filter
     * @param itemKeys List of <code>EdgeKey</code>s corresponding
     *            to the edge keys for the <code>Item</code>s in <code>items</code>.
     * @return a filtered list of items, containing only those whose edge keys
     *            are not <code>FlowGraph.ExceptionEdgeKey</code>s with 
     *            whose exception types are <code>Error</code>s.
     */    
    protected final List filterItemsNonError(List items, List itemKeys) {
        List filtered = new ArrayList(items.size());
        Iterator i = items.iterator();
        Iterator j = itemKeys.iterator();
        while (i.hasNext() && j.hasNext()) {
            Item item = (Item)i.next();
            EdgeKey key = (EdgeKey)j.next();
            
            if (!(key instanceof ExceptionEdgeKey &&
               ((ExceptionEdgeKey)key).type().isSubtype(ts.Error()))) {
                // the key is not an error edge key.
                filtered.add(item);
            }
        }
        
        if (i.hasNext() || j.hasNext()) {
            throw new InternalCompilerError("item and item key lists " +
                                            "have different sizes.");
        }
        
        return filtered;
    }
    
	/**
	 * Filter a list of <code>Item</code>s to contain only <code>Item</code>s
	 * that are not associated with exception flows, that is, only 
	 * <code>Item</code>s whose associated <code>EdgeKey</code>s are not 
	 * <code>FlowGraph.ExceptionEdgeKey</code>s.
	 * 
	 * @param items List of Items to filter
	 * @param itemKeys List of <code>EdgeKey</code>s corresponding
	 *            to the edge keys for the <code>Item</code>s in <code>items</code>.
	 * @return a filtered list of items, containing only those whose edge keys
	 *            are not <code>FlowGraph.ExceptionEdgeKey</code>s.
	 */    
	protected final List filterItemsNonException(List items, List itemKeys) {
		List filtered = new ArrayList(items.size());
		Iterator i = items.iterator();
		Iterator j = itemKeys.iterator();
		while (i.hasNext() && j.hasNext()) {
			Item item = (Item)i.next();
			EdgeKey key = (EdgeKey)j.next();
            
			if (!(key instanceof ExceptionEdgeKey)) {
				// the key is not an exception edge key.
				filtered.add(item);
			}
		}
        
		if (i.hasNext() || j.hasNext()) {
			throw new InternalCompilerError("item and item key lists " +
											"have different sizes.");
		}
        
		return filtered;
	}
 
	/**
	 * Filter a list of <code>Item</code>s to contain only <code>Item</code>s
	 * that are associated with exception flows, whose exception is a subclass
	 * of <code>excType</code>. That is, only 
	 * <code>Item</code>s whose associated <code>EdgeKey</code>s are  
	 * <code>FlowGraph.ExceptionEdgeKey</code>s, with the type a subclass
	 * of <code>excType</code>.
	 * 
	 * @param items List of Items to filter
	 * @param itemKeys List of <code>EdgeKey</code>s corresponding
	 *            to the edge keys for the <code>Item</code>s in <code>items</code>.
	 * @param excType an Exception <code>Type</code>.
	 * @return a filtered list of items, containing only those whose edge keys
	 *            are not <code>FlowGraph.ExceptionEdgeKey</code>s.
	 */    
	protected final List filterItemsExceptionSubclass(List items, List itemKeys, Type excType) {
		List filtered = new ArrayList(items.size());
		Iterator i = items.iterator();
		Iterator j = itemKeys.iterator();
		while (i.hasNext() && j.hasNext()) {
			Item item = (Item)i.next();
			EdgeKey key = (EdgeKey)j.next();
            
			if (key instanceof ExceptionEdgeKey) {
				// the key is an exception edge key.
				ExceptionEdgeKey eek = (ExceptionEdgeKey)key;
				if (eek.type().isImplicitCastValid(excType)) {
					filtered.add(item);
				}
			}
		}
        
		if (i.hasNext() || j.hasNext()) {
			throw new InternalCompilerError("item and item key lists " +
											"have different sizes.");
		}
        
		return filtered;
	}
 
    /**
     * Filter a list of <code>Item</code>s to contain only <code>Item</code>s
     * that are associated with the given <code>EdgeKey</code>.
     * 
     * @param items List of Items to filter
     * @param itemKeys List of <code>EdgeKey</code>s corresponding
     *            to the edge keys for the <code>Item</code>s in <code>items</code>.
     * @param filterEdgeKey the <code>EdgeKey</code> to use as a filter.
     * @return a filtered list of items, containing only those whose edge keys
     *            are the same as <code>filterEdgeKey</code>s.
     */    
    protected final List filterItems(List items, List itemKeys, FlowGraph.EdgeKey filterEdgeKey) {
        List filtered = new ArrayList(items.size());
        Iterator i = items.iterator();
        Iterator j = itemKeys.iterator();
        while (i.hasNext() && j.hasNext()) {
            Item item = (Item)i.next();
            EdgeKey key = (EdgeKey)j.next();
            
            if (filterEdgeKey.equals(key)) {
                // the key matches the filter
                filtered.add(item);
            }
        }
        
        if (i.hasNext() || j.hasNext()) {
            throw new InternalCompilerError("item and item key lists " +
                                            "have different sizes.");
        }
        
        return filtered;
    }
 
    
    /**
     * This utility method is for subclasses to determine if the node currently
     * under consideration has both true and false edges leaving it.  That is,
     * the flow graph at this node has successor edges with the
     * <code>EdgeKey</code>s <code>Edge_KEY_TRUE</code> and
     * <code>Edge_KEY_FALSE</code>.
     * 
     * @param edgeKeys the <code>Set</code> of <code>EdgeKey</code>s of the
     * successor edges of a given node.
     * @return true if the <code>edgeKeys</code> contains both
     * <code>Edge_KEY_TRUE</code> and
     * <code>Edge_KEY_FALSE</code>
     */
    protected static final boolean hasTrueFalseBranches(Set edgeKeys) {
        return edgeKeys.contains(FlowGraph.EDGE_KEY_FALSE) &&
               edgeKeys.contains(FlowGraph.EDGE_KEY_TRUE);
    }
        
    /**
     * This utility method is meant to be used by subclasses to help them
     * produce appropriate <code>Item</code>s for the
     * <code>FlowGraph.EDGE_KEY_TRUE</code> and
     * <code>FlowGraph.EDGE_KEY_FALSE</code> edges from a boolean condition.
     * 
     * @param booleanCond the boolean condition that is used to branch on. The
     *              type of the expression must be boolean.
     * @param startingItem the <code>Item</code> at the start of the flow for
     *              the expression <code>booleanCond</code>. 
     * @param succEdgeKeys the set of <code>EdgeKeys</code> of the successor
     *              nodes of the current node. Must contain both
     *              <code>FlowGraph.EDGE_KEY_TRUE</code>
     *              and <code>FlowGraph.EDGE_KEY_FALSE</code>.
     * @param navigator an instance of <code>ConditionNavigator</code> to be
     *              used to generate appropriate <code>Item</code>s from the
     *              boolean condition.
     * @return a <code>Map</code> containing mappings for all entries in
     *              <code>succEdgeKeys</code>.
     *              <code>FlowGraph.EDGE_KEY_TRUE</code> and
     *              <code>FlowGraph.EDGE_KEY_FALSE</code> 
     *              map to <code>Item</code>s calculated for them using
     *              navigator, and all other objects in
     *              <code>succEdgeKeys</code> are mapped to
     *              <code>startingItem</code>.
     */
    protected static Map constructItemsFromCondition(Expr booleanCond, 
                                                     Item startingItem,
                                                     Set succEdgeKeys,
                                                     ConditionNavigator navigator) {
        // check the arguments to make sure this method is used correctly
        if (!booleanCond.type().isBoolean()) {
            throw new IllegalArgumentException("booleanCond must be a boolean expression");
        }
        if (!hasTrueFalseBranches(succEdgeKeys)) {
            throw new IllegalArgumentException("succEdgeKeys does not have true and false branches.");
        }
        
        
        BoolItem results = navigator.navigate(booleanCond, startingItem);
        
        Map m = new HashMap();
        m.put(FlowGraph.EDGE_KEY_TRUE, results.trueItem);
        m.put(FlowGraph.EDGE_KEY_FALSE, results.falseItem);
        
        // put the starting item in the map for any EdgeKeys other than
        // FlowGraph.EDGE_KEY_TRUE and FlowGraph.EDGE_KEY_FALSE
        for (Iterator iter = succEdgeKeys.iterator(); iter.hasNext(); ) {
            EdgeKey e = (EdgeKey)iter.next();
            if (!FlowGraph.EDGE_KEY_TRUE.equals(e) &&
                !FlowGraph.EDGE_KEY_FALSE.equals(e)) {
                m.put(e, startingItem);
            }
        }
        
        return m;
    }
    
    /**
     * This class contains two <code>Item</code>s, one being the 
     * <code>Item</code> that is used when an expression is true, the
     * other being the one that is used when an expression is false. It is used
     * by the <code>ConditionNavigator</code>.
     */
    protected static class BoolItem {
        public BoolItem(Item trueItem, Item falseItem) {
            this.trueItem = trueItem;
            this.falseItem = falseItem;            
        }
        Item trueItem;
        Item falseItem;
        public Item trueItem() { return trueItem; }
        public Item falseItem() { return falseItem; }
        public String toString() {
            return "[ true: " + trueItem + "; false: " + falseItem + " ]";
        }
        
    }

    /**
     * A <code>ConditionNavigator</code> is used to traverse boolean
     * expressions that are
     * used as conditions, such as in if statements, while statements, 
     * left branches of && and ||. The <code>ConditionNavigator</code> is used
     * to generate
     * a finer-grained analysis, so that the branching flows from a 
     * condition can take into account the fact that the condition is true or
     * false. For example, in the statement <code>if (cond) s1 else s2</code>,
     * dataflow for <code>s1</code> can continue in the knowledge that 
     * <code>cond</code> evaluated to true, and similarly, <code>s2</code>
     * can be analyzed using the knowledge that <code>cond</code> evaluated to
     * false.
     * 
     * @deprecated
     */
    protected abstract static class ConditionNavigator {
        /**
         * Navigate the expression <code>expr</code>, where the 
         * <code>Item</code> at the start of evaluating the expression is 
         * <code>startingItem</code>.
         * 
         * A <code>BoolItem</code> is returned, containing the 
         * <code>Item</code>s that are appropriate when <code>expr</code>
         * evaluates to true and false.
         */
        public BoolItem navigate(Expr expr, Item startingItem) {
            if (expr.type().isBoolean()) {
                if (expr instanceof Binary) {
                    Binary b = (Binary)expr;
                    if (Binary.COND_AND.equals(b.operator()) ||
                        Binary.BIT_AND.equals(b.operator())) {
                        
                        BoolItem leftRes = navigate(b.left(), startingItem);
                        Item rightResStart = startingItem;
                        if (Binary.COND_AND.equals(b.operator())) {
                            // due to short circuiting, if the right
                            // branch is evaluated, the starting item is
                            // in fact the true part of the left result
                            rightResStart = leftRes.trueItem;                            
                        }
                        BoolItem rightRes = navigate(b.right(), rightResStart);
                        return andResults(leftRes, rightRes, startingItem);
                    }
                    else if (Binary.COND_OR.equals(b.operator()) ||
                             Binary.BIT_OR.equals(b.operator())) {
                        
                        BoolItem leftRes = navigate(b.left(), startingItem);
                        Item rightResStart = startingItem;
                        if (Binary.COND_OR.equals(b.operator())) {
                            // due to short circuiting, if the right
                            // branch is evaluated, the starting item is
                            // in fact the false part of the left result
                            rightResStart = leftRes.falseItem;                            
                        }
                        BoolItem rightRes = navigate(b.right(), rightResStart);
                        return orResults(leftRes, rightRes, startingItem);
                    }
                }
                else if (expr instanceof Unary) {
                    Unary u = (Unary)expr;
                    if (Unary.NOT.equals(u.operator())) {
                        BoolItem res = navigate(u.expr(), startingItem);
                        return notResult(res);
                    }
                }

            }
            
            // either we are not a boolean expression, or not a logical 
            // connective. Let the subclass deal with it.
            return handleExpression(expr, startingItem);
        }
        
        /**
         * Combine the results of analyzing the left and right arms of
         * an AND boolean operator (either &amp;&amp; or &amp;).
         */
        public BoolItem andResults(BoolItem left, 
                                   BoolItem right, 
                                   Item startingItem) {
            return new BoolItem(combine(left.trueItem, right.trueItem),
                                startingItem);            
        }

        /**
         * Combine the results of analyzing the left and right arms of
         * an OR boolean operator (either || or |).
         */
        public BoolItem orResults(BoolItem left, 
                                  BoolItem right, 
                                  Item startingItem) {
            return new BoolItem(startingItem,
                                combine(left.falseItem, right.falseItem));                        
        }

        /**
         * Modify the results of analyzing the child of 
         * a NEGATION boolean operator (a !).
         */
        public BoolItem notResult(BoolItem results) {
            return new BoolItem(results.falseItem, results.trueItem);            
        }

        /**
         * Combine two <code>Item</code>s together, when the information 
         * contained in both items is true. Thus, for example, in a not-null
         * analysis, where <code>Item</code>s are sets of not-null variables,
         * combining them corresponds to unioning the sets. Note that this
         * could be a different operation to the confluence operation.
         */
        public abstract Item combine(Item item1, Item item2);

        /**
         * Produce a <code>BoolItem</code> for an expression that is not
         * a boolean operator, such as &&, &, ||, | or !.
         */
        public abstract BoolItem handleExpression(Expr expr, Item startingItem);
    }
    
    protected static int flowCounter = 0;
    /**
     * Dump a flow graph, labeling edges with their flows, to aid in the
     * debugging of data flow.
     */
    protected void dumpFlowGraph(FlowGraph graph, Term root) {
        String name = StringUtil.getShortNameComponent(this.getClass().getName());
        name += flowCounter++;

        String rootName = "";
        if (graph.root() instanceof CodeDecl) {
            CodeDecl cd = (CodeDecl)graph.root();
            rootName = cd.codeInstance().toString() + " in " + 
                        cd.codeInstance().container().toString();
        }


        Report.report(2, "digraph DataFlow" + name + " {");
        Report.report(2, "  label=\"Dataflow: " + name + "\\n" + rootName +
            "\"; fontsize=20; center=true; ratio=auto; size = \"8.5,11\";");

        // Loop around the nodes...
        for (Iterator iter = graph.peers().iterator(); iter.hasNext(); ) {
            Peer p = (Peer)iter.next();
            
            // dump out this node
            Report.report(2,
                          p.hashCode() + " [ label = \"" +
                          StringUtil.escape(p.node.toString()) + "\\n(" + 
                          StringUtil.escape(StringUtil.getShortNameComponent(p.node.getClass().getName()))+ ")\" ];");
            
            // dump out the successors.
            for (Iterator iter2 = p.succs.iterator(); iter2.hasNext(); ) {
                Edge q = (Edge)iter2.next();
                Report.report(2,
                              q.getTarget().hashCode() + " [ label = \"" +
                              StringUtil.escape(q.getTarget().node.toString()) + " (" + 
                              StringUtil.escape(StringUtil.getShortNameComponent(q.getTarget().node.getClass().getName()))+ ")\" ];");
                String label = q.getKey().toString();
                if (p.outItems != null) {
                    label += "\\n" + p.outItems.get(q.getKey());
                }
                else {
                    label += "\\n[no dataflow available]";
                }
                Report.report(2, p.hashCode() + " -> " + q.getTarget().hashCode() + 
                              " [label=\"" + label + "\"];");
            }
            
        }
        Report.report(2, "}");
    }
}
