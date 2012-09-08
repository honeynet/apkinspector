package polyglot.ast;

/**
 * A <code>Case</code> is a representation of a Java <code>case</code>
 * statement.  It can only be contained in a <code>Switch</code>.
 */
public interface Case extends SwitchElement
{
    /**
     * Get the case label.  This must should a constant expression.
     * The case label is null for the <code>default</code> case.
     */
    Expr expr();

    /**
     * Set the case label.  This must should a constant expression,
     * or null.
     */
    Case expr(Expr expr);

    /** Returns true iff this is the default case. */
    boolean isDefault();

    /**
     * Returns the value of the case label.  This value is only valid
     * after type-checking.
     */
    long value();

    /**
     * Set the value of the case label.
     */
    Case value(long value);
}
