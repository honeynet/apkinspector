package polyglot.ast;

/**
 * A <code>FieldAssign</code> represents a Java assignment expression to
 * a field.  For instance, <code>this.x = e</code>.
 * 
 * The class of the <code>Expr</code> returned by
 * <code>FieldAssign.left()</code>is guaranteed to be a <code>Field</code>.
 */
public interface FieldAssign extends Assign
{
}
