package polyglot.ast;

/**
 * An <code>Eval</code> is a statement that evaluates an expression then
 * discards the result.
 */
public interface Eval extends ForInit, ForUpdate
{
    /** Expression to evaluate. */
    Expr expr();
    /** Set the expression to evaluate. */
    Eval expr(Expr expr);
}
