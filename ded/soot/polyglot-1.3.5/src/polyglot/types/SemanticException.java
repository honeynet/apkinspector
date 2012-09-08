package polyglot.types;

import polyglot.util.Position;
import polyglot.main.Report;
import java.util.*;

/**
 * Thrown during any number of phases of the compiler during which a semantic
 * error may be detected.
 */
public class SemanticException extends Exception {
    protected Position position;
    
    public SemanticException() {
        super();
        trace(this, 5);
    }

    public SemanticException(Throwable cause) {
        super(cause);
        trace(this, 5);
    }

    public SemanticException(Position position) {
	super();
	this.position = position;
        trace(this, 5);
    }

    public SemanticException(String m) {
        super(m);
        trace(this, 5);
    }

    public SemanticException(String m, Throwable cause) {
        super(m, cause);
        trace(this, 5);
    }

    public SemanticException(String m, Position position) {
	super(m);
	this.position = position;
        trace(this, 5);
    }

    public Position position() {
	return position;
    }

    static void trace(Exception e, int level) {
        if (Report.should_report(Report.errors, level)) {
            e.printStackTrace();
        }
    }
}
