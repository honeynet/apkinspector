package polyglot.ast;

/**
 * A <code>Conditional</code> is a representation of a Java ternary
 * expression.  That is, <code>(cond ? consequent : alternative)</code>.
 */
public interface Conditional extends Expr {
    /** Get the condition to test. */
    Expr cond();
    /** Set the condition to test. */
    Conditional cond(Expr cond);

    /** Get the expression to evaluate when the condition is true. */
    Expr consequent();
    /** Set the expression to evaluate when the condition is true. */
    Conditional consequent(Expr consequent);

    /** Get the expression to evaluate when the condition is false. */
    Expr alternative();
    /** Set the expression to evaluate when the condition is false. */
    Conditional alternative(Expr alternative);
}
