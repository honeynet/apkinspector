package polyglot.visit;

import polyglot.ast.*;
import polyglot.util.*;
import java.util.*;

/**
 * The <code>AlphaRenamer</code> runs over the AST and alpha-renames any local
 * variable declarations that it encounters.
 **/
public class AlphaRenamer extends NodeVisitor {

  protected NodeFactory nf;

  // Each set in this stack tracks the set of local decls in a block that
  // we're traversing.
  protected Stack setStack;

  protected Map renamingMap;

  // Tracks the set of variables known to be fresh.
  protected Set freshVars;

  /**
   * Creates a visitor for alpha-renaming locals.
   *
   * @param nf  The node factory to be used when generating new nodes.
   **/
  public AlphaRenamer(NodeFactory nf) {
    this.nf = nf;

    this.setStack = new Stack();
    this.setStack.push( new HashSet() );

    this.renamingMap = new HashMap();
    this.freshVars = new HashSet();
  }

  public NodeVisitor enter( Node n ) {
    if ( n instanceof Block ) {
      // Push a new, empty set onto the stack.
      setStack.push( new HashSet() );
    }

    if ( n instanceof LocalDecl ) {
      LocalDecl l = (LocalDecl)n;
      String name = l.name();

      if ( !freshVars.contains(name) ) {
	// Add a new entry to the current renaming map.
	String name_ = UniqueID.newID(name);

	freshVars.add(name_);

	((Set)setStack.peek()).add( name );
	renamingMap.put( name, name_ );
      }
    }

    return this;
  }

  public Node leave( Node old, Node n, NodeVisitor v ) {
    if ( n instanceof Block ) {
      // Pop the current name set off the stack and remove the corresponding
      // entries from the renaming map.
      Set s = (Set)setStack.pop();
      renamingMap.keySet().removeAll(s);
      return n;
    }

    if ( n instanceof Local ) {
      // Rename the local if its name is in the renaming map.
      Local l = (Local)n;
      String name = l.name();

      if ( !renamingMap.containsKey(name) ) {
	return n;
      }

      return l.name( (String)renamingMap.get(name) );
    }

    if ( n instanceof LocalDecl ) {
      // Rename the local decl.
      LocalDecl l = (LocalDecl)n;
      String name = l.name();

      if ( freshVars.contains(name) ) {
	return n;
      }

      if ( !renamingMap.containsKey(name) ) {
	throw new InternalCompilerError( "Unexpected error encountered while "
					 + "alpha-renaming." );
      }

      return l.name( (String)renamingMap.get(name) );
    }

    return n;
  }
}
