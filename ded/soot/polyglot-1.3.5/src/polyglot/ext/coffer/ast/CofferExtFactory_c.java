package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.ext.coffer.extension.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;
import java.util.*;

/** An implementation of the <code>CofferNodeFactory</code> interface. 
 */
public class CofferExtFactory_c extends AbstractExtFactory_c
{
    public CofferExtFactory_c() {
        super();
    }

    public final Ext extCanonicalKeySetNode() {
    Ext e = extCanonicalKeySetNodeImpl();
    return e;
    }

    public final Ext extFree() {
        Ext e = extFreeImpl();
        return e;
    }

    public final Ext extKeyNode() {
        Ext e = extKeyNodeImpl();
        return e;
    }

    public final Ext extThrowConstraintNode() {
        Ext e = extThrowConstraintNodeImpl();
        return e;
    }

    public final Ext extTrackedTypeNode() {
        Ext e = extTrackedTypeNodeImpl();
        return e;
    }
    public final Ext extAmbKeySetNode() {
        Ext e = extAmbKeySetNodeImpl();
        return e;
    }

    public Ext extNodeImpl() {
        return new CofferExt_c();
    }

    public Ext extAssignImpl() {
        return new AssignExt_c();
    }

    public Ext extLocalImpl() {
        return new LocalExt_c();
    }

    public Ext extSpecialImpl() {
        return new SpecialExt_c();
    }

    public Ext extLocalDeclImpl() {
        return new LocalDeclExt_c();
    }

    public Ext extConstructorCallImpl() {
        return extProcedureCallImpl();
    }

    public Ext extCallImpl() {
        return extProcedureCallImpl();
    }

    public Ext extProcedureCallImpl() {
        return new ProcedureCallExt_c();
    }

    public Ext extNewImpl() {
        return new NewExt_c();
    }

    public Ext extFreeImpl() {
        return new FreeExt_c();
    }

    public Ext extCanonicalKeySetNodeImpl() {
        return extNodeImpl();
    }

    public Ext extAmbKeySetNodeImpl() {
        return extNodeImpl();
    }

    public Ext extKeyNodeImpl() {
        return  extNodeImpl();
    }
    public Ext extTrackedTypeNodeImpl() {
        return  extTypeNodeImpl();
    }
    public Ext extThrowConstraintNodeImpl() {
        return  extNodeImpl();
    }

    public Ext extProcedureDeclImpl() {
        return new ProcedureDeclExt_c();
    }

}
