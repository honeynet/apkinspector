package polyglot.visit;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;

import java.util.*;

/**
 * The <code>NodeScrambler</code> is test case generator of sorts. Since it
 * is ofter useful to introduce ``random'' errors into source code, this
 * class provides a way of doing so in a semi-structed manner. The process
 * takes place in two phases. First, a "FirstPass" is made to collect 
 * a list of nodes and their parents. Then a second pass is made to randomly 
 * replace a branch of the tree with another suitable branch. 
 */
public class NodeScrambler extends NodeVisitor
{
  public FirstPass fp;

  protected HashMap pairs;
  protected LinkedList nodes;
  protected LinkedList currentParents;
  protected long seed;
  protected Random ran;
  protected boolean scrambled = false;
  protected CodeWriter cw;

  public NodeScrambler()
  {
    this.fp = new FirstPass();

    this.pairs = new HashMap();
    this.nodes = new LinkedList();
    this.currentParents = new LinkedList();
    this.cw = new CodeWriter( System.err, 72);

    Random ran = new Random();
    seed = ran.nextLong();
    
    System.err.println( "Using seed: " + seed);
    this.ran = new Random( seed);
  }

  /**
   * Create a new <code>NodeScrambler</code> with the given random number
   * generator seed.
   */
  public NodeScrambler( long seed)
  {
    this.fp = new FirstPass();
    
    this.pairs = new HashMap();
    this.nodes = new LinkedList();
    this.currentParents = new LinkedList();
    this.cw = new CodeWriter( System.err, 72);
    this.seed = seed;
    
    this.ran = new Random( seed);
  }

  /**
   * Scans throught the AST, create a list of all nodes present, along with
   * the set of parents for each node in the tree. <b>This visitor should be
   * run before the main <code>NodeScrambler</code> visits the tree.</b>
   */
  public class FirstPass extends NodeVisitor 
  {
    public NodeVisitor enter( Node n)
    {
      pairs.put( n, currentParents.clone());
      nodes.add( n);
      
      currentParents.add( n);
      return this;
    }
    
    public Node leave( Node old, Node n, NodeVisitor v)
    {
      currentParents.remove( n);
      return n;
    }
  }

  public long getSeed()
  {
    return seed;
  }

  public Node override( Node n)
  {
    if( coinFlip()) {
      Node m = potentialScramble( n);
      if( m == null) {
        /* No potential replacement. */
        return null;
      }
      else {
        scrambled = true;

        try {
          System.err.println( "Replacing:");
          n.dump( cw);
          cw.newline();
          cw.flush();
          System.err.println( "With:");
          m.dump( cw);
          cw.newline();
          cw.flush();
        }
        catch( Exception e)
        {
          e.printStackTrace();
          return null;
        }
        return m;
      }
    }
    else {
      return null;
    }  
  }

  protected boolean coinFlip()
  {
    if( scrambled) {
      return false;
    }
    else {
      if( ran.nextDouble() > 0.9) {
        return true;
      }
      else {
        return false;
      }
    }
  }

  protected Node potentialScramble( Node n)
  {
    Class required = Node.class;

    if( n instanceof SourceFile) {
      return null;
    }
    if( n instanceof Import) {
      required = Import.class;
    }
    else if( n instanceof TypeNode) {
      required = TypeNode.class;
    }
    else if( n instanceof ClassDecl) {
      required = ClassDecl.class;
    }
    else if( n instanceof ClassMember) {
      required = ClassMember.class;
    }
    else if( n instanceof Formal) {
      required = Formal.class;
    }
    else if( n instanceof Expr) {
      required = Expr.class;
    }
    else if( n instanceof Block) {
      required = Block.class;
    }
    else if( n instanceof Catch) {
      required = Catch.class;
    }
    else if( n instanceof LocalDecl) {
      required = LocalDecl.class;
    }
    else if( n instanceof Stmt) {
      required = Stmt.class;
    }

    LinkedList parents = (LinkedList)pairs.get( n);
    Iterator iter1 = nodes.iterator(), iter2;
    boolean isParent;

    while( iter1.hasNext()) {
      Node m = (Node)iter1.next();
      if( required.isAssignableFrom( m.getClass())) {

        isParent = false;
        iter2 = parents.iterator();
        while( iter2.hasNext()) {
          if( m == iter2.next()) {
            isParent = true;
          }
        }

        if( !isParent && m != n) {
          return m;
        }
      }
    }

    return null;
  }
}
