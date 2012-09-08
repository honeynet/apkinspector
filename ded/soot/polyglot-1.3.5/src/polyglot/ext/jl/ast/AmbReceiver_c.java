package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

/**
 * An <code>AmbReceiver</code> is an ambiguous AST node composed of
 * dot-separated list of identifiers that must resolve to a receiver.
 */
public class AmbReceiver_c extends AmbPrefix_c implements AmbReceiver
{
    protected Type type;

    public AmbReceiver_c(Position pos, Prefix prefix, String name) {
	super(pos, prefix, name);
    }

    public Type type() {
            return this.type;
    }

    public AmbReceiver type(Type type) {
            AmbReceiver_c n = (AmbReceiver_c) copy();
            n.type = type;
            return n;
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        return type(tb.typeSystem().unknownType(position()));
    }

    /** Disambiguate the receiver. */
    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
	Node n = super.disambiguate(ar);

	if (n instanceof Receiver) {
	    return n;
	}

	throw new SemanticException("Could not find type, field, or " +
	    "local variable \"" + 
            (prefix == null ? name : prefix.toString() + "." + name) +
            "\".", position());
    }
}
