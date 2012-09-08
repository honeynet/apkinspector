package polyglot.ext.coffer.types;

import polyglot.types.*;
import polyglot.ext.param.types.*;
import polyglot.util.*;
import java.util.*;

public class CofferSubstClassType_c extends SubstClassType_c
    implements CofferSubstType
{
    public CofferSubstClassType_c(CofferTypeSystem ts, Position pos,
                                 ClassType base, CofferSubst subst) {
        super(ts, pos, base, subst);
    }

    ////////////////////////////////////////////////////////////////
    // Implement methods of CofferSubstType

    public PClass instantiatedFrom() {
        return ((CofferParsedClassType) base).instantiatedFrom();
    }

    public List actuals() {
        PClass pc = instantiatedFrom();
        CofferSubst subst = (CofferSubst) this.subst;

        List actuals = new ArrayList(pc.formals().size());

        for (Iterator i = pc.formals().iterator(); i.hasNext(); ) {
            Key key = (Key) i.next();
            actuals.add(subst.substKey(key));
        }

        return actuals;
    }

    ////////////////////////////////////////////////////////////////
    // Implement methods of CofferClassType

    public Key key() {
        CofferClassType base = (CofferClassType) this.base;
        CofferSubst subst = (CofferSubst) this.subst;
        return subst.substKey(base.key());
    }

    public String toString() {
        return "tracked(" + subst + ") " + base;
    }
}
