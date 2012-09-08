package polyglot.ast;

import polyglot.types.ConstructorInstance;
import polyglot.types.ParsedClassType;
import java.util.List;

/**
 * A <code>New</code> is an immutable representation of the use of the
 * <code>new</code> operator to create a new instance of a class.  In
 * addition to the type of the class being created, a <code>New</code> has a
 * list of arguments to be passed to the constructor of the object and an
 * optional <code>ClassBody</code> used to support anonymous classes.
 */
public interface New extends Expr, ProcedureCall
{
    /** The type object for anonymous classes, or null. */
    ParsedClassType anonType();

    /** Set the type object for anonymous classes. */
    New anonType(ParsedClassType anonType);

    /** The constructor invoked by this expression. */
    ConstructorInstance constructorInstance();

    /** Set the constructor invoked by this expression. */
    New constructorInstance(ConstructorInstance ci);

    /**
     * The qualifier expression for the type, or null. If non-null, this
     * expression creates an inner class of the static type of the qualifier.
     */
    Expr qualifier();

    /** Set the qualifier expression for the type. */
    New qualifier(Expr qualifier);

    /** The type we are creating, possibly qualified by qualifier. */
    TypeNode objectType();

    /** Set the type we are creating. */
    New objectType(TypeNode t);

    /** Actual arguments to pass to the constructor.
     * @return A list of {@link polyglot.ast.Expr Expr}.
     */
    List arguments();

    /** Set the actual arguments to pass to the constructor.
     * @param arguments A list of {@link polyglot.ast.Expr Expr}.
     */
    ProcedureCall arguments(List arguments);

    /** The class body for anonymous classes, or null. */
    ClassBody body();

    /** Set the class body for anonymous classes. */
    New body(ClassBody b);
}
