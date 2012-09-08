package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>Return</code> represents a <code>return</code> statement in Java.
 * It may or may not return a value.  If not <code>expr()</code> should return
 * null.
 */
public class Return_c extends Stmt_c implements Return
{
    protected Expr expr;

    public Return_c(Position pos, Expr expr) {
	super(pos);
	this.expr = expr;
    }

    /** Get the expression to return, or null. */
    public Expr expr() {
	return this.expr;
    }

    /** Set the expression to return, or null. */
    public Return expr(Expr expr) {
	Return_c n = (Return_c) copy();
	n.expr = expr;
	return n;
    }

    /** Reconstruct the statement. */
    protected Return_c reconstruct(Expr expr) {
	if (expr != this.expr) {
	    Return_c n = (Return_c) copy();
	    n.expr = expr;
	    return n;
	}

	return this;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Expr expr = (Expr) visitChild(this.expr, v);
	return reconstruct(expr);
    }

    /** Type check the statement. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();
	Context c = tc.context();

	CodeInstance ci = c.currentCode();

	if (ci instanceof InitializerInstance) {
	    throw new SemanticException(
		"Cannot return from an initializer block.", position());
	}

	if (ci instanceof ConstructorInstance) {
	    if (expr != null) {
		throw new SemanticException(
		    "Cannot return a value from " + ci + ".",
		    position());
	    }

	    return this;
	}

	if (ci instanceof MethodInstance) {
	    MethodInstance mi = (MethodInstance) ci;

	    if (mi.returnType().isVoid()) {
                if (expr != null) {
                    throw new SemanticException("Cannot return a value from " +
                        mi + ".", position());
                }
                else {
                    return this;
                }
	    }
            else if (expr == null) {
                throw new SemanticException("Must return a value from " +
                    mi + ".", position());
            }

	    if (ts.isImplicitCastValid(expr.type(), mi.returnType())) {
	        return this;
	    }

            if (ts.numericConversionValid(mi.returnType(),
                                          expr.constantValue())) {
                return this;
            }

	    throw new SemanticException("Cannot return expression of type " +
		expr.type() + " from " + mi + ".", expr.position());
	}

	throw new InternalCompilerError("Unrecognized code type.");
    }
  
    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        if (child == expr) {
            Context c = av.context();
            CodeInstance ci = c.currentCode();

            if (ci instanceof MethodInstance) {
                MethodInstance mi = (MethodInstance) ci;

                TypeSystem ts = av.typeSystem();

                // If expr is an integral constant, we can relax the expected
                // type to the type of the constant.
                if (ts.numericConversionValid(mi.returnType(),
                                              child.constantValue())) {
                    return child.type();
                }
                else {
                    return mi.returnType();
                }
            }
        }

        return child.type();
    }

    public String toString() {
	return "return" + (expr != null ? " " + expr : "") + ";";
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write("return") ;
	if (expr != null) {
	    w.write(" ");
	    print(expr, w, tr);
	}
	w.write(";");
    }

    public Term entry() {
        if (expr != null) return expr.entry();
        return this;
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        if (expr != null) {
            v.visitCFG(expr, this);
        }

        v.visitReturn(this);
        return Collections.EMPTY_LIST;
    }
}
