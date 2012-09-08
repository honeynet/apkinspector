package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>Unary</code> represents a Java unary expression, an
 * immutable pair of an expression and an operator.
 */
public class Unary_c extends Expr_c implements Unary
{
    protected Unary.Operator op;
    protected Expr expr;

    public Unary_c(Position pos, Unary.Operator op, Expr expr) {
	super(pos);
	this.op = op;
	this.expr = expr;
    }

    /** Get the precedence of the expression. */
    public Precedence precedence() {
	return Precedence.UNARY;
    }

    /** Get the sub-expression of the expression. */
    public Expr expr() {
	return this.expr;
    }

    /** Set the sub-expression of the expression. */
    public Unary expr(Expr expr) { Unary_c n = (Unary_c) copy(); n.expr = expr;
      return n; }

    /** Get the operator. */
    public Unary.Operator operator() {
	return this.op;
    }

    /** Set the operator. */
    public Unary operator(Unary.Operator op) {
	Unary_c n = (Unary_c) copy();
	n.op = op;
	return n;
    }

    /** Reconstruct the expression. */
    protected Unary_c reconstruct(Expr expr) {
	if (expr != this.expr) {
	    Unary_c n = (Unary_c) copy();
	    n.expr = expr;
	    return n;
	}

	return this;
    }

    /** Visit the children of the expression. */
    public Node visitChildren(NodeVisitor v) {
	Expr expr = (Expr) visitChild(this.expr, v);
	return reconstruct(expr);
    }

    /** Type check the expression. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();

	if (op == POST_INC || op == POST_DEC ||
	    op == PRE_INC || op == PRE_DEC) {

	    if (! expr.type().isNumeric()) {
		throw new SemanticException("Operand of " + op +
		    " operator must be numeric.", expr.position());
	    }

            if (! (expr instanceof Variable)) {
		throw new SemanticException("Operand of " + op +
		    " operator must be a variable.", expr.position());
            }
            
            if (((Variable) expr).flags().isFinal()) {
		throw new SemanticException("Operand of " + op +
		    " operator must be a non-final variable.",
                    expr.position());
            }

	    return type(expr.type());
	}

	if (op == BIT_NOT) {
	    if (! ts.isImplicitCastValid(expr.type(), ts.Long())) {
		throw new SemanticException("Operand of " + op +
		    " operator must be numeric.", expr.position());
	    }

	    return type(ts.promote(expr.type()));
	}

	if (op == NEG || op == POS) {
	    if (! expr.type().isNumeric()) {
		throw new SemanticException("Operand of " + op +
		    " operator must be numeric.", expr.position());
	    }

	    return type(ts.promote(expr.type()));
	}

	if (op == NOT) {
	    if (! expr.type().isBoolean()) {
		throw new SemanticException("Operand of " + op +
		    " operator must be boolean.", expr.position());
	    }

	    return type(expr.type());
	}

	return this;
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        TypeSystem ts = av.typeSystem();

        try {
            if (child == expr) {
                if (op == POST_INC || op == POST_DEC ||
                    op == PRE_INC || op == PRE_DEC) {

                    if (ts.isImplicitCastValid(child.type(), av.toType())) {
                        return ts.promote(child.type());
                    }
                    else {
                        return av.toType();
                    }
                }
                else if (op == NEG || op == POS) {
                    if (ts.isImplicitCastValid(child.type(), av.toType())) {
                        return ts.promote(child.type());
                    }
                    else {
                        return av.toType();
                    }
                }
                else if (op == BIT_NOT) {
                    if (ts.isImplicitCastValid(child.type(), av.toType())) {
                        return ts.promote(child.type());
                    }
                    else {
                        return av.toType();
                    }
                }
                else if (op == NOT) {
                    return ts.Boolean();
                }
            }
        }
        catch (SemanticException e) {
        }

        return child.type();
    }

    /** Check exceptions thrown by the statement. */
    public String toString() {
        if (op == NEG && expr instanceof IntLit && ((IntLit) expr).boundary()) {
            return op.toString() + ((IntLit) expr).positiveToString();
        }
        else if (op.isPrefix()) {
	    return op.toString() + expr.toString();
	}
	else {
	    return expr.toString() + op.toString();
	}
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        if (op == NEG && expr instanceof IntLit && ((IntLit) expr).boundary()) {
	    w.write(op.toString());
            w.write(((IntLit) expr).positiveToString());
        }
        else if (op.isPrefix()) {
	    w.write(op.toString());
	    printSubExpr(expr, false, w, tr);
	}
	else {
	    printSubExpr(expr, false, w, tr);
	    w.write(op.toString());
	}
    }

    public Term entry() {
        return expr.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        if (expr.type().isBoolean()) {
            v.visitCFG(expr, FlowGraph.EDGE_KEY_TRUE, this,
                             FlowGraph.EDGE_KEY_FALSE, this);
        }
        else {
            v.visitCFG(expr, this);
        }
        return succs;
    }
    
    public boolean isConstant() {
	return expr.isConstant();
    }

    public Object constantValue() {
        if (! isConstant()) {
	    return null;
	}
	
	Object v = expr.constantValue();

        if (v instanceof Boolean) {
            boolean vv = ((Boolean) v).booleanValue();
            if (op == NOT) return Boolean.valueOf(!vv);
        }
        if (v instanceof Double) {
            double vv = ((Double) v).doubleValue();
            if (op == POS) return new Double(+vv);
            if (op == NEG) return new Double(-vv);
        }
        if (v instanceof Float) {
            float vv = ((Float) v).floatValue();
            if (op == POS) return new Float(+vv);
            if (op == NEG) return new Float(-vv);
        }
        if (v instanceof Long) {
            long vv = ((Long) v).longValue();
            if (op == BIT_NOT) return new Long(~vv);
            if (op == POS) return new Long(+vv);
            if (op == NEG) return new Long(-vv);
        }
        if (v instanceof Integer) {
            int vv = ((Integer) v).intValue();
            if (op == BIT_NOT) return new Integer(~vv);
            if (op == POS) return new Integer(+vv);
            if (op == NEG) return new Integer(-vv);
        }
        if (v instanceof Character) {
            char vv = ((Character) v).charValue();
            if (op == BIT_NOT) return new Integer(~vv);
            if (op == POS) return new Integer(+vv);
            if (op == NEG) return new Integer(-vv);
        }
        if (v instanceof Short) {
            short vv = ((Short) v).shortValue();
            if (op == BIT_NOT) return new Integer(~vv);
            if (op == POS) return new Integer(+vv);
            if (op == NEG) return new Integer(-vv);
        }
        if (v instanceof Byte) {
            byte vv = ((Byte) v).byteValue();
            if (op == BIT_NOT) return new Integer(~vv);
            if (op == POS) return new Integer(+vv);
            if (op == NEG) return new Integer(-vv);
        }

        // not a constant
        return null;
    }
}
