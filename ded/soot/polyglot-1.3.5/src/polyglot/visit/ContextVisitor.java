package polyglot.visit;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.frontend.Job;
import polyglot.main.Report;
import java.util.*;

/**
 * A visitor which maintains a context throughout the visitor's pass.  This is 
 * the base class of the disambiguation and type checking visitors.
 *
 * TODO: update this documentation.
 * For a node <code>n</code> methods are called in this order:
 * <pre>
 * v.enter(n)
 *   v.enterScope(n);
 *     c' = n.enterScope(c)
 *   v' = copy(v) with c' for c
 * n' = n.visitChildren(v')
 * v.leave(n, n', v')
 *   v.addDecls(n')
 *     n.addDecls(c)
 * </pre>
 */
public class ContextVisitor extends ErrorHandlingVisitor
{
    protected ContextVisitor outer;

    /** The current context of this visitor. */
    protected Context context;

    public ContextVisitor(Job job, TypeSystem ts, NodeFactory nf) {
        super(job, ts, nf);
        this.outer = null;
        this.context = null;
    }

    public NodeVisitor begin() {
        context = job.context();

        if (context == null) {
            context = ts.createContext();
        }

        outer = null;

        return super.begin();
    }

    /** Returns the context for this visitor.
     *
     *  @return Returns the context that is currently in use by this visitor.
     *  @see polyglot.types.Context
     */
    public Context context() {
        return context;
    }

    /** Returns a new ContextVisitor that is a copy of the current visitor,
     *  except with an updated context.
     *
     *  @param c The new context that is to be used.
     *  @return Returns a copy of this visitor with the new context 
     *  <code>c</code>.
     */
    public ContextVisitor context(Context c) {
        ContextVisitor v = (ContextVisitor) this.copy();
        v.context = c;
        return v;
    }

    /**
     * Returns a new context based on the current context, the Node current 
     * being visited (<code>parent</code>), and the Node that is being 
     * entered (<code>n</code>).  This new context is to be used
     * for visiting <code>n</code>. 
     *
     * @return The new context after entering Node <code>n</code>.
     */
    protected Context enterScope(Node parent, Node n) {
        if (parent != null) {
            return parent.del().enterScope(n, context);
        }
        // no parent node yet.
        return n.del().enterScope(context);
    }

    /**
     * Imperatively update the context with declarations to be added after
     * visiting the node.
     */
    protected void addDecls(Node n) {
        n.addDecls(context);
    }

    public NodeVisitor enter(Node parent, Node n) {
        if (Report.should_report(Report.visit, 5))
	    Report.report(5, "enter(" + n + ")");

        ContextVisitor v = this;

        Context c = this.enterScope(parent, n);

        if (c != this.context) {
            v = (ContextVisitor) this.copy();
            v.context = c;
            v.outer = this;
            v.error = false;
        }

        return v.superEnter(parent, n);
    }

    public NodeVisitor superEnter(Node parent, Node n) {
        return super.enter(parent, n);
    }

    public Node leave(Node parent, Node old, Node n, NodeVisitor v) {
        Node m = super.leave(parent, old, n, v);

        this.addDecls(m);

        return m;
    }
}
