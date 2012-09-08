package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.visit.*;
import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>Conditional</code> is a representation of a Java ternary
 * expression.  That is, <code>(cond ? consequent : alternative)</code>.
 */
public class Conditional_c extends Expr_c implements Conditional
{
    protected Expr cond;
    protected Expr consequent;
    protected Expr alternative;

    public Conditional_c(Position pos, Expr cond, Expr consequent, Expr alternative) {
	super(pos);
	this.cond = cond;
	this.consequent = consequent;
	this.alternative = alternative;
    }

    /** Get the precedence of the expression. */
    public Precedence precedence() { 
	return Precedence.CONDITIONAL;
    }

    /** Get the conditional of the expression. */
    public Expr cond() {
	return this.cond;
    }

    /** Set the conditional of the expression. */
    public Conditional cond(Expr cond) {
	Conditional_c n = (Conditional_c) copy();
	n.cond = cond;
	return n;
    }

    /** Get the consequent of the expression. */
    public Expr consequent() {
	return this.consequent;
    }

    /** Set the consequent of the expression. */
    public Conditional consequent(Expr consequent) {
	Conditional_c n = (Conditional_c) copy();
	n.consequent = consequent;
	return n;
    }

    /** Get the alternative of the expression. */
    public Expr alternative() {
	return this.alternative;
    }

    /** Set the alternative of the expression. */
    public Conditional alternative(Expr alternative) {
	Conditional_c n = (Conditional_c) copy();
	n.alternative = alternative;
	return n;
    }

    /** Reconstruct the expression. */
    protected Conditional_c reconstruct(Expr cond, Expr consequent, Expr alternative) {
	if (cond != this.cond || consequent != this.consequent || alternative != this.alternative) {
	    Conditional_c n = (Conditional_c) copy();
	    n.cond = cond;
	    n.consequent = consequent;
	    n.alternative = alternative;
	    return n;
	}

	return this;
    }

    /** Visit the children of the expression. */
    public Node visitChildren(NodeVisitor v) {
	Expr cond = (Expr) visitChild(this.cond, v);
	Expr consequent = (Expr) visitChild(this.consequent, v);
	Expr alternative = (Expr) visitChild(this.alternative, v);
	return reconstruct(cond, consequent, alternative);
    }

    /** Type check the expression. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();

	if (! ts.equals(cond.type(), ts.Boolean())) {
	     throw new SemanticException(
		 "Condition of ternary expression must be of type boolean.",
		 cond.position());
	}

	Expr e1 = consequent;
	Expr e2 = alternative;
	Type t1 = e1.type();
	Type t2 = e2.type();

	// From the JLS, section:
	// If the second and third operands have the same type (which may be
	// the null type), then that is the type of the conditional expression.
	if (ts.equals(t1, t2)) {
	    return type(t1);
	}

	// Otherwise, if the second and third operands have numeric type, then
	// there are several cases:
	if (t1.isNumeric() && t2.isNumeric()) {
	    // - If one of the operands is of type byte and the other is of
	    // type short, then the type of the conditional expression is
	    // short.
	    if (t1.isByte() && t2.isShort() || t1.isShort() && t2.isByte()) {
	        return type(ts.Short());
	    }

	    // - If one of the operands is of type T where T is byte, short, or
	    // char, and the other operand is a constant expression of type int
	    // whose value is representable in type T, then the type of the
	    // conditional expression is T.

            if (t1.isIntOrLess() &&
                t2.isInt() &&
                ts.numericConversionValid(t1, e2.constantValue())) {
                return type(t1);
	    }

            if (t2.isIntOrLess() &&
                t1.isInt() &&
                ts.numericConversionValid(t2, e1.constantValue())) {
                return type(t2);
	    }

	    // - Otherwise, binary numeric promotion (�5.6.2) is applied to the
	    // operand types, and the type of the conditional expression is the
	    // promoted type of the second and third operands. Note that binary
	    // numeric promotion performs value set conversion (�5.1.8).
	    return type(ts.promote(t1, t2));
	}

	// If one of the second and third operands is of the null type and the
	// type of the other is a reference type, then the type of the
	// conditional expression is that reference type.
	if (t1.isNull() && t2.isReference()) return type(t2);
	if (t2.isNull() && t1.isReference()) return type(t1);

	// If the second and third operands are of different reference types,
	// then it must be possible to convert one of the types to the other
	// type (call this latter type T) by assignment conversion (�5.2); the
	// type of the conditional expression is T. It is a compile-time error
	// if neither type is assignment compatible with the other type.

	if (t1.isReference() && t2.isReference()) {
	    if (ts.isImplicitCastValid(t1, t2)) {
		return type(t2);
	    }
	    if (ts.isImplicitCastValid(t2, t1)) {
		return type(t1);
	    }
	}

	throw new SemanticException(
	    "Could not find a type for ternary conditional expression.",
	    position());
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        TypeSystem ts = av.typeSystem();

        if (child == cond) {
            return ts.Boolean();
        }

        if (child == consequent || child == alternative) {
            return type();
        }

        return child.type();
    }

    public String toString() {
	return cond + " ? " + consequent + " : " + alternative;
    }

    /** Write the expression to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr)
    {
	printSubExpr(cond, false, w, tr);
	w.write(" ? ");
	printSubExpr(consequent, false, w, tr);
	w.write(" : ");
	printSubExpr(alternative, false, w, tr);
    }

    public Term entry() {
        return cond.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFG(cond, FlowGraph.EDGE_KEY_TRUE, consequent.entry(),
                         FlowGraph.EDGE_KEY_FALSE, alternative.entry());
        v.visitCFG(consequent, this);
        v.visitCFG(alternative, this);

        return succs;
    }
    
    public boolean isConstant() {
	return cond.isConstant() && consequent.isConstant() && alternative.isConstant();
    }

    public Object constantValue() {
        Object cond_ = cond.constantValue();
        Object then_ = consequent.constantValue();
        Object else_ = alternative.constantValue();

        if (cond_ instanceof Boolean && then_ != null && else_ != null) {
            boolean c = ((Boolean) cond_).booleanValue();
            if (c) {
                return then_;
            }
            else {
                return else_;
            }
        }

        return null;
    }
}
