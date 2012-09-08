package polyglot.types;

import polyglot.ast.*;

/**
 * A <code>Resolver</code> is responsible for looking up types and
 * packages by name.
 */
public interface Resolver {

    /**
     * Find a type object by name.
     */
    public Named find(String name) throws SemanticException;
}
