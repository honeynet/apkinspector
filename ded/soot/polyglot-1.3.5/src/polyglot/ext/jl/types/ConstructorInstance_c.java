package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>ConstructorInstance</code> contains type information for a
 * constructor.
 */
public class ConstructorInstance_c extends ProcedureInstance_c
                                implements ConstructorInstance
{
    /** Used for deserializing types. */
    protected ConstructorInstance_c() { }

    public ConstructorInstance_c(TypeSystem ts, Position pos,
	                         ClassType container,
				 Flags flags, List formalTypes, List excTypes) {
        super(ts, pos, container, flags, formalTypes, excTypes);
    }

    public ConstructorInstance flags(Flags flags) {
        if (!flags.equals(this.flags)) {
            ConstructorInstance_c n = (ConstructorInstance_c) copy();
            n.flags = flags;
            return n;
        }
        return this;
    }

    public ConstructorInstance formalTypes(List l) {
        if (!CollectionUtil.equals(this.formalTypes, l)) {
            ConstructorInstance_c n = (ConstructorInstance_c) copy();
            n.formalTypes = new ArrayList(l);
            return n;
        }
        return this;
    }

    public ConstructorInstance throwTypes(List l) {
        if (!CollectionUtil.equals(this.excTypes, l)) {
            ConstructorInstance_c n = (ConstructorInstance_c) copy();
            n.excTypes = new ArrayList(l);
            return n;
        }
        return this;
    }

    public ConstructorInstance container(ClassType container) {
        if (this.container != container) {
            ConstructorInstance_c n = (ConstructorInstance_c) copy();
            n.container = container;
            return n;
        }
        return this;
    }

    public String toString() {
	return designator() + " " + flags.translate() + signature();
    }
    
    public String signature() {
        return container + "(" + TypeSystem_c.listToString(formalTypes) + ")";
    }

    public String designator() {
        return "constructor";
    }

    public boolean equalsImpl(TypeObject o) {
        if (! (o instanceof ConstructorInstance) ) return false;
        return super.equalsImpl(o);
    }

    public boolean isCanonical() {
	return container.isCanonical()
	    && listIsCanonical(formalTypes)
	    && listIsCanonical(excTypes);
    }
}
