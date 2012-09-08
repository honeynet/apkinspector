package polyglot.util;

/**
 * Interface used to copy objects.  Similar to Cloneable except that
 * <code>copy()</code> must be public not protected as <code>clone()</code> is.
 */
public interface Copy extends Cloneable
{
    public Object copy();
}
