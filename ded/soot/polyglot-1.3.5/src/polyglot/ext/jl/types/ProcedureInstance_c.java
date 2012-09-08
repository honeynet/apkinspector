package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>ProcedureInstance_c</code> contains the type information for a Java
 * procedure (either a method or a constructor).
 */
public abstract class ProcedureInstance_c extends TypeObject_c
                                       implements ProcedureInstance
{
    protected ReferenceType container;
    protected Flags flags;
    protected List formalTypes;
    protected List excTypes;

    /** Used for deserializing types. */
    protected ProcedureInstance_c() { }

    public ProcedureInstance_c(TypeSystem ts, Position pos,
			       ReferenceType container,
			       Flags flags, List formalTypes, List excTypes) {
        super(ts, pos);
	this.container = container;
	this.flags = flags;
	this.formalTypes = TypedList.copyAndCheck(formalTypes, Type.class, true);
	this.excTypes = TypedList.copyAndCheck(excTypes, Type.class, true);
    }

    public ReferenceType container() {
        return container;
    }

    public Flags flags() {
        return flags;
    }

    public List formalTypes() {
        return Collections.unmodifiableList(formalTypes);
    }

    public List throwTypes() {
        return Collections.unmodifiableList(excTypes);
    }

    public int hashCode() {
        return container.hashCode() + flags.hashCode();
    }

    public boolean equalsImpl(TypeObject o) {
        if (o instanceof ProcedureInstance) {
	    ProcedureInstance i = (ProcedureInstance) o;
	    // FIXME: Check excTypes too?
	    return flags.equals(i.flags())
	        && ts.equals(container, i.container())
	        && ts.hasFormals(this, i.formalTypes());
	}

	return false;
    }

    protected boolean listIsCanonical(List l) {
	for (Iterator i = l.iterator(); i.hasNext(); ) {
	    TypeObject o = (TypeObject) i.next();
	    if (! o.isCanonical()) {
		return false;
	    }
	}

	return true;
    }

    public final boolean moreSpecific(ProcedureInstance p) {
        return ts.moreSpecific(this, p);
    }


    /**
     * Returns whether <code>this</code> is <i>more specific</i> than
     * <code>p</code>, where <i>more specific</i> is defined as JLS
     * 15.12.2.2.
     *<p>
     * <b>Note:</b> There is a fair amount of guesswork since the JLS
     * does not include any info regarding Java 1.2, so all inner class
     * rules are found empirically using jikes and javac.
     */
    public boolean moreSpecificImpl(ProcedureInstance p) {
        ProcedureInstance p1 = this;
        ProcedureInstance p2 = p;

        // rule 1:
        ReferenceType t1 = p1.container();
        ReferenceType t2 = p2.container();

        if (t1.isClass() && t2.isClass()) {
            if (! t1.isSubtype(t2) &&
                ! t1.toClass().isEnclosed(t2.toClass())) {
                return false;
            }
        }
        else {
            if (! t1.isSubtype(t2)) {
                return false;
            }
        }

        // rule 2:
        return p2.callValid(p1.formalTypes());
    }

    /** Returns true if the procedure has the given formal parameter types. */
    public final boolean hasFormals(List formalTypes) {
        return ts.hasFormals(this, formalTypes);
    }

    /** Returns true if the procedure has the given formal parameter types. */
    public boolean hasFormalsImpl(List formalTypes) {
        List l1 = this.formalTypes();
        List l2 = formalTypes;

        Iterator i1 = l1.iterator();
        Iterator i2 = l2.iterator();

        while (i1.hasNext() && i2.hasNext()) {
            Type t1 = (Type) i1.next();
            Type t2 = (Type) i2.next();

            if (! ts.equals(t1, t2)) {
                return false;
            }
        }

        return ! (i1.hasNext() || i2.hasNext());
    }

    /** Returns true iff <code>this</code> throws fewer exceptions than
     * <code>p</code>. */
    public final boolean throwsSubset(ProcedureInstance p) {
        return ts.throwsSubset(this, p);
    }

    /** Returns true iff <code>this</code> throws fewer exceptions than
     * <code>p</code>. */
    public boolean throwsSubsetImpl(ProcedureInstance p) {
        SubtypeSet s1 = new SubtypeSet(ts.Throwable());
        SubtypeSet s2 = new SubtypeSet(ts.Throwable());

        s1.addAll(this.throwTypes());
        s2.addAll(p.throwTypes());

        for (Iterator i = s1.iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            if (! ts.isUncheckedException(t) && ! s2.contains(t)) {
                return false;
            }
        }

        return true;
    }

    /** Returns true if a call can be made with the given argument types. */
    public final boolean callValid(List argTypes) {
        return ts.callValid(this, argTypes);
    }

    /** Returns true if a call can be made with the given argument types. */
    public boolean callValidImpl(List argTypes) {
        List l1 = this.formalTypes();
        List l2 = argTypes;

        Iterator i1 = l1.iterator();
        Iterator i2 = l2.iterator();

        while (i1.hasNext() && i2.hasNext()) {
            Type t1 = (Type) i1.next();
            Type t2 = (Type) i2.next();

            if (! ts.isImplicitCastValid(t2, t1)) {
                return false;
            }
        }

        return ! (i1.hasNext() || i2.hasNext());
    }
}
