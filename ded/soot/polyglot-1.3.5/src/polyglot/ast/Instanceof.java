package polyglot.ast;

/**
 * An <code>Instanceof</code> is an immutable representation of
 * the use of the <code>instanceof</code> operator.
 */
public interface Instanceof extends Expr 
{
    /** Get the expression to check. */
    Expr expr();
    /** Set the expression to check. */
    Instanceof expr(Expr expr);

    /** Get the type to compare against. */
    TypeNode compareType();
    /** Set the type to compare against. */
    Instanceof compareType(TypeNode compareType);
}
