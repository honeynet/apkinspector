package polyglot.ast;

/**
 * An immutable representation of a Java language <code>if</code> statement.
 * Contains an expression whose value is tested, a "then" statement 
 * (consequent), and optionally an "else" statement (alternate).
 */
public interface If extends CompoundStmt 
{
    /** Get the if's condition. */
    Expr cond();
    /** Set the if's condition. */
    If cond(Expr cond);

    /** Get the if's then clause. */
    Stmt consequent();
    /** Set the if's then clause. */
    If consequent(Stmt consequent);

    /** Get the if's else clause, or null. */
    Stmt alternative();
    /** Set the if's else clause. */
    If alternative(Stmt alternative);
}
