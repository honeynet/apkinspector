package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;

/**
 * A canonical key set AST node.  This is just an AST node
 * veneer around a <code>KeySet</code> type object.
 */
public interface CanonicalKeySetNode extends KeySetNode
{
    public CanonicalKeySetNode keys(KeySet keys);
}
