package polyglot.ast;

import polyglot.util.Enum;

/** 
 * An <code>IntLit</code> represents a literal in Java of an integer
 * type.
 */
public interface IntLit extends NumLit 
{
    /** Integer literal kinds: int (e.g., 0) or long (e.g., 0L). */
    public static class Kind extends Enum {
        public Kind(String name) { super(name); }
    }

    public static final Kind INT   = new Kind("int");
    public static final Kind LONG  = new Kind("long");

    /** Get the literal's value. */
    long value();

    /** Set the literal's value. */
    IntLit value(long value);

    /** Get the kind of the literal: INT or LONG. */
    Kind kind();

    /** Set the kind of the literal: INT or LONG. */
    IntLit kind(Kind kind);

    /** Is this a boundary case, i.e., max int or max long + 1? */
    boolean boundary();

    /** Print the string as a positive number. */
    String positiveToString();
}
