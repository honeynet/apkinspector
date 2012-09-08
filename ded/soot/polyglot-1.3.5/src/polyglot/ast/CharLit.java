package polyglot.ast;

/** 
 * An <code>CharLit</code> represents a literal in java of
 * <code>char</code> type.
 */
public interface CharLit extends NumLit
{    
    /**
     * The literal's value.
     */
    char value();

    /**
     * Set the literal's value.
     */
    CharLit value(char value);
}
