package polyglot.types;

import polyglot.util.*;
import java.io.*;

/**
 * A <code>TypeObject</code> is a compile-time value created by the type system.
 * It is a static representation of a type that is not necessarily 
 * first-class.  It is similar to a compile-time meta-object.
 */
public interface TypeObject extends Copy, Serializable
{
    /**
     * Return true if the type object contains no unknown/ambiguous types.
     */
    boolean isCanonical();

    /**
     * The object's type system.
     */
    TypeSystem typeSystem();

    /**
     * The object's position, or null.
     */
    Position position();
    
    /**
     * Return true iff this type object is the same as <code>t</code>.
     * All Polyglot extensions should attempt to maintain pointer
     * equality between TypeObjects.  If this cannot be done,
     * extensions can override TypeObject_c.equalsImpl(), and
     * don't forget to override hashCode().
     *
     * @see polyglot.ext.jl.types.TypeObject_c#equalsImpl(TypeObject)
     * @see java.lang.Object#hashCode()
     */
    boolean equalsImpl(TypeObject t);
}
