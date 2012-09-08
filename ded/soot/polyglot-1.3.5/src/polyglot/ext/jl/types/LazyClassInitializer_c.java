package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.*;
import polyglot.frontend.Job;
import java.io.*;
import java.util.*;

/**
 * A LazyClassInitializer is responsible for initializing members of
 * a class after it has been created.  Members are initialized lazily
 * to correctly handle cyclic dependencies between classes.
 */
public class LazyClassInitializer_c implements LazyClassInitializer
{
    protected TypeSystem ts;

    public LazyClassInitializer_c(TypeSystem ts) {
        this.ts = ts;
    }

    public boolean fromClassFile() {
        return false;
    }

    public void initConstructors(ParsedClassType ct) {
    }

    public void initMethods(ParsedClassType ct) {
    }

    public void initFields(ParsedClassType ct) {
    }

    public void initMemberClasses(ParsedClassType ct) {
    }

    public void initInterfaces(ParsedClassType ct) {
    }
}
