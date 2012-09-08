package polyglot.visit;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.frontend.Job;
import java.util.*;

/** Visitor which allows type information to be utilized to perform AST 
    modifications.  
    
    The major advantage of this visitor is the new <code>ascribe()</code>
    method, which allows AST translations based on the expression
    and also the type that is expected. For the base translation (standard
    Java), the type of the expression and the type that is expceted 
    are the same. Language extensions however may not have this property, 
    and can take advantage of the <code>ascribe</code> method to transform
    the AST into a different form that will pass Java type-checking.
    
    @see #ascribe
    */
public class AscriptionVisitor extends ContextVisitor
{
    Type type;
    AscriptionVisitor outer;

    /**
     *  Default constructor. See the constructor in <code> ErrorHandingVisitor
     *  </code> for more details.
     * 
     *  @see polyglot.visit.ErrorHandlingVisitor#ErrorHandlingVisitor
     */
    public AscriptionVisitor(Job job, TypeSystem ts, NodeFactory nf) {
        super(job, ts, nf);
        type = null;
        outer = null;
    }

    // FIXME: what does this do?
    /**
     */
    public AscriptionVisitor pop() {
        return outer;
    }

    /** Returns the type that is expected of the expression that is being 
     *  visited.
     */
    public Type toType() {
        return type;
    }

    // TODO is this comment revealing too much implementation?
    /** Sets up the expected type information for later calls to 
     *  <code>ascribe()</code>. Other than that, plays the same role
     *  as the <code>enterCall</code> method in 
     *  <code>ErrorHandlingVisitor</code>.
     */
    public NodeVisitor enterCall(Node parent, Node n) throws SemanticException {
        Type t = null;

        if (parent != null && n instanceof Expr) {
            t = parent.childExpectedType((Expr) n, this);
        }

        AscriptionVisitor v = (AscriptionVisitor) copy();
        v.outer = this;
        v.type = t;

        return v;
    }

    /** The <code>ascribe()</code> method is called for each expression 
      * and is passed the type the expression is <i>used at</i> rather 
      * than the type the type 
      * checker assigns to it.
      *
      * For instance, with the following code:
      *
      *     <code>Object o = new Integer(3);</code>
      *
      * <code>ascribe()</code> will be called with expression 
      * <code>new Integer(3)</code> and type <code>Object</code>.
      *
      * @param e The expression that is being visited
      * @param toType The type that the parent node is expecting.
      * @return The new translated Expr node, or if nothing has changed, just
      * e.
      */
    public Expr ascribe(Expr e, Type toType) throws SemanticException {
        return e;
    }

    // TODO is this comment revealing too much implementation?
    /** Calls <code>ascribe()<code> with the expected type and expression 
     *  as appropriate. Otherwise functionally the same as the <code> 
     *  leaveCall</code> method in <code>ErrorHandlingVisitor</code>.
     */
    public Node leaveCall(Node old, Node n, NodeVisitor v)
        throws SemanticException {

        if (n instanceof Expr) {
            Expr e = (Expr) n;
            Type type = ((AscriptionVisitor) v).type;
            return ascribe(e, type);
        }

	return n;
    }
}
