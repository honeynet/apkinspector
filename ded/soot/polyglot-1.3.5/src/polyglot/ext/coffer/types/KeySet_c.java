package polyglot.ext.coffer.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

import java.util.*;

public class KeySet_c extends TypeObject_c implements KeySet
{
    HashSet set;

    public KeySet_c(TypeSystem ts, Position pos) {
        super(ts, pos);
        this.set = new HashSet();
    }

    public int size() {
        return set.size();
    }

    public Iterator iterator() {
        return set.iterator();
    }

    public boolean contains(Key key) {
        return set.contains(key);
    }

    public KeySet add(Key key) {
        if (set.contains(key)) return this;
        KeySet_c s = (KeySet_c) copy();
        s.set = new HashSet(set);
        s.set.add(key);
        return s;
    }

    public KeySet remove(Key key) {
        if (! set.contains(key)) return this;
        KeySet_c s = (KeySet_c) copy();
        s.set = new HashSet(set);
        s.set.remove(key);
        return s;
    }

    public KeySet addAll(KeySet keys) {
        KeySet_c other = (KeySet_c) keys;
        KeySet_c s = (KeySet_c) copy();
        s.set = new HashSet(set);
        s.set.addAll(other.set);
        return s;
    }

    public KeySet removeAll(KeySet keys) {
        KeySet_c other = (KeySet_c) keys;
        KeySet_c s = (KeySet_c) copy();
        s.set = new HashSet(set);
        s.set.removeAll(other.set);
        return s;
    }

    public KeySet retainAll(KeySet keys) {
        KeySet_c other = (KeySet_c) keys;
        KeySet_c s = (KeySet_c) copy();
        s.set = new HashSet(set);
        s.set.retainAll(other.set);
        return s;
    }

    public boolean containsAll(KeySet keys) {
        KeySet_c other = (KeySet_c) keys;
        return set.containsAll(other.set);
    }

    public boolean equalsImpl(TypeObject o) {
        if (o instanceof KeySet_c) {
            KeySet_c other = (KeySet_c) o;
            return set.equals(other.set);
        }
        return false;
    }

    public boolean isEmpty() {
        return set.isEmpty();
    }

    public boolean isCanonical() {
        for (Iterator i = iterator(); i.hasNext(); ) {
            Key k = (Key) i.next();
            if (! k.isCanonical())
                return false;
        }
        return true;
    }

    public String toString() {
        return set.toString().replace('[', '{').replace(']', '}');
    }
}
