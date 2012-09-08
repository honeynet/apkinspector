package polyglot.types;

import polyglot.ast.*;
import polyglot.util.*;
import java.util.*;

/**
 * An <code>CompoundResolver</code> resolves names using more than one
 * context.
 */
public class CompoundResolver implements TopLevelResolver {
    protected TopLevelResolver head;
    protected TopLevelResolver tail;

    /**
     * Create a compound resolver.
     * @param head The first resolver to search.
     * @param tail The second resolver to search.
     */
    public CompoundResolver(TopLevelResolver head, TopLevelResolver tail) {
	this.head = head;
	this.tail = tail;
    }

    public String toString() {
        return "(compound " + head + " " + tail + ")";
    }
    
    /**
     * Check if a package exists.
     */
    public boolean packageExists(String name) {
	return head.packageExists(name) || tail.packageExists(name);
    }

    /**
     * Find a type object by name.
     */
    public Named find(String name) throws SemanticException {
	try {
	    return head.find(name);
	}
	catch (NoClassException e) {
	    return tail.find(name);
	}
    }
}
