package polyglot.ext.coffer.extension;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.ast.*;
import polyglot.ext.coffer.types.*;

import java.util.*;

public class NewExt_c extends ProcedureCallExt_c {
    public void checkHeldKeys(KeySet held, KeySet stored)
        throws SemanticException
    {
        New n = (New) node();

        CofferConstructorInstance vci =
            (CofferConstructorInstance) n.constructorInstance();

        super.checkHeldKeys(held, stored);

        if (n.type() instanceof CofferClassType) {
            Key key = ((CofferClassType) n.type()).key();

            if (key != null) {
                if (held.contains(key) || stored.contains(key)) {
                    throw new SemanticException(
                        "Can evaluate \"new\" expression of type \"" +
                        n.type() + "\" only if key \"" + key +
                        "\" is not held.", n.position());
                }
            }
        }
    }
}
