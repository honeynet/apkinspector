package polyglot.ast;

import polyglot.util.CodeWriter;
import polyglot.util.Copy;
import polyglot.types.SemanticException;
import polyglot.types.Context;
import polyglot.visit.*;

/**
 * <code>JL</code> contains all methods implemented by an AST node.
 * AST nodes and delegates for AST nodes must implement this interface.
 */
public interface JL extends NodeOps, Copy
{
    /** Pointer back to the node we are delegating for, possibly this. */
    public Node node();

    /** Initialize the back pointer to the node. */
    public void init(Node node);
}
