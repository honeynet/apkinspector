package polyglot.ext.coffer.ast;

import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/** An implementation of the <code>TrackedTypeNode</code> interface,
 * a type node for a class instantiated with a key.
 */
public class TrackedTypeNode_c extends TypeNode_c implements TrackedTypeNode
{
    protected TypeNode base;
    protected KeyNode key;

    public TrackedTypeNode_c(Position pos, KeyNode key, TypeNode base) {
	super(pos);
        this.key = key;
	this.base = base;
    }

    public TypeNode base() {
	return this.base;
    }

    public TrackedTypeNode base(TypeNode base) {
	TrackedTypeNode_c n = (TrackedTypeNode_c) copy();
	n.base = base;
	return n;
    }

    public KeyNode key() {
	return this.key;
    }

    public TrackedTypeNode key(KeyNode key) {
	TrackedTypeNode_c n = (TrackedTypeNode_c) copy();
	n.key = key;
	return n;
    }

    protected TrackedTypeNode_c reconstruct(TypeNode base, KeyNode key) {
	if (base != this.base || key != this.key) {
	    TrackedTypeNode_c n = (TrackedTypeNode_c) copy();
	    n.base = base;
	    n.key = key;
	    return n;
	}

	return this;
    }

    public Node visitChildren(NodeVisitor v) {
	TypeNode base = (TypeNode) visitChild(this.base, v);
	KeyNode key = (KeyNode) visitChild(this.key, v);
	return reconstruct(base, key);
    }

    public Node disambiguate(AmbiguityRemover sc) throws SemanticException {
        CofferTypeSystem ts = (CofferTypeSystem) sc.typeSystem();
	Type b = (Type) base.type();

	if (! b.isCanonical()) {
	    throw new SemanticException(
		"Cannot instantiate from a non-canonical type " + b);
	}

        if (! (b instanceof CofferClassType)) {
	    throw new SemanticException(
		"Cannot instantiate from a non-polymorphic type " + b);
	}

	CofferClassType t = (CofferClassType) b;

        Key key = this.key.key();

        if (! key.isCanonical()) {
	    throw new SemanticException(
		"Cannot instantiate from a non-canonical key " + key);
	}

        Key formal = t.key();
        Map subst = new HashMap();
        subst.put(formal, key);

	return sc.nodeFactory().CanonicalTypeNode(position(),
                                                  ts.subst(t, subst));
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot type check ambiguous node " + this + ".");
    }

    public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot exception check ambiguous node " + this + ".");
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("tracked(");
        w.write(key.toString());
        w.write(") ");
        print(base, w, tr);
    }

    public void translate(CodeWriter w, Translator tr) {
	throw new InternalCompilerError(position(),
	    "Cannot translate ambiguous node " + this + ".");
    }

    public String toString() {
        return "tracked(" + key + ") " + base;
    }
}
