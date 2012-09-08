package polyglot.ast;

import polyglot.util.Copy;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.types.Type;
import polyglot.visit.*;
import java.util.List;

/**
 * A <code>Node</code> represents an AST node.  All AST nodes must implement
 * this interface.  Nodes should be immutable: methods which set fields
 * of the node should copy the node, set the field in the copy, and then
 * return the copy.
 */
public interface Node extends JL, Copy
{
    /**
     * Set the delegate of the node.
     */
    Node del(JL del);

    /**
     * Get the node's delegate.
     */
    JL del();

    /**
     * Set the extension of the node.
     */
    Node ext(Ext ext);

    /**
     * Get the node's extension.
     */
    Ext ext();

    /**
     * Set the node's nth extension, n &gt;= 1.
     */
    Node ext(int n, Ext ext);

    /**
     * Get the node's nth extension, n &gt;= 1.
     */
    Ext ext(int n);

    /**
     * Get the position of the node in the source file.  Returns null if
     * the position is not set.
     */
    Position position();

    /** Create a copy of the node with a new position. */
    Node position(Position position);

    /**
     * Visit the node.  This method is equivalent to
     * <code>visitEdge(null, v)</code>.
     *
     * @param v The visitor which will traverse/rewrite the AST.
     * @return A new AST if a change was made, or <code>this</code>.
     */
    Node visit(NodeVisitor v);

    /**
     * Visit the node, passing in the node's parent.  This method is called by
     * a <code>NodeVisitor</code> to traverse the AST starting at this node.
     * This method should call the <code>override</code>, <code>enter</code>,
     * and <code>leave<code> methods of the visitor.  The method may return a
     * new version of the node.
     *
     * @param parent The parent of <code>this</code> in the AST.
     * @param v The visitor which will traverse/rewrite the AST.
     * @return A new AST if a change was made, or <code>this</code>.
     */
    Node visitEdge(Node parent, NodeVisitor v);

    /**
     * Visit a single child of the node.
     *
     * @param v The visitor which will traverse/rewrite the AST.
     * @param child The child to visit.
     * @return The result of <code>child.visit(v)</code>, or <code>null</code>
     * if <code>child</code> was <code>null</code>.
     */
    Node visitChild(Node child, NodeVisitor v);

    /**
     * Visit all the elements of a list.
     * @param l The list to visit.
     * @param v The visitor to use.
     * @return A new list with each element from the old list
     *         replaced by the result of visiting that element.
     *         If <code>l</code> is a <code>TypedList</code>, the
     *         new list will also be typed with the same type as 
     *         <code>l</code>.  If <code>l</code> is <code>null</code>,
     *         <code>null</code> is returned.
     */
    public List visitList(List l, NodeVisitor v);

    /**
     * Get the expected type of a child expression of <code>this</code>.
     * The expected type is determined by the context in that the child occurs
     * (e.g., for <code>x = e</code>, the expected type of <code>e</code> is
     * the declared type of <code>x</code>.
     *
     * The expected type should impose the least constraints on the child's
     * type that are allowed by the parent node.
     *
     * @param child A child expression of this node.
     * @param av An ascription visitor.
     * @return The expected type of <code>child</code>.
     */
    Type childExpectedType(Expr child, AscriptionVisitor av);

    /**
     * Dump the AST node for debugging purposes.
     */
    void dump(CodeWriter w);
}
