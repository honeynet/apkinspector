package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.types.Package;
import java.io.*;

/**
 * An unknown type.  This is used as a place-holder until types are
 * disambiguated.
 */
public class UnknownType_c extends Type_c implements UnknownType
{
    /** Used for deserializing types. */
    protected UnknownType_c() { }
    
    /** Creates a new type in the given a TypeSystem. */
    public UnknownType_c(TypeSystem ts) {
        super(ts);
    }

    public boolean isCanonical() {
        return false;
    }

    public String translate(Resolver c) {
	throw new InternalCompilerError("Cannot translate an unknown type.");
    }

    public String toString() {
	return "<unknown>";
    }
}
