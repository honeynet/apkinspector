package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * An immutable representation of a Java language <code>while</code>
 * statement.  It contains a statement to be executed and an expression
 * to be tested indicating whether to reexecute the statement.
 */ 
public class While_c extends Loop_c implements While
{
    protected Expr cond;
    protected Stmt body;

    public While_c(Position pos, Expr cond, Stmt body) {
	super(pos);
	this.cond = cond;
	this.body = body;
    }

    /** Get the conditional of the statement. */
    public Expr cond() {
	return this.cond;
    }

    /** Set the conditional of the statement. */
    public While cond(Expr cond) {
	While_c n = (While_c) copy();
	n.cond = cond;
	return n;
    }

    /** Get the body of the statement. */
    public Stmt body() {
	return this.body;
    }

    /** Set the body of the statement. */
    public While body(Stmt body) {
	While_c n = (While_c) copy();
	n.body = body;
	return n;
    }

    /** Reconstruct the statement. */
    protected While_c reconstruct(Expr cond, Stmt body) {
	if (cond != this.cond || body != this.body) {
	    While_c n = (While_c) copy();
	    n.cond = cond;
	    n.body = body;
	    return n;
	}

	return this;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Expr cond = (Expr) visitChild(this.cond, v);
	Stmt body = (Stmt) visitChild(this.body, v);
	return reconstruct(cond, body);
    }

    /** Type check the statement. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();
	
	if (! ts.equals(cond.type(), ts.Boolean())) {
	    throw new SemanticException(
		"Condition of while statement must have boolean type.",
		cond.position());
	}
	
	return this;
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        TypeSystem ts = av.typeSystem();

        if (child == cond) {
            return ts.Boolean();
        }

        return child.type();
    }

    public String toString() {
	return "while (" + cond + ") ...";
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write("while (");
	printBlock(cond, w, tr);
	w.write(")");
	printSubStmt(body, w, tr);
    }

    public Term entry() {
        return cond.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        if (condIsConstantTrue()) {
            v.visitCFG(cond, body.entry());
        }
        else {
            v.visitCFG(cond, FlowGraph.EDGE_KEY_TRUE, body.entry(), 
                             FlowGraph.EDGE_KEY_FALSE, this);
        }

        v.push(this).visitCFG(body, cond.entry());

        return succs;
    }

    public Term continueTarget() {
        return cond.entry();
    }
}
