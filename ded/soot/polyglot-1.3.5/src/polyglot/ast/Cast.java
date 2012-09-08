package polyglot.ast;

/**
 * A <code>Cast</code> is an immutable representation of a casting
 * operation.  It consists of an <code>Expr</code> being cast and a
 * <code>TypeNode</code> being cast to.
 */ 
public interface Cast extends Expr
{
    /**
     * The type to cast to.
     */
    TypeNode castType();

    /**
     * Set the type to cast to.
     */
    Cast castType(TypeNode castType);

    /**
     * The expression to cast.
     */
    Expr expr();

    /**
     * Set the expression to cast.
     */
    Cast expr(Expr expr);
}
