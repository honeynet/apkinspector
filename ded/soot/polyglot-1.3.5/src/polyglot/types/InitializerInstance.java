package polyglot.types;

/**
 * A <code>InitializerInstance</code> contains the type information for a
 * static or anonymous initializer.
 */
public interface InitializerInstance extends CodeInstance
{
    /**
     * Set the initializer's flags.
     */
    InitializerInstance flags(Flags flags);

    /**
     * Set the initializer's containing class.
     */
    InitializerInstance container(ClassType container);
}
