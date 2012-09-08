package polyglot.ast;

/**
 * A Java language <code>do</code> statement. 
 * It contains a statement to be executed and an expression to be tested 
 * indicating whether to reexecute the statement.
 */ 
public interface Do extends Loop 
{
    /** Set the body of the do statement. */
    Do body(Stmt body);

    /** Set the condition of the do statement. */
    Do cond(Expr cond);
}
