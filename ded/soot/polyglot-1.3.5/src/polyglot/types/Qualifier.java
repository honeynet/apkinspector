package polyglot.types;

/**
 * A <code>Qualifier</code> can be used to qualify a type: it can be either
 * a package or a named class type.
 */
public interface Qualifier extends TypeObject
{
    /**
     * Return true if the qualifier is a package.
     */
    boolean isPackage();

    /**
     * Cast the qualifier to a package, or return null.
     * This method will probably be deprecated.
     */
    Package toPackage();

    /**
     * Return true if the qualifier is a type.
     */
    boolean isType();

    /**
     * Cast the qualifier to a type, or return null.
     * This method will probably be deprecated.
     */
    Type toType();
}
