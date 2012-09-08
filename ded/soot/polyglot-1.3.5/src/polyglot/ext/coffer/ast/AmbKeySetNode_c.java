package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import java.util.*;

/**
 * Implementation of an ambiguous key set AST node.
 */
public class AmbKeySetNode_c extends Node_c implements AmbKeySetNode
{
    protected List keys;
    protected KeySet keySet;

    public AmbKeySetNode_c(Position pos, List keys) {
        super(pos);
        this.keys = TypedList.copyAndCheck(keys, KeyNode.class, true);
    }

    public KeySet keys() {
        return keySet;
    }

    public List keyNodes() {
        return keys;
    }

    public AmbKeySetNode keyNodes(List keys) {
        AmbKeySetNode_c n = (AmbKeySetNode_c) copy();
        n.keys = TypedList.copyAndCheck(keys, KeyNode.class, true);
        return n;
    }

    public AmbKeySetNode_c reconstruct(List keys) {
        if (! CollectionUtil.equals(this.keys, keys)) {
            AmbKeySetNode_c n = (AmbKeySetNode_c) copy();
            n.keys = TypedList.copyAndCheck(keys, KeyNode.class, true);
            return n;
        }

        return this;
    }

    public Node visitChildren(NodeVisitor v) {
        List keys = visitList(this.keys, v);
        return reconstruct(keys);
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        CofferTypeSystem ts = (CofferTypeSystem) tb.typeSystem();

        KeySet s = ts.emptyKeySet(position());

        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            KeyNode key = (KeyNode) i.next();
            s = s.add(key.key());
        }

        AmbKeySetNode_c n = (AmbKeySetNode_c) copy();
        n.keys = keys;
        n.keySet = s;
        return n;
    }
        
    public Node disambiguate(AmbiguityRemover sc) throws SemanticException {
        CofferTypeSystem ts = (CofferTypeSystem) sc.typeSystem();
        CofferNodeFactory nf = (CofferNodeFactory) sc.nodeFactory();

        KeySet s = ts.emptyKeySet(position());

        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            KeyNode key = (KeyNode) i.next();

            if (key.key().isCanonical()) {
                s = s.add(key.key());
            }
            else {
                // return this;
                throw new SemanticException("Could not disambiguate " +
                                            this + ".");
            }
        }

        return nf.CanonicalKeySetNode(position(), s);
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("[");

        w.begin(0);

        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            KeyNode key = (KeyNode) i.next();
            print(key, w, tr);
            if (i.hasNext()) {
                w.write(",");
                w.allowBreak(0, " ");
            }
        }

        w.end();

        w.write("]");
    }

    public void translate(CodeWriter w, Translator tr) {
	throw new InternalCompilerError(position(),
	    "Cannot translate ambiguous key set " + this + ".");
    }

    public String toString() {
        String s = "[";

        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            KeyNode key = (KeyNode) i.next();

            s += key.toString();

            if (i.hasNext()) {
                s += ", ";
            }
        }

        s += "]";
        
        return s;
    }
}
