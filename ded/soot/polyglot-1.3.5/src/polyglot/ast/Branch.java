package polyglot.ast;

import polyglot.util.Enum;

/**
 * A <code>Branch</code> is an immutable representation of a branch
 * statment in Java (a break or continue).
 */
public interface Branch extends Stmt
{
    /** Branch kind: either break or continue. */
    public static class Kind extends Enum {
        public Kind(String name) { super(name); }
    }

    public static final Kind BREAK    = new Kind("break");
    public static final Kind CONTINUE = new Kind("continue");

    /**
     * The kind of branch.
     */
    Kind kind();

    /**
     * Set the kind of branch.
     */
    Branch kind(Kind kind);

    /**
     * Target label of the branch.
     */
    String label();

    /**
     * Set the target label of the branch.
     */
    Branch label(String label);
}
