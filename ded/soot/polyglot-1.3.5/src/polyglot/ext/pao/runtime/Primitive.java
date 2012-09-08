package polyglot.ext.pao.runtime;

/**
 * Boxed primitives. This is the abstract superclass of the classes used to box
 * primitive values at runtime.
 */
public abstract class Primitive {
    /**
     * Method used to implement <code>o == p</code> when <code>o</code> or
     * <code>p</code> could be a boxed primitive. Boxed primitives are
     * compared by their primitive value, not by identity.
     * 
     * @param o object to compare to p
     * @param p object to compare to o
     * @return true if <code>o == p</code> or if o and p are instances of
     *         <code>Primitive</code> and box the same primitive value.
     */
    public static boolean equals(Object o, Object p) {
        return o == p || (o instanceof Primitive && ((Primitive)o).equals(p));
    }
}