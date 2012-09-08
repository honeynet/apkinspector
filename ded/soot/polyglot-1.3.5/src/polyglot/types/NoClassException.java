package polyglot.types;

import polyglot.util.Position;

/**
 * Signals an error in the class resolver system. This exception is thrown
 * when a <code>ClassResolver</code> is unable to resolve a given class name.
 */
public class NoClassException extends SemanticException {
    private String className;
    
    public NoClassException(String className) {
        super("Class \"" + className + "\" not found."); 
        this.className = className;
    }
    
    public NoClassException(String className, Named scope) {
        super("Class \"" + className + "\" not found"
                + (scope != null ? (" in scope of " + scope.toString())
                                 : "."));
        this.className = className;
    }
  
    public NoClassException(String className, Position position) {
        super("Class \"" + className + "\" not found.", position);
        this.className = className;
    }
    
    public String getClassName() {
        return className;
    }
}
