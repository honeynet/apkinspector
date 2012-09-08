package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.ast.Assert;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import polyglot.main.Options;
import java.util.*;

/**
 * An <code>Assert</code> is an assert statement.
 */
public class Assert_c extends Stmt_c implements Assert
{
    protected Expr cond;
    protected Expr errorMessage;

    public Assert_c(Position pos, Expr cond, Expr errorMessage) {
	super(pos);
	this.cond = cond;
	this.errorMessage = errorMessage;
    }

    /** Get the condition to check. */
    public Expr cond() {
	return this.cond;
    }

    /** Set the condition to check. */
    public Assert cond(Expr cond) {
	Assert_c n = (Assert_c) copy();
	n.cond = cond;
	return n;
    }

    /** Get the error message to report. */
    public Expr errorMessage() {
	return this.errorMessage;
    }

    /** Set the error message to report. */
    public Assert errorMessage(Expr errorMessage) {
	Assert_c n = (Assert_c) copy();
	n.errorMessage = errorMessage;
	return n;
    }

    /** Reconstruct the statement. */
    protected Assert_c reconstruct(Expr cond, Expr errorMessage) {
	if (cond != this.cond || errorMessage != this.errorMessage) {
	    Assert_c n = (Assert_c) copy();
	    n.cond = cond;
	    n.errorMessage = errorMessage;
	    return n;
	}

	return this;
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();

        if (! Options.global.assertions) {
            ErrorQueue eq = tc.errorQueue();
            eq.enqueue(ErrorInfo.WARNING,
                       "assert statements are disabled. Recompile " +
                       "with -assert and ensure the post compiler supports " +
                       "assert (e.g., -post \"javac -source 1.4\"). " +
                       "Removing the statement and continuing.",
                       cond.position());
        }

        if (! ts.equals(cond.type(), ts.Boolean())) {
            throw new SemanticException("Condition of assert statement " +
                                        "must have boolean type.",
                                        cond.position());
        }

        if (errorMessage != null && ts.equals(errorMessage.type(), ts.Void())) {
            throw new SemanticException("Error message in assert statement " +
                                        "must have a value.",
                                        errorMessage.position());
        }

        return this;
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        TypeSystem ts = av.typeSystem();

        if (child == cond) {
            return ts.Boolean();
        }

        /*
        if (child == errorMessage) {
            return ts.String();
        }
        */

        return child.type();
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Expr cond = (Expr) visitChild(this.cond, v);
	Expr errorMessage = (Expr) visitChild(this.errorMessage, v);
	return reconstruct(cond, errorMessage);
    }

    public String toString() {
	return "assert " + cond.toString() +
                (errorMessage != null
                    ? ": " + errorMessage.toString() : "") + ";";
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("assert ");
	print(cond, w, tr);

        if (errorMessage != null) {
            w.write(": ");
            print(errorMessage, w, tr);
        }

        w.write(";");
    }

    public void translate(CodeWriter w, Translator tr) {
        if (! Options.global.assertions) {
            w.write(";");
        }
        else {
            prettyPrint(w, tr);
        }
    }

    public Term entry() {
        return cond.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        if (errorMessage != null) {
            v.visitCFG(cond, errorMessage.entry());
            v.visitCFG(errorMessage, this);
        }
        else {
            v.visitCFG(cond, this);
        }

        return succs;
    }
}
