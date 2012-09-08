package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

/** An implementation of the <code>CofferConstructorInstance</code> interface. 
 */
public class CofferConstructorInstance_c extends ConstructorInstance_c
				     implements CofferConstructorInstance
{
    protected KeySet entryKeys;
    protected KeySet returnKeys;
    protected List throwConstraints;

    public CofferConstructorInstance_c(CofferTypeSystem ts, Position pos,
	    ClassType container, Flags flags,
	    List argTypes,
            KeySet entryKeys, KeySet returnKeys, List throwConstraints)
    {
	super(ts, pos, container, flags, argTypes, Collections.EMPTY_LIST);
        this.entryKeys = entryKeys;
        this.returnKeys = returnKeys;
        this.throwConstraints = TypedList.copyAndCheck(throwConstraints, ThrowConstraint.class, true);

        if (entryKeys == null)
            throw new InternalCompilerError("null entry keys for " + this);
    }

    public KeySet entryKeys() {
	return entryKeys;
    }

    public KeySet returnKeys() {
	return returnKeys;
    }

    public List throwConstraints() {
        return throwConstraints;
    }

    public List throwTypes() {
        return new CachingTransformingList(throwConstraints, new GetType());
    }

    public class GetType implements Transformation {
        public Object transform(Object o) {
            return ((ThrowConstraint) o).throwType();
        }
    }

    public ConstructorInstance throwTypes(List throwTypes) {
        Iterator i = throwTypes.iterator();
        Iterator j = throwConstraints.iterator();

        boolean changed = false;

        List l = new LinkedList();

        while (i.hasNext() && j.hasNext()) {
            Type t = (Type) i.next();
            ThrowConstraint c = (ThrowConstraint) j.next();
            if (t != c.throwType()) {
                c = c.throwType(t);
                changed = true;
            }

            l.add(c);
        }

        if (i.hasNext() || j.hasNext()) {
            throw new InternalCompilerError("unimplemented");
        }

        if (! changed) {
            return this;
        }

        return (ConstructorInstance) throwConstraints(l);
    }

    public CofferProcedureInstance entryKeys(KeySet entryKeys) {
	CofferConstructorInstance_c n = (CofferConstructorInstance_c) copy();
        n.entryKeys = entryKeys;
	return n;
    }

    public CofferProcedureInstance returnKeys(KeySet returnKeys) {
	CofferConstructorInstance_c n = (CofferConstructorInstance_c) copy();
        n.returnKeys = returnKeys;
	return n;
    }

    public CofferProcedureInstance throwConstraints(List throwConstraints) {
	CofferConstructorInstance_c n = (CofferConstructorInstance_c) copy();
        n.throwConstraints = TypedList.copyAndCheck(throwConstraints, ThrowConstraint.class, true);
	return n;
    }

    public String toString() {
        return super.toString() + " " + entryKeys + "->" + returnKeys +
                                  " throws " + throwConstraints;
    }
}
