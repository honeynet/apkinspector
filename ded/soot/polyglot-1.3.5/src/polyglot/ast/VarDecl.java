package polyglot.ast;

import polyglot.types.Type;
import polyglot.types.Flags;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;

/**
 * A <code>VarDecl</code> represents a variable declaration, of either a formal
 * or a local variable.
 */
public interface VarDecl extends Term
{
    /** Get the type object for the declaration's type. */
    Type declType();

    /** Get the declaration's flags. */
    Flags flags();

    /** Get the declaration's type. */
    TypeNode type();

    /** Get the declaration's name. */
    String name();

    /**
     * Get the type object for the local we are declaring.  This field may
     * not be valid until after signature disambiguation.
     */
    LocalInstance localInstance();

}
