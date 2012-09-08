package polyglot.ast;

import polyglot.types.Type;

/**
 * An <code>ArrayTypeNode</code> is a type node for a non-canonical
 * array type.
 */
public interface ArrayTypeNode extends TypeNode
{
    /**
     * Base of the array.
     */
    TypeNode base();

    /**
     * Set the base of the array.
     */
    ArrayTypeNode base(TypeNode base);
}
