package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * An immutable representation of a Java language <code>for</code>
 * statement.  Contains a statement to be executed and an expression
 * to be tested indicating whether to reexecute the statement.
 */
public class For_c extends Loop_c implements For
{
    protected List inits;
    protected Expr cond;
    protected List iters;
    protected Stmt body;

    public For_c(Position pos, List inits, Expr cond, List iters, Stmt body) {
	super(pos);
	this.inits = TypedList.copyAndCheck(inits, ForInit.class, true);
	this.cond = cond;
	this.iters = TypedList.copyAndCheck(iters, ForUpdate.class, true);
	this.body = body;
    }

    /** List of initialization statements */
    public List inits() {
	return Collections.unmodifiableList(this.inits);
    }

    /** Set the inits of the statement. */
    public For inits(List inits) {
	For_c n = (For_c) copy();
	n.inits = TypedList.copyAndCheck(inits, ForInit.class, true);
	return n;
    }

    /** Loop condition */
    public Expr cond() {
	return this.cond;
    }

    /** Set the conditional of the statement. */
    public For cond(Expr cond) {
	For_c n = (For_c) copy();
	n.cond = cond;
	return n;
    }

    /** List of iterator expressions. */
    public List iters() {
	return Collections.unmodifiableList(this.iters);
    }

    /** Set the iterator expressions of the statement. */
    public For iters(List iters) {
	For_c n = (For_c) copy();
	n.iters = TypedList.copyAndCheck(iters, ForUpdate.class, true);
	return n;
    }

    /** Loop body */
    public Stmt body() {
	return this.body;
    }

    /** Set the body of the statement. */
    public For body(Stmt body) {
	For_c n = (For_c) copy();
	n.body = body;
	return n;
    }

    /** Reconstruct the statement. */
    protected For_c reconstruct(List inits, Expr cond, List iters, Stmt body) {
	if (! CollectionUtil.equals(inits, this.inits) || cond != this.cond || ! CollectionUtil.equals(iters, this.iters) || body != this.body) {
	    For_c n = (For_c) copy();
	    n.inits = TypedList.copyAndCheck(inits, ForInit.class, true);
	    n.cond = cond;
	    n.iters = TypedList.copyAndCheck(iters, ForUpdate.class, true);
	    n.body = body;
	    return n;
	}

	return this;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	List inits = visitList(this.inits, v);
	Expr cond = (Expr) visitChild(this.cond, v);
	List iters = visitList(this.iters, v);
	Stmt body = (Stmt) visitChild(this.body, v);
	return reconstruct(inits, cond, iters, body);
    }

    public Context enterScope(Context c) {
	return c.pushBlock();
    }

    /** Type check the statement. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();

        // Check that all initializers have the same type.
        // This should be enforced by the parser, but check again here,
        // just to be sure.
        Type t = null;

        for (Iterator i = inits.iterator(); i.hasNext(); ) {
            ForInit s = (ForInit) i.next();

            if (s instanceof LocalDecl) {
                LocalDecl d = (LocalDecl) s;
                Type dt = d.type().type();
                if (t == null) {
                    t = dt;
                }
                else if (! t.equals(dt)) {
                    throw new InternalCompilerError("Local variable " +
                        "declarations in a for loop initializer must all " +
                        "be the same type, in this case " + t + ", not " +
                        dt + ".", d.position());
                }
            }
        }

	if (cond != null &&
	    ! ts.isImplicitCastValid(cond.type(), ts.Boolean())) {
	    throw new SemanticException(
		"The condition of a for statement must have boolean type.",
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

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write("for (");
	w.begin(0);

	if (inits != null) {
            boolean first = true;
	    for (Iterator i = inits.iterator(); i.hasNext(); ) {
		ForInit s = (ForInit) i.next();
	        printForInit(s, w, tr, first);
                first = false;

		if (i.hasNext()) {
		    w.write(",");
		    w.allowBreak(2, " ");
		}
	    }
	}

	w.write(";"); 
	w.allowBreak(0);

	if (cond != null) {
	    printBlock(cond, w, tr);
	}

	w.write (";");
	w.allowBreak(0);
	
	if (iters != null) {
	    for (Iterator i = iters.iterator(); i.hasNext();) {
		ForUpdate s = (ForUpdate) i.next();
		printForUpdate(s, w, tr);
		
		if (i.hasNext()) {
		    w.write(",");
		    w.allowBreak(2, " ");
		}
	    }
	}

	w.end();
	w.write(")");

	printSubStmt(body, w, tr);
    }

    public String toString() {
	return "for (...) ...";
    }

    private void printForInit(ForInit s, CodeWriter w, PrettyPrinter tr, boolean printType) {
        boolean oldSemiColon = tr.appendSemicolon(false);
        boolean oldPrintType = tr.printType(printType);
        printBlock(s, w, tr);
        tr.printType(oldPrintType);
        tr.appendSemicolon(oldSemiColon);
    }

    private void printForUpdate(ForUpdate s, CodeWriter w, PrettyPrinter tr) {
        boolean oldSemiColon = tr.appendSemicolon(false);
        printBlock(s, w, tr);
        tr.appendSemicolon(oldSemiColon);
    }

    public Term entry() {
        return listEntry(inits, (cond != null ? cond.entry() : body.entry()));
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFGList(inits, (cond != null ? cond.entry() : body.entry()));

        if (cond != null) {
            if (condIsConstantTrue()) {
                v.visitCFG(cond, body.entry());
            }
            else {
                v.visitCFG(cond, FlowGraph.EDGE_KEY_TRUE, body.entry(), 
                                 FlowGraph.EDGE_KEY_FALSE, this);
            }
        }

        v.push(this).visitCFG(body, continueTarget());
        v.visitCFGList(iters, (cond != null ? cond.entry() : body.entry()));

        return succs;
    }

    public Term continueTarget() {
        return listEntry(iters, (cond != null ? cond.entry() : body.entry()));
    }
}
