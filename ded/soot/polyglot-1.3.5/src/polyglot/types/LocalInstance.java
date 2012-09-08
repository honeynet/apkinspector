package polyglot.types;

/**
 * A <code>LocalInstance</code> contains type information for a local variable.
 */
public interface LocalInstance extends VarInstance
{
    /**
     * Set the local's flags.
     */
    LocalInstance flags(Flags flags);

    /**
     * Set the local's name.
     */
    LocalInstance name(String name);

    /**
     * Set the local's type.
     */
    LocalInstance type(Type type);

    /**
     * Set the local's constant value.
     */
    LocalInstance constantValue(Object value);

    /**
     * Destructively set the local's constant value.
     */
    void setConstantValue(Object value);
}
