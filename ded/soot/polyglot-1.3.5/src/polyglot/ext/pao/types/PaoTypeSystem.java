package polyglot.ext.pao.types;

import polyglot.types.*;

/**
 * The PAO type system interface. Several new methods are added to the
 * type system to facilitate the boxing and unboxing of primitive values.
 */
public interface PaoTypeSystem extends TypeSystem {
    /**
     * Returns the method instance for the runtime method that tests two boxed
     * primitive values for equality.
     * 
     * @return the method instance for the runtime method that tests two boxed
     *         primitive values for equality.
     * 
     * @see polyglot.ext.pao.runtime.Primitive#equals(Object, Object)
     */
    MethodInstance primitiveEquals();

    /**
     * Returns the method instance for getting the primitive value from a boxed
     * representation of primitive values of type <code>t</code>.
     * 
     * @param t the primitive type for which we want the getter method to access
     *            the primitive value of a boxed primitive value.
     * @return the method instance for getting the primitive value from a boxed
     *         representation of primitive values of type <code>t</code>.
     * 
     * @see polyglot.ext.pao.runtime.Boolean#booleanValue()
     * @see polyglot.ext.pao.runtime.Byte#byteValue()
     * @see polyglot.ext.pao.runtime.Character#charValue()
     * @see polyglot.ext.pao.runtime.Double#doubleValue()
     * @see polyglot.ext.pao.runtime.Float#floatValue()
     * @see polyglot.ext.pao.runtime.Integer#intValue()
     * @see polyglot.ext.pao.runtime.Long#longValue()
     * @see polyglot.ext.pao.runtime.Short#shortValue()
     */
    MethodInstance getter(PrimitiveType t);

    /**
     * Returns the constructor instance for the class used to represent boxed
     * values of type <code>t</code>.
     * 
     * @param t the <code>PrimitiveType</code> for which the constructor
     *            instance of the class representing boxed values is returned.
     * @return the constructor instance for the class used to represent boxed
     *         values of type <code>t</code>.
     */
    ConstructorInstance wrapper(PrimitiveType t);

    /**
     * Returns the class type used to represent boxed values of type
     * <code>t</code>.
     * 
     * @param t the <code>PrimitiveType</code> for which the type used to
     *            represent boxed values is returned.
     * @return the class type used to represent boxed values of type
     *         <code>t</code>.
     */
    ClassType boxedType(PrimitiveType t);
}
