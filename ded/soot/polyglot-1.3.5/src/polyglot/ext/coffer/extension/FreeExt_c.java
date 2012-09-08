package polyglot.ext.coffer.extension;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.ast.*;
import polyglot.ext.coffer.types.*;

import java.util.*;

public class FreeExt_c extends CofferExt_c {
    public KeySet keyFlow(KeySet held_keys, Type throwType) {
        Free f = (Free) node();

        Type t = f.expr().type();

        if (! (t instanceof CofferClassType)) {
            return held_keys;
        }

        CofferClassType ct = (CofferClassType) t;

        return held_keys.remove(ct.key());
    }

    public void checkHeldKeys(KeySet held, KeySet stored) throws SemanticException {
        Free f = (Free) node();

        Type t = f.expr().type();

        if (! (t instanceof CofferClassType)) {
            throw new SemanticException("Cannot free expression of " +
                                        "non-tracked type \"" + t + "\".",
                                        f.position());
        }

        CofferClassType ct = (CofferClassType) t;

        if (! held.contains(ct.key())) {
            throw new SemanticException("Key \"" + ct.key() +
                                        "\" not held.", f.position());
        }
    }
}
