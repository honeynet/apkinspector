package polyglot.types;

/**
 * A <code>FieldInstance</code> contains type information for a field.
 */
public interface FieldInstance extends VarInstance, MemberInstance
{
    /**
     * Set the flags of the field.
     */
    FieldInstance flags(Flags flags);

    /**
     * Set the name of the field.
     */
    FieldInstance name(String name);

    /**
     * Set the type of the field.
     */
    FieldInstance type(Type type);

    /**
     * Set the containing class of the field.
     */
    FieldInstance container(ReferenceType container);

    /**
     * Set the constant value of the field.
     * @param value the constant value.  Should be an instance of String,
     * Boolean, Byte, Short, Character, Integer, Long, Float, Double, or null.
     */
    FieldInstance constantValue(Object value);

    /**
     * Destructively set the constant value of the field.
     * @param value the constant value.  Should be an instance of String,
     * Boolean, Byte, Short, Character, Integer, Long, Float, Double, or null.
     */
    void setConstantValue(Object value);
}
