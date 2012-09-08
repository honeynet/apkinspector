package polyglot.types;

import java.util.List;

/**
 * A <code>MethodInstance</code> represents the type information for a Java
 * method.
 */
public interface MethodInstance extends ProcedureInstance
{
    /**
     * The method's return type.
     */
    Type returnType();

    /**
     * Set the method's return type.
     */
    MethodInstance returnType(Type returnType);

    /**
     * The method's name.
     */
    String name();

    /**
     * Set the method's name.
     */
    MethodInstance name(String name);

    /**
     * Set the method's flags.
     */
    MethodInstance flags(Flags flags);

    /**
     * Set the method's formal parameter types.
     * @param l A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    MethodInstance formalTypes(List l);

    /**
     * Set the method's exception throw types.
     * @param l A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    MethodInstance throwTypes(List l);

    /**
     * Set the method's containing type.
     */
    MethodInstance container(ReferenceType container);

    /**
     * Get the list of methods this method (potentially) overrides, in order
     * from this class (i.e., including <code>this</code>) to super classes.
     * @return A list of <code>MethodInstance</code>, starting with
     * <code>this</code>. Note that this list does not include methods declared
     * in interfaces. Use <code>implemented</code> for that.
     * @see polyglot.types.MethodInstance
     */
    List overrides();

    /**
     * Return true if this method can override <code>mi</code>, false otherwise.
     */
    boolean canOverride(MethodInstance mi);

    /**
     * Return true if this method can override <code>mi</code>, throws
     * a SemanticException otherwise.
     */
    void checkOverride(MethodInstance mi) throws SemanticException;

    /**
     * Get the set of methods this method implements.  No ordering is
     * specified since the superinterfaces need not form a linear list
     * (i.e., they can form a tree).  
     * @return List[MethodInstance]
     */
    List implemented(); 
    
    /**
     * Return true if this method has the same signature as <code>mi</code>.
     */
    boolean isSameMethod(MethodInstance mi);

    /**
     * Return true if this method can be called with name <code>name</code>
     * and actual parameters of types <code>actualTypes</code>.
     * @param name The method to call.
     * @param actualTypes A list of argument types of type <code>Type</code>.
     * @see polyglot.types.Type
     */
    boolean methodCallValid(String name, List actualTypes);

    /**
     * Get the list of methods this method (potentially) overrides, in order
     * from this class (i.e., including <code>this</code>) to super classes.
     * This method should not be called except by <code>TypeSystem</code>
     * and by subclasses.
     * @return A list of <code>MethodInstance</code>, starting with
     * <code>this</code>.
     * @see polyglot.types.MethodInstance
     */
    List overridesImpl();

    /**
     * Return true if this method can override <code>mi</code>.
     * This method should not be called except by <code>TypeSystem</code>
     * and by subclasses.
     * If quiet is true and this method cannot override <code>mi</code>, then
     * false is returned; otherwise, if quiet is false and this method cannot 
     * override <code>mi</code>, then a SemanticException is thrown.
     */
    boolean canOverrideImpl(MethodInstance mi, boolean quiet) throws SemanticException;

    /**
     * Get the set of methods in rt and its superinterfaces that
     * this method implements.  No ordering is specified.
     * @return List[MethodInstance]
     * @param rt The point in the type hierarchy to begin looking for methods.
     */
    List implementedImpl(ReferenceType rt);
    
    /**
     * Return true if this method has the same signature as <code>mi</code>.
     * This method should not be called except by <code>TypeSystem</code>
     * and by subclasses.
     */
    boolean isSameMethodImpl(MethodInstance mi);

    /**
     * Return true if this method can be called with name <code>name</code>
     * and actual parameters of types <code>actualTypes</code>.
     * This method should not be called except by <code>TypeSystem</code>
     * and by subclasses.
     */
    boolean methodCallValidImpl(String name, List actualTypes);
}
