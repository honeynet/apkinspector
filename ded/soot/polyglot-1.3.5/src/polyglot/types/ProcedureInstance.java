package polyglot.types;

import java.util.List;

/**
 * A <code>ProcedureInstance</code> contains the type information for a Java
 * procedure (either a method or a constructor).
 */
public interface ProcedureInstance extends CodeInstance
{
    /**
     * List of formal parameter types.
     * @return A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    List formalTypes();

    /**
     * List of declared exception types thrown.
     * @return A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    List throwTypes();

    /**
     * Returns a String representing the signature of the procedure.
     * This includes just the name of the method (or name of the class, if
     * it is a constructor), and the argument types.
     */
    String signature();

    /**
     * Returns either "method" or "constructor".
     */
    String designator();

    /**
     * Return true if <code>this</code> is more specific than <code>pi</code>
     * in terms of method overloading.
     */
    boolean moreSpecific(ProcedureInstance pi);

    /**
     * Returns true if the procedure has the given arguments.
     */
    boolean hasFormals(List arguments);

    /**
     * Returns true if the procedure throws a subset of the exceptions
     * thrown by <code>pi</code>.
     */
    boolean throwsSubset(ProcedureInstance pi);

    /**
     * Returns true if the procedure can be called with the given arguments.
     */
    boolean callValid(List actualTypes);

    /**
     * Return true if <code>this</code> is more specific than <code>pi</code>
     * in terms of method overloading.
     */
    boolean moreSpecificImpl(ProcedureInstance pi);

    /**
     * Returns true if the procedure has the given arguments.
     */
    boolean hasFormalsImpl(List arguments);

    /**
     * Returns true if the procedure throws a subset of the exceptions
     * thrown by <code>pi</code>.
     */
    boolean throwsSubsetImpl(ProcedureInstance pi);

    /**
     * Returns true if the procedure can be called with the given arguments.
     */
    boolean callValidImpl(List actualTypes);
}
