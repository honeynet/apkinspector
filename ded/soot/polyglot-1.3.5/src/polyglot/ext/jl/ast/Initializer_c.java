package polyglot.ext.jl.ast;

import java.util.Iterator;
import java.util.List;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.util.SubtypeSet;
import polyglot.visit.*;

/**
 * An <code>Initializer</code> is an immutable representation of an
 * initializer block in a Java class (which appears outside of any
 * method).  Such a block is executed before the code for any of the
 * constructors.  Such a block can optionally be static, in which case
 * it is executed when the class is loaded.
 */
public class Initializer_c extends Term_c implements Initializer
{
    protected Flags flags;
    protected Block body;
    protected InitializerInstance ii;

    public Initializer_c(Position pos, Flags flags, Block body) {
	super(pos);
	this.flags = flags;
	this.body = body;
    }

    /** Get the flags of the initializer. */
    public Flags flags() {
	return this.flags;
    }

    /** Set the flags of the initializer. */
    public Initializer flags(Flags flags) {
	Initializer_c n = (Initializer_c) copy();
	n.flags = flags;
	return n;
    }

    /** Get the initializer instance of the initializer. */
    public InitializerInstance initializerInstance() {
        return ii;
    }

    public CodeInstance codeInstance() {
	return initializerInstance();
    }

    /** Set the initializer instance of the initializer. */
    public Initializer initializerInstance(InitializerInstance ii) {
	Initializer_c n = (Initializer_c) copy();
	n.ii = ii;
	return n;
    }

    /** Get the body of the initializer. */
    public Block body() {
	return this.body;
    }

    /** Set the body of the initializer. */
    public CodeDecl body(Block body) {
	Initializer_c n = (Initializer_c) copy();
	n.body = body;
	return n;
    }

    /** Reconstruct the initializer. */
    protected Initializer_c reconstruct(Block body) {
	if (body != this.body) {
	    Initializer_c n = (Initializer_c) copy();
	    n.body = body;
	    return n;
	}

	return this;
    }

    /** Visit the children of the initializer. */
    public Node visitChildren(NodeVisitor v) {
	Block body = (Block) visitChild(this.body, v);
	return reconstruct(body);
    }

    public Context enterScope(Context c) {
	return c.pushCode(ii);
    }

    public NodeVisitor buildTypesEnter(TypeBuilder tb) throws SemanticException {
        return tb.pushCode();
    }

    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry() {
        return this.body().entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFG(this.body(), this);
        return succs;
    }

    /** Build type objects for the method. */
    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        TypeSystem ts = tb.typeSystem();
        ClassType ct = tb.currentClass();
        InitializerInstance ii = ts.initializerInstance(position(), ct, flags);
        return initializerInstance(ii);
    }

    public NodeVisitor addMembersEnter(AddMemberVisitor am) {
        // do not add members for the children of this node.
        return am.bypassChildren(this);
    }

    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
        // Do not visit body on the clean-super and clean-signatures passes.
        if (ar.kind() == AmbiguityRemover.SUPER ||
            ar.kind() == AmbiguityRemover.SIGNATURES) {
            return ar.bypassChildren(this);
        }
        return ar;
    }

    /** Type check the initializer. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();

	try {
	    ts.checkInitializerFlags(flags());
	}
	catch (SemanticException e) {
	    throw new SemanticException(e.getMessage(), position());
	}

        // check that inner classes do not declare static initializers
        if (flags().isStatic() &&
              initializerInstance().container().toClass().isInnerClass()) {
            // it's a static initializer in an inner class.
            throw new SemanticException("Inner classes cannot declare " + 
                    "static initializers.", this.position());             
        }

	return this;
    }

    /** Check exceptions thrown by the initializer. */
    public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
      	TypeSystem ts = ec.typeSystem();

	SubtypeSet s = (SubtypeSet) ec.throwsSet();

	for (Iterator i = s.iterator(); i.hasNext(); ) {
	    Type t = (Type) i.next();

	    if (! t.isUncheckedException()) {
                // TODO: This should agree with Java Language Spec 2nd Ed. 8.6
                // An instance initializer of a named class may not throw
                // a checked exception unless that exception or one of its 
                // superclasses is explicitly declared in the throws clause
                // of each contructor or its class, and the class has at least
                // one explicitly declared constructor.
                if (initializerInstance().flags().isStatic()) {
                    throw new SemanticException(
                        "A static initializer block may not throw a " + t + ".",
                        ec.exceptionPosition(t));
                }
                
                if (!initializerInstance().container().toClass().isAnonymous()) {
                        // XXX should only throw this if it is not common to all
                        // declared constructors, and there is at least one
                        // declared constructor.
        		throw new SemanticException(
        		    "An instance initializer block may not throw a " + t + ".",
                            ec.exceptionPosition(t));
                }
	    }
	}

	return super.exceptionCheck(ec);
    }

    /** Write the initializer to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write(flags.translate());
	printBlock(body, w, tr);
    }

    public void dump(CodeWriter w) {
	super.dump(w);

	if (ii != null) {
	    w.allowBreak(4, " ");
	    w.begin(0);
	    w.write("(instance " + ii + ")");
	    w.end();
	}
    }

    public String toString() {
	return flags.translate() + "{ ... }";
    }
}
