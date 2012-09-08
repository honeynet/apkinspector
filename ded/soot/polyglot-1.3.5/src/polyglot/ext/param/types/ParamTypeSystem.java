package polyglot.ext.param.types;

import polyglot.types.*;
import polyglot.util.Position;
import java.util.*;

/**
 * Type system for parameterized types.
 */
public interface ParamTypeSystem extends TypeSystem {
    /**
     * Create a new mutable PClass.
     *
     * @param pos The position of the pclass
     */
    MuPClass mutablePClass(Position pos);

    /**
     * Instantiate a parametric type on a list of actual parameters.
     *
     * @param pos The position of the instantiated type
     * @param base The parameterized type
     * @param actuals The list of actuals
     *
     * @throws SemanticException when the actuals do not agree with the formals
     */
    ClassType instantiate(Position pos, PClass base, List actuals)
        throws SemanticException;

    /**
     * Apply a parameter substitution to a type.
     *
     * @param base The type on which we perform substitutions.
     * @param substMap Map from formal parameters to actuals; the formals are
     * not necessarily formals of <code>base</code>.
     */
    Type subst(Type base, Map substMap);

    /**
     * Apply a parameter substitution to a type.
     *
     * @param base The type on which we perform substitutions.
     * @param substMap Map from formal parameters to actuals; the formals are
     * not necessarily formals of <code>base</code>.
     * @param cache Cache of substitutions performed, implemented as a map from
     * base type to substituted type.  This is passed in to ensure pointers to
     * outer classes are substituted correctly.
     */
    Type subst(Type base, Map substMap, Map cache);

    /**
     * Create a substitutor.
     *
     * @param substMap Map from formal parameters to actuals; the formals are
     * not necessarily formals of <code>base</code>.
     * @param cache Cache of substitutions performed, implemented as a map from
     * base type to substituted type.  This is passed in to ensure pointers to
     * outer classes are substituted correctly.
     */
    Subst subst(Map substMap, Map cache);
}
