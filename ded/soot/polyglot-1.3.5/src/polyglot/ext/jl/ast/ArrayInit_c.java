package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.main.Options;
import java.util.*;

/**
 * An <code>ArrayInit</code> is an immutable representation of
 * an array initializer, such as { 3, 1, { 4, 1, 5 } }.  Note that
 * the elements of these array may be expressions of any type (e.g.,
 * <code>Call</code>).
 */
public class ArrayInit_c extends Expr_c implements ArrayInit
{
    protected List elements;

    public ArrayInit_c(Position pos, List elements) {
	super(pos);
	this.elements = TypedList.copyAndCheck(elements, Expr.class, true);
    }

    /** Get the elements of the initializer. */
    public List elements() {
	return this.elements;
    }

    /** Set the elements of the initializer. */
    public ArrayInit elements(List elements) {
	ArrayInit_c n = (ArrayInit_c) copy();
	n.elements = TypedList.copyAndCheck(elements, Expr.class, true);
	return n;
    }

    /** Reconstruct the initializer. */
    protected ArrayInit_c reconstruct(List elements) {
	if (! CollectionUtil.equals(elements, this.elements)) {
	    ArrayInit_c n = (ArrayInit_c) copy();
	    n.elements = TypedList.copyAndCheck(elements, Expr.class, true);
	    return n;
	}

	return this;
    }

    /** Visit the children of the initializer. */
    public Node visitChildren(NodeVisitor v) {
	List elements = visitList(this.elements, v);
	return reconstruct(elements);
    }

    /** Type check the initializer. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();

	Type type = null;

	for (Iterator i = elements.iterator(); i.hasNext(); ) {
	    Expr e = (Expr) i.next();

	    if (type == null) {
	        type = e.type();
	    }
	    else {
	        type = ts.leastCommonAncestor(type, e.type());
	    }
	}

	if (type == null) {
	    return type(ts.Null());
	}
	else {
	    return type(ts.arrayOf(type));
	}
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        if (elements.isEmpty()) {
            return child.type();
        }

        Type t = av.toType();

        if (! t.isArray()) {
            throw new InternalCompilerError("Type of array initializer must " +
                                            "be an array.", position());
        }

        t = t.toArray().base();

        TypeSystem ts = av.typeSystem();

	for (Iterator i = elements.iterator(); i.hasNext(); ) {
	    Expr e = (Expr) i.next();

            if (e == child) {
                if (ts.numericConversionValid(t, e.constantValue())) {
                    return child.type();
                }
                else {
                    return t;
                }
            }
        }

        return child.type();
    }

    public void typeCheckElements(Type lhsType) throws SemanticException {
        TypeSystem ts = lhsType.typeSystem();

        if (! lhsType.isArray()) {
          throw new SemanticException("Cannot initialize " + lhsType +
                                      " with " + type + ".", position());
        }

        // Check if we can assign each individual element.
        Type t = lhsType.toArray().base();

        for (Iterator i = elements.iterator(); i.hasNext(); ) {
            Expr e = (Expr) i.next();
            Type s = e.type();

            if (e instanceof ArrayInit) {
                ((ArrayInit) e).typeCheckElements(t);
                continue;
            }

            if (! ts.isImplicitCastValid(s, t) &&
                ! ts.equals(s, t) &&
                ! ts.numericConversionValid(t, e.constantValue())) {
                throw new SemanticException("Cannot assign " + s +
                                            " to " + t + ".", e.position());
            }
        }
    }

    public String toString() {
	return "{ ... }";
    }

    /** Write the initializer to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
  	w.write("{ ");

	for (Iterator i = elements.iterator(); i.hasNext(); ) {
	    Expr e = (Expr) i.next();
	    print(e, w, tr);

	    if (i.hasNext()) {
		w.write(", ");
	    }
	}

	w.write(" }");
    }

    public Term entry() {
        return listEntry(elements, this);
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFGList(elements, this);
        return succs;
    }
}
