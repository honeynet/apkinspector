package polyglot.ast;

import polyglot.util.Enum;

/** 
 * A <code>FloatLit</code> represents a literal in java of type
 * <code>float</code> or <code>double</code>.
 */
public interface FloatLit extends Lit 
{    
    /** Integer literal kinds: float (e.g., 0.0F) or double (e.g., 0.0). */
    public static class Kind extends Enum {
        public Kind(String name) { super(name); }
    }

    public static final Kind FLOAT = new Kind("float");
    public static final Kind DOUBLE = new Kind("double");

    /** The kind of literal: FLOAT or DOUBLE. */
    Kind kind();

    /** Set the kind of literal: FLOAT or DOUBLE. */
    FloatLit kind(Kind kind);

    /** The literal's value. */
    double value();

    /** Set the literal's value. */
    FloatLit value(double value);
}
