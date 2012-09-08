package polyglot.ast;

/**
 * An <code>AmbQualifierNode</code> is an ambiguous AST node composed of
 * dot-separated list of identifiers that must resolve to a type qualifier.
 */
public interface AmbQualifierNode extends Ambiguous, QualifierNode
{
    /**
     * Qualifier of the qualifier.
     */
    QualifierNode qual();

    /**
     * Ambiguous name.
     */
    String name();
}
