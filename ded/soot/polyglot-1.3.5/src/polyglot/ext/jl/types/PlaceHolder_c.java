package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;

/**
 * A place holder type when serializing the Polylgot type information. 
 * When serializing the type information for some class <code>C</code>, 
 * Placeholders are used to prevent serializing the class type information
 * for classes that <code>C</code> depends on.  
 */
public class PlaceHolder_c implements PlaceHolder
{
    /**
     * The name of the place holder.
     */
    protected String name;

    /** Used for deserializing types. */
    protected PlaceHolder_c() { }
    
    /** Creates a place holder type for the type. */
    public PlaceHolder_c(Type t) {
	if (t.isClass()) {
            name = t.typeSystem().getTransformedClassName(t.toClass());
        }
	else {
	    throw new InternalCompilerError("Cannot serialize " + t + ".");
	}
    }

    /** Restore the placeholder into a proper type. */ 
    public TypeObject resolve(TypeSystem ts) {
        try {
            return ts.systemResolver().find(name);
	} catch (SemanticException se) {
	    throw new InternalCompilerError(se);
	}
    }

    public String translate(Resolver c) {
	throw new InternalCompilerError("Cannot translate place holder type.");
    }

    public String toString() {
	return "PlaceHolder(" + name + ")";
    }
}
