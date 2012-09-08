package polyglot.ext.jl.types;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import polyglot.main.Options;
import polyglot.visit.Translator;
import polyglot.types.ClassType;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.ReferenceType;
import polyglot.types.Resolver;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * A <code>ClassType</code> represents a class -- either loaded from a
 * classpath, parsed from a source file, or obtained from other source.
 */
public abstract class ClassType_c extends ReferenceType_c implements ClassType
{
    /** Used for deserializing types. */
    protected ClassType_c() { }

    public ClassType_c(TypeSystem ts) {
	this(ts, null);
    }

    public ClassType_c(TypeSystem ts, Position pos) {
	super(ts, pos);
    }

    /** Get the class's kind. */
    public abstract Kind kind();

    /** Get the class's outer class, or null if a top-level class. */
    public abstract ClassType outer();

    /** Get the short name of the class, if possible. */ 
    public abstract String name();

    /** Get the container class if a member class. */
    public ReferenceType container() {
        if (! isMember())
            throw new InternalCompilerError("Non-member classes cannot have container classes.");
        if (outer() == null)
            throw new InternalCompilerError("Nested classes must have outer classes.");
        return outer();
    }

    /** Get the full name of the class, if possible. */
    public String fullName() {
        String name;
        if (isAnonymous()) {
            if (superType() != null) {
                name = "<anon subtype of " + superType().toString() + ">";
            }
            else {
                name = "<anon subtype of unknown>";
            }
        }
        else {
            name = name();
        } 
        if (isTopLevel() && package_() != null) {
            return package_().fullName() + "." + name;
        }
        else if (isMember() && container() instanceof Named) {
            return ((Named) container()).fullName() + "." + name;
        }
        else {
            return name;
        }
    }

    public boolean isTopLevel() { return kind() == TOP_LEVEL; }
    public boolean isMember() { return kind() == MEMBER; }
    public boolean isLocal() { return kind() == LOCAL; }
    public boolean isAnonymous() { return kind() == ANONYMOUS; }

    /**
    * @deprecated Was incorrectly defined. Use isNested for nested classes, 
    *          and isInnerClass for inner classes.
    */
    public final boolean isInner() {
        return isNested();
    }

    public boolean isNested() {
        // Implement this way rather than with ! isTopLevel() so that
        // extensions can add more kinds.
        return kind() == MEMBER || kind() == LOCAL || kind() == ANONYMOUS;
    }
    
    public boolean isInnerClass() {
        // it's an inner class if it is not an interface, it is a nested
        // class, and it is not explicitly or implicitly static. 
        return !flags().isInterface() && isNested() && !flags().isStatic() && !inStaticContext();
    }
    
    public boolean isCanonical() { return true; }
    public boolean isClass() { return true; }
    public ClassType toClass() { return this; }

    /** Get the class's package. */
    public abstract Package package_();

    /** Get the class's flags. */
    public abstract Flags flags();

    /** Get the class's constructors. */
    public abstract List constructors();

    /** Get the class's member classes. */
    public abstract List memberClasses();

    /** Get the class's methods. */
    public abstract List methods();

    /** Get the class's fields. */
    public abstract List fields();

    /** Get the class's interfaces. */
    public abstract List interfaces();

    /** Get the class's super type. */
    public abstract Type superType();

    /** Get a field of the class by name. */
    public FieldInstance fieldNamed(String name) {
        for (Iterator i = fields().iterator(); i.hasNext(); ) {
	    FieldInstance fi = (FieldInstance) i.next();
	    if (fi.name().equals(name)) {
	        return fi;
	    }
	}

	return null;
    }

    /** Get a member class of the class by name. */
    public ClassType memberClassNamed(String name) {
        for (Iterator i = memberClasses().iterator(); i.hasNext(); ) {
	    ClassType t = (ClassType) i.next();
	    if (t.name().equals(name)) {
	        return t;
	    }
	}

	return null;
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

        // Check subtype relation for classes.
        if (! flags().isInterface()) {
            if (ts.equals(this, ts.Object())) {
                return false;
            }

            if (superType() == null) {
                return false;
            }

            if (ts.isSubtype(superType(), ancestor)) {
                return true;
            }
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

    public boolean isThrowable() {
        return ts.isSubtype(this, ts.Throwable());
    }

    public boolean isUncheckedException() {
        if (isThrowable()) {
            Collection c = ts.uncheckedExceptions();
                                  
            for (Iterator i = c.iterator(); i.hasNext(); ) {
                Type t = (Type) i.next();

                if (ts.isSubtype(this, t)) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isImplicitCastValidImpl(Type toType) {
        if (! toType.isClass()) return false;
        return ts.isSubtype(this, toType);
    }

    /**
     * Requires: all type arguments are canonical.  ToType is not a NullType.
     *
     * Returns true iff a cast from this to toType is valid; in other
     * words, some non-null members of this are also members of toType.
     **/
    public boolean isCastValidImpl(Type toType) {
	if (! toType.isCanonical()) return false;
	if (! toType.isReference()) return false;

	if (toType.isArray()) {
	    // Ancestor is not an array, but child is.  Check if the array
	    // is a subtype of the ancestor.  This happens when ancestor
	    // is java.lang.Object.
	    return ts.isSubtype(toType, this);
	}

	// Both types should be classes now.
	if (! toType.isClass()) return false;

	// From and to are neither primitive nor an array. They are distinct.
	boolean fromInterface = flags().isInterface();
	boolean toInterface   = toType.toClass().flags().isInterface();
	boolean fromFinal     = flags().isFinal();
	boolean toFinal       = toType.toClass().flags().isFinal();

	// This is taken from Section 5.5 of the JLS.
	if (! fromInterface) {
	    // From is not an interface.
	    if (! toInterface) {
		// Nether from nor to is an interface.
		return ts.isSubtype(this, toType) || ts.isSubtype(toType, this);
	    }

	    if (fromFinal) {
		// From is a final class, and to is an interface
		return ts.isSubtype(this, toType);
	    }

	    // From is a non-final class, and to is an interface.
	    return true;
	}
	else {
	    // From is an interface
	    if (! toInterface && ! toFinal) {
		// To is a non-final class.
		return true;
	    }

	    if (toFinal) {
		// To is a final class.
		return ts.isSubtype(toType, this);
	    }

	    // To and From are both interfaces.
	    return true;
	}
    }

    public final boolean isEnclosed(ClassType maybe_outer) {
        return ts.isEnclosed(this, maybe_outer);
    }

    public final boolean hasEnclosingInstance(ClassType encl) {
        return ts.hasEnclosingInstance(this, encl);
    }

    public String translate(Resolver c) {
        if (isTopLevel()) {
            if (package_() == null) {
                return name();
            }

            // Use the short name if it is unique.
            if (c != null) {
                try {
                    Named x = c.find(name());

                    if (ts.equals(this, x)) {
                        return name();
                    }
                }
                catch (SemanticException e) {
                }
            }

              return package_().translate(c) + "." + name();
        }
        else if (isMember()) {
            // Use only the short name if the outer class is anonymous.
            if (container().toClass().isAnonymous()) {
                return name();
            }

            // Use the short name if it is unique.
            if (c != null) {
                try {
                    Named x = c.find(name());

                    if (ts.equals(this, x)) {
                        return name();
                    }
                }
                catch (SemanticException e) {
                }
            }

            return container().translate(c) + "." + name();
        }
        else if (isLocal()) {
            return name();
        }
        else {
            throw new InternalCompilerError("Cannot translate an anonymous class.");
        }
    }

    public String toString() {
        if (isTopLevel()) {
            if (package_() != null) {
                return package_().toString() + "." + name();
            }

            return name();
        }
        else if (isMember()) {
            return container().toString() + "." + name();
        }
        else if (isLocal()) {
            return name();
        }
        else {
            if (superType() != null) {
                return "<anon subtype of " + superType().toString() + ">";
            }
            else {
                return "<anon subtype of unknown>";
            }
        }
    }

    public boolean isEnclosedImpl(ClassType maybe_outer) {
        if (isTopLevel())
            return false;
        else if (outer() != null)
            return outer().equals(maybe_outer) ||
                  outer().isEnclosed(maybe_outer);
        else
            throw new InternalCompilerError("Non top-level classes " + 
                    "must have outer classes.");
    }

    /** 
     * Return true if an object of the class has
     * an enclosing instance of <code>encl</code>. 
     */
    public boolean hasEnclosingInstanceImpl(ClassType encl) {
        if (this.equals(encl)) {
            // object o is the zeroth lexically enclosing instance of itself. 
            return true;
        }
        
        if (!isInnerClass() || inStaticContext()) {
            // this class is not an inner class, or was declared in a static
            // context; it cannot have an enclosing
            // instance of anything. 
            return false;
        }
        
        // see if the immediately lexically enclosing class has an 
        // appropriate enclosing instance
        return this.outer().hasEnclosingInstance(encl);
    }
}
