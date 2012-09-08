package polyglot.ast;

import polyglot.util.CodeWriter;
import polyglot.util.Copy;

/**
 * <code>Ext</code> is the super type of all node extension objects.
 * It contains a pointer back to the node it is extending and a possibly-null
 * pointer to another extension node.
 */
public interface Ext extends Copy
{
    /** The node that we are extending. */
    Node node();

    /**
     * Initialize the extension object's pointer back to the node.
     * This also initializes the back pointers for all extensions of
     * the extension.
     */
    void init(Node node);

    /** An extension of this extension, or null. */
    Ext ext();

    /** Set the extension of this extension. */
    Ext ext(Ext ext);

    /**
     * Dump the AST node for debugging purposes.
     */
    void dump(CodeWriter w);
}
