package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

public class CofferContext_c extends Context_c implements CofferContext {
    protected Map keys;

    public CofferContext_c(TypeSystem ts) {
        super(ts);
    }

    protected Context_c push() {
        CofferContext_c c = (CofferContext_c) super.push();
        c.keys = null;
        return c;
    }

    public void addKey(Key key) {
        if (keys == null) {
            keys = new HashMap();
        }

        keys.put(key.name(), key);
    }

    public Key findKey(String name) throws SemanticException {
        Key key = null;

        if (keys != null) {
            key = (Key) keys.get(name);
        }

        if (key != null) {
            return key;
        }

        if (outer != null) {
            return ((CofferContext) outer).findKey(name);
        }

        throw new SemanticException("Key \"" + name + "\" not found.");
    }

    protected String mapsToString() {
        return super.mapsToString() + " keys=" + keys;
    }
}
