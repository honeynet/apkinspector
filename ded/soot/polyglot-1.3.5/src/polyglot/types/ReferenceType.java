package polyglot.types;

import java.util.List;

/**
 * A <code>ReferenceType</code> represents a reference type: a type which
 * contains methods and fields and which is a subtype of Object.
 */
public interface ReferenceType extends Type
{ 
    /**
     * Return the type's super type.
     */
    Type superType();

    /**
     * Return the type's interfaces.
     * @return A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    List interfaces();

    /**
     * Return the type's fields.
     * @return A list of <code>FieldInstance</code>.
     * @see polyglot.types.FieldInstance
     */
    List fields();

    /**
     * Return the type's methods.
     * @return A list of <code>MethodInstance</code>.
     * @see polyglot.types.MethodInstance
     */
    List methods();

    /**
     * Return the field named <code>name</code>, or null.
     */
    FieldInstance fieldNamed(String name);

    /**
     * Return the methods named <code>name</code>, if any.
     * @param name Name of the method to search for.
     * @return A list of <code>MethodInstance</code>.
     * @see polyglot.types.MethodInstance
     */
    List methodsNamed(String name);

    /**
     * Return the methods named <code>name</code> with the given formal
     * parameter types, if any.
     * @param name Name of the method to search for.
     * @param argTypes A list of <code>Type</code>.
     * @return A list of <code>MethodInstance</code>.
     * @see polyglot.types.Type
     * @see polyglot.types.MethodInstance
     */
    List methods(String name, List argTypes);

    /**
     * Return the true if the type has the given method.
     */
    boolean hasMethod(MethodInstance mi);

    /**
     * Return the true if the type has the given method.
     */
    boolean hasMethodImpl(MethodInstance mi);
}
