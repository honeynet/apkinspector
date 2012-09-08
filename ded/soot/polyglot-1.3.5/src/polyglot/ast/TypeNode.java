package polyglot.ast;

import polyglot.types.Type;
import polyglot.types.SemanticException;

/**
 * A <code>TypeNode</code> is the syntactic representation of a 
 * <code>Type</code> within the abstract syntax tree.
 */
public interface TypeNode extends Receiver, QualifierNode
{
    /** Set the type object for this node. */
    TypeNode type(Type type);

    /** Short name of the type, or null if the type is not <code>Named</code>. */
    String name();
}
