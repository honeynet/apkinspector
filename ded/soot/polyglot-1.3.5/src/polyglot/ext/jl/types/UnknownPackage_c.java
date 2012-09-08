package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.types.Package;
import java.io.*;

/**
 * An unknown type.  This is used as a place-holder until types are
 * disambiguated.
 */
public class UnknownPackage_c extends Package_c implements UnknownPackage
{
    /** Used for deserializing types. */
    protected UnknownPackage_c() { }
    
    /** Creates a new type in the given a TypeSystem. */
    public UnknownPackage_c(TypeSystem ts) {
        super(ts);
    }

    public boolean isCanonical() {
        return false;
    }

    public String translate(Resolver c) {
	throw new InternalCompilerError("Cannot translate an unknown package.");
    }

    public String toString() {
	return "<unknown>";
    }
}
