package polyglot.ext.coffer.extension;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.ast.*;
import polyglot.ext.coffer.types.*;

import java.util.*;

public class AssignExt_c extends CofferExt_c {
    public KeySet keyFlow(KeySet held_keys, Type throwType) {
        Assign n = (Assign) node();
        return super.keyFlow(held_keys, throwType);
    }

    public KeySet keyAlias(KeySet stored_keys, Type throwType) {
        Assign n = (Assign) node();

        if (n.right().type() instanceof CofferClassType) {
            CofferClassType t = (CofferClassType) n.right().type();

            if (t.key() != null && n.left() instanceof Local) {
                return stored_keys.add(t.key());
            }
        }

        return stored_keys;
    }

    public void checkHeldKeys(KeySet held, KeySet stored) throws SemanticException {
        Assign n = (Assign) node();

        if (n.right().type() instanceof CofferClassType) {
            CofferClassType t = (CofferClassType) n.right().type();

            if (t.key() != null && ! held.contains(t.key())) {
                throw new SemanticException("Cannot assign tracked value unless key \"" + t.key() + "\" held.", n.position());
            }

            if (t.key() != null && stored.contains(t.key())) {
                throw new SemanticException("Cannot assign tracked value with key \"" + t.key() + "\" more than once.", n.position());
            }
        }
    }
}
