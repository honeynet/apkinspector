package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

import java.util.*;

/**
 * A <code>Node</code> represents an AST node.  All AST nodes must implement
 * this interface.  Nodes should be immutable: methods which set fields
 * of the node should copy the node, set the field in the copy, and then
 * return the copy.
 */
public abstract class Node_c implements Node
{
    protected Position position;
    protected JL del;
    protected Ext ext;

    public Node_c(Position pos) {
        this.position = pos;
    }

    public void init(Node node) {
        if (node != this) {
            throw new InternalCompilerError("Cannot use a Node as a delegate or extension.");
        }
    }

    public Node node() {
        return this;
    }

    public JL del() {
        return del != null ? del : this;
    }

    public Node del(JL del) {
        if (this.del == del) {
            return this;
        }

        JL old = this.del;
        this.del = null;

        Node_c n = (Node_c) copy();

        n.del = del != this ? del : null;

        if (n.del != null) {
            n.del.init(n);
        }

        this.del = old;

        return n;
    }

    public Ext ext(int n) {
        if (n < 1) throw new InternalCompilerError("n must be >= 1");
        if (n == 1) return ext();
        return ext(n-1).ext();
    }

    public Node ext(int n, Ext ext) {
        if (n < 1)
            throw new InternalCompilerError("n must be >= 1");
        if (n == 1)
            return ext(ext);

        Ext prev = this.ext(n-1);
        if (prev == null)
            throw new InternalCompilerError("cannot set the nth extension if there is no (n-1)st extension");
        return this.ext(n-1, prev.ext(ext));
    }

    public Ext ext() {
        return ext;
    }

    public Node ext(Ext ext) {
        if (this.ext == ext) {
            return this;
        }

        Ext old = this.ext;
        this.ext = null;

        Node_c n = (Node_c) copy();

        n.ext = ext;

        if (n.ext != null) {
            n.ext.init(n);
        }

        this.ext = old;

        return n;
    }

    public Object copy() {
        try {
            Node_c n = (Node_c) super.clone();

            if (this.del != null) {
                n.del = (JL) this.del.copy();
                n.del.init(n);
            }

            if (this.ext != null) {
                n.ext = (Ext) this.ext.copy();
                n.ext.init(n);
            }

            return n;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalCompilerError("Java clone() weirdness.");
        }
    }

    public Position position() {
	return this.position;
    }

    public Node position(Position position) {
	Node_c n = (Node_c) copy();
	n.position = position;
	return n;
    }

    public Node visitChild(Node n, NodeVisitor v) {
	if (n == null) {
	    return null;
	}

	return v.visitEdge(this, n);
    }

    public Node visit(NodeVisitor v) {
	return v.visitEdge(null, this);
    }

    public Node visitEdge(Node parent, NodeVisitor v) {
	Node n = v.override(parent, this);

	if (n == null) {
	    NodeVisitor v_ = v.enter(parent, this);

	    if (v_ == null) {
		throw new InternalCompilerError(
		    "NodeVisitor.enter() returned null.");
	    }

	    n = this.del().visitChildren(v_);

	    if (n == null) {
		throw new InternalCompilerError(
		    "Node_c.visitChildren() returned null.");
	    }

	    n = v.leave(parent, this, n, v_);

	    if (n == null) {
		throw new InternalCompilerError(
		    "NodeVisitor.leave() returned null.");
	    }
	}

	return n;
    }

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
    public List visitList(List l, NodeVisitor v) {
	if (l == null) {
	    return null;
	}

        List result = l;
	List vl = new ArrayList(l.size());

	for (Iterator i = l.iterator(); i.hasNext(); ) {
	    Node n = (Node) i.next();
	    Node m = visitChild(n, v);
            if (n != m) {
                result = vl;
            }
	    vl.add(m);
	}

	return result;
    }

    public Node visitChildren(NodeVisitor v) {
	return this;
    }

    /**
     * Push a new scope upon entering this node, and add any declarations to the
     * context that should be in scope when visiting children of this node.
     * 
     * @param c the current <code>Context</code>
     * @return the <code>Context</code> to be used for visiting this node. 
     */
    public Context enterScope(Context c) { return c; }

    /**
     * Push a new scope for visiting the child node <code>child</code>. 
     * The default behavior is to delegate the call to the child node, and let
     * it add appropriate declarations that should be in scope. However,
     * this method gives parent nodes have the ability to modify this behavior.
     * 
     * @param child the child node about to be entered.
     * @param c the current <code>Context</code>
     * @return the <code>Context</code> to be used for visiting node 
     *           <code>child</code>
     */
    public Context enterScope(Node child, Context c) { 
        return child.del().enterScope(c); 
    }

    /**
     * Add any declarations to the context that should be in scope when
     * visiting later sibling nodes.
     */
    public void addDecls(Context c) { }

    // These methods override the methods in Ext_c.
    // These are the default implementation of these passes.

    public Node buildTypesOverride(TypeBuilder tb) throws SemanticException {
	return null;
    }

    public NodeVisitor buildTypesEnter(TypeBuilder tb) throws SemanticException {
	return tb;
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
	return this;
    }

    /** Remove any remaining ambiguities from the AST. */
    public Node disambiguateOverride(AmbiguityRemover ar) throws SemanticException {
	return null;
    }

    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
	return ar;
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
	return this;
    }

    /** Add members to a class. */
    public Node addMembersOverride(AddMemberVisitor am) throws SemanticException {
	return null;
    }

    public NodeVisitor addMembersEnter(AddMemberVisitor am) throws SemanticException {
	return am;
    }

    public Node addMembers(AddMemberVisitor am) throws SemanticException {
	return this;
    }

    /** Type check the AST. */
    public Node typeCheckOverride(TypeChecker tc) throws SemanticException {
	return null;
    }

    public NodeVisitor typeCheckEnter(TypeChecker tc) throws SemanticException {
	return tc;
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	return this;
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
	return child.type();
    }

    /** Check that exceptions are properly propagated throughout the AST. */
    public Node exceptionCheckOverride(ExceptionChecker ec) throws SemanticException {
	return null;
    }

    public NodeVisitor exceptionCheckEnter(ExceptionChecker ec) throws SemanticException {
	return ec.push();
    }

    public Node exceptionCheck(ExceptionChecker ec) throws SemanticException { 
        List l = this.del().throwTypes(ec.typeSystem());
        for (Iterator i = l.iterator(); i.hasNext(); ) {
            ec.throwsException((Type)i.next(), position());
        }
    	return this;
    }

    public List throwTypes(TypeSystem ts) {
       return Collections.EMPTY_LIST;
    }

    /** Pretty-print the AST using the given <code>CodeWriter</code>. */
    public void prettyPrint(CodeWriter w, PrettyPrinter pp) { }

    public void printBlock(Node n, CodeWriter w, PrettyPrinter pp) {
        w.begin(0);
        print(n, w, pp);
        w.end();
    }

    public void printSubStmt(Stmt stmt, CodeWriter w, PrettyPrinter pp) {
        if (stmt instanceof Block) {
            w.write(" ");
            print(stmt, w, pp);
        }
        else {
            w.allowBreak(4, " ");
            printBlock(stmt, w, pp);
        }
    }

    public void print(Node child, CodeWriter w, PrettyPrinter pp) {
        pp.print(this, child, w);
    }

    /** Translate the AST using the given <code>CodeWriter</code>. */
    public void translate(CodeWriter w, Translator tr) {
        // By default, just rely on the pretty printer.
        this.del().prettyPrint(w, tr);
    }

    public void dump(CodeWriter w) {
        w.write(StringUtil.getShortNameComponent(getClass().getName()));

        w.allowBreak(4, " ");
        w.begin(0);
        w.write("(del " + del() + ")");
        w.end();

        w.allowBreak(4, " ");
        w.begin(0);
        w.write("(ext ");
	if (ext() == null) w.write("null");
	else ext().dump(w);
	w.write(")");
        w.end();

        w.allowBreak(4, " ");
        w.begin(0);
        w.write("(position " + (position != null ? position.toString()
                                                  : "UNKNOWN") + ")");
        w.end();
    }

    public String toString() {
          // This is really slow and so you are encouraged to override.
          // return new StringPrettyPrinter(5).toString(this);

          // Not slow anymore.
          return getClass().getName();
    }
}
