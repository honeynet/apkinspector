package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.util.*;
import java.util.*;

public class ThrowConstraint_c extends TypeObject_c implements ThrowConstraint {
    Type throwType;
    KeySet keys;

    public ThrowConstraint_c(CofferTypeSystem ts, Position pos,
                             Type throwType, KeySet keys) {
        super(ts, pos);
        this.throwType = throwType;
        this.keys = keys;
    }

    public KeySet keys() {
        return keys;
    }

    public ThrowConstraint keys(KeySet keys) {
        ThrowConstraint_c n = (ThrowConstraint_c) copy();
        n.keys = keys;
        return n;
    }

    public Type throwType() {
        return throwType;
    }

    public ThrowConstraint throwType(Type throwType) {
        ThrowConstraint_c n = (ThrowConstraint_c) copy();
        n.throwType = throwType;
        return n;
    }

    public boolean isCanonical() {
        return keys.isCanonical() && throwType.isCanonical();
    }

    public String toString() {
        return throwType.toString() + keys.toString();
    }
}
