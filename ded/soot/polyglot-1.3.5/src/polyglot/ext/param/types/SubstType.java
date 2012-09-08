package polyglot.ext.param.types;

import polyglot.types.*;
import java.util.Iterator;

/**
 * A type on which substitutions have been applied.
 */
public interface SubstType extends Type
{
    /** The type on which substitutions are performed. */ 
    Type base();

    /** The substitution function. */ 
    Subst subst();

    /** Entries of underlying substitution map.
     * @return An <code>Iterator</code> of <code>Map.Entry</code>.
     */ 
    Iterator entries();
}
