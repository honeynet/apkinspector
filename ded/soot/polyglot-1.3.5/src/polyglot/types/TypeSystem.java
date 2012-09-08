package polyglot.types;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.util.Position;

/**
 * The <code>TypeSystem</code> defines the types of the language and
 * how they are related.
 */
public interface TypeSystem {
    /**
     * Initialize the type system with the compiler.  This method must be
     * called before any other type system method is called.
     *
     * @param resolver The resolver to use for loading types from class files
     *                 or other source files.
     * @param extInfo The ExtensionInfo the TypeSystem is being created for.
     */
    void initialize(LoadedClassResolver resolver, ExtensionInfo extInfo)
                    throws SemanticException;

    /**
     * Returns the system resolver.  This resolver can load top-level classes
     * with fully qualified names from the class path and the source path.
     */
    TopLevelResolver systemResolver();

    /**
     * Return the type system's table resolver.
     * This resolver contains types parsed from source files.
     */
    TableResolver parsedResolver();

    /**
     * Return the type system's loaded resolver.
     * This resolver contains types loaded from class files.
     */
    LoadedClassResolver loadedResolver();

    /** Create an import table for the source file.
     * @param sourceName Name of the source file to import into.  This is used
     * mainly for error messages and for debugging. 
     * @param pkg The package of the source file in which to import.
     */
    ImportTable importTable(String sourceName, Package pkg);

    /** Create an import table for the source file.
     * @param pkg The package of the source file in which to import.
     */
    ImportTable importTable(Package pkg);

    /**
     * Return a list of the packages names that will be imported by
     * default.  A list of Strings is returned, not a list of Packages.
     */
    List defaultPackageImports();

    /**
     * Returns true if the package named <code>name</code> exists.
     */
    boolean packageExists(String name);

    /** Get the named type object with the following name.
     * @param name The name of the type object to look for.
     * @exception SemanticException when object is not found.    
     */
    Named forName(String name) throws SemanticException;
    
    /** Get the  type with the following name.
     * @param name The name to create the type for.
     * @exception SemanticException when type is not found.    
     */
    Type typeForName(String name) throws SemanticException;

    /** Create an initailizer instance.
     * @param pos Position of the initializer.
     * @param container Containing class of the initializer.
     * @param flags The initializer's flags.
     */
    InitializerInstance initializerInstance(Position pos, ClassType container,
                                            Flags flags);

    /** Create a constructor instance.
     * @param pos Position of the constructor.
     * @param container Containing class of the constructor.
     * @param flags The constructor's flags.
     * @param argTypes The constructor's formal parameter types.
     * @param excTypes The constructor's exception throw types.
     */
    ConstructorInstance constructorInstance(Position pos, ClassType container,
                                            Flags flags, List argTypes,
                                            List excTypes);

    /** Create a method instance.
     * @param pos Position of the method.
     * @param container Containing type of the method.
     * @param flags The method's flags.
     * @param returnType The method's return type.
     * @param name The method's name.
     * @param argTypes The method's formal parameter types.
     * @param excTypes The method's exception throw types.
     */
    MethodInstance methodInstance(Position pos, ReferenceType container,
                                  Flags flags, Type returnType, String name,
                                  List argTypes, List excTypes);

    /** Create a field instance.
     * @param pos Position of the field.
     * @param container Containing type of the field.
     * @param flags The field's flags.
     * @param type The field's type.
     * @param name The field's name.
     */
    FieldInstance fieldInstance(Position pos, ReferenceType container,
                                Flags flags, Type type, String name);

    /** Create a local variable instance.
     * @param pos Position of the local variable.
     * @param flags The local variable's flags.
     * @param type The local variable's type.
     * @param name The local variable's name.
     */
    LocalInstance localInstance(Position pos, Flags flags, Type type,
                                String name);

    /** Create a default constructor instance.
     * @param pos Position of the constructor.
     * @param container Containing class of the constructor. 
     */
    ConstructorInstance defaultConstructor(Position pos, ClassType container);

    /** Get an unknown type. */
    UnknownType unknownType(Position pos);

    /** Get an unknown package. */
    UnknownPackage unknownPackage(Position pos);

    /** Get an unknown type qualifier. */
    UnknownQualifier unknownQualifier(Position pos);

    /**
     * Returns true iff child descends from ancestor or child == ancestor.
     * This is equivalent to:
     * <pre>
     *    descendsFrom(child, ancestor) || equals(child, ancestor)
     * </pre>
     */
    boolean isSubtype(Type child, Type ancestor);

    /**
     * Returns true iff child is not ancestor, but child descends from ancestor.     */
    boolean descendsFrom(Type child, Type ancestor);

    /**
     * Returns true iff a cast from fromType to toType is valid; in other
     * words, some non-null members of fromType are also members of toType.
     */
    boolean isCastValid(Type fromType, Type toType);

    /**
     * Returns true iff an implicit cast from fromType to toType is valid;
     * in other words, every member of fromType is member of toType.
     */
    boolean isImplicitCastValid(Type fromType, Type toType);

    /**
     * Returns true iff type1 and type2 represent the same type object.
     */
    boolean equals(TypeObject type1, TypeObject type2);

    /**
     * Returns true if <code>value</code> can be implicitly cast to type
     * <code>t</code>.  This method should be removed.  It is kept for backward
     * compatibility.
     */
    boolean numericConversionValid(Type t, long value);

    /**
     * Returns true if <code>value</code> can be implicitly cast to
     * type <code>t</code>.
     */
    boolean numericConversionValid(Type t, Object value);

    /**
     * Returns the least common ancestor of type1 and type2
     * @exception SemanticException if the LCA does not exist
     */
    Type leastCommonAncestor(Type type1, Type type2) throws SemanticException;

    /**
     * Returns true iff <code>type</code> is a canonical
     * (fully qualified) type.
     */
    boolean isCanonical(Type type);

    /**
     * Checks whether a class member can be accessed from Context context.
     */
    boolean isAccessible(MemberInstance mi, Context context);

    /**
     * Checks whether a class can be accessed from Context context.
     */
    boolean classAccessible(ClassType ct, Context context);

    /**
     * Checks whether a top-level or member class can be accessed from the
     * package pkg.  Returns false for local and anonymous classes.
     */
    boolean classAccessibleFromPackage(ClassType ct, Package pkg);

    /**
     * Returns whether inner is enclosed within outer
     */
    boolean isEnclosed(ClassType inner, ClassType outer);

    /**
     * Returns whether an object of the inner class <code>inner</code> has an
     * enclosing instance of class <code>encl</code>. 
     */
    boolean hasEnclosingInstance(ClassType inner, ClassType encl);
    
    ////
    // Various one-type predicates.
    ////

    /**
     * Returns true iff the type t can be coerced to a String in the given 
     * Context. If a type can be coerced to a String then it can be 
     * concatenated with Strings, e.g. if o is of type T, then the code snippet
     *         "" + o
     * would be allowed.
     */
    boolean canCoerceToString(Type t, Context c);
    
    /**
     * Returns true iff an object of type <code>type</code> may be thrown.
     */
    boolean isThrowable(Type type);

    /**
     * Returns a true iff the type or a supertype is in the list
     * returned by uncheckedExceptions().
     */
    boolean isUncheckedException(Type type);

    /**
     * Returns a collection of the Throwable types that need not be declared
     * in method and constructor signatures.
     */
    Collection uncheckedExceptions();

    /** Unary promotion for numeric types.
     * @exception SemanticException if the type cannot be promoted. 
     */
    PrimitiveType promote(Type t) throws SemanticException;

    /** Binary promotion for numeric types.
     * @exception SemanticException if the types cannot be promoted. 
     */
    PrimitiveType promote(Type t1, Type t2) throws SemanticException;

    ////
    // Functions for type membership.
    ////

    /**
     * Deprecated version of the findField method.
     * @deprecated
     */
    FieldInstance findField(ReferenceType container, String name, Context c)
        throws SemanticException;

    /**
     * Returns the field named 'name' defined on 'type'.
     * We check if the field is accessible from the class currClass.
     * @exception SemanticException if the field cannot be found or is
     * inaccessible.
     */
    FieldInstance findField(ReferenceType container, String name, ClassType currClass)
        throws SemanticException;

    /**
     * Returns the field named 'name' defined on 'type'.
     * @exception SemanticException if the field cannot be found or is
     * inaccessible.
     */
    FieldInstance findField(ReferenceType container, String name)
	throws SemanticException;

    /**
     * Find a method.  We need to pass the class from which the method
     * is being found because the method
     * we find depends on whether the method is accessible from that
     * class.
     * We also check if the field is accessible from the context 'c'.
     * @exception SemanticException if the method cannot be found or is
     * inaccessible.
     */
    MethodInstance findMethod(ReferenceType container,
                              String name, List argTypes,
                              ClassType currClass) throws SemanticException;
    /**
     * Deprecated version of the findMethod method.
     * @deprecated
     */
    MethodInstance findMethod(ReferenceType container,
                              String name, List argTypes,
                              Context c) throws SemanticException;

    /**
     * Find a constructor.  We need to pass the class from which the constructor
     * is being found because the constructor
     * we find depends on whether the constructor is accessible from that
     * class.
     * @exception SemanticException if the constructor cannot be found or is
     * inaccessible.
     */
    ConstructorInstance findConstructor(ClassType container, List argTypes,
                                        ClassType currClass) throws SemanticException;

    /**
     * Deprecated version of the findConstructor method.
     * @deprecated
     */
    ConstructorInstance findConstructor(ClassType container, List argTypes,
                                        Context c) throws SemanticException;

    /**
     * Find a member class.
     * We check if the field is accessible from the class currClass.
     * @exception SemanticException if the class cannot be found or is
     * inaccessible.
     */
    ClassType findMemberClass(ClassType container, String name, ClassType currClass)
    throws SemanticException;

    /**
     * Deprecated version of the findMemberClass method.
     * @deprecated
     */
    ClassType findMemberClass(ClassType container, String name, Context c)
    throws SemanticException;

    /**
     * Find a member class.
     * @exception SemanticException if the class cannot be found or is
     * inaccessible.
     */
    ClassType findMemberClass(ClassType container, String name)
	throws SemanticException;

    /**
     * Returns the immediate supertype of type, or null if type has no
     * supertype.
     **/
    Type superType(ReferenceType type);

    /**
     * Returns an immutable list of all the interface types which type
     * implements.
     **/
    List interfaces(ReferenceType type);

    ////
    // Functions for method testing.
    ////

    /**
     * Returns true iff <code>m1</code> throws fewer exceptions than
     * <code>m2</code>.
     */
    boolean throwsSubset(ProcedureInstance m1, ProcedureInstance m2);

    /**
     * Returns true iff <code>t</code> has the method <code>mi</code>.
     */
    boolean hasMethod(ReferenceType t, MethodInstance mi);

    /**
     * Returns true iff <code>t</code> has a method with name <code>name</code>
     * either defined in <code>t</code> or inherited into it.
     */
    boolean hasMethodNamed(ReferenceType t, String name);

    /**
     * Returns true iff <code>m1</code> is the same method as <code>m2</code>.
     */
    boolean isSameMethod(MethodInstance m1, MethodInstance m2);

    /**
     * Returns true iff <code>m1</code> is more specific than <code>m2</code>.
     */
    boolean moreSpecific(ProcedureInstance m1, ProcedureInstance m2);

    /**
     * Returns true iff <code>p</code> has exactly the formal arguments
     * <code>formalTypes</code>.
     */
    boolean hasFormals(ProcedureInstance p, List formalTypes);

    ////
    // Functions which yield particular types.
    ////

    /**
     * The type of <code>null</code>.
     */
    NullType Null();

    /**
     * <code>void</code>
     */
    PrimitiveType Void();

    /**
     * <code>boolean</code>
     */
    PrimitiveType Boolean();

    /**
     * <code>char</code>
     */
    PrimitiveType Char();

    /**
     * <code>byte</code>
     */
    PrimitiveType Byte();

    /**
     * <code>short</code>
     */
    PrimitiveType Short();

    /**
     * <code>int</code>
     */
    PrimitiveType Int();

    /**
     * <code>long</code>
     */
    PrimitiveType Long();

    /**
     * <code>float</code>
     */
    PrimitiveType Float();

    /**
     * <code>double</code>
     */
    PrimitiveType Double();

    /**
     * <code>java.lang.Object</code>
     */
    ClassType Object();

    /**
     * <code>java.lang.String</code>
     */
    ClassType String();

    /**
     * <code>java.lang.Class</code>
     */
    ClassType Class();

    /**
     * <code>java.lang.Throwable</code>
     */
    ClassType Throwable();

    /**
     * <code>java.lang.Error</code>
     */
    ClassType Error();

    /**
     * <code>java.lang.Exception</code>
     */
    ClassType Exception();

    /**
     * <code>java.lang.RuntimeException</code>
     */
    ClassType RuntimeException();

    /**
     * <code>java.lang.Cloneable</code>
     */
    ClassType Cloneable();

    /**
     * <code>java.io.Serializable</code>
     */
    ClassType Serializable();

    /**
     * <code>java.lang.NullPointerException</code>
     */
    ClassType NullPointerException();

    /**
     * <code>java.lang.ClassCastException</code>
     */
    ClassType ClassCastException();

    /**
     * <code>java.lang.ArrayIndexOutOfBoundsException</code>
     */
    ClassType OutOfBoundsException();

    /**
     * <code>java.lang.ArrayStoreException</code>
     */
    ClassType ArrayStoreException();

    /**
     * <code>java.lang.ArithmeticException</code>
     */
    ClassType ArithmeticException();

    /**
     * Return an array of <code>type</code>
     */
    ArrayType arrayOf(Type type);

    /**
     * Return an array of <code>type</code>
     */
    ArrayType arrayOf(Position pos, Type type);

    /**
     * Return a <code>dims</code>-array of <code>type</code>
     */
    ArrayType arrayOf(Type type, int dims);

    /**
     * Return a <code>dims</code>-array of <code>type</code>
     */
    ArrayType arrayOf(Position pos, Type type, int dims);

    /**
     * Return a package by name.
     * Fail if the package does not exists.
     */
    Package packageForName(String name) throws SemanticException;

    /**
     * Return a package by name with the given outer package.
     * Fail if the package does not exists.
     */
    Package packageForName(Package prefix, String name) throws SemanticException;

    /**
     * Return a package by name.
     */
    Package createPackage(String name);

    /**
     * Return a package by name with the given outer package.
     */
    Package createPackage(Package prefix, String name);

    /**
     * Create a new context object for looking up variables, types, etc.
     */
    Context createContext();

    /** Get a resolver for looking up a type in a package. */
    Resolver packageContextResolver(Resolver resolver, Package pkg);

    /** Get a resolver for looking up a type in a class context. */
    Resolver classContextResolver(ClassType ct);

    /**
     * The default lazy class initializer.
     */
    LazyClassInitializer defaultClassInitializer();

    /**
     * Create a new empty class.
     */
    ParsedClassType createClassType(LazyClassInitializer init);

    /**
     * Create a new empty class.
     */
    ParsedClassType createClassType();

    /**
     * Create a new empty class.
     */
    ParsedClassType createClassType(LazyClassInitializer init, Source fromSource);

    /**
     * Create a new empty class.
     */
    ParsedClassType createClassType(Source fromSource);

    /**
     * Return the set of objects that should be serialized into the
     * type information for the given ClassType.
     * Usually only the clazz itself should get encoded, and references
     * to other classes should just have their name written out.
     * If it makes sense for additional types to be fully encoded,
     * (ie, they're necessary to correctly reconstruct the given clazz,
     * and the usual class resolvers can't otherwise find them) they
     * should be returned in the set in addition to clazz.
     */
    Set getTypeEncoderRootSet(Type clazz);

    /**
     * Get the transformed class name of a class.
     * This utility method returns the "mangled" name of the given class,
     * whereby all periods ('.') following the toplevel class name
     * are replaced with dollar signs ('$'). If any of the containing
     * classes is not a member class or a top level class, then null is
     * returned.
     */
    public String getTransformedClassName(ClassType ct);

    /** Get a place-holder for serializing a type object.
     * @param o The object to get the place-holder for.
     * @param roots The root objects for the serialization.  Place holders
     * are not created for these.
     */
    Object placeHolder(TypeObject o, java.util.Set roots);

    /** Get a place-holder for serializing a type object.
     * @param o The object to get the place-holder for.
     */
    Object placeHolder(TypeObject o);

    /**
     * Translate a package.
     */
    String translatePackage(Resolver c, Package p);

    /**
     * Translate a primitive type.
     */
    String translatePrimitive(Resolver c, PrimitiveType t);

    /**
     * Translate an array type.
     */
    String translateArray(Resolver c, ArrayType t);

    /**
     * Translate a top-level class type.
     */
    String translateClass(Resolver c, ClassType t);

    /**
     * Return the boxed version of <code>t</code>.
     */
    String wrapperTypeString(PrimitiveType t);

    /**
     * Return true if <code>mi</code> can be called with name <code>name</code>
     * and actual parameters of types <code>actualTypes</code>.
     */
    boolean methodCallValid(MethodInstance mi, String name, List argTypes);

    /**
     * Return true if <code>pi</code> can be called with 
     * actual parameters of types <code>actualTypes</code>.
     */
    boolean callValid(ProcedureInstance mi, List argTypes);

    /**
     * Get the list of methods <code>mi</code> (potentially) overrides, in
     * order from this class (that is, including <code>this</code>) to super
     * classes.
     */
    List overrides(MethodInstance mi);

    /**
     * Return true if <code>mi</code> can override <code>mj</code>.
     */
    boolean canOverride(MethodInstance mi, MethodInstance mj);

    /**
     * Throw a SemanticException if <code>mi</code> cannot override 
     * <code>mj</code>.
     */
    void checkOverride(MethodInstance mi, MethodInstance mj) throws SemanticException;

    /**
     * Get the list of methods <code>mi</code> implements, in no
     * specified order.
     */
    List implemented(MethodInstance mi);
    
    /**
     * Return the primitive with the given name.
     */
    PrimitiveType primitiveForName(String name) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal method flags.
     */
    void checkMethodFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal local variable flags.
     */
    void checkLocalFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal field flags.
     */
    void checkFieldFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal constructor flags.
     */
    void checkConstructorFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal initializer flags.
     */
    void checkInitializerFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal top-level class flags.
     */
    void checkTopLevelClassFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal member class flags.
     */
    void checkMemberClassFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal local class flags.
     */
    void checkLocalClassFlags(Flags f) throws SemanticException;

    /**
     * Assert if the flags <code>f</code> are legal access flags.
     */
    void checkAccessFlags(Flags f) throws SemanticException;

    /**
     * Assert that <code>t</code> has no cycles in the super type+nested class
     * graph starting at <code>t</code>.
     */
    void checkCycles(ReferenceType t) throws SemanticException;

    /**
     * Assert that <code>ct</code> implements all abstract methods that it 
     * has to; that is, if it is a concrete class, then it must implement all
     * interfaces and abstract methods that it or its superclasses declare.
     */
    public void checkClassConformance(ClassType ct) throws SemanticException;

    /**
     * Returns <code>t</code>, modified as necessary to make it a legal
     * static target.
     */
    public Type staticTarget(Type t);

    /**
     * Given the JVM encoding of a set of flags, returns the Flags object
     * for that encoding.
     */
    public Flags flagsForBits(int bits); 

    /**
     * Create a new unique Flags object.
     * @param name the name of the flag
     * @param print_after print the new flag after these flags
     */
    public Flags createNewFlag(String name, Flags print_after);

    public Flags NoFlags();
    public Flags Public();
    public Flags Protected();
    public Flags Private();
    public Flags Static();
    public Flags Final();
    public Flags Synchronized();
    public Flags Transient();
    public Flags Native();
    public Flags Interface();
    public Flags Abstract();
    public Flags Volatile();
    public Flags StrictFP();
}
