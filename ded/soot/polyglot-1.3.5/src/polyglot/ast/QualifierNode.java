package polyglot.ast;

import polyglot.types.TypeSystem;
import polyglot.types.Qualifier;
import polyglot.types.SemanticException;

/**
 * A <code>QualifierNode</code> represents any node that can be used as a type
 * qualifier (<code>polyglot.types.Qualifier</code>).  It can resolve to either
 * an enclosing type or can be a package.
 */
public interface QualifierNode extends Prefix
{
    /** The qualifier type object. */
    Qualifier qualifier();
}
