package polyglot.ext.jl.qq;

import polyglot.util.InternalCompilerError;
import polyglot.util.Position;

/**
 * Signals an error in the quasiquoter.
 */
public class QQError extends InternalCompilerError {
    public QQError(String msg, Position pos) {
        super(msg, pos);
    }
}
