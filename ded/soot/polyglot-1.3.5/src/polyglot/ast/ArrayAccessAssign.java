package polyglot.ast;

/**
 * A <code>ArrayAccessAssign</code> represents a Java assignment expression
 * to an array element.  For instance, <code>A[3] = e</code>.
 * 
 * The class of the <code>Expr</code> returned by
 * <code>ArrayAccessAssign.left()</code>is guaranteed to be an
 * <code>ArrayAccess</code>.
 */
public interface ArrayAccessAssign extends Assign
{
    boolean throwsArrayStoreException();
}
