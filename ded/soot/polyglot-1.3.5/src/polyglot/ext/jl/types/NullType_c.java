package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;

/**
 * A <code>NullType</code> represents the type of the Java keyword
 * <code>null</code>.
 */
public class NullType_c extends Type_c implements NullType
{
    /** Used for deserializing types. */
    protected NullType_c() { }

    public NullType_c(TypeSystem ts) {
	super(ts);
    }
    
    public String translate(Resolver c) {
	throw new InternalCompilerError("Cannot translate a null type.");
    }

    public String toString() {
	return "type(null)";
    }
    
    public boolean equalsImpl(TypeObject t) {
        return t instanceof NullType;
    }

    public int hashCode() {
	return 6060842;
    }

    public boolean isCanonical() { return true; }
    public boolean isNull() { return true; }

    public NullType toNull() { return this; }

    public boolean descendsFromImpl(Type ancestor) {
        if (ancestor.isNull()) return false;
        if (ancestor.isReference()) return true;
        return false;
    }

    public boolean isImplicitCastValidImpl(Type toType) {
        return toType.isNull() || toType.isReference();
    }

    /**
     * Requires: all type arguments are canonical.  ToType is not a NullType.
     *
     * Returns true iff a cast from this to toType is valid; in other
     * words, some non-null members of this are also members of toType.
     **/
    public boolean isCastValidImpl(Type toType) {
        return toType.isNull() || toType.isReference();
    }
}
