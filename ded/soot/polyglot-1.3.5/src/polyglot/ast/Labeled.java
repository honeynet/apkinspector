package polyglot.ast;

/**
 * Am immutable representation of a Java statement with a label.  A labeled
 * statement contains the statement being labeled and a string label.
 */
public interface Labeled extends CompoundStmt 
{
    /** The label. */
    String label();
    /** Set the label. */
    Labeled label(String label);

    /** The statement to label. */
    Stmt statement();
    /** Set the statement to label. */
    Labeled statement(Stmt statement);
}
