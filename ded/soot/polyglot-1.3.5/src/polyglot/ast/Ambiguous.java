package polyglot.ast;

/**
 * <code>Ambiguous</code> represents an ambiguous AST node.  This interface is
 * just a tag.  These nodes should not appear after the disambiguate
 * pass.
 */
public interface Ambiguous extends Node
{
}
