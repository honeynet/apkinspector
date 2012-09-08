package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * An immutable representation of a Java language <code>if</code> statement.
 * Contains an expression whose value is tested, a ``then'' statement 
 * (consequent), and optionally an ``else'' statement (alternate).
 */
public class If_c extends Stmt_c implements If
{
    protected Expr cond;
    protected Stmt consequent;
    protected Stmt alternative;

    public If_c(Position pos, Expr cond, Stmt consequent, Stmt alternative) {
	super(pos);
	this.cond = cond;
	this.consequent = consequent;
	this.alternative = alternative;
    }

    /** Get the conditional of the statement. */
    public Expr cond() {
	return this.cond;
    }

    /** Set the conditional of the statement. */
    public If cond(Expr cond) {
	If_c n = (If_c) copy();
	n.cond = cond;
	return n;
    }

    /** Get the consequent of the statement. */
    public Stmt consequent() {
	return this.consequent;
    }

    /** Set the consequent of the statement. */
    public If consequent(Stmt consequent) {
	If_c n = (If_c) copy();
	n.consequent = consequent;
	return n;
    }

    /** Get the alternative of the statement. */
    public Stmt alternative() {
	return this.alternative;
    }

    /** Set the alternative of the statement. */
    public If alternative(Stmt alternative) {
	If_c n = (If_c) copy();
	n.alternative = alternative;
	return n;
    }

    /** Reconstruct the statement. */
    protected If_c reconstruct(Expr cond, Stmt consequent, Stmt alternative) {
	if (cond != this.cond || consequent != this.consequent || alternative != this.alternative) {
	    If_c n = (If_c) copy();
	    n.cond = cond;
	    n.consequent = consequent;
	    n.alternative = alternative;
	    return n;
	}

	return this;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Expr cond = (Expr) visitChild(this.cond, v);
	Stmt consequent = (Stmt) visitChild(this.consequent, v);
	Stmt alternative = (Stmt) visitChild(this.alternative, v);
	return reconstruct(cond, consequent, alternative);
    }

    /** Type check the statement. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();

	if (! ts.equals(cond.type(), ts.Boolean())) {
	    throw new SemanticException(
		"Condition of if statement must have boolean type.",
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
	return "if (" + cond + ") " + consequent +
	    (alternative != null ? " else " + alternative : "");
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {    
	w.write("if (");
	printBlock(cond, w, tr);
	w.write(")");
       
	printSubStmt(consequent, w, tr);

	if (alternative != null) {
	    if (consequent instanceof Block) {
		// allow the "} else {" formatting
		w.write(" ");
	    }
	    else {
		w.allowBreak(0, " ");
	    }

	    w.write ("else");
	    printSubStmt(alternative, w, tr);
	}
    }

    public Term entry() {
        return cond.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        if (alternative == null) {
            v.visitCFG(cond, FlowGraph.EDGE_KEY_TRUE, consequent.entry(), 
                             FlowGraph.EDGE_KEY_FALSE, this);
            v.visitCFG(consequent, this);
        }
        else {
            v.visitCFG(cond, FlowGraph.EDGE_KEY_TRUE, consequent.entry(),
                             FlowGraph.EDGE_KEY_FALSE, alternative.entry());
            v.visitCFG(consequent, this);
            v.visitCFG(alternative, this);
        }

        return succs;
    }
}
