package polyglot.ast;

import polyglot.types.Flags;

/** 
 * An interface representing a variable.  A Variable is any expression
 * that can appear on the left-hand-side of an assignment.
 */
public interface Variable extends Expr
{
    /** Return the access flags of the variable, or Flags.NONE */
    public Flags flags();
}
