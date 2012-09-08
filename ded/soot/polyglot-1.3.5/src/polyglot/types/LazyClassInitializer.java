package polyglot.types;

/**
 * A LazyClassInitializer is responsible for initializing members of
 * a class after it has been created.  Members are initialized lazily
 * to correctly handle cyclic dependencies between classes.
 */
public interface LazyClassInitializer
{
    /**
     * Return true if the class is from a class file.
     */
    public boolean fromClassFile();

    /**
     * Initialize <code>ct</code>'s constructors.
     */
    public void initConstructors(ParsedClassType ct);

    /**
     * Initialize <code>ct</code>'s methods.
     */
    public void initMethods(ParsedClassType ct);

    /**
     * Initialize <code>ct</code>'s fields.
     */
    public void initFields(ParsedClassType ct);

    /**
     * Initialize <code>ct</code>'s member classes.
     */
    public void initMemberClasses(ParsedClassType ct);

    /**
     * Initialize <code>ct</code>'s interfaces.
     */
    public void initInterfaces(ParsedClassType ct);
}
