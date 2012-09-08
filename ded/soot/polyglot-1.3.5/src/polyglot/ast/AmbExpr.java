package polyglot.ast;

/**
 * An <code>AmbExpr</code> is an ambiguous AST node composed of a single
 * identifier that must resolve to an expression.
 */
public interface AmbExpr extends Expr, Ambiguous
{
    /**
     * Ambiguous name.
     */
    String name();
    AmbExpr name(String name);
}
