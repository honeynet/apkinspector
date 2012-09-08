package polyglot.types;

/**
 * An <code>Importable</code> is a type object that can be imported by another
 * type object.  An <code>Importable</code> is contained in a
 * <code>Package</code>.  
 */
public interface Importable extends Named
{
    Package package_();
}
