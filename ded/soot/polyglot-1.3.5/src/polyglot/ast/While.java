package polyglot.ast;

/**
 * An immutable representation of a Java language <code>while</code>
 * statement.  It contains a statement to be executed and an expression
 * to be tested indicating whether to reexecute the statement.
 */ 
public interface While extends Loop 
{
    /** Set the loop condition. */
    While cond(Expr cond);

    /** Set the loop body. */
    While body(Stmt body);
}
