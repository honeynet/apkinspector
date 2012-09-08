package polyglot.visit;

import java.util.HashMap;
import java.util.Map;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.ErrorQueue;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.SubtypeSet;

/** Visitor which checks if exceptions are caught or declared properly. */
public class ExceptionChecker extends ErrorHandlingVisitor
{
    protected ExceptionChecker outer;
    /**
     * Lazily instantiate the SubtypeSet in the getter method
     */
    private SubtypeSet scope = null;
    protected Map exceptionPositions;

    public ExceptionChecker(Job job, TypeSystem ts, NodeFactory nf) {
	super(job, ts, nf);
	this.outer = null;
        this.exceptionPositions = new HashMap();
    }

    public ExceptionChecker pushNew() {
        ExceptionChecker ec = (ExceptionChecker) this.visitChildren();
        ec.scope = null;
        ec.outer = this;
        ec.exceptionPositions = new HashMap();
        return ec;
    }

    public ExceptionChecker push() {
        ExceptionChecker ec = (ExceptionChecker) this.visitChildren();
        ec.outer = this;
        ec.exceptionPositions = new HashMap();
        return ec;
    }

    public ExceptionChecker pop() {
        return outer;
    }

    /**
     * This method is called when we are to perform a "normal" traversal of 
     * a subtree rooted at <code>n</code>.   At every node, we will push a 
     * stack frame.  Each child node will add the exceptions that it throws
     * to this stack frame. For most nodes ( excdeption for the try / catch)
     * will just aggregate the stack frames.
     *
     * @param n The root of the subtree to be traversed.
     * @return The <code>NodeVisitor</code> which should be used to visit the 
     *  children of <code>n</code>.
     *
     */
    protected NodeVisitor enterCall(Node n) throws SemanticException {
	return n.exceptionCheckEnter(this);
    }

    protected NodeVisitor enterError(Node n) {
	return push();
    }

    /**
     * Here, we pop the stack frame that we pushed in enter and agregate the 
     * exceptions.
     *
     * @param old The original state of root of the current subtree.
     * @param n The current state of the root of the current subtree.
     * @param v The <code>NodeVisitor</code> object used to visit the children.
     * @return The final result of the traversal of the tree rooted at 
     *  <code>n</code>.
     */
    protected Node leaveCall(Node old, Node n, NodeVisitor v)
	throws SemanticException {
        
        ExceptionChecker inner = (ExceptionChecker) v;
        
        if (inner.outer != this) throw new InternalCompilerError("oops!");
        
        // gather exceptions from this node.
        n = n.del().exceptionCheck(inner);
        
        // Merge results from the children and free the checker used for the
        // children.
        SubtypeSet t = inner.throwsSet();
        throwsSet().addAll(t);
        exceptionPositions.putAll(inner.exceptionPositions);

	return n;
    }

    /**
     * The ast nodes will use this callback to notify us that they throw an 
     * exception of type t. This should only be called by MethodExpr node, 
     * and throw node, since they are the only node which can generate
     * exceptions.  
     *
     * @param t The type of exception that the node throws.
     */
    public void throwsException(Type t, Position pos) {
	throwsSet().add(t) ;
        exceptionPositions.put(t, pos);
    }
    
    /**
     * Method to allow the throws clause and method body to inspect and
     * modify the throwsSet.
     */
    public SubtypeSet throwsSet() {
        if (scope == null) {
            this.scope = new SubtypeSet(ts.Throwable());            
        }
        return scope;
    }
    
    /**
     * Method to determine the position at which a particular exception is 
     * thrown
     */
    public Position exceptionPosition(Type t) {
        return (Position)exceptionPositions.get(t);
    }
}
