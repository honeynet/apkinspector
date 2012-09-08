package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * A immutable representation of a Java language <code>do</code> statement. 
 * It contains a statement to be executed and an expression to be tested 
 * indicating whether to reexecute the statement.
 */ 
public class Do_c extends Loop_c implements Do
{
    protected Stmt body;
    protected Expr cond;

    public Do_c(Position pos, Stmt body, Expr cond) {
	super(pos);
	this.body = body;
	this.cond = cond;
    }

    /** Get the body of the statement. */
    public Stmt body() {
	return this.body;
    }

    /** Set the body of the statement. */
    public Do body(Stmt body) {
	Do_c n = (Do_c) copy();
	n.body = body;
	return n;
    }

    /** Get the conditional of the statement. */
    public Expr cond() {
	return this.cond;
    }

    /** Set the conditional of the statement. */
    public Do cond(Expr cond) {
	Do_c n = (Do_c) copy();
	n.cond = cond;
	return n;
    }

    /** Reconstruct the statement. */
    protected Do_c reconstruct(Stmt body, Expr cond) {
	if (body != this.body || cond != this.cond) {
	    Do_c n = (Do_c) copy();
	    n.body = body;
	    n.cond = cond;
	    return n;
	}

	return this;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Stmt body = (Stmt) visitChild(this.body, v);
	Expr cond = (Expr) visitChild(this.cond, v);
	return reconstruct(body, cond);
    }

    /** Type check the statement. */
    public Node typeCheck(TypeChecker tc) throws SemanticException
    {
        TypeSystem ts = tc.typeSystem();

        if (! ts.equals(cond.type(), ts.Boolean())) {
	    throw new SemanticException(
		"Condition of do statement must have boolean type.",
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
	return "do { ... } while (" + cond + ")";
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr)
    {
	w.write("do ");
	printSubStmt(body, w, tr);
	w.write("while(");
	printBlock(cond, w, tr);
	w.write("); ");
    }


    public Term entry() {
        return body.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.push(this).visitCFG(body, cond.entry());

        if (condIsConstantTrue()) {
            v.visitCFG(cond, body.entry());
        }
        else {
            v.visitCFG(cond, FlowGraph.EDGE_KEY_TRUE, body.entry(), 
                             FlowGraph.EDGE_KEY_FALSE, this);
        }

        return succs;
    }

    public Term continueTarget() {
        return cond.entry();
    }
}
