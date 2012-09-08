package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;

/**
 * An AST node for a <code>KeySet</code>.  The key set may be ambiguous. 
 */
public interface KeySetNode extends Node
{
    KeySet keys();
}
