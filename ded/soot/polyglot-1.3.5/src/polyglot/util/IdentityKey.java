package polyglot.util;

/**
 * Class to be used for inserting objects in hashtables using pointer equality.
 */
public class IdentityKey
{
    Object obj;

    public IdentityKey(Object obj) {
        this.obj = obj;
    }

    public Object object() {
        return obj;
    }

    public int hashCode() {
        return System.identityHashCode(obj);
    }

    public boolean equals(Object other) {
        return other instanceof IdentityKey
            && ((IdentityKey) other).obj == obj;
    }

    public String toString() {
        return "Id(" + obj + ")";
    }
}

