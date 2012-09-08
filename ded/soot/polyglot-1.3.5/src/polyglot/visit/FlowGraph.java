package polyglot.visit;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

public class FlowGraph {
  /**
   * Maps from AST nodes to path maps and hence to <code>Peer</code>s
   * that represent occurrences of the
   * AST node in the flow graph.  In particular, <code>peerMap</code>
   * maps <code>IdentityKey(Node)</code>s to path maps.  A path map is a
   * map from paths (<code>ListKey(List of Terms)</code>) to
   * <code>Peer</code>s.  In particular, if <code>n</code> is an AST
   * node in a finally block, then there will be a <code>Peer</code> of
   * <code>n</code> for each possible path to the finally block, and the
   * path map records which <code>Peer</code> corresponds to which path.
   * If <code>n</code> does not occur in a finally block, then the path
   * map should have only a single entry, from an empty list to the
   * unique <code>Peer</code> for <code>n</code>.
   *
   * <p>
   * <b>WARNING</b>: the AST must be a tree, not a DAG.  Otherwise the
   * same peer may be used for a node that appears at multiple points in
   * the AST.  These points may have different data flows.
   * </p>
   */  
  protected Map peerMap;
  
  /**
   * The root of the AST that this is a flow graph for.
   */
  protected Term root;
  
  /**
   * Is the flow in this flow graph forward or backward?
   */
  protected boolean forward;

  FlowGraph(Term root, boolean forward) {
    this.root = root;
    this.forward = forward;
    this.peerMap = new HashMap();
  }

  public Term startNode() { return forward ? root.entry() : root; }
  public Term finishNode() { return forward ? root : root.entry(); }
  public Term entryNode() { return root.entry(); }
  public Term exitNode() { return root; }
  public Term root() { return root; }
  public boolean forward() { return forward; }

  public Collection pathMaps() {
    return peerMap.values();
  }

  public Map pathMap(Node n) {
    return (Map) peerMap.get(new IdentityKey(n));
  }

  /**
   * Return a collection of all <code>Peer</code>s in this flow graph.
   */
  public Collection peers() {
    Collection c = new ArrayList();
    for (Iterator i = peerMap.values().iterator(); i.hasNext(); ) {
      Map m = (Map) i.next();
      for (Iterator j = m.values().iterator(); j.hasNext(); ) {
        c.add(j.next());
      }
    }
    return c;
  }

  /**
   * Retrieve the <code>Peer</code> for the <code>Term n</code>, where 
   * <code>n</code> does not appear in a finally block. If no such Peer 
   * exists, then one will be created.
   * 
   * @param df unused; for legacy purposes only?
   */
  public Peer peer(Term n, DataFlow df) {
    return peer(n, Collections.EMPTY_LIST, df);
  }

  /**
   * Return a collection of all of the <code>Peer</code>s for the given 
   * <code>Term n</code>. 
   */
  public Collection peers(Term n) {
    IdentityKey k = new IdentityKey(n);
    Map pathMap = (Map) peerMap.get(k);
    if (pathMap == null) {
        return Collections.EMPTY_LIST;
    }
    return pathMap.values();
  }

  /**
   * Retrieve the <code>Peer</code> for the <code>Term n</code> that is 
   * associated with the given path to the finally block. (A term that occurs
   * in a finally block has one Peer for each possible path to that finally
   * block.) If no such Peer exists, then one will be created.
   * 
   * @param df unused; for legacy purposes only?
   */
  public Peer peer(Term n, List path_to_finally, DataFlow df) {
    IdentityKey k = new IdentityKey(n);
    Map pathMap = (Map) peerMap.get(k);
    if (pathMap == null) {
      pathMap = new HashMap();
      peerMap.put(k, pathMap);
    }

    ListKey lk = new ListKey(path_to_finally);
    Peer p = (Peer) pathMap.get(lk);
    if (p == null) {
      p = new Peer(n, path_to_finally);
      pathMap.put(lk, p);
    }
    return p;
  }

  /**
   * This class provides an identifying label for edges in the flow graph.
   * Thus, the condition of an if statement will have at least two edges
   * leaving it (in a forward flow graph): one will have the EdgeKey
   * FlowGraph.EDGE_KEY_TRUE, and is the flow that is taken when the condition
   * evaluates to true, and one will have the EdgeKey FlowGraph.EDGE_KEY_FALSE,
   * and is the flow that is taken when the condition evaluates to false. 
   * 
   * The differentiation of the flow graph edges allows for a finer grain
   * data flow analysis, as the dataflow equations can incorporate the 
   * knowledge that a condition is true or false on certain flow paths.
   */
  public static class EdgeKey {
      protected Object o;
      protected EdgeKey(Object o) {
          this.o = o;
      }
      public int hashCode() {
          return o.hashCode();
      }
      public boolean equals(Object other) {
          return (other instanceof EdgeKey) && 
                  (((EdgeKey)other).o.equals(this.o));
      }
      public String toString() {
          return o.toString();
      }
  }
  
  /**
   * This class extends EdgeKey and is the key for edges that are
   * taken when an exception of type t is thrown. Thus, the flow from
   * line 2 in the example below to the catch block (line 4) would have an
   * ExceptionEdgeKey constructed with the Type representing 
   * NullPointerExceptions.
   * 
   * <pre>
   * ...
   * try {                                      // line 1
   *   o.foo();                                 // line 2
   * }                                          // line 3
   * catch (NullPointerException e) {           // line 4
   *   ...
   * }
   * ...
   * </pre>
   */
  public static class ExceptionEdgeKey extends EdgeKey {
      public ExceptionEdgeKey(Type t) {
          super(t);
      }

      public Type type() {
          return (Type) o;
      }

      public String toString() {
          return (type().isClass() ? type().toClass().name() : type().toString() );
      }
  }
  
  /**
   * This EdgeKey is the EdgeKey for edges where the expression evaluates
   * to true.
   */
  public static final EdgeKey EDGE_KEY_TRUE = new EdgeKey("true");
  
  /**
   * This EdgeKey is the EdgeKey for edges where the expression evaluates
   * to false.
   */
  public static final EdgeKey EDGE_KEY_FALSE = new EdgeKey("false");

  /**
   * This EdgeKey is the EdgeKey for edges where the flow is not suitable 
   * for EDGE_KEY_TRUE, EDGE_KEY_FALSE or an 
   * ExceptionEdgeKey, such as the edges from a switch
   * statement to its cases and
   * the flow from a sink node in the control flow graph.
   */
  public static final EdgeKey EDGE_KEY_OTHER = new EdgeKey("");

  /**
   * This class represents an edge in the flow graph. The target of the edge
   * is either the head or the tail of the edge, depending on how the Edge is 
   * used. Thus, the target field in Edges in the collection Peer.preds is the
   * source Peer, while the target field in Edges in the collection Peer.succs 
   * is the destination Peer of edges.
   * 
   * Each Edge has an EdgeKey, which identifies when flow uses that edge in 
   * the flow graph. See EdgeKey for more information.
   */
  public static class Edge {
      protected Edge(EdgeKey key, Peer target) {
          this.key = key;
          this.target = target;
      }
      public EdgeKey getKey() {
          return key;
      }
      public Peer getTarget() {
          return target;
      }
      protected EdgeKey key;
      protected Peer target;
      public String toString() {
          return "(" + key + ")" + target;
      }
      
  }
  
  /**
   * A <code>Peer</code> is an occurance of an AST node in a flow graph. 
   * For most AST nodes, there will be only one Peer for each AST node. 
   * However, if the AST node occurs in a finally block, then there will be
   * multiple <code>Peer</code>s for that AST node, one for each possible
   * path to the finally block. This is becuase flow graphs for finally blocks 
   * are copied, one copy for each possible path to the finally block.
   */
  public static class Peer {
    protected DataFlow.Item inItem;  // Input Item for dataflow analysis
    protected Map outItems; // Output Items for dataflow analysis, a map from EdgeKeys to DataFlowlItems
    protected Term node; // The AST node that this peer is an occurance of.
    protected List succs; // List of successor Edges 
    protected List preds; // List of predecessor Edges 
    protected List path_to_finally; // the path to the finally block that 
                                    // uniquely distinguishes this Peer
                                    // from the other Peers for the AST node.

    /**
     * Set of all the different EdgeKeys that occur in the Edges in the 
     * succs. This Set is lazily constructed, as needed, by the 
     * method succEdgeKeys()
     */     
    private Set succEdgeKeys;

    public Peer(Term node, List path_to_finally) {
      this.node = node;
      this.path_to_finally = path_to_finally;
      this.inItem = null;
      this.outItems = null;
      this.succs = new ArrayList();
      this.preds = new ArrayList();
      this.succEdgeKeys = null;
    }

    /** The successor Edges. */
    public List succs() { return succs; }

    /** The predecessor Edges. */
    public List preds() { return preds; }

    /** The node for which this is a peer. */
    public Term node()  { return node; }

    /**
     * The input data flow item.  Should only be called
     * after data flow analysis is performed.
     */
    public DataFlow.Item inItem() { return inItem; }

    /**
     * The output item for a particular EdgeKey.  Should only be called
     * after data flow analysis is performed.
     */
    public DataFlow.Item outItem(EdgeKey key) {
      return (DataFlow.Item) outItems.get(key);
    }

    public String toString() {
      return node + "[" + hashCode() + ": " + path_to_finally + "]";
    }

    public Set succEdgeKeys() {
        if (this.succEdgeKeys == null) {
            // the successor edge keys have not yet been calculated. do it
            // now.
            this.succEdgeKeys = new HashSet();
            for (Iterator iter = this.succs.iterator(); iter.hasNext(); ) {
                Edge e = (Edge)iter.next();
                this.succEdgeKeys.add(e.getKey());
            }
            if (this.succEdgeKeys.isEmpty()) {
                // There are no successors for this node. Add in the OTHER
                // edge key, so that there is something to map the output
                // item from...
                this.succEdgeKeys.add(FlowGraph.EDGE_KEY_OTHER);
            }
        }
        return this.succEdgeKeys;
    }
  }

  /**
   * Class to be used for inserting Lists in hashtables using collection
   * equality (as defined in {@link polyglot.util.CollectionUtil CollectionUtil}).
   */
  protected static class ListKey {
    protected List list;

    ListKey(List list) {
      this.list = list;
    }

    public int hashCode() {
      return list.hashCode();
    }

    public boolean equals(Object other) {
      if (other instanceof ListKey) {
          ListKey k = (ListKey) other;
          return CollectionUtil.equals(list, k.list);
      }

      return false;
    }
  }
  
  public String toString() {
    
    StringBuffer sb = new StringBuffer();
    Set todo = new HashSet(this.peers());
    LinkedList queue = new LinkedList(this.peers(this.startNode()));
    
    while (!queue.isEmpty()) {
        Peer p = (Peer)queue.removeFirst();
        todo.remove(p);
//        sb.append(StringUtil.getShortNameComponent(p.node.getClass().getName()) + " ["+p.node+"]" + "\n");
        sb.append(p.node+" (" + p.node.position()+ ")\n");
        for (Iterator i = p.succs.iterator(); i.hasNext(); ) {
            Edge e = (Edge)i.next();
            Peer q = e.getTarget();
            sb.append("    -> " + q.node+" (" + q.node.position()+ ")\n");
            //sb.append("  " + StringUtil.getShortNameComponent(q.node.getClass().getName()) + " ["+q.node+"]" + "\n");
            if (todo.contains(q) && !queue.contains(q)) {
                queue.addLast(q);
            }
        }
        
        if (queue.isEmpty() && !todo.isEmpty()) {
            sb.append("\n\n***UNREACHABLE***\n");
            queue.addAll(todo);
            todo = Collections.EMPTY_SET;
        }
    }
    
    return sb.toString();
  }
}
