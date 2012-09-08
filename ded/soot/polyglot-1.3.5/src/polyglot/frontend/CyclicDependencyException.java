package polyglot.frontend;

import java.util.*;

/**
 * Thrown during when the compiler tries to run a pass that is
 * already running.
 */
public class CyclicDependencyException extends Exception {
    public CyclicDependencyException() {
        super();
    }

    public CyclicDependencyException(String m) {
        super(m);
    }
}
