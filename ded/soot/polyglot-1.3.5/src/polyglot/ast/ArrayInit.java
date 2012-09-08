package polyglot.ast;

import polyglot.types.*;
import java.util.List;

/**
 * An <code>ArrayInit</code> is an immutable representation of
 * an array initializer, such as { 3, 1, { 4, 1, 5 } }.  Note that
 * the elements of these array may be expressions of any type (e.g.,
 * <code>Call</code>).
 */
public interface ArrayInit extends Expr
{
    /**
     * Get the initializer elements.
     * @return A list of {@link polyglot.ast.Expr Expr}.
     */
    List elements();

    /**
     * Set the initializer elements.
     * @param elements A list of {@link polyglot.ast.Expr Expr}.
     */
    ArrayInit elements(List elements);

    /**
     * Type check the individual elements of the array initializer against the
     * left-hand-side type.  Each element is checked to see if it can be
     * assigned to a variable of type lhsType.
     * @param lhsType Type to compare against.
     * @exception SemanticException if there is a type error.
     */
    void typeCheckElements(Type lhsType) throws SemanticException;
}
