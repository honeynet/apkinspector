package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.main.Options;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>Branch</code> is an immutable representation of a branch
 * statment in Java (a break or continue).
 */
public class Branch_c extends Stmt_c implements Branch
{
    protected Branch.Kind kind;
    protected String label;

    public Branch_c(Position pos, Branch.Kind kind, String label) {
	super(pos);
	this.kind = kind;
	this.label = label;
    }

    /** Get the kind of the branch. */
    public Branch.Kind kind() {
	return this.kind;
    }

    /** Set the kind of the branch. */
    public Branch kind(Branch.Kind kind) {
	Branch_c n = (Branch_c) copy();
	n.kind = kind;
	return n;
    }

    /** Get the target label of the branch. */
    public String label() {
	return this.label;
    }

    /** Set the target label of the branch. */
    public Branch label(String label) {
	Branch_c n = (Branch_c) copy();
	n.label = label;
	return n;
    }

    public String toString() {
	return kind.toString() + (label != null ? " " + label : "");
    }

    /** Write the expression to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
  	  w.write(kind.toString());
	  if (label != null) {
	    w.write(" " + label);
	  }
      w.write(";");
    }

    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry() {
        return this;
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitBranchTarget(this);
        return Collections.EMPTY_LIST;
    }
}
