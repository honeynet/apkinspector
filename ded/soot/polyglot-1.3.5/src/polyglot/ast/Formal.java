package polyglot.ast;

import polyglot.types.Type;
import polyglot.types.Flags;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;

/**
 * A <code>Formal</code> represents a formal parameter to a method
 * or constructor or to a catch block.  It consists of a type and a variable
 * identifier.
 */
public interface Formal extends VarDecl
{
    /** Set the declaration's flags. */
    Formal flags(Flags flags);

    /** Set the declaration's type. */
    Formal type(TypeNode type);

    /** Set the declaration's name. */
    Formal name(String name);

    /**
     * Set the type object for the local we are declaring.
     */
    Formal localInstance(LocalInstance li);
}
