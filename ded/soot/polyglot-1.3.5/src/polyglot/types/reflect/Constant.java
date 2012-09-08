package polyglot.types.reflect;

import java.io.*;

/**
 * A Constant is used to represent an item in the constant pool of a class.
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class Constant
{
    int tag;
    Object value;

    /**
     * Constant tag for class types.
     * This is used to reference other classes, such as the superclass,
     * and is used by the checkcast and instanceof instructions.
     * The Fieldref, Methodref and InterfaceMethodref constant types
     * refer to this constant type.
     */
    static final byte CLASS              = 7;

    /**
     * Constant tag for field references.
     * This is used to reference a field in (possibly) another class.
     * The getfield, putfield, getstatic, and putstatic instructions use
     * this constant type.
     */
    static final byte FIELD_REF           = 9;

    /**
     * Constant tag for method references.
     * This is used to reference a method in (possibly) another class.
     * The invokevirtual, invokespecial, and invokestatic instructions use
     * this constant type.
     */
    static final byte METHOD_REF          = 10;

    /**
     * Constant tag for java.lang.String constants.
     * The actual string value is stored indirectly in a Utf8 constant.
     */
    static final byte STRING             = 8;

    /**
     * Constant tag for int, short, byte, char, and boolean constants. 
     */
    static final byte INTEGER            = 3;

    /**
     * Constant tag for float constants. 
     */
    static final byte FLOAT              = 4;

    /**
     * Constant tag for long constants. 
     */
    static final byte LONG               = 5;

    /**
     * Constant tag for double constants. 
     */
    static final byte DOUBLE             = 6;

    /**
     * Constant tag for method references.
     * This is used to reference a method in an interface.
     * The invokeinterface instruction uses this constant type.
     */
    static final byte INTERFACE_METHOD_REF = 11;

    /**
     * Constant tag for holding the name and type of a field or method.
     * The Fieldref, Methodref and InterfaceMethodref constant types
     * refer to this constant type.
     */
    static final byte NAME_AND_TYPE        = 12;

    /**
     * Constant tag for holding the a UTF8 format string.
     * The string is used to hold the name and type descriptor for
     * NameandType constants, the class name for Class constants,
     * the string value for String constants.
     */
    static final byte UTF8               = 1;

    /**
     * @param tag
     *        The constant's tag.
     * @param value
     *        The constant's value.
     */
    Constant(final int tag, final Object value)
    {
	this.tag = tag;
	this.value = value;
    }

    /**
     * Get the tag of the constant.
     *
     * @return
     *        The tag.
     */
    final int tag()
    {
	return tag;
    }

    /**
     * Get the value of the constant.
     *
     * @return
     *        The value.
     */
    public final Object value()
    {
	return value;
    }

    /**
     * Hash the constant.
     *
     * @return
     *        The hash code.
     */
    public int hashCode()
    {
	switch (tag) {
	    case CLASS:
	    case STRING:
	    case INTEGER:
	    case FLOAT:
	    case LONG:
	    case DOUBLE:
	    case UTF8:
		return tag ^ value.hashCode();
	    case FIELD_REF:
	    case METHOD_REF:
	    case INTERFACE_METHOD_REF:
	    case NAME_AND_TYPE:
		return tag ^ ((int[]) value)[0] ^ ((int[]) value)[1];
	}

	return tag;
    }

    /**
     * Check if an object is equal to this constant.
     *
     * @param obj
     *        The object to compare against.
     * @return
     *        true if equal, false if not.
     */
    public boolean equals(Object other)
    {
	if (! (other instanceof Constant)) {
	    return false;
	}

	Constant c = (Constant) other;

	if (tag != c.tag) {
	    return false;
	}

	switch (tag) {
	    case CLASS:
	    case STRING:
	    case INTEGER:
	    case FLOAT:
	    case LONG:
	    case DOUBLE:
	    case UTF8:
		return value.equals(c.value);
	    case FIELD_REF:
	    case METHOD_REF:
	    case INTERFACE_METHOD_REF:
	    case NAME_AND_TYPE:
		return ((int[]) value)[0] == ((int[]) c.value)[0] &&
		       ((int[]) value)[1] == ((int[]) c.value)[1];
	}

	return false;
    }
}
