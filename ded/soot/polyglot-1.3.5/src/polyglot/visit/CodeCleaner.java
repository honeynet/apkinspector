package polyglot.visit;

import polyglot.ast.*;
import java.util.*;

/**
 * The <code>CodeCleaner</code> runs over the AST and performs some trivial
 * dead code elimination, while flattening blocks wherever possible.
 **/
public class CodeCleaner extends NodeVisitor {

  protected NodeFactory nf;
  protected AlphaRenamer alphaRen;

  /**
   * Creates a visitor for cleaning code.
   *
   * @param nf  The node factory to be used when generating new nodes.
   **/
  public CodeCleaner(NodeFactory nf) {
    this.nf = nf;
    this.alphaRen = new AlphaRenamer(nf);
  }

  public Node leave( Node old, Node n, NodeVisitor v ) {
    if ( !(n instanceof Block || n instanceof Labeled) ) {
      return n;
    }

    // If we have a labeled block consisting of just one statement, then
    // flatten the block and label the statement instead.  We also flatten
    // labeled blocks when there is no reference to the label within the
    // block.
    if ( n instanceof Labeled ) {
      Labeled l = (Labeled)n;
      if ( !(l.statement() instanceof Block) ) {
        return n;
      }

      Block b = (Block)l.statement();
      if ( b.statements().size() != 1 ) {
	if ( labelRefs(b).contains(l.label()) ) {
	  return n;
	}

	// There's no reference to the label within the block, so flatten and
	// clean up dead code.
        return nf.Block( b.position(), clean(flattenBlock(b)) );
      }

      // Alpha-rename local decls in the block that we're flattening.
      b = (Block)b.visit(alphaRen);
      return nf.Labeled( l.position(), l.label(),
                         (Stmt)b.statements().get(0) );
    }

    // Flatten any blocks that may be contained in this one, and clean up dead
    // code.
    Block b = (Block)n;
    List stmtList = clean(flattenBlock(b));

    if ( b instanceof SwitchBlock ) {
      return nf.SwitchBlock( b.position(), stmtList );
    }

    return nf.Block( b.position(), stmtList );
  }

  /**
   * Turns a Block into a list of Stmts.
   **/
  protected List flattenBlock( Block b ) {
    List stmtList = new LinkedList();
    for ( Iterator it = b.statements().iterator(); it.hasNext(); ) {
      Stmt stmt = (Stmt)it.next();
      if ( stmt instanceof Block ) {
	// Alpha-rename local decls in the block that we're flattening.
	stmt = (Stmt)stmt.visit(alphaRen);
        stmtList.addAll( ((Block)stmt).statements() );
      } else {
        stmtList.add( stmt );
      }
    }

    return stmtList;
  }

  /**
   * Performs some trivial dead code elimination on a list of statements.
   **/
  protected List clean( List l ) {
    List stmtList = new LinkedList();
    for ( Iterator it = l.iterator(); it.hasNext(); ) {
      Stmt stmt = (Stmt)it.next();
      stmtList.add( stmt );

      if ( stmt instanceof Branch || stmt instanceof Return
           || stmt instanceof Throw ) {
	return stmtList;
      }
    }

    return l;
  }

  /**
   * Traverses a Block and determines the set of label references.
   **/
  protected Set labelRefs( Block b ) {
    final Set result = new HashSet();
    b.visit( new NodeVisitor() {
	public Node leave( Node old, Node n, NodeVisitor v ) {
	  if ( n instanceof Branch ) {
	    result.add( ((Branch)n).label() );
	  }

	  return n;
	}
      } );

    return result;
  }
}
