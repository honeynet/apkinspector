package polyglot.ast;

import polyglot.types.Type;
import polyglot.types.SemanticException;

/**
 * A <code>Catch</code> represents one half of a <code>try-catch</code>
 * statement.  Specifically, the second half.
 */
public interface Catch extends CompoundStmt
{
    /**
     * The type of the catch's formal.  This is the same as
     * formal().type().type().  May not be valid until after type-checking.
     */
    Type catchType();

    /**
     * The catch block's formal paramter.
     */
    Formal formal();

    /**
     * Set the catch block's formal paramter.
     */
    Catch formal(Formal formal);

    /**
     * The body of the catch block.
     */
    Block body();

    /**
     * Set the body of the catch block.
     */
    Catch body(Block body);
}
