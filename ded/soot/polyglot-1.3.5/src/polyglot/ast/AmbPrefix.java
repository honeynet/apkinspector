package polyglot.ast;

/**
 * An <code>AmbPrefix</code> is an ambiguous AST node composed of dot-separated
 * list of identifiers that must resolve to a prefix.
 */
public interface AmbPrefix extends Prefix, Ambiguous
{
    /**
     * Prefix of the prefix.
     */
    Prefix prefix();

    /**
     * Ambiguous name.
     */
    String name();
}
