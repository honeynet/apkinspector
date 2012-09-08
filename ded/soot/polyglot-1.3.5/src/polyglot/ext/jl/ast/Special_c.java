package polyglot.ext.jl.ast;

import java.util.List;

import polyglot.main.*;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;

/**
 * A <code>Special</code> is an immutable representation of a
 * reference to <code>this</code> or <code>super</code in Java.  This
 * reference can be optionally qualified with a type such as 
 * <code>Foo.this</code>.
 */
public class Special_c extends Expr_c implements Special
{
    protected Special.Kind kind;
    protected TypeNode qualifier;

    public Special_c(Position pos, Special.Kind kind, TypeNode qualifier) {
	super(pos);
	this.kind = kind;
	this.qualifier = qualifier;
    }

    /** Get the precedence of the expression. */
    public Precedence precedence() {
	return Precedence.LITERAL;
    }

    /** Get the kind of the special expression, either this or super. */
    public Special.Kind kind() {
	return this.kind;
    }

    /** Set the kind of the special expression, either this or super. */
    public Special kind(Special.Kind kind) {
	Special_c n = (Special_c) copy();
	n.kind = kind;
	return n;
    }

    /** Get the qualifier of the special expression. */
    public TypeNode qualifier() {
	return this.qualifier;
    }

    /** Set the qualifier of the special expression. */
    public Special qualifier(TypeNode qualifier) {
	Special_c n = (Special_c) copy();
	n.qualifier = qualifier;
	return n;
    }

    /** Reconstruct the expression. */
    protected Special_c reconstruct(TypeNode qualifier) {
	if (qualifier != this.qualifier) {
	    Special_c n = (Special_c) copy();
	    n.qualifier = qualifier;
	    return n;
	}

	return this;
    }

    /** Visit the children of the expression. */
    public Node visitChildren(NodeVisitor v) {
	TypeNode qualifier = (TypeNode) visitChild(this.qualifier, v);
	return reconstruct(qualifier);
    }

    /** Type check the expression. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();
        Context c = tc.context();

        ClassType t;

        if (qualifier == null) {
            // an unqualified "this" or "super"
            t = c.currentClass();
        }
        else {    
	    if (! qualifier.type().isClass()) {
		throw new SemanticException("Qualified " + kind +
		    " expression must be of a class type",
		    qualifier.position());
	    }

            t = qualifier.type().toClass();

            if (!c.currentClass().hasEnclosingInstance(t)) {
                throw new SemanticException("The nested class \"" + 
                            c.currentClass() + "\" does not have " +
                            "an enclosing instance of type \"" +
                            t + "\".", qualifier.position());
            }
	}

        if (c.inStaticContext() && ts.equals(t, c.currentClass())) {
            // trying to access "this" or "super" from a static context.
            throw new SemanticException("Cannot access a non-static " +
                "field or method, or refer to \"this\" or \"super\" " + 
                "from a static context.",  this.position());
        }

	if (kind == THIS) {
	    return type(t);
	}
	else if (kind == SUPER) {
	    return type(t.superType());
	}
        return this;
    }

    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry() {
        return this;
    }

    /**
     * Visit this term in evaluation order.
     */
    public List acceptCFG(CFGBuilder v, List succs) {
        return succs;
    }

    public String toString() {
	return (qualifier != null ? qualifier + "." : "") + kind;
    }

    /** Write the expression to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
      if (qualifier != null) {
        print(qualifier, w, tr);
        w.write(".");
      }
      
      w.write(kind.toString());
    }

    public void dump(CodeWriter w) {
      super.dump(w);

      if (kind != null) {
        w.allowBreak(4, " ");
        w.begin(0);
        w.write("(kind " + kind + ")");
        w.end();
      }
    }
}
