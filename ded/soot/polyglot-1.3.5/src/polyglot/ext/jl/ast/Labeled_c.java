package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * Am immutable representation of a Java statement with a label.  A labeled
 * statement contains the statement being labelled and a string label.
 */
public class Labeled_c extends Stmt_c implements Labeled
{
    protected String label;
    protected Stmt statement;

    public Labeled_c(Position pos, String label, Stmt statement) {
	super(pos);
	this.label = label;
	this.statement = statement;
    }

    /** Get the label of the statement. */
    public String label() {
	return this.label;
    }

    /** Set the label of the statement. */
    public Labeled label(String label) {
	Labeled_c n = (Labeled_c) copy();
	n.label = label;
	return n;
    }

    /** Get the sub-statement of the statement. */
    public Stmt statement() {
	return this.statement;
    }

    /** Set the sub-statement of the statement. */
    public Labeled statement(Stmt statement) {
	Labeled_c n = (Labeled_c) copy();
	n.statement = statement;
	return n;
    }

    /** Reconstruct the statement. */
    protected Labeled_c reconstruct(Stmt statement) {
	if (statement != this.statement) {
	    Labeled_c n = (Labeled_c) copy();
	    n.statement = statement;
	    return n;
	}

	return this;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Stmt statement = (Stmt) visitChild(this.statement, v);
	return reconstruct(statement);
    }

    public String toString() {
	return label + ": " + statement;
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write(label + ": ");
	print(statement, w, tr);
    }

    public Term entry() {
        return statement.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.push(this).visitCFG(statement, this);
        return succs;
    }
}
