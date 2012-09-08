package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.frontend.*;
import java.util.*;

/**
 * A local class declaration statement.  The node is just a wrapper around
 * a class declaration.
 */
public class LocalClassDecl_c extends Stmt_c implements LocalClassDecl
{
    protected ClassDecl decl;

    public LocalClassDecl_c(Position pos, ClassDecl decl) {
	super(pos);
	this.decl = decl;
    }

    /** Get the class declaration. */
    public ClassDecl decl() {
	return this.decl;
    }

    /** Set the class declaration. */
    public LocalClassDecl decl(ClassDecl decl) {
	LocalClassDecl_c n = (LocalClassDecl_c) copy();
	n.decl = decl;
	return n;
    }

    /** Reconstruct the statement. */
    protected LocalClassDecl_c reconstruct(ClassDecl decl) {
        if (decl != this.decl) {
	    LocalClassDecl_c n = (LocalClassDecl_c) copy();
	    n.decl = decl;
	    return n;
	}

	return this;
    }

    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry() {
        return this.decl().entry();
    }

    /**
     * Visit this term in evaluation order.
     */
    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFG(this.decl(), this);
        return succs;
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
        ClassDecl decl = (ClassDecl) visitChild(this.decl, v);
        return reconstruct(decl);
    }

    public void addDecls(Context c) {
        // We should now be back in the scope of the enclosing block.
        // Add the type.
        if (! decl.type().toClass().isLocal())
            throw new InternalCompilerError("Non-local " + decl.type() +
                                            " found in method body.");
        c.addNamed(decl.type().toClass());
    }

    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
        return ar.bypassChildren(this);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (ar.kind() == AmbiguityRemover.ALL) {
            Job sj = ar.job().spawn(ar.context(), decl,
                                    Pass.CLEAN_SUPER, Pass.ADD_MEMBERS_ALL);

            if (! sj.status()) {
                if (! sj.reportedErrors()) {
                    throw new SemanticException("Could not disambiguate local " +
                                                "class \"" + decl.name() + "\".",
                                                position());
                }
                throw new SemanticException();
            }

            ClassDecl d = (ClassDecl) sj.ast();
            LocalClassDecl n = decl(d);
            return n.visitChildren(ar);
        }

        return this;
    }

    public String toString() {
	return decl.toString();
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        printBlock(decl, w, tr);
	w.write(";");
    }
}
