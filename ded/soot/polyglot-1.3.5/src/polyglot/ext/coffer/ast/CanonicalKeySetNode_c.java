package polyglot.ext.coffer.ast;

import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

/**
 * Implementation of a canonical key set AST node.  This is just an AST node
 * veneer around a <code>KeySet</code> type object.
 */
public class CanonicalKeySetNode_c extends Node_c implements CanonicalKeySetNode
{
    protected KeySet keys;

    public CanonicalKeySetNode_c(Position pos, KeySet keys) {
        super(pos);
        this.keys = keys;
    }

    public KeySet keys() {
        return keys;
    }

    public CanonicalKeySetNode keys(KeySet keys) {
        CanonicalKeySetNode_c n = (CanonicalKeySetNode_c) copy();
        n.keys = keys;
        return n;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write(keys.toString());
    }

    public void translate(CodeWriter w, Translator tr) {
	throw new InternalCompilerError(position(),
	    "Cannot translate " + this + ".");
    }

    public String toString() {
        return keys.toString();
    }
}
