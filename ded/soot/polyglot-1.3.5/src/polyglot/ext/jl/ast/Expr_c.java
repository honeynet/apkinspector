package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

/**
 * An <code>Expr</code> represents any Java expression.  All expressions
 * must be subtypes of Expr.
 */
public abstract class Expr_c extends Term_c implements Expr
{
    protected Type type;

    public Expr_c(Position pos) {
	super(pos);
    }

    /**
     * Get the type of the expression.  This may return an
     * <code>UnknownType</code> before type-checking, but should return the
     * correct type after type-checking.
     */
    public Type type() {
	return this.type;
    }

    /** Set the type of the expression. */
    public Expr type(Type type) {
        if (type == this.type) return this;
	Expr_c n = (Expr_c) copy();
	n.type = type;
	return n;
    }

    public void dump(CodeWriter w) {
        super.dump(w);

	if (type != null) {
	    w.allowBreak(4, " ");
	    w.begin(0);
	    w.write("(type " + type + ")");
	    w.end();
	}
    }

    /** Get the precedence of the expression. */
    public Precedence precedence() {
	return Precedence.UNKNOWN;
    }

    public boolean isConstant() {
        return false;
    }

    public Object constantValue() {
        return null;
    }

    public String stringValue() {
        return (String) constantValue();
    }

    public boolean booleanValue() {
        return ((Boolean) constantValue()).booleanValue();
    }

    public byte byteValue() {
        return ((Byte) constantValue()).byteValue();
    }

    public short shortValue() {
        return ((Short) constantValue()).shortValue();
    }

    public char charValue() {
        return ((Character) constantValue()).charValue();
    }

    public int intValue() {
        return ((Integer) constantValue()).intValue();
    }

    public long longValue() {
        return ((Long) constantValue()).longValue();
    }

    public float floatValue() {
        return ((Float) constantValue()).floatValue();
    }

    public double doubleValue() {
        return ((Double) constantValue()).doubleValue();
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        return type(tb.typeSystem().unknownType(position()));
    }

    /**
     * Correctly parenthesize the subexpression <code>expr<code> given
     * the its precendence and the precedence of the current expression.
     *
     * If the sub-expression has the same precedence as this expression
     * we do not parenthesize.
     *
     * @param expr The subexpression.
     * (right-) associative operator.
     * @param w The output writer.
     * @param pp The pretty printer.
     */
    public void printSubExpr(Expr expr, CodeWriter w, PrettyPrinter pp) {
        printSubExpr(expr, true, w, pp);
    }

    /**
     * Correctly parenthesize the subexpression <code>expr<code> given
     * the its precendence and the precedence of the current expression.
     *
     * If the sub-expression has the same precedence as this expression
     * we parenthesize if the sub-expression does not associate; e.g.,
     * we parenthesis the right sub-expression of a left-associative
     * operator.
     *
     * @param expr The subexpression.
     * @param associative Whether expr is the left (right) child of a left-
     * (right-) associative operator.
     * @param w The output writer.
     * @param pp The pretty printer.
     */
    public void printSubExpr(Expr expr, boolean associative,
                             CodeWriter w, PrettyPrinter pp) {
        if (! associative && precedence().equals(expr.precedence()) ||
	    precedence().isTighter(expr.precedence())) {
	    w.write("(");
            printBlock(expr, w, pp);
	    w.write( ")");
	}
        else {
            printBlock(expr, w, pp);
        }
    }
}
