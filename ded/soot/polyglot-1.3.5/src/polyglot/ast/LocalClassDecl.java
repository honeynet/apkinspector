package polyglot.ast;

import polyglot.util.*;
import polyglot.types.*;

/**
 * A local class declaration statement.  The node is just a wrapper around
 * a class declaration.
 */
public interface LocalClassDecl extends CompoundStmt
{
    /** The class declaration. */
    ClassDecl decl();
}
