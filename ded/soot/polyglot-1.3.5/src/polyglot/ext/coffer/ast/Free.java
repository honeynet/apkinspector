package polyglot.ext.coffer.ast;

import polyglot.ast.*;

/**
 * This statement revokes the key associated with a tracked expression.
 * The expression cannot be evaluated after this statement executes.
 */
public interface Free extends Stmt
{
    Expr expr();
    Free expr(Expr expr);
}
