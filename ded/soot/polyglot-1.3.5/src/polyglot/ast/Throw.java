package polyglot.ast;

/**
 * A <code>Throw</code> is an immutable representation of a <code>throw</code>
 * statement. Such a statement contains a single <code>Expr</code> which
 * evaluates to the object being thrown.
 */
public interface Throw extends Stmt
{
    /* The expression to throw. */
    Expr expr();

    /* Set the expression to throw. */
    Throw expr(Expr expr);
}
