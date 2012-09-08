package polyglot.main;

/** An exception used to indicate a command-line usage error. */
public class UsageError extends Exception {
    final int exitCode;
    public UsageError(String s) { this(s,1); }
    public UsageError(String s, int exitCode) { 
            super(s); 
            this.exitCode = exitCode; 
    }
}
