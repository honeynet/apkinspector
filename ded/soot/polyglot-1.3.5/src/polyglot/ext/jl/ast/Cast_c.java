package polyglot.ext.jl.ast;

import polyglot.ast.*;

import polyglot.util.*;
import polyglot.visit.*;
import polyglot.types.*;
import polyglot.main.Options;
import java.util.*;

/**
 * A <code>Cast</code> is an immutable representation of a casting
 * operation.  It consists of an <code>Expr</code> being cast and a
 * <code>TypeNode</code> being cast to.
 */ 
public class Cast_c extends Expr_c implements Cast
{
    protected TypeNode castType;
    protected Expr expr;

    public Cast_c(Position pos, TypeNode castType, Expr expr) {
	super(pos);
	this.castType = castType;
	this.expr = expr;
    }

    /** Get the precedence of the expression. */
    public Precedence precedence() {
	return Precedence.CAST;
    }

    /** Get the cast type of the expression. */
    public TypeNode castType() {
	return this.castType;
    }

    /** Set the cast type of the expression. */
    public Cast castType(TypeNode castType) {
	Cast_c n = (Cast_c) copy();
	n.castType = castType;
	return n;
    }

    /** Get the expression being cast. */
    public Expr expr() {
	return this.expr;
    }

    /** Set the expression being cast. */
    public Cast expr(Expr expr) {
	Cast_c n = (Cast_c) copy();
	n.expr = expr;
	return n;
    }

    /** Reconstruct the expression. */
    protected Cast_c reconstruct(TypeNode castType, Expr expr) {
	if (castType != this.castType || expr != this.expr) {
	    Cast_c n = (Cast_c) copy();
	    n.castType = castType;
	    n.expr = expr;
	    return n;
	}

	return this;
    }

    /** Visit the children of the expression. */
    public Node visitChildren(NodeVisitor v) {
	TypeNode castType = (TypeNode) visitChild(this.castType, v);
	Expr expr = (Expr) visitChild(this.expr, v);
	return reconstruct(castType, expr);
    }

    /** Type check the expression. */
    public Node typeCheck(TypeChecker tc) throws SemanticException
    {
        TypeSystem ts = tc.typeSystem();

        if (! ts.isCastValid(expr.type(), castType.type())) {
	    throw new SemanticException("Cannot cast the expression of type \"" 
					+ expr.type() + "\" to type \"" 
					+ castType.type() + "\".",
				        position());
	}

	return type(castType.type());
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        TypeSystem ts = av.typeSystem();

        if (child == expr) {
            if (castType.type().isReference()) {
                return ts.Object();
            }
            else if (castType.type().isNumeric()) {
                return ts.Double();
            }
            else if (castType.type().isBoolean()) {
                return ts.Boolean();
            }
        }

        return child.type();
    }
  
    public String toString() {
	return "(" + castType + ") " + expr;
    }

    /** Write the expression to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr)
    {
	w.begin(0);
	w.write("(");
	print(castType, w, tr);
	w.write(")");
	w.allowBreak(2, " ");
	printSubExpr(expr, w, tr);
	w.end();
    }

    public Term entry() {
        return expr.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFG(expr, this);
        return succs;
    }

    public List throwTypes(TypeSystem ts) {
        if (expr.type().isReference()) {
            return Collections.singletonList(ts.ClassCastException());
        }

        return Collections.EMPTY_LIST;
    }
    
    public boolean isConstant() {
	return expr.isConstant() && castType.type().isPrimitive();
    }
    
    public Object constantValue() {
        Object v = expr.constantValue();

	if (v == null) {
	    return null;
	}
	
        if (v instanceof Boolean) {
            if (castType.type().isBoolean()) return v;
        }

        if (v instanceof String) {
            TypeSystem ts = castType.type().typeSystem();
            if (castType.type().equals(ts.String())) return v;
        }

        if (v instanceof Double) {
            double vv = ((Double) v).doubleValue();

            if (castType.type().isDouble()) return new Double((double) vv);
            if (castType.type().isFloat()) return new Float((float) vv);
            if (castType.type().isLong()) return new Long((long) vv);
            if (castType.type().isInt()) return new Integer((int) vv);
            if (castType.type().isChar()) return new Character((char) vv);
            if (castType.type().isShort()) return new Short((short) vv);
            if (castType.type().isByte()) return new Byte((byte) vv);
        }

        if (v instanceof Float) {
            float vv = ((Float) v).floatValue();

            if (castType.type().isDouble()) return new Double((double) vv);
            if (castType.type().isFloat()) return new Float((float) vv);
            if (castType.type().isLong()) return new Long((long) vv);
            if (castType.type().isInt()) return new Integer((int) vv);
            if (castType.type().isChar()) return new Character((char) vv);
            if (castType.type().isShort()) return new Short((short) vv);
            if (castType.type().isByte()) return new Byte((byte) vv);
        }

        if (v instanceof Number) {
            long vv = ((Number) v).longValue();

            if (castType.type().isDouble()) return new Double((double) vv);
            if (castType.type().isFloat()) return new Float((float) vv);
            if (castType.type().isLong()) return new Long((long) vv);
            if (castType.type().isInt()) return new Integer((int) vv);
            if (castType.type().isChar()) return new Character((char) vv);
            if (castType.type().isShort()) return new Short((short) vv);
            if (castType.type().isByte()) return new Byte((byte) vv);
        }

        if (v instanceof Character) {
            char vv = ((Character) v).charValue();

            if (castType.type().isDouble()) return new Double((double) vv);
            if (castType.type().isFloat()) return new Float((float) vv);
            if (castType.type().isLong()) return new Long((long) vv);
            if (castType.type().isInt()) return new Integer((int) vv);
            if (castType.type().isChar()) return new Character((char) vv);
            if (castType.type().isShort()) return new Short((short) vv);
            if (castType.type().isByte()) return new Byte((byte) vv);
        }

        // not a constant
        return null;
    }
}
