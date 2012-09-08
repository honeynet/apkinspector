package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

/** An implementation of the <code>CofferMethodInstance</code> interface. 
 */
public class CofferMethodInstance_c extends MethodInstance_c
                                implements CofferMethodInstance
{
    protected KeySet entryKeys;
    protected KeySet returnKeys;
    protected List throwConstraints;

    public CofferMethodInstance_c(CofferTypeSystem ts, Position pos,
	    ReferenceType container, Flags flags, Type returnType,
	    String name, List argTypes,
            KeySet entryKeys, KeySet returnKeys, List throwConstraints)
    {
	super(ts, pos, container, flags, returnType, name, argTypes, Collections.EMPTY_LIST);

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

    public MethodInstance throwTypes(List throwTypes) {
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

        return (MethodInstance) throwConstraints(l);
    }

    public class GetType implements Transformation {
        public Object transform(Object o) {
            return ((ThrowConstraint) o).throwType();
        }
    }

    public CofferProcedureInstance entryKeys(KeySet entryKeys) {
	CofferMethodInstance_c n = (CofferMethodInstance_c) copy();
        n.entryKeys = entryKeys;
	return n;
    }

    public CofferProcedureInstance returnKeys(KeySet returnKeys) {
	CofferMethodInstance_c n = (CofferMethodInstance_c) copy();
        n.returnKeys = returnKeys;
	return n;
    }

    public CofferProcedureInstance throwConstraints(List throwConstraints) {
	CofferMethodInstance_c n = (CofferMethodInstance_c) copy();
        n.throwConstraints = TypedList.copyAndCheck(throwConstraints, ThrowConstraint.class, true);
	return n;
    }

    public boolean canOverrideImpl(MethodInstance mj, boolean quiet) throws SemanticException {
        CofferMethodInstance mi = this;

        KeySet e;
        KeySet r;
        List l;

        if (mj instanceof CofferMethodInstance) {
            e = ((CofferMethodInstance) mj).entryKeys();
            r = ((CofferMethodInstance) mj).returnKeys();
            l = ((CofferMethodInstance) mj).throwConstraints();
        }
        else {
            CofferTypeSystem ts = (CofferTypeSystem) this.ts;
            e = ts.emptyKeySet(position());
            r = ts.emptyKeySet(position());
            l = Collections.EMPTY_LIST;
        }

        // Can pass through more keys.
        KeySet newKeys = entryKeys.removeAll(e);

        if (! returnKeys.equals(r.addAll(newKeys))) {
            if (quiet) return false;
            throw new SemanticException(mi.signature() + " in " + mi.container() +
                                        " cannot override " + 
                                        mj.signature() + " in " + mj.container() + 
                                        ";", 
                                        mi.position());
        }

        CONSTRAINTS:
        for (Iterator i = throwConstraints.iterator(); i.hasNext(); ) {
            ThrowConstraint c = (ThrowConstraint) i.next();

            for (Iterator j = l.iterator(); j.hasNext(); ) {
                ThrowConstraint superC = (ThrowConstraint) j.next();

                if (superC.throwType().equals(c.throwType())) {
                    if (! c.keys().equals(superC.keys().addAll(newKeys))) {
                        if (quiet) return false;
                        throw new SemanticException(mi.signature() + " in " + mi.container() +
                                " cannot override " + 
                                mj.signature() + " in " + mj.container() + 
                                ";", 
                                mi.position());
                    }
                    continue CONSTRAINTS;
                }
            }

            if (! c.keys().equals(newKeys)) {
                if (quiet) return false;
                throw new SemanticException(mi.signature() + " in " + mi.container() +
                        " cannot override " + 
                        mj.signature() + " in " + mj.container() + 
                        ";", 
                        mi.position());
            }
        }

        return super.canOverrideImpl(mj, quiet);
    }

    public String toString() {
        return super.toString() + " " + entryKeys + "->" + returnKeys +
                                  " throws " + throwConstraints;
    }
}
