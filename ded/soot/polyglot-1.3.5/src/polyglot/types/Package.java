package polyglot.types;

/**
 * An <code>Package</code> represents a Java package.
 */
public interface Package extends Qualifier, Named
{
    /**
     * The package's outer package.
     */
    Package prefix();

    /**
     * Return a string that is the translation of this package.
     * @param c A resolver in which to look up the package.
     */
    String translate(Resolver c);
}
