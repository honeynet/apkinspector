package polyglot.types;

import polyglot.util.Position;

/**
 * A <code>Type</code> is the base type of all classes which represent
 * types.
 */
public interface Type extends Qualifier
{
    /**
     * Return a string into which to translate the type.
     * @param c A resolver in which to lookup this type to determine if
     * the type is unique in the given resolver.
     */
    String translate(Resolver c);

    /**
     * Return an array of this type.
     */
    ArrayType arrayOf();

    /**
     * Return a <code>dims</code>-array of this type.
     */
    ArrayType arrayOf(int dims);

    /**
     * Cast the type to a class type, or null.
     */
    ClassType toClass();

    /**
     * Cast the type to a null type, or null.
     */
    NullType toNull();

    /**
     * Cast the type to a reference type, or null.
     */
    ReferenceType toReference();

    /**
     * Cast the type to a primitive type, or null.
     */
    PrimitiveType toPrimitive();

    /**
     * Cast the type to an array type, or null.
     */
    ArrayType toArray();

    /**
     * Return true if this type is a subtype of <code>ancestor</code>.
     */
    boolean isSubtype(Type ancestor);

    /**
     * Return true if this type descends from <code>ancestor</code>.
     */
    boolean descendsFrom(Type ancestor);

    /**
     * Return true if this type can be cast to <code>toType</code>.
     */
    boolean isCastValid(Type toType);

    /**
     * Return true if a value of this type can be assigned to a variable of
     * type <code>toType</code>.
     */
    boolean isImplicitCastValid(Type toType);

    /**
     * Return true a literal <code>value</code> can be converted to this type.
     */
    boolean numericConversionValid(Object value);

    /**
     * Return true a literal <code>value</code> can be converted to this type.
     */
    boolean numericConversionValid(long value);

    /**
     * Return true if this type is a subtype of <code>ancestor</code>.
     */
    boolean isSubtypeImpl(Type t);

    /**
     * Return true if this type descends from <code>ancestor</code>.
     */
    boolean descendsFromImpl(Type t);

    /**
     * Return true if this type can be cast to <code>toType</code>.
     */
    boolean isCastValidImpl(Type t);

    /**
     * Return true if a value of this type can be assigned to a variable of
     * type <code>toType</code>.
     */
    boolean isImplicitCastValidImpl(Type t);

    /**
     * Return true a literal <code>value</code> can be converted to this type.
     */
    boolean numericConversionValidImpl(Object value);

    /**
     * Return true a literal <code>value</code> can be converted to this type.
     * This method should be removed.  It is kept for backward compatibility.
     */
    boolean numericConversionValidImpl(long value);

    /**
     * Return true if a primitive type.
     */
    boolean isPrimitive();

    /**
     * Return true if void.
     */
    boolean isVoid();

    /**
     * Return true if boolean.
     */
    boolean isBoolean();

    /**
     * Return true if char.
     */
    boolean isChar();

    /**
     * Return true if byte.
     */
    boolean isByte();

    /**
     * Return true if short.
     */
    boolean isShort();

    /**
     * Return true if int.
     */
    boolean isInt();

    /**
     * Return true if long.
     */
    boolean isLong();

    /**
     * Return true if float.
     */
    boolean isFloat();

    /**
     * Return true if double.
     */
    boolean isDouble();

    /**
     * Return true if int, short, byte, or char.
     */
    boolean isIntOrLess();

    /**
     * Return true if long, int, short, byte, or char.
     */
    boolean isLongOrLess();

    /**
     * Return true if double, float, long, int, short, byte, or char.
     */
    boolean isNumeric();

    /**
     * Return true if a reference type.
     */
    boolean isReference();

    /**
     * Return true if a null type.
     */
    boolean isNull();

    /**
     * Return true if an array type.
     */
    boolean isArray();

    /**
     * Return true if a class type.
     */
    boolean isClass();

    /**
     * Return true if a subclass of Throwable.
     */
    boolean isThrowable();

    /**
     * Return true if an unchecked exception.
     */
    boolean isUncheckedException();

    /**
     * Return true if the types can be compared; that is, if they have
     * the same type system.
     */
    boolean isComparable(Type t);

    /**
     * Yields a string representing this type.  The string
     * should be consistent with equality.  That is,
     * if this.equals(anotherType), then it should be
     * that this.toString().equals(anotherType.toString()).
     *
     * The string does not have to be a legal Java identifier.
     * It is suggested, but not required, that it be an
     * easily human readable representation, and thus useful
     * in error messages and generated output.
     */
    String toString();
}
