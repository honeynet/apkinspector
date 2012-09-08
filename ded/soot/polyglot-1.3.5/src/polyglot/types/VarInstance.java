package polyglot.types;

/**
 * A <code>VarInstance</code> contains type information for a variable.  It may
 * be either a local or a field.
 */
public interface VarInstance extends TypeObject
{
    /**
     * The flags of the variable.
     */
    Flags flags();

    /**
     * The name of the variable.
     */
    String name();

    /**
     * The type of the variable.
     */
    Type type();

    /**
     * The variable's constant value, or null.
     */
    Object constantValue();

    /**
     * Whether the variable has a constant value.
     */
    boolean isConstant();

    /**
     * Destructively set the type of the variable.
     * This method should be deprecated.
     */
    void setType(Type type); //destructive update   
    
    /**
     * Destructively set the flags of the variable.
     */
    void setFlags(Flags flags);
}
