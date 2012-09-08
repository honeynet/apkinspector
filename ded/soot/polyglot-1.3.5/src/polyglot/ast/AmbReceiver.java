package polyglot.ast;

/**
 * An <code>AmbReceiver</code> is an ambiguous AST node composed of
 * dot-separated list of identifiers that must resolve to a receiver.
 */
public interface AmbReceiver extends Ambiguous, Receiver
{
    /**
     * Prefix of the receiver.
     */
    Prefix prefix();

    /**
     * Ambiguous name.
     */
    String name();
}
