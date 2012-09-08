package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

public class ParamKey_c extends Key_c implements ParamKey
{
    public ParamKey_c(TypeSystem ts, Position pos, String name) {
        super(ts, pos, name);
    }
}
