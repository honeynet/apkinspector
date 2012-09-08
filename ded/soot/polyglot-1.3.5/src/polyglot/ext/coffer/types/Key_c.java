package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

public abstract class Key_c extends TypeObject_c implements Key
{
    protected String name;

    public Key_c(TypeSystem ts, Position pos, String name) {
        super(ts, pos);
        this.name = name;
    }

    public String name() {
        return name;
    }

    public Key name(String name) {
	Key_c n = (Key_c) copy();
	n.name = name;
	return n;
    }

    public boolean equalsImpl(TypeObject o) {
        return o == this;
    }

    public int hashCode() {
        return name.hashCode();
    }

    public String toString() {
        return name;
        // return getClass().getName() + "(" + name + "@" + System.identityHashCode(this) + ")";
    }

    public boolean isCanonical() {
        return true;
    }
}
