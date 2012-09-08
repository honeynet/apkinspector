package polyglot.ast;

import polyglot.types.*;

/**
 * <code>Typed</code> represents any node that has a type
 * associated with it.
 */
public interface Typed
{
    /**
     * Return the type of this node, or null if no type has been
     * assigned yet.
     */
    Type type();
}
