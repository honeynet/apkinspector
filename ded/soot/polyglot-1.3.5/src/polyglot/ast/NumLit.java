package polyglot.ast;

/**
 * An integer literal: longs, ints, shorts, bytes, and chars.
 */
public interface NumLit extends Lit
{
    /** The literal's value. */
    long longValue();
}
