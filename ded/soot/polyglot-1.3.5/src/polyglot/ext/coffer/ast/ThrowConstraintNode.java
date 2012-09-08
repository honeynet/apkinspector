package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.ext.coffer.types.*;

/**
 * An AST node for an exception throw declaration annotated with a key set.
 */
public interface ThrowConstraintNode extends Node {
    TypeNode type();
    KeySetNode keys();
    ThrowConstraint constraint();

    ThrowConstraintNode type(TypeNode type);
    ThrowConstraintNode keys(KeySetNode keys);
    ThrowConstraintNode constraint(ThrowConstraint constraint);
}
