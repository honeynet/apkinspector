package polyglot.ast;

import polyglot.util.Enum;

/**
 * A <code>Unary</code> represents a Java unary expression, an
 * immutable pair of an expression and an an operator.
 */
public interface Unary extends Expr 
{
    /** Unary expression operator. */
    public static class Operator extends Enum {
        protected boolean prefix;
        protected String name;
        
        public Operator(String name, boolean prefix) {
            super(name + (prefix ? "" : "post"));
            this.name = name;
            this.prefix = prefix;
        }
        
        /** Returns true of the operator is a prefix operator, false if
         * postfix. */
        public boolean isPrefix() { return prefix; }
        
        public String toString() { return name; }
    }
    
    public static final Operator BIT_NOT  = new Operator("~", true);
    public static final Operator NEG      = new Operator("-", true);
    public static final Operator POST_INC = new Operator("++", false);
    public static final Operator POST_DEC = new Operator("--", false);
    public static final Operator PRE_INC  = new Operator("++", true);
    public static final Operator PRE_DEC  = new Operator("--", true);
    public static final Operator POS      = new Operator("+", true);
    public static final Operator NOT      = new Operator("!", true);

    /** The sub-expression on that to apply the operator. */
    Expr expr();
    /** Set the sub-expression on that to apply the operator. */
    Unary expr(Expr e);

    /** The operator to apply on the sub-expression. */
    Operator operator();
    /** Set the operator to apply on the sub-expression. */
    Unary operator(Operator o);
}
