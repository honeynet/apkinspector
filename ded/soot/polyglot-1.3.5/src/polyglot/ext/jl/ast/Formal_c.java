package polyglot.ext.jl.ast;

import polyglot.ast.*;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.main.Options;
import java.util.*;

/**
 * A <code>Formal</code> represents a formal parameter for a procedure
 * or catch block.  It consists of a type and a variable identifier.
 */
public class Formal_c extends Term_c implements Formal
{
    protected LocalInstance li;
    protected Flags flags;
    protected TypeNode type;
    protected String name;
    protected boolean reachable;

    public Formal_c(Position pos, Flags flags, TypeNode type,
                    String name)
    {
	super(pos);
        this.flags = flags;
        this.type = type;
        this.name = name;
    }

    /** Get the type of the formal. */
    public Type declType() {
        return type.type();
    }

    /** Get the flags of the formal. */
    public Flags flags() {
	return flags;
    }

    /** Set the flags of the formal. */
    public Formal flags(Flags flags) {
	Formal_c n = (Formal_c) copy();
	n.flags = flags;
	return n;
    }

    /** Get the type node of the formal. */
    public TypeNode type() {
	return type;
    }

    /** Set the type node of the formal. */
    public Formal type(TypeNode type) {
	Formal_c n = (Formal_c) copy();
	n.type = type;
	return n;
    }

    /** Get the name of the formal. */
    public String name() {
	return name;
    }

    /** Set the name of the formal. */
    public Formal name(String name) {
	Formal_c n = (Formal_c) copy();
	n.name = name;
	return n;
    }

    /** Get the local instance of the formal. */
    public LocalInstance localInstance() {
        return li;
    }

    /** Set the local instance of the formal. */
    public Formal localInstance(LocalInstance li) {
        Formal_c n = (Formal_c) copy();
	n.li = li;
	return n;
    }

    /** Reconstruct the formal. */
    protected Formal_c reconstruct(TypeNode type) {
	if (this.type != type) {
	    Formal_c n = (Formal_c) copy();
	    n.type = type;
	    return n;
	}

	return this;
    }

    /** Visit the children of the formal. */
    public Node visitChildren(NodeVisitor v) {
	TypeNode type = (TypeNode) visitChild(this.type, v);
	return reconstruct(type);
    }

    public void addDecls(Context c) {
        c.addVariable(li);
    }

    /** Write the formal to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
      w.write(flags.translate());

      print(type, w, tr);
      w.write(" ");
      w.write(name);
    }

    /** Build type objects for the formal. */
    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        Formal_c n = (Formal_c) super.buildTypes(tb);

        TypeSystem ts = tb.typeSystem();

        LocalInstance li = ts.localInstance(position(), Flags.NONE,
                                            ts.unknownType(position()), name());

        return n.localInstance(li);
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (declType().isCanonical() && ! li.type().isCanonical()) {
            TypeSystem ts = ar.typeSystem();
            LocalInstance li = ts.localInstance(position(), flags(),
                                                declType(), name());
            return localInstance(li);
        }

        return this;
    }

    /** Type check the formal. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();

	try {
	    ts.checkLocalFlags(flags());
	}
	catch (SemanticException e) {
	    throw new SemanticException(e.getMessage(), position());
	}

	return this;
    }

    public Term entry() {
        return this;
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        return succs;
    }

    public void dump(CodeWriter w) {
	super.dump(w);

	if (li != null) {
	    w.allowBreak(4, " ");
	    w.begin(0);
	    w.write("(instance " + li + ")");
	    w.end();
	}

	w.allowBreak(4, " ");
	w.begin(0);
	w.write("(name " + name + ")");
	w.end();
    }

    public String toString() {
        return flags.translate() + type + " " + name;
    }
}
