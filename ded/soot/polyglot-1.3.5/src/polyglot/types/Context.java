package polyglot.types;

import java.util.List;

import polyglot.util.Copy;

/**
 * A context represents a stack of scopes used for looking up types, methods,
 * and variables.  To push a new scope call one of the <code>push*</code>
 * methods to return a new context.  The old context may still be used
 * and may be accessed directly through a call to <code>pop()</code>.
 * While the stack of scopes is treated functionally, each individual
 * scope is updated in place.  Names added to the context are added
 * in the current scope.
 */
public interface Context extends Resolver, Copy
{
    /** The type system. */
    TypeSystem typeSystem();

    /** Add a variable to the current scope. */
    void addVariable(VarInstance vi);

    /** Add a method to the current scope. */
    void addMethod(MethodInstance mi);

    /** Add a named type object to the current scope. */
    void addNamed(Named t);

    /** Looks up a method in the current scope.
     * @param formalTypes A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    MethodInstance findMethod(String name, List formalTypes) throws SemanticException;

    /** Looks up a local variable or field in the current scope. */
    VarInstance findVariable(String name) throws SemanticException;

    /** Looks up a local variable or field in the current scope. */
    VarInstance findVariableSilent(String name);

    /** Looks up a local variable in the current scope. */
    LocalInstance findLocal(String name) throws SemanticException;

    /** Looks up a field in the current scope. */
    FieldInstance findField(String name) throws SemanticException;

    /**
     * Finds the class which added a field to the scope.
     * This is usually a subclass of <code>findField(name).container()</code>.
     */
    ClassType findFieldScope(String name) throws SemanticException;

    /**
     * Finds the class which added a method to the scope.
     * This is usually a subclass of <code>findMethod(name).container()</code>.
     */
    ClassType findMethodScope(String name) throws SemanticException;

    /** Get import table currently in scope. */
    ImportTable importTable();

    /** Get the outer-most resolver for the source file currently in scope.
     * This is usually just the import table.
     */
    Resolver outerResolver();

    /** Enter the scope of a source file. */
    Context pushSource(ImportTable it);

    /** Enter the scope of a class. */
    Context pushClass(ParsedClassType scope, ClassType type);

    /** Enter the scope of a method or constructor. */
    Context pushCode(CodeInstance f);

    /** Enter the scope of a block. */
    Context pushBlock();

    /** Enter a static scope. In general, this is only used for
     * explicit constructor calls; static methods, initializers of static
     * fields and static initializers are generally handled by pushCode().
     */
    Context pushStatic();

    /** Pop the context. */
    Context pop();

    /** Return whether innermost non-block scope is a code scope. */
    boolean inCode();

    /** Returns whether the symbol is defined within the current method. */
    boolean isLocal(String name);

    /** 
     * Returns whether the current context is a static context.
     * A statement of expression occurs in a static context if and only if the
     * inner-most method, constructor, instance initializer, static initializer,
     * field initializer, or explicit constructor statement enclosing the 
     * statement or expressions is a static method, static initializer, the 
     * variable initializer of a static variable, or an explicity constructor 
     * invocation statment. (Java Language Spec, 2nd Edition, 8.1.2)
     */
    boolean inStaticContext();

    /** Return the innermost class in scope. */
    ClassType currentClass();
 
    /** Return the innermost class in scope. */
    ParsedClassType currentClassScope();

    /** Return the innermost method or constructor in scope. */
    CodeInstance currentCode();

    /** The current package, or null if not in a package. */
    Package package_();
}
