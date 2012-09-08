package polyglot.ast;

/**
 * An <code>AmbTypeNode</code> is an ambiguous AST node composed of
 * dot-separated list of identifiers that must resolve to a type.
 */
public interface AmbTypeNode extends TypeNode, Ambiguous
{
    /**
     * Qualifier of the type.
     */
    QualifierNode qual();

    /**
     * Set the qualifier of the type.
     */
    AmbTypeNode qual(QualifierNode qual);

    /**
     * Ambiguous name.
     */
    String name();

    /**
     * Set the ambiguous name.
     */
    AmbTypeNode name(String name);
}
