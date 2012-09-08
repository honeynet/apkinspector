package polyglot.types;

import polyglot.util.*;
import polyglot.main.Report;
import java.util.*;

/**
 * A <code>ClassContextResolver</code> looks up type names qualified with a class name.
 * For example, if the class is "A.B", the class context will return the class
 * for member class "A.B.C" (if it exists) when asked for "C".
 */
public class ClassContextResolver extends ClassResolver {
    protected TypeSystem ts;
    protected ClassType type;

    /**
     * Construct a resolver.
     * @param ts The type system.
     * @param type The type in whose context we search for member types.
     */
    public ClassContextResolver(TypeSystem ts, ClassType type) {
	this.ts = ts;
	this.type = type;
    }

    public String toString() {
        return "(class-context " + type + ")";
    }

    /**
     * Find a type object in the context of the class.
     * @param name The name to search for.
     */
    public Named find(String name) throws SemanticException {
        if (Report.should_report(TOPICS, 2))
	    Report.report(2, "Looking for " + name + " in " + this);

        if (! StringUtil.isNameShort(name)) {
            throw new InternalCompilerError(
                "Cannot lookup qualified name " + name);
        }

        // Check if the name is for a member class.
        ClassType inner = ts.findMemberClass(type, name);

        if (inner != null) {
            if (Report.should_report(TOPICS, 2))
                Report.report(2, "Found member class " + inner);
            return inner;
        }

        throw new NoClassException(name, type);
    }

    /**
     * The class in whose context we look.
     */
    public ClassType classType() {
	return type;
    }

    protected static final Collection TOPICS = 
            CollectionUtil.list(Report.types, Report.resolver);

}
