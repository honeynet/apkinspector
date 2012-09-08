package polyglot.ast;

import polyglot.util.Enum;

/**
 * An <code>Assign</code> represents a Java assignment expression.
 */
public interface Assign extends Expr
{
    /** Assignment operator. */
    public static class Operator extends Enum {
	public Operator(String name) { super(name); } 
    }

    public static final Operator ASSIGN         = new Operator("=");
    public static final Operator ADD_ASSIGN     = new Operator("+=");
    public static final Operator SUB_ASSIGN     = new Operator("-=");
    public static final Operator MUL_ASSIGN     = new Operator("*=");
    public static final Operator DIV_ASSIGN     = new Operator("/=");
    public static final Operator MOD_ASSIGN     = new Operator("%=");
    public static final Operator BIT_AND_ASSIGN = new Operator("&=");
    public static final Operator BIT_OR_ASSIGN  = new Operator("|=");
    public static final Operator BIT_XOR_ASSIGN = new Operator("^=");
    public static final Operator SHL_ASSIGN     = new Operator("<<=");
    public static final Operator SHR_ASSIGN     = new Operator(">>=");
    public static final Operator USHR_ASSIGN    = new Operator(">>>=");

    /**
     * Left child (target) of the assignment.
     * The target must be a Variable, but this is not enforced
     * statically to keep Polyglot backward compatible.
     */
    Expr left();

    /**
     * Set the left child (target) of the assignment.
     * The target must be a Variable, but this is not enforced
     * statically to keep Polyglot backward compatible.
     */
    Assign left(Expr left);

    /**
     * The assignment's operator.
     */
    Operator operator();

    /**
     * Set the assignment's operator.
     */
    Assign operator(Operator op);

    /**
     * Right child (source) of the assignment.
     */
    Expr right();

    /**
     * Set the right child (source) of the assignment.
     */
    Assign right(Expr right);
    
    boolean throwsArithmeticException();
}
