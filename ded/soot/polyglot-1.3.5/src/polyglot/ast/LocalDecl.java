package polyglot.ast;

import polyglot.types.Type;
import polyglot.types.Flags;
import polyglot.types.LocalInstance;
import polyglot.types.SemanticException;

/** 
 * A local variable declaration statement: a type, a name and an optional
 * initializer.
 */
public interface LocalDecl extends ForInit, VarDecl
{
    /** Set the declaration's flags. */
    LocalDecl flags(Flags flags);

    /** Set the declaration's type. */
    LocalDecl type(TypeNode type);

    /** Set the declaration's name. */
    LocalDecl name(String name);

    /** Get the declaration's initializer expression, or null. */
    Expr init();
    /** Set the declaration's initializer expression. */
    LocalDecl init(Expr init);

    /**
     * Set the type object for the local we are declaring.
     */
    LocalDecl localInstance(LocalInstance li);
}
