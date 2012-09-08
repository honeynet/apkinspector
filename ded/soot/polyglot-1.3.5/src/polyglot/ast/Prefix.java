package polyglot.ast;

/**
 * A <code>Prefix</code> represents any node that can be used as the
 * prefix of a <code>Receiver</code>.  If the receiver is a type, its prefix
 * can either be an enclosing type or can be a package.  If the receiver is an
 * expression, its prefix can be either an expression or a type.
 */
public interface Prefix extends Node
{
}
