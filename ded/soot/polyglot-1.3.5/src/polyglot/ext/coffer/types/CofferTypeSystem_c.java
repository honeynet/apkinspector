package polyglot.ext.coffer.types;

import polyglot.ext.param.types.*;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

public class CofferTypeSystem_c extends ParamTypeSystem_c
                            implements CofferTypeSystem
{
    protected void initTypes() {
        // Do not initialize types.  This allows us to compile
        // java.lang.Object.
    }

    public ParsedClassType createClassType(LazyClassInitializer init, 
                                           Source fromSource)
    {
        if (! init.fromClassFile()) {
            return new CofferParsedClassType_c(this, init, fromSource);
        }
        else {
            return super.createClassType(init, fromSource);
        }
    }

    public MethodInstance methodInstance(Position pos,
                                         ReferenceType container,
                                         Flags flags, Type returnType,
                                         String name, List argTypes,
                                         List excTypes) {

        List l = new LinkedList();

        for (Iterator i = excTypes.iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            l.add(throwConstraint(t.position(), t, emptyKeySet(pos)));
        }

        return cofferMethodInstance(pos, container, flags, returnType, name,
                                   argTypes, emptyKeySet(pos),
                                   emptyKeySet(pos), l);
    }

    public CofferMethodInstance cofferMethodInstance(Position pos,
                                                   ReferenceType container,
                                                   Flags flags, Type returnType,
                                                   String name, List argTypes,
                                                   KeySet entryKeys,
                                                   KeySet returnKeys,
                                                   List throwConstraints) {

        CofferMethodInstance mi = new CofferMethodInstance_c(this, pos,
                                                           container, flags,
                                                           returnType, name,
                                                           argTypes,
                                                           entryKeys,
                                                           returnKeys,
                                                           throwConstraints);
        return mi;
    }

    public ConstructorInstance constructorInstance(Position pos,
                                                   ClassType container,
                                                   Flags flags,
                                                   List argTypes,
                                                   List excTypes) {

        List l = new LinkedList();

        for (Iterator i = excTypes.iterator(); i.hasNext(); ) {
            Type t = (Type) i.next();
            l.add(throwConstraint(t.position(), t, emptyKeySet(pos)));
        }

        return cofferConstructorInstance(pos, container, flags,
                                        argTypes, emptyKeySet(pos),
                                        emptyKeySet(pos), l);
    }

    public CofferConstructorInstance cofferConstructorInstance(Position pos,
                                                   ClassType container,
                                                   Flags flags,
                                                   List argTypes,
                                                   KeySet entryKeys,
                                                   KeySet returnKeys,
                                                   List throwConstraints) {

        CofferConstructorInstance ci = new CofferConstructorInstance_c(this, pos,
                                                           container, flags,
                                                           argTypes,
                                                           entryKeys,
                                                           returnKeys,
                                                           throwConstraints);
        return ci;
    }

    public boolean canOverride(MethodInstance mi, MethodInstance mj) {
        return super.canOverride(mi, mj);
    }

    public KeySet emptyKeySet(Position pos) {
        return new KeySet_c(this, pos);
    }

    public InstKey instKey(Position pos, String name) {
        return new InstKey_c(this, pos, name);
    }

    public UnknownKey unknownKey(Position pos, String name) {
        return new UnknownKey_c(this, pos, name);
    }

    public ParamKey paramKey(Position pos, String name) {
        return new ParamKey_c(this, pos, name);
    }

    public Subst subst(Map substMap, Map cache) {
        return new CofferSubst_c(this, substMap, cache);
    }

    public ThrowConstraint throwConstraint(Position pos, Type type, KeySet keys) {
        return new ThrowConstraint_c(this, pos, type, keys);
    }

    public Context createContext() {
        return new CofferContext_c(this);
    }

    public Collection uncheckedExceptions() {
        return CollectionUtil.list(Error(), NullPointerException());
    }
}
