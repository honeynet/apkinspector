package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.main.Report;
import java.util.*;

/**
 * A <code>MethodInstance</code> represents the type information for a Java
 * method.
 */
public class MethodInstance_c extends ProcedureInstance_c
                                implements MethodInstance
{
    protected String name;
    protected Type returnType;

    /** Used for deserializing types. */
    protected MethodInstance_c() { }

    public MethodInstance_c(TypeSystem ts, Position pos,
	 		    ReferenceType container,
	                    Flags flags, Type returnType, String name,
			    List formalTypes, List excTypes) {
        super(ts, pos, container, flags, formalTypes, excTypes);
	this.returnType = returnType;
	this.name = name;
    }

    public MethodInstance flags(Flags flags) {
        if (!flags.equals(this.flags)) {
            MethodInstance_c n = (MethodInstance_c) copy();
            n.flags = flags;
            return n;
        }
        return this;
    }

    public String name() {
        return name;
    }

    public MethodInstance name(String name) {
        if ((name != null && !name.equals(this.name)) ||
            (name == null && name != this.name)) {
            MethodInstance_c n = (MethodInstance_c) copy();
            n.name = name;
            return n;
        }
        return this;
    }

    public Type returnType() {
        return returnType;
    }

    public MethodInstance returnType(Type returnType) {
        if (this.returnType != returnType) {
            MethodInstance_c n = (MethodInstance_c) copy();
            n.returnType = returnType;
            return n;
        }
        return this;
    }

    public MethodInstance formalTypes(List l) {
        if (!CollectionUtil.equals(this.formalTypes, l)) {
            MethodInstance_c n = (MethodInstance_c) copy();
            n.formalTypes = new ArrayList(l);
            return n;
        }
        return this;
    }

    public MethodInstance throwTypes(List l) {
        if (!CollectionUtil.equals(this.excTypes, l)) {
            MethodInstance_c n = (MethodInstance_c) copy();
            n.excTypes = new ArrayList(l);
            return n;
        }
        return this;
    }

    public MethodInstance container(ReferenceType container) {
        if (this.container != container) {
            MethodInstance_c n = (MethodInstance_c) copy();
            n.container = container;
            return n;
        }
        return this;
    }

    public int hashCode() {
        //return container.hashCode() + flags.hashCode() +
	//       returnType.hashCode() + name.hashCode();
	return flags.hashCode() + name.hashCode();
    }

    public boolean equalsImpl(TypeObject o) {
        if (o instanceof MethodInstance) {
	    MethodInstance i = (MethodInstance) o;
	    return ts.equals(returnType, i.returnType())
	        && name.equals(i.name())
		&& super.equalsImpl(i);
	}

	return false;
    }

    public String toString() {
	String s = designator() + " " + flags.translate() + returnType + " " +
                   signature();

	if (! excTypes.isEmpty()) {
	    s += " throws " + TypeSystem_c.listToString(excTypes);
	}

	return s;
    }

    public String signature() {
        return name + "(" + TypeSystem_c.listToString(formalTypes) + ")";
    }

    public String designator() {
        return "method";
    }

    /** Returns true iff <this> is the same method as <m> */
    public final boolean isSameMethod(MethodInstance m) {
        return ts.isSameMethod(this, m);
    }

    /** Returns true iff <this> is the same method as <m> */
    public boolean isSameMethodImpl(MethodInstance m) {
        return this.name().equals(m.name()) && hasFormals(m.formalTypes());
    }

    public boolean isCanonical() {
	return container.isCanonical()
	    && returnType.isCanonical()
	    && listIsCanonical(formalTypes)
	    && listIsCanonical(excTypes);
    }

    public final boolean methodCallValid(String name, List argTypes) {
        return ts.methodCallValid(this, name, argTypes);
    }

    public boolean methodCallValidImpl(String name, List argTypes) {
        return name().equals(name) && ts.callValid(this, argTypes);
    }

    public List overrides() {
        return ts.overrides(this);
    }

    public List overridesImpl() {
        List l = new LinkedList();
        ReferenceType rt = container();

        while (rt != null) {
            // add any method with the same name and formalTypes from 
            // rt
            l.addAll(rt.methods(name, formalTypes));

            ReferenceType sup = null;
            if (rt.superType() != null && rt.superType().isReference()) {
                sup = (ReferenceType) rt.superType();    
            }
            
            rt = sup;
        };

        return l;
    }

    public final boolean canOverride(MethodInstance mj) {
        return ts.canOverride(this, mj);
    }

    public final void checkOverride(MethodInstance mj) throws SemanticException {
        ts.checkOverride(this, mj);
    }

    /**
     * Leave this method in for historic reasons, to make sure that extensions
     * modify their code correctly.
     */
    public final boolean canOverrideImpl(MethodInstance mj) throws SemanticException {
        throw new RuntimeException("canOverrideImpl(MethodInstance mj) should not be called.");
    }
        
    /**
     * @param quiet If true, then no Semantic Exceptions will be thrown, and the
     *              return value will be true or false. Otherwise, if the method
     *              cannot override, then a SemanticException will be thrown, else
     *              the method will return true.
     */
    public boolean canOverrideImpl(MethodInstance mj, boolean quiet) throws SemanticException {
        MethodInstance mi = this;

        if (!(mi.name().equals(mj.name()) && mi.hasFormals(mj.formalTypes()))) {
            if (quiet) return false;
            throw new SemanticException("Arguments are different", mi.position());
        }

        if (! ts.equals(mi.returnType(), mj.returnType())) {
            if (Report.should_report(Report.types, 3))
                Report.report(3, "return type " + mi.returnType() +
                              " != " + mj.returnType());
            if (quiet) return false;
            throw new SemanticException(mi.signature() + " in " + mi.container() +
                                        " cannot override " + 
                                        mj.signature() + " in " + mj.container() + 
                                        "; attempting to use incompatible " +
                                        "return type\n" +                                        
                                        "found: " + mi.returnType() + "\n" +
                                        "required: " + mj.returnType(), 
                                        mi.position());
        } 

        if (! ts.throwsSubset(mi, mj)) {
            if (Report.should_report(Report.types, 3))
                Report.report(3, mi.throwTypes() + " not subset of " +
                              mj.throwTypes());
            if (quiet) return false;
            throw new SemanticException(mi.signature() + " in " + mi.container() +
                                        " cannot override " + 
                                        mj.signature() + " in " + mj.container() + 
                                        "; the throw set is not a subset of the " +
                                        "overridden method's throw set", 
                                        mi.position());
        }   

        if (mi.flags().moreRestrictiveThan(mj.flags())) {
            if (Report.should_report(Report.types, 3))
                Report.report(3, mi.flags() + " more restrictive than " +
                              mj.flags());
            if (quiet) return false;
            throw new SemanticException(mi.signature() + " in " + mi.container() +
                                        " cannot override " + 
                                        mj.signature() + " in " + mj.container() + 
                                        "; attempting to assign weaker " + 
                                        "access privileges", 
                                        mi.position());
        }

        if (mi.flags().isStatic() != mj.flags().isStatic()) {
            if (Report.should_report(Report.types, 3))
                Report.report(3, mi.signature() + " is " + 
                              (mi.flags().isStatic() ? "" : "not") + 
                              " static but " + mj.signature() + " is " +
                              (mj.flags().isStatic() ? "" : "not") + " static");
            if (quiet) return false;
            throw new SemanticException(mi.signature() + " in " + mi.container() +
                                        " cannot override " + 
                                        mj.signature() + " in " + mj.container() + 
                                        "; overridden method is " + 
                                        (mj.flags().isStatic() ? "" : "not") +
                                        "static", 
                                        mi.position());
        }

        if (mi != mj && !mi.equals(mj) && mj.flags().isFinal()) {
	    // mi can "override" a final method mj if mi and mj are the same method instance.
            if (Report.should_report(Report.types, 3))
                Report.report(3, mj.flags() + " final");
            if (quiet) return false;
            throw new SemanticException(mi.signature() + " in " + mi.container() +
                                        " cannot override " + 
                                        mj.signature() + " in " + mj.container() + 
                                        "; overridden method is final", 
                                        mi.position());
        }

        return true;
    }
    
    public List implemented() {
	return ts.implemented(this);
    }

    public List implementedImpl(ReferenceType rt) {
	if (rt == null) {
	    return Collections.EMPTY_LIST;
	}

        List l = new LinkedList();
        l.addAll(rt.methods(name, formalTypes));

	Type superType = rt.superType();
	if (superType != null) {
	    l.addAll(implementedImpl(superType.toReference())); 
	}
	
	List ints = rt.interfaces();
	for (Iterator i = ints.iterator(); i.hasNext(); ) {
            ReferenceType rt2 = (ReferenceType) i.next();
	    l.addAll(implementedImpl(rt2));
	}
	
        return l;
    }
}
