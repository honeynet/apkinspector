package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

/**
 * An <code>AmbPrefix</code> is an ambiguous AST node composed of dot-separated
 * list of identifiers that must resolve to a prefix.
 */
public class AmbPrefix_c extends Node_c implements AmbPrefix
{
    protected Prefix prefix;
    protected String name;

    public AmbPrefix_c(Position pos, Prefix prefix, String name) {
	super(pos);
	this.prefix = prefix;
	this.name = name;
    }

    /** Get the name of the prefix. */
    public String name() {
	return this.name;
    }

    /** Set the name of the prefix. */
    public AmbPrefix name(String name) {
	AmbPrefix_c n = (AmbPrefix_c) copy();
	n.name = name;
	return n;
    }

    /** Get the prefix of the prefix. */
    public Prefix prefix() {
	return this.prefix;
    }

    /** Set the prefix of the prefix. */
    public AmbPrefix prefix(Prefix prefix) {
	AmbPrefix_c n = (AmbPrefix_c) copy();
	n.prefix = prefix;
	return n;
    }

    /** Reconstruct the prefix. */
    protected AmbPrefix_c reconstruct(Prefix prefix) {
	if (prefix != this.prefix) {
	    AmbPrefix_c n = (AmbPrefix_c) copy();
	    n.prefix = prefix;
	    return n;
	}

	return this;
    }

    /** Visit the children of the prefix. */
    public Node visitChildren(NodeVisitor v) {
	Prefix prefix = (Prefix) visitChild(this.prefix, v);
	return reconstruct(prefix);
    }

    /** Disambiguate the prefix. */
    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
	return ar.nodeFactory().disamb().disambiguate(this, ar, position(), prefix, name);
    }

    /** Type check the prefix. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot type check ambiguous node " + this + ".");
    } 

    /** Check exceptions thrown by the prefix. */
    public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot exception check ambiguous node " + this + ".");
    } 

    /** Write the prefix to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	if (prefix != null) {
            print(prefix, w, tr);
            w.write(".");
        }
                
        w.write(name);
    }

    public String toString() {
	return (prefix == null
		? name
		: prefix.toString() + "." + name) + "{amb}";
    }
}
