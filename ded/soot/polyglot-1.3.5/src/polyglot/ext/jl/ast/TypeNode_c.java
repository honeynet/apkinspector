package polyglot.ext.jl.ast;

import polyglot.ast.*;

import polyglot.util.*;
import polyglot.types.*;
import polyglot.visit.*;

/**
 * A <code>TypeNode</code> is the syntactic representation of a 
 * <code>Type</code> within the abstract syntax tree.
 */
public abstract class TypeNode_c extends Node_c implements TypeNode
{
    protected Type type;

    public TypeNode_c(Position pos) {
	super(pos);
    }

    /** Get the short name of the type, or null. */
    public String name() {
        if (type instanceof Named) {
            return ((Named) type).name();
        }
        return null;
    }

    /** Get the type as a qualifier. */
    public Qualifier qualifier() {
        return type();
    }

    /** Get the type this node encapsulates. */
    public Type type() {
	return this.type;
    }

    /** Set the type this node encapsulates. */
    public TypeNode type(Type type) {
	TypeNode_c n = (TypeNode_c) copy();
	n.type = type;
	return n;
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        if (type == null) {
            TypeSystem ts = tb.typeSystem();
            return type(ts.unknownType(position()));
        }
        else {
            return this;
        }
    }

    public String toString() {
	if (type != null) {
	    return type.toString();
	}
	else {
	    return "<unknown type>";
	}
    }

    public abstract void prettyPrint(CodeWriter w, PrettyPrinter tr);
}
