package polyglot.ext.coffer.extension;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.ast.*;
import polyglot.ext.coffer.types.*;

import java.util.*;

public class CofferExt_c extends Ext_c implements CofferExt {
    public String KeysToString(KeySet set) {
        return "K" + eysToString(set);
    }

    public String keysToString(KeySet set) {
        return "k" + eysToString(set);
    }

    private String eysToString(KeySet set) {
        if (set.size() == 1) {
            Key k = (Key) set.iterator().next();
            return "ey \"" + k + "\"";
        }
        else {
            String s = "eys [";
            for (Iterator i = set.iterator(); i.hasNext(); ) {
                s += "\"" + i.next() + "\"";
                if (i.hasNext())
                    s += ", ";
            }
            s += "]";
            return s;
        }
    }

    public KeySet keyFlow(KeySet held_keys, Type throwType) { return held_keys; }
    public KeySet keyAlias(KeySet stored_keys, Type throwType) { return stored_keys; }

    public void checkHeldKeys(KeySet held, KeySet stored) throws SemanticException {
        if (node() instanceof Expr) {
            Expr e = (Expr) node();
                
            if (e.type() instanceof CofferClassType) {
                Key key = ((CofferClassType) e.type()).key();

                if (key != null) {
                    if (! held.contains(key)) {
                        throw new SemanticException(
                            "Can evaluate expression of type \"" +
                            e.type() + "\" only if key \"" + key +
                            "\" is held.", e.position());
                    }
                    if (stored.contains(key)) {
                        throw new SemanticException(
                            "Can evaluate expression of type \"" +
                            e.type() + "\" only if key \"" + key +
                            "\" is not held in a variable.",
                            e.position());
                    }
                }
            }
        }
    }
}
