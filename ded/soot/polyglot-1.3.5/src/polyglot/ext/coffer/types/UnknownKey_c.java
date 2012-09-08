package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

public class UnknownKey_c extends Key_c implements UnknownKey
{
    public UnknownKey_c(TypeSystem ts, Position pos, String name) {
        super(ts, pos, name);
    }

    public boolean isCanonical() {
        return false;
    }
}
