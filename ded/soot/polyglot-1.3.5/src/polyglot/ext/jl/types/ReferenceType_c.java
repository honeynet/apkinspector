package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>ReferenceType</code> represents a reference type --
 * a type on which contains methods and fields and is a subtype of
 * Object.
 */
public abstract class ReferenceType_c extends Type_c implements ReferenceType
{
    protected ReferenceType_c() {
	super();
    }

    public ReferenceType_c(TypeSystem ts) {
	this(ts, null);
    }

    public ReferenceType_c(TypeSystem ts, Position pos) {
	super(ts, pos);
    }

    public boolean isReference() { return true; }
    public ReferenceType toReference() { return this; }

    /**
     * Returns a list of MethodInstances for all the methods declared in this.
     * It does not return methods declared in supertypes.
     */
    public abstract List methods();

    /**
     * Returns a list of FieldInstances for all the fields declared in this.
     * It does not return fields declared in supertypes.
     */
    public abstract List fields();

    /** 
     * Returns the supertype of this class.  For every class except Object,
     * this is non-null.
     */
    public abstract Type superType();

    /**
     * Returns a list of the types of this class's interfaces.
     */
    public abstract List interfaces();

    /** Return true if t has a method mi */
    public final boolean hasMethod(MethodInstance mi) {
        return ts.hasMethod(this, mi);
    }

    /** Return true if t has a method mi */
    public boolean hasMethodImpl(MethodInstance mi) {
        for (Iterator j = methods().iterator(); j.hasNext(); ) {
            MethodInstance mj = (MethodInstance) j.next();

            if (ts.isSameMethod(mi, mj)) {
                return true;
            }
        }

        return false;
    }

    public boolean descendsFromImpl(Type ancestor) {
        if (! ancestor.isCanonical()) {
            return false;
        }

        if (ancestor.isNull()) {
            return false;
        }

        if (ts.equals(this, ancestor)) {
            return false;
        }

        if (! ancestor.isReference()) {
            return false;
        }

        if (ts.equals(ancestor, ts.Object())) {
            return true;
        }

        // Next check interfaces.
        for (Iterator i = interfaces().iterator(); i.hasNext(); ) {
            Type parentType = (Type) i.next();

            if (ts.isSubtype(parentType, ancestor)) {
                return true;
            }
        }

        return false;
    }

    public boolean isImplicitCastValidImpl(Type toType) {
        return ts.isSubtype(this, toType);
    }

    public List methodsNamed(String name) {
        List l = new LinkedList();

        for (Iterator i = methods().iterator(); i.hasNext(); ) {
            MethodInstance mi = (MethodInstance) i.next();
            if (mi.name().equals(name)) {
                l.add(mi);
            }
        }

        return l;
    }

    public List methods(String name, List argTypes) {
        List l = new LinkedList();

        for (Iterator i = methodsNamed(name).iterator(); i.hasNext(); ) {
            MethodInstance mi = (MethodInstance) i.next();
            if (mi.hasFormals(argTypes)) {
                l.add(mi);
            }
        }

        return l;
    }

    /**
     * Requires: all type arguments are canonical.  ToType is not a NullType.
     *
     * Returns true iff a cast from this to toType is valid; in other
     * words, some non-null members of this are also members of toType.
     **/
    public boolean isCastValidImpl(Type toType) {
        if (! toType.isReference()) return false;
        return ts.isSubtype(this, toType) || ts.isSubtype(toType, this);
    }
}
