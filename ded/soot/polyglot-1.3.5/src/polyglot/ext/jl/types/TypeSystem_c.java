package polyglot.ext.jl.types;

import java.lang.reflect.Modifier;
import java.util.*;

import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.Source;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.StringUtil;

/**
 * TypeSystem_c
 *
 * Overview:
 *    A TypeSystem_c is a universe of types, including all Java types.
 **/
public class TypeSystem_c implements TypeSystem
{
    protected TopLevelResolver systemResolver;
    protected TableResolver parsedResolver;
    protected LoadedClassResolver loadedResolver;
    protected Map flagsForName;

    public TypeSystem_c() {}

    /**
     * Initializes the type system and its internal constants (which depend on
     * the resolver).
     */
    public void initialize(LoadedClassResolver loadedResolver, ExtensionInfo extInfo)
                           throws SemanticException {

        if (Report.should_report(Report.types, 1))
	    Report.report(1, "Initializing " + getClass().getName());

        // The parsed class resolver.  This resolver contains classes parsed
        // from source files.
        this.parsedResolver = new TableResolver();


        // The loaded class resolver.  This resolver automatically loads types
        // from class files and from source files not mentioned on the command
        // line.
        this.loadedResolver = loadedResolver;

        CompoundResolver compoundResolver =
            new CompoundResolver(parsedResolver, loadedResolver);

        // The system class resolver.  The class resolver contains a map from
        // class names to ClassTypes.  A Job looks up classes first in its
        // import table and then in the system resolver.  The system resolver
        // first tries to find the class in parsed class resolver.
        this.systemResolver = new CachingResolver(compoundResolver, extInfo);

        initFlags();

        initTypes();
    }

    protected void initTypes() throws SemanticException {
        // FIXME: don't do this when rewriting a type system!

        // Prime the resolver cache so that we don't need to check
        // later if these are loaded.

        // We cache the most commonly used ones in fields.
        /* DISABLED CACHING OF COMMON CLASSES; CAUSES PROBLEMS IF
           COMPILING CORE CLASSES (e.g. java.lang package).
           TODO: Longer term fix. Maybe a flag to tell if we are compiling
                 core classes? XXX
        Object();
        Class();
        String();
        Throwable();

        systemResolver.find("java.lang.Error");
        systemResolver.find("java.lang.Exception");
        systemResolver.find("java.lang.RuntimeException");
        systemResolver.find("java.lang.Cloneable");
        systemResolver.find("java.io.Serializable");
        systemResolver.find("java.lang.NullPointerException");
        systemResolver.find("java.lang.ClassCastException");
        systemResolver.find("java.lang.ArrayIndexOutOfBoundsException");
        systemResolver.find("java.lang.ArrayStoreException");
        systemResolver.find("java.lang.ArithmeticException");
        */
    }

    public TopLevelResolver systemResolver() {
      return systemResolver;
    }

    public TableResolver parsedResolver() {
        return parsedResolver;
    }

    public LoadedClassResolver loadedResolver() {
        return loadedResolver;
    }

    public ImportTable importTable(String sourceName, Package pkg) {
        assert_(pkg);
        return new ImportTable(this, systemResolver, pkg, sourceName);
    }

    public ImportTable importTable(Package pkg) {
        assert_(pkg);
        return new ImportTable(this, systemResolver, pkg);
    }

    /**
     * Returns true if the package named <code>name</code> exists.
     */
    public boolean packageExists(String name) {
        return systemResolver.packageExists(name);
    }

    protected void assert_(Collection l) {
        for (Iterator i = l.iterator(); i.hasNext(); ) {
            TypeObject o = (TypeObject) i.next();
            assert_(o);
        }
    }

    protected void assert_(TypeObject o) {
        if (o != null && o.typeSystem() != this) {
            throw new InternalCompilerError("we are " + this + " but " + o +
                                            " is from " + o.typeSystem());
        }
    }

    public String wrapperTypeString(PrimitiveType t) {
        assert_(t);

	if (t.kind() == PrimitiveType.BOOLEAN) {
	    return "java.lang.Boolean";
	}
	if (t.kind() == PrimitiveType.CHAR) {
	    return "java.lang.Character";
	}
	if (t.kind() == PrimitiveType.BYTE) {
	    return "java.lang.Byte";
	}
	if (t.kind() == PrimitiveType.SHORT) {
	    return "java.lang.Short";
	}
	if (t.kind() == PrimitiveType.INT) {
	    return "java.lang.Integer";
	}
	if (t.kind() == PrimitiveType.LONG) {
	    return "java.lang.Long";
	}
	if (t.kind() == PrimitiveType.FLOAT) {
	    return "java.lang.Float";
	}
	if (t.kind() == PrimitiveType.DOUBLE) {
	    return "java.lang.Double";
	}
	if (t.kind() == PrimitiveType.VOID) {
	    return "java.lang.Void";
	}

	throw new InternalCompilerError("Unrecognized primitive type.");
    }

    public Context createContext() {
	return new Context_c(this);
    }

    public Resolver packageContextResolver(Resolver cr, Package p) {
        assert_(p);
	return new PackageContextResolver(this, p, cr);
    }

    public Resolver classContextResolver(ClassType type) {
        assert_(type);
	return new ClassContextResolver(this, type);
    }

    public FieldInstance fieldInstance(Position pos,
	                               ReferenceType container, Flags flags,
				       Type type, String name) {
        assert_(container);
        assert_(type);
	return new FieldInstance_c(this, pos, container, flags, type, name);
    }

    public LocalInstance localInstance(Position pos,
	                               Flags flags, Type type, String name) {
        assert_(type);
	return new LocalInstance_c(this, pos, flags, type, name);
    }

    public ConstructorInstance defaultConstructor(Position pos,
                                                  ClassType container) {
        assert_(container);
        
        // access for the default constructor is determined by the 
        // access of the containing class. See the JLS, 2nd Ed., 8.8.7.
        Flags access = Flags.NONE;
        if (container.flags().isPrivate()) {
            access = access.Private();
        }
        if (container.flags().isProtected()) {
            access = access.Protected();            
        }
        if (container.flags().isPublic()) {
            access = access.Public();            
        }
        return constructorInstance(pos, container,
                                   access, Collections.EMPTY_LIST,
                                   Collections.EMPTY_LIST);
    }

    public ConstructorInstance constructorInstance(Position pos,
	                                           ClassType container,
						   Flags flags, List argTypes,
						   List excTypes) {
        assert_(container);
        assert_(argTypes);
        assert_(excTypes);
	return new ConstructorInstance_c(this, pos, container, flags,
	                                 argTypes, excTypes);
    }

    public InitializerInstance initializerInstance(Position pos,
	                                           ClassType container,
						   Flags flags) {
        assert_(container);
	return new InitializerInstance_c(this, pos, container, flags);
    }

    public MethodInstance methodInstance(Position pos,
	                                 ReferenceType container, Flags flags,
					 Type returnType, String name,
					 List argTypes, List excTypes) {

        assert_(container);
        assert_(returnType);
        assert_(argTypes);
        assert_(excTypes);
	return new MethodInstance_c(this, pos, container, flags,
				    returnType, name, argTypes, excTypes);
    }

    /**
     * Returns true iff child and ancestor are distinct
     * reference types, and child descends from ancestor.
     **/
    public boolean descendsFrom(Type child, Type ancestor) {
        assert_(child);
        assert_(ancestor);
        return child.descendsFromImpl(ancestor);
    }

    /**
     * Requires: all type arguments are canonical.  ToType is not a NullType.
     *
     * Returns true iff a cast from fromType to toType is valid; in other
     * words, some non-null members of fromType are also members of toType.
     **/
    public boolean isCastValid(Type fromType, Type toType) {
        assert_(fromType);
        assert_(toType);
        return fromType.isCastValidImpl(toType);
    }

    /**
     * Requires: all type arguments are canonical.
     *
     * Returns true iff an implicit cast from fromType to toType is valid;
     * in other words, every member of fromType is member of toType.
     *
     * Returns true iff child and ancestor are non-primitive
     * types, and a variable of type child may be legally assigned
     * to a variable of type ancestor.
     *
     */
    public boolean isImplicitCastValid(Type fromType, Type toType) {
        assert_(fromType);
        assert_(toType);
        return fromType.isImplicitCastValidImpl(toType);
    }

    /**
     * Returns true iff type1 and type2 represent the same type object.
     */
    public boolean equals(TypeObject type1, TypeObject type2) {
        assert_(type1);
        assert_(type2);
        if (type1 instanceof TypeObject_c) {
            return ((TypeObject_c)type1).equalsImpl(type2);
        } else {
            throw new InternalCompilerError("Unknown implementation of "
                + "TypeObject", type1.position());
        }
    }

    /**
     * Returns true if <code>value</code> can be implicitly cast to Primitive
     * type <code>t</code>.
     */
    public boolean numericConversionValid(Type t, Object value) {
        assert_(t);
        return t.numericConversionValidImpl(value);
    }

    /**
     * Returns true if <code>value</code> can be implicitly cast to Primitive
     * type <code>t</code>.  This method should be removed.  It is kept for
     * backward compatibility.
     */
    public boolean numericConversionValid(Type t, long value) {
        assert_(t);
        return t.numericConversionValidImpl(value);
    }

    ////
    // Functions for one-type checking and resolution.
    ////

    /**
     * Returns true iff <type> is a canonical (fully qualified) type.
     */
    public boolean isCanonical(Type type) {
        assert_(type);
	return type.isCanonical();
    }

    /**
     * Checks whether the member mi can be accessed from Context "context".
     */
    public boolean isAccessible(MemberInstance mi, Context context) {
        return isAccessible(mi, context.currentClass());
    }

    /**
     * Checks whether the member mi can be accessed from code that is
     * declared in the class contextClass.
     */
    protected boolean isAccessible(MemberInstance mi, ClassType contextClass) {
        assert_(mi);

        ReferenceType target = mi.container();
	Flags flags = mi.flags();

        if (! target.isClass()) {
            // public members of non-classes are accessible;
            // non-public members of non-classes are inaccessible
            return flags.isPublic();
        }

        ClassType targetClass = target.toClass();

        if (! classAccessible(targetClass, contextClass)) {
            return false;
        }

        if (equals(targetClass, contextClass))
            return true;

        // If the current class and the target class are both in the
        // same class body, then protection doesn't matter, i.e.
        // protected and private members may be accessed. Do this by
        // working up through contextClass's containers.
        if (isEnclosed(contextClass, targetClass) || isEnclosed(targetClass, contextClass))
            return true;

        ClassType ct = contextClass;
        while (!ct.isTopLevel()) {
            ct = ct.outer();
            if (isEnclosed(targetClass, ct))
                return true;
        }

	// protected
        if (flags.isProtected()) {
            // If the current class is in a
            // class body that extends/implements the target class, then
            // protected members can be accessed. Do this by
            // working up through contextClass's containers.
            if (descendsFrom(contextClass, targetClass)) {
                return true;
            }

            ct = contextClass;
            while (!ct.isTopLevel()) {
                ct = ct.outer();
                if (descendsFrom(ct, targetClass)) {
                    return true;
                }
            }
        }

        return accessibleFromPackage(flags, targetClass.package_(), contextClass.package_());
    }

    /** True if the class targetClass accessible from the context. */
    public boolean classAccessible(ClassType targetClass, Context context) {
        if (context.currentClass() == null) {
            return classAccessibleFromPackage(targetClass, context.importTable().package_());
        }
        else {
            return classAccessible(targetClass, context.currentClass());
        }
    }

    /** True if the class targetClass accessible from the body of class contextClass. */
    protected boolean classAccessible(ClassType targetClass, ClassType contextClass) {
        assert_(targetClass);

        if (targetClass.isMember()) {
            return isAccessible(targetClass, contextClass);
        }

        // Local and anonymous classes are accessible if they can be named.
        // This method wouldn't be called if they weren't named.
        if (! targetClass.isTopLevel()) {
            return true;
        }

        // targetClass must be a top-level class
        
        // same class
	if (equals(targetClass, contextClass))
            return true;

        if (isEnclosed(contextClass, targetClass))
            return true;

        return accessibleFromPackage(targetClass.flags(),
                                     targetClass.package_(), contextClass.package_());
    }

    /** True if the class targetClass accessible from the package pkg. */
    public boolean classAccessibleFromPackage(ClassType targetClass, Package pkg) {
        assert_(targetClass);

        // Local and anonymous classes are not accessible from the outermost
        // scope of a compilation unit.
        if (! targetClass.isTopLevel() && ! targetClass.isMember())
            return false;

	Flags flags = targetClass.flags();

        if (targetClass.isMember()) {
            if (! targetClass.container().isClass()) {
                // public members of non-classes are accessible
                return flags.isPublic();
            }

            if (! classAccessibleFromPackage(targetClass.container().toClass(), pkg)) {
                return false;
            }
        }

        return accessibleFromPackage(flags, targetClass.package_(), pkg);
    }

    /**
     * Return true if a member (in an accessible container) or a
     * top-level class with access flags <code>flags</code>
     * in package <code>pkg1</code> is accessible from package
     * <code>pkg2</code>.
     */
    protected boolean accessibleFromPackage(Flags flags, Package pkg1, Package pkg2) {
        // Check if public.
        if (flags.isPublic()) {
            return true;
        }

        // Check if same package.
        if (flags.isPackage() || flags.isProtected()) {
            if (pkg1 == null && pkg2 == null)
                return true;
            if (pkg1 != null && pkg1.equals(pkg2))
                return true;
	}

        // Otherwise private.
	return false;
    }

    public boolean isEnclosed(ClassType inner, ClassType outer) {
        return inner.isEnclosedImpl(outer);
    }

    public boolean hasEnclosingInstance(ClassType inner, ClassType encl) {
        return inner.hasEnclosingInstanceImpl(encl);
    }

    public void checkCycles(ReferenceType goal) throws SemanticException {
	checkCycles(goal, goal);
    }

    protected void checkCycles(ReferenceType curr, ReferenceType goal)
	throws SemanticException {

        assert_(curr);
        assert_(goal);

	if (curr == null) {
	    return;
	}

	ReferenceType superType = null;

	if (curr.superType() != null) {
	    superType = curr.superType().toReference();
	}

	if (goal == superType) {
	    throw new SemanticException("Circular inheritance involving " + goal, 
                                        curr.position());
	}

	checkCycles(superType, goal);

	for (Iterator i = curr.interfaces().iterator(); i.hasNext(); ) {
	    Type si = (Type) i.next();

	    if (si == goal) {
                throw new SemanticException("Circular inheritance involving " + goal, 
                                            curr.position());
	    }

	    checkCycles(si.toReference(), goal);
        }    
        if (curr.isClass()) {
            checkCycles(curr.toClass().outer(), goal);
        }
    }

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
    public boolean canCoerceToString(Type t, Context c) {
        // every Object can be coerced to a string, as can any primitive,
        // except void.
        return ! t.isVoid();
    }

    /**
     * Returns true iff an object of type <type> may be thrown.
     **/
    public boolean isThrowable(Type type) {
        assert_(type);
        return type.isThrowable();
    }

    /**
     * Returns a true iff the type or a supertype is in the list
     * returned by uncheckedExceptions().
     */
    public boolean isUncheckedException(Type type) {
        assert_(type);
        return type.isUncheckedException();
    }

    /**
     * Returns a list of the Throwable types that need not be declared
     * in method and constructor signatures.
     */
    public Collection uncheckedExceptions() {
        List l = new ArrayList(2);
	l.add(Error());
	l.add(RuntimeException());
	return l;
    }

    public boolean isSubtype(Type t1, Type t2) {
        assert_(t1);
        assert_(t2);
        return t1.isSubtypeImpl(t2);
    }

    ////
    // Functions for type membership.
    ////

    /**
     * @deprecated
     */
    public FieldInstance findField(ReferenceType container, String name,
                               Context c) throws SemanticException {
        ClassType ct = null;
        if (c != null) ct = c.currentClass();
        return findField(container, name, ct);
    }
    
    /**
     * Returns the FieldInstance for the field <code>name</code> defined
     * in type <code>container</code> or a supertype, and visible from
     * <code>currClass</code>.  If no such field is found, a SemanticException
     * is thrown.  <code>currClass</code> may be null.
     **/
    public FieldInstance findField(ReferenceType container, String name,
	                           ClassType currClass) throws SemanticException {
	Collection fields = findFields(container, name);
	
	if (fields.size() == 0) {
	    throw new NoMemberException(NoMemberException.FIELD,
					"Field \"" + name +
					"\" not found in type \"" +
					container + "\".");
	}
	
	Iterator i = fields.iterator();
	FieldInstance fi = (FieldInstance) i.next();
	
	if (i.hasNext()) {
	    FieldInstance fi2 = (FieldInstance) i.next();
	    
	    throw new SemanticException("Field \"" + name +
					"\" is ambiguous; it is defined in both " +
					fi.container() + " and " +
					fi2.container() + "."); 
	}
	
	if (currClass != null && ! isAccessible(fi, currClass)) {
            throw new SemanticException("Cannot access " + fi + ".");
        }
	
        return fi;
    }

    /**
     * Returns the FieldInstance for the field <code>name</code> defined
     * in type <code>container</code> or a supertype.  If no such field is
     * found, a SemanticException is thrown.
     */
    public FieldInstance findField(ReferenceType container, String name)
	throws SemanticException {
	
	return findField(container, name, (ClassType) null);
    }
	    
    
    /**
     * Returns a set of fields named <code>name</code> defined
     * in type <code>container</code> or a supertype.  The list
     * returned may be empty.
     */
    protected Set findFields(ReferenceType container, String name) {
        assert_(container);

        if (container == null) {
            throw new InternalCompilerError("Cannot access field \"" + name +
                "\" within a null container type.");
        }

	FieldInstance fi = container.fieldNamed(name);
	
	if (fi != null) {
	    return Collections.singleton(fi);
	}

	Set fields = new HashSet();

	if (container.superType() != null && container.superType().isReference()) {
	    Set superFields = findFields(container.superType().toReference(), name);
	    fields.addAll(superFields);
	}

	if (container.isClass()) {
	    // Need to check interfaces for static fields.
	    ClassType ct = container.toClass();
	
	    for (Iterator i = ct.interfaces().iterator(); i.hasNext(); ) {
		Type it = (Type) i.next();
		Set superFields = findFields(it.toReference(), name);
		fields.addAll(superFields);
	    }
	}
	
	return fields;
    }

    /**
     * @deprecated
     */
    public ClassType findMemberClass(ClassType container, String name,
                                     Context c) throws SemanticException {
        return findMemberClass(container, name, c.currentClass());
    }
    
    public ClassType findMemberClass(ClassType container, String name,
                                     ClassType currClass) throws SemanticException
    {
	assert_(container);
	
	Set s = findMemberClasses(container, name);
	
	if (s.size() == 0) {
	    throw new NoClassException(name, container);
	}
	
	Iterator i = s.iterator();
	ClassType t = (ClassType) i.next();
	
	if (i.hasNext()) {
	    ClassType t2 = (ClassType) i.next();
	    throw new SemanticException("Member type \"" + name +
					"\" is ambiguous; it is defined in both " +
					t.container() + " and " +
					t2.container() + ".");
	}
	
	if (currClass != null && ! isAccessible(t, currClass)) {
	    throw new SemanticException("Cannot access member type \"" + t + "\".");
	}
	
	return t;
    }
    
    public Set findMemberClasses(ClassType container, String name) throws SemanticException {
	assert_(container);
	
	ClassType mt = container.memberClassNamed(name);
	
	if (mt != null) {
	    if (! mt.isMember()) {
		throw new InternalCompilerError("Class " + mt +
						" is not a member class, " +
						" but is in " + container +
						"\'s list of members.");
	    }
	    
	    if (mt.outer() != container) {
		throw new InternalCompilerError("Class " + mt +
						" has outer class " +
						mt.outer() +
						" but is a member of " +
						container);
	    }
	    
	    return Collections.singleton(mt);
	}
	
	Set memberClasses = new HashSet();
	
	if (container.superType() != null) {
	    Set s = findMemberClasses(container.superType().toClass(), name);
	    memberClasses.addAll(s);
	}
	
	for (Iterator i = container.interfaces().iterator(); i.hasNext(); ) {
	    Type it = (Type) i.next();
	    
	    Set s =  findMemberClasses(it.toClass(), name);
	    memberClasses.addAll(s);
	}
	
	return memberClasses;
    }
    
    public ClassType findMemberClass(ClassType container, String name)
        throws SemanticException {

	return findMemberClass(container, name, (ClassType) null);
    }
    
    protected static String listToString(List l) {
	StringBuffer sb = new StringBuffer();

	for (Iterator i = l.iterator(); i.hasNext(); ) {
	    Object o = i.next();
            sb.append(o.toString());

	    if (i.hasNext()) {
                sb.append(", ");
	    }
	}

	return sb.toString();
    }

    /**
     * @deprecated
     */
    public MethodInstance findMethod(ReferenceType container,
                                 String name, List argTypes, Context c)
    throws SemanticException {
        return findMethod(container, name, argTypes, c.currentClass());
    }

    /**
     * Returns the list of methods with the given name defined or inherited
     * into container, checking if the methods are accessible from the
     * body of currClass
     */
    public boolean hasMethodNamed(ReferenceType container, String name) {
        assert_(container);

        if (container == null) {
            throw new InternalCompilerError("Cannot access method \"" + name +
                "\" within a null container type.");
        }

	if (! container.methodsNamed(name).isEmpty()) {
            return true;
	}

	if (container.superType() != null && container.superType().isReference()) {
            if (hasMethodNamed(container.superType().toReference(), name)) {
                return true;
            }
	}

	if (container.isClass()) {
	    ClassType ct = container.toClass();
	
	    for (Iterator i = ct.interfaces().iterator(); i.hasNext(); ) {
		Type it = (Type) i.next();
                if (hasMethodNamed(it.toReference(), name)) {
                    return true;
                }
	    }
	}
	
        return false;
    }

    /**
     * Requires: all type arguments are canonical.
     *
     * Returns the MethodInstance named 'name' defined on 'type' visible in
     * context.  If no such field may be found, returns a fieldmatch
     * with an error explaining why.  Access flags are considered.
     **/
    public MethodInstance findMethod(ReferenceType container,
	                             String name, List argTypes, ClassType currClass)
	throws SemanticException {

        assert_(container);
        assert_(argTypes);

	List acceptable = findAcceptableMethods(container, name, argTypes, currClass);

	if (acceptable.size() == 0) {
	    throw new NoMemberException(NoMemberException.METHOD,
		"No valid method call found for " + name +
		"(" + listToString(argTypes) + ")" +
		" in " +
		container + ".");
	}

	MethodInstance mi = (MethodInstance)
	    findProcedure(acceptable, container, argTypes, currClass);

	if (mi == null) {
	    throw new SemanticException("Reference to " + name +
					" is ambiguous, multiple methods match: "
					+ acceptable);
	}
		
	return mi;
    }

    /**
     * @deprecated
     */
    public ConstructorInstance findConstructor(ClassType container,
                                 List argTypes, Context c)
    throws SemanticException {
        return findConstructor(container, argTypes, c.currentClass());
    }

    public ConstructorInstance findConstructor(ClassType container,
                           List argTypes, ClassType currClass)
	throws SemanticException {

        assert_(container);
        assert_(argTypes);

	List acceptable = findAcceptableConstructors(container, argTypes, currClass);

	if (acceptable.size() == 0) {
	    throw new NoMemberException(NoMemberException.CONSTRUCTOR,
                                        "No valid constructor found for " +
                                        container + "(" + listToString(argTypes) + ").");
	}

	ConstructorInstance ci = (ConstructorInstance)
	    findProcedure(acceptable, container, argTypes, currClass);

	if (ci == null) {
	    throw new NoMemberException(NoMemberException.CONSTRUCTOR,
		"Reference to " + container + " is ambiguous, multiple " +
		"constructors match: " + acceptable);
	}

	return ci;
    }

    protected ProcedureInstance findProcedure(List acceptable,
	                                      ReferenceType container,
					      List argTypes,
					      ClassType currClass)
	throws SemanticException {

        Collection maximal = findMostSpecificProcedures(acceptable, container,
                                                        argTypes, currClass);

        if (maximal.size() == 1) {
            return (ProcedureInstance) maximal.iterator().next();
        }

        return null;
    }
        
    protected Collection findMostSpecificProcedures(List acceptable,
                                                    ReferenceType container,                                                    List argTypes,
                                                    ClassType currClass)
        throws SemanticException {

        assert_(container);
        assert_(argTypes);

	// now, use JLS 15.11.2.2
	// First sort from most- to least-specific.
	MostSpecificComparator msc = new MostSpecificComparator();
	Collections.sort(acceptable, msc);

        List maximal = new ArrayList(acceptable.size());

	Iterator i = acceptable.iterator();

	ProcedureInstance first = (ProcedureInstance) i.next();
        maximal.add(first);

	// Now check to make sure that we have a maximal most-specific method.
	while (i.hasNext()) {
	    ProcedureInstance p = (ProcedureInstance) i.next();

	    if (msc.compare(first, p) >= 0) {
                maximal.add(p);
	    }
	}

        if (maximal.size() > 1) {
            // If exactly one method is not abstract, it is the most specific.
            List notAbstract = new ArrayList(maximal.size());
            for (Iterator j = maximal.iterator(); j.hasNext(); ) {
                ProcedureInstance p = (ProcedureInstance) j.next();
                if (! p.flags().isAbstract()) {
                    notAbstract.add(p);
                }
            }

            if (notAbstract.size() == 1) {
                maximal = notAbstract;
            }
            else if (notAbstract.size() == 0) {
                // all are abstract; if all signatures match, any will do.
                Iterator j = maximal.iterator();
                first = (ProcedureInstance) j.next();
                while (j.hasNext()) {
                    ProcedureInstance p = (ProcedureInstance) j.next();
                    if (! first.hasFormals(p.formalTypes())) {
                        // not all signatures match; must be ambiguous
                        return maximal;
                    }
                }

                // all signatures match, just take the first
                maximal = Collections.singletonList(first);
            }
        }

        return maximal;
    }

    /**
     * Class to handle the comparisons; dispatches to moreSpecific method.
     */
    protected class MostSpecificComparator implements Comparator {
	public int compare(Object o1, Object o2) {
	    ProcedureInstance p1 = (ProcedureInstance) o1;
	    ProcedureInstance p2 = (ProcedureInstance) o2;

	    if (moreSpecific(p1, p2)) return -1;
	    if (moreSpecific(p2, p1)) return 1;

            // JLS2 15.12.2.2 "two or more maximally specific methods"
            // if both abstract or not abstract, equally applicable
            // otherwise the non-abstract is more applicable
            if (p1.flags().isAbstract() == p2.flags().isAbstract())
                return 0;

            if (p1.flags().isAbstract())
                return 1;
            else
                return -1;
        }
    }

    /**
     * Populates the list acceptable with those MethodInstances which are
     * Applicable and Accessible as defined by JLS 15.11.2.1
     */
    protected List findAcceptableMethods(ReferenceType container, String name,
                                         List argTypes, ClassType currClass)
	throws SemanticException {

        assert_(container);
        assert_(argTypes);

        // The list of acceptable methods. These methods are accessible from
        // currClass, the method call is valid, and they are not overridden
        // by an unacceptable method (which can occur with protected methods
        // only).
        List acceptable = new ArrayList();

        // A list of unacceptable methods, where the method call is valid, but
        // the method is not accessible. This list is needed to make sure that
        // the acceptable methods are not overridden by an unacceptable method.
        List unacceptable = new ArrayList();
        
	Set visitedTypes = new HashSet();

	LinkedList typeQueue = new LinkedList();
	typeQueue.addLast(container);

	while (! typeQueue.isEmpty()) {
	    Type type = (Type) typeQueue.removeFirst();

	    if (visitedTypes.contains(type)) {
		continue;
	    }

	    visitedTypes.add(type);

            if (Report.should_report(Report.types, 2))
		Report.report(2, "Searching type " + type + " for method " +
                              name + "(" + listToString(argTypes) + ")");

	    if (! type.isReference()) {
	        throw new SemanticException("Cannot call method in " +
		    " non-reference type " + type + ".");
	    }
	    
	    for (Iterator i = type.toReference().methods().iterator(); i.hasNext(); ) {
		MethodInstance mi = (MethodInstance) i.next();

		if (Report.should_report(Report.types, 3))
		    Report.report(3, "Trying " + mi);

                if (methodCallValid(mi, name, argTypes)) {
                    if (isAccessible(mi, currClass)) {
                        if (Report.should_report(Report.types, 3)) {
                            Report.report(3, "->acceptable: " + mi + " in "
                                          + mi.container());
                        }

                        acceptable.add(mi);
                    }
                    else {
                        // method call is valid, but the method is
                        // unacceptable.
                        unacceptable.add(mi);
                    }
		}
            }
            if (type.toReference().superType() != null) {
                typeQueue.addLast(type.toReference().superType());
            }

            typeQueue.addAll(type.toReference().interfaces());
        }

        // remove any method in acceptable that are overridden by an
        // unacceptable
        // method.
        for (Iterator i = unacceptable.iterator(); i.hasNext();) {
            MethodInstance mi = (MethodInstance)i.next();
            acceptable.removeAll(mi.overrides());
        }

        return acceptable;
    }

    /**
     * Populates the list acceptable with those MethodInstances which are
     * Applicable and Accessible as defined by JLS 15.11.2.1
     */
    protected List findAcceptableConstructors(ClassType container,
                                              List argTypes,
                                              ClassType currClass)
        throws SemanticException
    {
        assert_(container);
        assert_(argTypes);

	List acceptable = new ArrayList();

	if (Report.should_report(Report.types, 2))
	    Report.report(2, "Searching type " + container +
                          " for constructor " + container + "(" +
                          listToString(argTypes) + ")");

	for (Iterator i = container.constructors().iterator(); i.hasNext(); ) {
	    ConstructorInstance ci = (ConstructorInstance) i.next();

	    if (Report.should_report(Report.types, 3))
		Report.report(3, "Trying " + ci);

	    if (callValid(ci, argTypes) && isAccessible(ci, currClass)) {
		if (Report.should_report(Report.types, 3))
		    Report.report(3, "->acceptable: " + ci);
		acceptable.add(ci);
	    }
	}

	return acceptable;
    }

    /**
     * Returns whether method 1 is <i>more specific</i> than method 2,
     * where <i>more specific</i> is defined as JLS 15.11.2.2
     */
    public boolean moreSpecific(ProcedureInstance p1, ProcedureInstance p2) {
        return p1.moreSpecificImpl(p2);
    }

    /**
     * Returns the supertype of type, or null if type has no supertype.
     **/
    public Type superType(ReferenceType type) {
        assert_(type);
	return type.superType();
    }

    /**
     * Returns an immutable list of all the interface types which type
     * implements.
     **/
    public List interfaces(ReferenceType type) {
        assert_(type);
	return type.interfaces();
    }

    /**
     * Requires: all type arguments are canonical.
     * Returns the least common ancestor of Type1 and Type2
     **/
    public Type leastCommonAncestor(Type type1, Type type2)
        throws SemanticException
    {
        assert_(type1);
        assert_(type2);

	if (equals(type1, type2)) return type1;

	if (type1.isNumeric() && type2.isNumeric()) {
	    if (isImplicitCastValid(type1, type2)) {
	        return type2;
	    }

	    if (isImplicitCastValid(type2, type1)) {
	        return type1;
	    }

	    if (type1.isChar() && type2.isByte() ||
	    	type1.isByte() && type2.isChar()) {
		return Int();
	    }

	    if (type1.isChar() && type2.isShort() ||
	    	type1.isShort() && type2.isChar()) {
		return Int();
	    }
	}

	if (type1.isArray() && type2.isArray()) {
	    return arrayOf(leastCommonAncestor(type1.toArray().base(),
					       type2.toArray().base()));
	}

	if (type1.isReference() && type2.isNull()) return type1;
	if (type2.isReference() && type1.isNull()) return type2;

	if (type1.isReference() && type2.isReference()) {
	    // Don't consider interfaces.
	    if (type1.isClass() && type1.toClass().flags().isInterface()) {
	        return Object();
	    }

	    if (type2.isClass() && type2.toClass().flags().isInterface()) {
	        return Object();
	    }

	    // Check against Object to ensure superType() is not null.
	    if (equals(type1, Object())) return type1;
	    if (equals(type2, Object())) return type2;

	    if (isSubtype(type1, type2)) return type2;
	    if (isSubtype(type2, type1)) return type1;

	    // Walk up the hierarchy
	    Type t1 = leastCommonAncestor(type1.toReference().superType(),
		                          type2);
	    Type t2 = leastCommonAncestor(type2.toReference().superType(),
					  type1);

	    if (equals(t1, t2)) return t1;

	    return Object();
	}

	throw new SemanticException(
	   "No least common ancestor found for types \"" + type1 +
	   "\" and \"" + type2 + "\".");
    }

    ////
    // Functions for method testing.
    ////

    /**
     * Returns true iff <p1> throws fewer exceptions than <p2>.
     */
    public boolean throwsSubset(ProcedureInstance p1, ProcedureInstance p2) {
        assert_(p1);
        assert_(p2);
        return p1.throwsSubsetImpl(p2);
    }

    /** Return true if t overrides mi */
    public boolean hasFormals(ProcedureInstance pi, List formalTypes) {
        assert_(pi);
        assert_(formalTypes);
        return pi.hasFormalsImpl(formalTypes);
    }

    /** Return true if t overrides mi */
    public boolean hasMethod(ReferenceType t, MethodInstance mi) {
        assert_(t);
        assert_(mi);
        return t.hasMethodImpl(mi);
    }

    public List overrides(MethodInstance mi) {
        return mi.overridesImpl();
    }

    public List implemented(MethodInstance mi) {
	return mi.implementedImpl(mi.container());
    }

    public boolean canOverride(MethodInstance mi, MethodInstance mj) {
        try {
            return mi.canOverrideImpl(mj, true);
        }
        catch (SemanticException e) {
            // this is the exception thrown by the canOverrideImpl check.
            // It should never be thrown if the quiet argument of
            // canOverrideImpl is true.
            throw new InternalCompilerError(e);
        }
    }

    public void checkOverride(MethodInstance mi, MethodInstance mj) throws SemanticException {
        mi.canOverrideImpl(mj, false);
    }

    /**
     * Returns true iff <m1> is the same method as <m2>
     */
    public boolean isSameMethod(MethodInstance m1, MethodInstance m2) {
        assert_(m1);
        assert_(m2);
        return m1.isSameMethodImpl(m2);
    }

    public boolean methodCallValid(MethodInstance prototype,
				   String name, List argTypes) {
        assert_(prototype);
        assert_(argTypes);
	return prototype.methodCallValidImpl(name, argTypes);
    }

    public boolean callValid(ProcedureInstance prototype, List argTypes) {
        assert_(prototype);
        assert_(argTypes);
        return prototype.callValidImpl(argTypes);
    }

    ////
    // Functions which yield particular types.
    ////
    public NullType Null()         { return NULL_; }
    public PrimitiveType Void()    { return VOID_; }
    public PrimitiveType Boolean() { return BOOLEAN_; }
    public PrimitiveType Char()    { return CHAR_; }
    public PrimitiveType Byte()    { return BYTE_; }
    public PrimitiveType Short()   { return SHORT_; }
    public PrimitiveType Int()     { return INT_; }
    public PrimitiveType Long()    { return LONG_; }
    public PrimitiveType Float()   { return FLOAT_; }
    public PrimitiveType Double()  { return DOUBLE_; }

    protected ClassType load(String name) {
      try {
          return (ClassType) typeForName(name);
      }
      catch (SemanticException e) {
          throw new InternalCompilerError("Cannot find class \"" +
                                          name + "\"; " + e.getMessage(),
                                          e);
      }
    }
    
    public Named forName(String name) throws SemanticException {
        try {
            return systemResolver.find(name);
        }
        catch (SemanticException e) {
            if (! StringUtil.isNameShort(name)) {
                String containerName = StringUtil.getPackageComponent(name);
                String shortName = StringUtil.getShortNameComponent(name);
                
                try {
                    Named container = forName(containerName);
		    if (container instanceof ClassType) {
			return classContextResolver((ClassType) container).find(shortName);
		    }
                }
                catch (SemanticException e2) {
                }
            }
	    
            // throw the original exception
            throw e;
        }
    }

    public Type typeForName(String name) throws SemanticException {
	return (Type) forName(name);
    }

    protected ClassType OBJECT_;
    protected ClassType CLASS_;
    protected ClassType STRING_;
    protected ClassType THROWABLE_;

    public ClassType Object()  { if (OBJECT_ != null) return OBJECT_;
                                 return OBJECT_ = load("java.lang.Object"); }
    public ClassType Class()   { if (CLASS_ != null) return CLASS_;
                                 return CLASS_ = load("java.lang.Class"); }
    public ClassType String()  { if (STRING_ != null) return STRING_;
                                 return STRING_ = load("java.lang.String"); }
    public ClassType Throwable() { if (THROWABLE_ != null) return THROWABLE_;
                                   return THROWABLE_ = load("java.lang.Throwable"); }
    public ClassType Error() { return load("java.lang.Error"); }
    public ClassType Exception() { return load("java.lang.Exception"); }
    public ClassType RuntimeException() { return load("java.lang.RuntimeException"); }
    public ClassType Cloneable() { return load("java.lang.Cloneable"); }
    public ClassType Serializable() { return load("java.io.Serializable"); }
    public ClassType NullPointerException() { return load("java.lang.NullPointerException"); }
    public ClassType ClassCastException()   { return load("java.lang.ClassCastException"); }
    public ClassType OutOfBoundsException() { return load("java.lang.ArrayIndexOutOfBoundsException"); }
    public ClassType ArrayStoreException()  { return load("java.lang.ArrayStoreException"); }
    public ClassType ArithmeticException()  { return load("java.lang.ArithmeticException"); }

    protected NullType createNull() {
        return new NullType_c(this);
    }

    protected PrimitiveType createPrimitive(PrimitiveType.Kind kind) {
        return new PrimitiveType_c(this, kind);
    }

    protected final NullType NULL_         = createNull();
    protected final PrimitiveType VOID_    = createPrimitive(PrimitiveType.VOID);
    protected final PrimitiveType BOOLEAN_ = createPrimitive(PrimitiveType.BOOLEAN);
    protected final PrimitiveType CHAR_    = createPrimitive(PrimitiveType.CHAR);
    protected final PrimitiveType BYTE_    = createPrimitive(PrimitiveType.BYTE);
    protected final PrimitiveType SHORT_   = createPrimitive(PrimitiveType.SHORT);
    protected final PrimitiveType INT_     = createPrimitive(PrimitiveType.INT);
    protected final PrimitiveType LONG_    = createPrimitive(PrimitiveType.LONG);
    protected final PrimitiveType FLOAT_   = createPrimitive(PrimitiveType.FLOAT);
    protected final PrimitiveType DOUBLE_  = createPrimitive(PrimitiveType.DOUBLE);

    public Object placeHolder(TypeObject o) {
        assert_(o);
    	return placeHolder(o, new HashSet());
    }

    public Object placeHolder(TypeObject o, Set roots) {
        assert_(o);

        if (o instanceof ClassType) {
            ClassType ct = (ClassType) o;

            // This should never happen: anonymous and local types cannot
            // appear in signatures.
            if (ct.isLocal() || ct.isAnonymous()) {
                throw new InternalCompilerError("Cannot serialize " + o + ".");
            }

            return new PlaceHolder_c(ct);
        }

	return o;
    }

    protected UnknownType unknownType = new UnknownType_c(this);
    protected UnknownPackage unknownPackage = new UnknownPackage_c(this);
    protected UnknownQualifier unknownQualifier = new UnknownQualifier_c(this);

    public UnknownType unknownType(Position pos) {
	return unknownType;
    }

    public UnknownPackage unknownPackage(Position pos) {
	return unknownPackage;
    }

    public UnknownQualifier unknownQualifier(Position pos) {
	return unknownQualifier;
    }

    public Package packageForName(Package prefix, String name) throws SemanticException {
        return createPackage(prefix, name);
    }

    public Package packageForName(String name) throws SemanticException {
        if (name == null || name.equals("")) {
	    return null;
	}

	String s = StringUtil.getShortNameComponent(name);
	String p = StringUtil.getPackageComponent(name);

	return packageForName(packageForName(p), s);
    }

    public Package createPackage(Package prefix, String name) {
        assert_(prefix);
	return new Package_c(this, prefix, name);
    }

    public Package createPackage(String name) {
        if (name == null || name.equals("")) {
	    return null;
	}

	String s = StringUtil.getShortNameComponent(name);
	String p = StringUtil.getPackageComponent(name);

	return createPackage(createPackage(p), s);
    }

    /**
     * Returns a type identical to <type>, but with <dims> more array
     * dimensions.
     */
    public ArrayType arrayOf(Type type) {
        assert_(type);
        return arrayOf(type.position(), type);
    }

    public ArrayType arrayOf(Position pos, Type type) {
        assert_(type);
	return arrayType(pos, type);
    }

    /**
     * Factory method for ArrayTypes.
     */
    protected ArrayType arrayType(Position pos, Type type) {
	return new ArrayType_c(this, pos, type);
    }

    public ArrayType arrayOf(Type type, int dims) {
        return arrayOf(null, type, dims);
    }

    public ArrayType arrayOf(Position pos, Type type, int dims) {
	if (dims > 1) {
	    return arrayOf(pos, arrayOf(pos, type, dims-1));
	}
	else if (dims == 1) {
	    return arrayOf(pos, type);
	}
	else {
	    throw new InternalCompilerError(
		"Must call arrayOf(type, dims) with dims > 0");
	}
    }

    /**
     * Returns a canonical type corresponding to the Java Class object
     * theClass.  Does not require that <theClass> have a JavaClass
     * registered in this typeSystem.  Does not register the type in
     * this TypeSystem.  For use only by JavaClass implementations.
     **/
    public Type typeForClass(Class clazz) throws SemanticException
    {
	if (clazz == Void.TYPE)      return VOID_;
	if (clazz == Boolean.TYPE)   return BOOLEAN_;
	if (clazz == Byte.TYPE)      return BYTE_;
	if (clazz == Character.TYPE) return CHAR_;
	if (clazz == Short.TYPE)     return SHORT_;
	if (clazz == Integer.TYPE)   return INT_;
	if (clazz == Long.TYPE)      return LONG_;
	if (clazz == Float.TYPE)     return FLOAT_;
	if (clazz == Double.TYPE)    return DOUBLE_;

	if (clazz.isArray()) {
	    return arrayOf(typeForClass(clazz.getComponentType()));
	}

	return (Type) systemResolver.find(clazz.getName());
    }

    public Set getTypeEncoderRootSet(Type t) {
        // The root set is now just the type itself. Previously it contained
        // the member classes tool
	return Collections.singleton(t);
    }

    /**
     * Get the transformed class name of a class.
     * This utility method returns the "mangled" name of the given class,
     * whereby all periods ('.') following the toplevel class name
     * are replaced with dollar signs ('$'). If any of the containing
     * classes is not a member class or a top level class, then null is
     * returned.
     */
    public String getTransformedClassName(ClassType ct) {
        StringBuffer sb = new StringBuffer(ct.fullName().length());
        if (!ct.isMember() && !ct.isTopLevel()) {
            return null;
        }
        while (ct.isMember()) {
            sb.insert(0, ct.name());
            sb.insert(0, '$');
            ct = ct.outer();
            if (!ct.isMember() && !ct.isTopLevel()) {
                return null;
            }
        }

        sb.insert(0, ct.fullName());
        return sb.toString();
    }

    public String translatePackage(Resolver c, Package p) {
        return p.translate(c);
    }

    public String translateArray(Resolver c, ArrayType t) {
        return t.translate(c);
    }

    public String translateClass(Resolver c, ClassType t) {
        return t.translate(c);
    }

    public String translatePrimitive(Resolver c, PrimitiveType t) {
        return t.translate(c);
    }

    public PrimitiveType primitiveForName(String name)
	throws SemanticException {

	if (name.equals("void")) return Void();
	if (name.equals("boolean")) return Boolean();
	if (name.equals("char")) return Char();
	if (name.equals("byte")) return Byte();
	if (name.equals("short")) return Short();
	if (name.equals("int")) return Int();
	if (name.equals("long")) return Long();
	if (name.equals("float")) return Float();
	if (name.equals("double")) return Double();

	throw new SemanticException("Unrecognized primitive type \"" +
	    name + "\".");
    }

    protected LazyClassInitializer defaultClassInit;

    public LazyClassInitializer defaultClassInitializer() {
        if (defaultClassInit == null) {
            defaultClassInit = new LazyClassInitializer_c(this);
        }

        return defaultClassInit;
    }

    public final ParsedClassType createClassType() {
        return createClassType(defaultClassInitializer(), null);
    }
    public final ParsedClassType createClassType(Source fromSource) {
        return createClassType(defaultClassInitializer(), fromSource);
    }

    public final ParsedClassType createClassType(LazyClassInitializer init) {
        return createClassType(init, null);
    }

    public ParsedClassType createClassType(LazyClassInitializer init, Source fromSource) {
        return new ParsedClassType_c(this, init, fromSource);
    }

    public List defaultPackageImports() {
	List l = new ArrayList(1);
	l.add("java.lang");
	return l;
    }

    public PrimitiveType promote(Type t1, Type t2) throws SemanticException {
	if (! t1.isNumeric()) {
	    throw new SemanticException(
		"Cannot promote non-numeric type " + t1);
	}

	if (! t2.isNumeric()) {
	    throw new SemanticException(
		"Cannot promote non-numeric type " + t2);
	}

	return promoteNumeric(t1.toPrimitive(), t2.toPrimitive());
    }

    protected PrimitiveType promoteNumeric(PrimitiveType t1, PrimitiveType t2) {
	if (t1.isDouble() || t2.isDouble()) {
	    return Double();
	}

	if (t1.isFloat() || t2.isFloat()) {
	    return Float();
	}

	if (t1.isLong() || t2.isLong()) {
	    return Long();
	}

	return Int();
    }

    public PrimitiveType promote(Type t) throws SemanticException {
	if (! t.isNumeric()) {
	    throw new SemanticException(
		"Cannot promote non-numeric type " + t);
	}

	return promoteNumeric(t.toPrimitive());
    }

    protected PrimitiveType promoteNumeric(PrimitiveType t) {
	if (t.isByte() || t.isShort() || t.isChar()) {
	    return Int();
	}

	return t.toPrimitive();
    }

    /** All possible <i>access</i> flags. */
    protected final Flags ACCESS_FLAGS = Public().Protected().Private();

    /** All flags allowed for a local variable. */
    protected final Flags LOCAL_FLAGS = Final();

    /** All flags allowed for a field. */
    protected final Flags FIELD_FLAGS = ACCESS_FLAGS.Static().Final()
                                        .Transient().Volatile();

    /** All flags allowed for a constructor. */
    protected final Flags CONSTRUCTOR_FLAGS = ACCESS_FLAGS
                                              .Synchronized().Native();

    /** All flags allowed for an initializer block. */
    protected final Flags INITIALIZER_FLAGS = Static();

    /** All flags allowed for a method. */
    protected final Flags METHOD_FLAGS = ACCESS_FLAGS.Abstract().Static()
                                         .Final().Native()
                                         .Synchronized().StrictFP();

    /** All flags allowed for a top-level class. */
    protected final Flags TOP_LEVEL_CLASS_FLAGS = ACCESS_FLAGS.clear(Private())
                                                  .Abstract().Final()
                                                  .StrictFP().Interface();

    /** All flags allowed for a member class. */
    protected final Flags MEMBER_CLASS_FLAGS = ACCESS_FLAGS.Static()
                                                  .Abstract().Final()
                                                  .StrictFP().Interface();


    /** All flags allowed for a local class. */
    protected final Flags LOCAL_CLASS_FLAGS = TOP_LEVEL_CLASS_FLAGS
                                              .clear(ACCESS_FLAGS);

    public void checkMethodFlags(Flags f) throws SemanticException {
      	if (! f.clear(METHOD_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare method with flags " +
		f.clear(METHOD_FLAGS) + ".");
	}

        if (f.isAbstract() && f.isPrivate()) {
	    throw new SemanticException(
		"Cannot declare method that is both abstract and private.");
        }

        if (f.isAbstract() && f.isStatic()) {
	    throw new SemanticException(
		"Cannot declare method that is both abstract and static.");
        }

        if (f.isAbstract() && f.isFinal()) {
	    throw new SemanticException(
		"Cannot declare method that is both abstract and final.");
        }

        if (f.isAbstract() && f.isNative()) {
	    throw new SemanticException(
		"Cannot declare method that is both abstract and native.");
        }

        if (f.isAbstract() && f.isSynchronized()) {
	    throw new SemanticException(
		"Cannot declare method that is both abstract and synchronized.");
        }

        if (f.isAbstract() && f.isStrictFP()) {
	    throw new SemanticException(
		"Cannot declare method that is both abstract and strictfp.");
        }

	checkAccessFlags(f);
    }

    public void checkLocalFlags(Flags f) throws SemanticException {
      	if (! f.clear(LOCAL_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare local variable with flags " +
		f.clear(LOCAL_FLAGS) + ".");
	}
    }

    public void checkFieldFlags(Flags f) throws SemanticException {
      	if (! f.clear(FIELD_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare field with flags " +
		f.clear(FIELD_FLAGS) + ".");
	}

	checkAccessFlags(f);
    }

    public void checkConstructorFlags(Flags f) throws SemanticException {
      	if (! f.clear(CONSTRUCTOR_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare constructor with flags " +
		f.clear(CONSTRUCTOR_FLAGS) + ".");
	}

	checkAccessFlags(f);
    }

    public void checkInitializerFlags(Flags f) throws SemanticException {
      	if (! f.clear(INITIALIZER_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare initializer with flags " +
		f.clear(INITIALIZER_FLAGS) + ".");
	}
    }

    public void checkTopLevelClassFlags(Flags f) throws SemanticException {
      	if (! f.clear(TOP_LEVEL_CLASS_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare a top-level class with flag(s) " +
		f.clear(TOP_LEVEL_CLASS_FLAGS) + ".");
	}

        if (f.isFinal() && f.isInterface()) {
            throw new SemanticException("Cannot declare a final interface.");
        }

	checkAccessFlags(f);
    }

    public void checkMemberClassFlags(Flags f) throws SemanticException {
      	if (! f.clear(MEMBER_CLASS_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare a member class with flag(s) " +
		f.clear(MEMBER_CLASS_FLAGS) + ".");
	}

        if (f.isStrictFP() && f.isInterface()) {
            throw new SemanticException("Cannot declare a strictfp interface.");
        }

        if (f.isFinal() && f.isInterface()) {
            throw new SemanticException("Cannot declare a final interface.");
        }

	checkAccessFlags(f);
    }

    public void checkLocalClassFlags(Flags f) throws SemanticException {
        if (f.isInterface()) {
            throw new SemanticException("Cannot declare a local interface.");
        }

      	if (! f.clear(LOCAL_CLASS_FLAGS).equals(Flags.NONE)) {
	    throw new SemanticException(
		"Cannot declare a local class with flag(s) " +
		f.clear(LOCAL_CLASS_FLAGS) + ".");
	}

	checkAccessFlags(f);
    }

    public void checkAccessFlags(Flags f) throws SemanticException {
        int count = 0;
        if (f.isPublic()) count++;
        if (f.isProtected()) count++;
        if (f.isPrivate()) count++;

	if (count > 1) {
	    throw new SemanticException(
		"Invalid access flags: " + f.retain(ACCESS_FLAGS) + ".");
	}
    }

    /**
     * Utility method to gather all the superclasses and interfaces of
     * <code>ct</code> that may contain abstract methods that must be
     * implemented by <code>ct</code>. The list returned also contains
     * <code>ct</code>.
     */
    protected List abstractSuperInterfaces(ReferenceType rt) {
        List superInterfaces = new LinkedList();
        superInterfaces.add(rt);

        for (Iterator iter = rt.interfaces().iterator(); iter.hasNext(); ) {
            ClassType interf = (ClassType)iter.next();
            superInterfaces.addAll(abstractSuperInterfaces(interf));
        }

        if (rt.superType() != null) {
            ClassType c = rt.superType().toClass();
            if (c.flags().isAbstract()) {
                // the superclass is abstract, so it may contain methods
                // that must be implemented.
                superInterfaces.addAll(abstractSuperInterfaces(c));
            }
            else {
                // the superclass is not abstract, so it must implement
                // all abstract methods of any interfaces it implements, and
                // any superclasses it may have.
            }
        }
        return superInterfaces;
    }

    /**
     * Assert that <code>ct</code> implements all abstract methods required;
     * that is, if it is a concrete class, then it must implement all
     * interfaces and abstract methods that it or it's superclasses declare, and if 
     * it is an abstract class then any methods that it overrides are overridden 
     * correctly.
     */
    public void checkClassConformance(ClassType ct) throws SemanticException {
        if (ct.flags().isAbstract()) {
            // don't need to check abstract classes and interfaces            
            return;
        }

        // build up a list of superclasses and interfaces that ct 
        // extends/implements that may contain abstract methods that 
        // ct must define.
        List superInterfaces = abstractSuperInterfaces(ct);

        // check each abstract method of the classes and interfaces in
        // superInterfaces
        for (Iterator i = superInterfaces.iterator(); i.hasNext(); ) {
            ReferenceType rt = (ReferenceType)i.next();
            for (Iterator j = rt.methods().iterator(); j.hasNext(); ) {
                MethodInstance mi = (MethodInstance)j.next();
                if (!mi.flags().isAbstract()) {
                    // the method isn't abstract, so ct doesn't have to
                    // implement it.
                    continue;
                }

                boolean implFound = false;
                ReferenceType curr = ct;
                while (curr != null && !implFound) {
                    List possible = curr.methods(mi.name(), mi.formalTypes());
                    for (Iterator k = possible.iterator(); k.hasNext(); ) {
                        MethodInstance mj = (MethodInstance)k.next();
                        if (!mj.flags().isAbstract() && 
                            ((isAccessible(mi, ct) && isAccessible(mj, ct)) || 
                                    isAccessible(mi, mj.container().toClass()))) {
                            // The method mj may be a suitable implementation of mi.
                            // mj is not abstract, and either mj's container 
                            // can access mi (thus mj can really override mi), or
                            // mi and mj are both accessible from ct (e.g.,
                            // mi is declared in an interface that ct implements,
                            // and mj is defined in a superclass of ct).
                            
                            // If neither the method instance mj nor the method 
                            // instance mi is declared in the class type ct, then 
                            // we need to check that it has appropriate protections.
                            if (!equals(ct, mj.container()) && !equals(ct, mi.container())) {
                                try {
                                    // check that mj can override mi, which
                                    // includes access protection checks.
                                    checkOverride(mj, mi);
                                }
                                catch (SemanticException e) {
                                    // change the position of the semantic
                                    // exception to be the class that we
                                    // are checking.
                                    throw new SemanticException(e.getMessage(),
                                        ct.position());
                                }
                            }
                            else {
                                // the method implementation mj or mi was
                                // declared in ct. So other checks will take
                                // care of access issues
                            }
                            implFound = true;
                            break;
                        }
                    }

                    if (curr == mi.container()) {
                        // we've reached the definition of the abstract 
                        // method. We don't want to look higher in the 
                        // hierarchy; this is not an optimization, but is 
                        // required for correctness. 
                        break;
                    }
                    
                    curr = curr.superType() ==  null ?
                           null : curr.superType().toReference();
                }


                // did we find a suitable implementation of the method mi?
                if (!implFound && !ct.flags().isAbstract()) {
                    throw new SemanticException(ct.fullName() + " should be " +
                            "declared abstract; it does not define " +
                            mi.signature() + ", which is declared in " +
                            rt.toClass().fullName(), ct.position());
                }
            }
        }
    }

    /**
     * Returns t, modified as necessary to make it a legal
     * static target.
     */
    public Type staticTarget(Type t) {
        // Nothing needs done in standard Java.
        return t;
    }

    protected void initFlags() {
        flagsForName = new HashMap();
        flagsForName.put("public", Flags.PUBLIC);
        flagsForName.put("private", Flags.PRIVATE);
        flagsForName.put("protected", Flags.PROTECTED);
        flagsForName.put("static", Flags.STATIC);
        flagsForName.put("final", Flags.FINAL);
        flagsForName.put("synchronized", Flags.SYNCHRONIZED);
        flagsForName.put("transient", Flags.TRANSIENT);
        flagsForName.put("native", Flags.NATIVE);
        flagsForName.put("interface", Flags.INTERFACE);
        flagsForName.put("abstract", Flags.ABSTRACT);
        flagsForName.put("volatile", Flags.VOLATILE);
        flagsForName.put("strictfp", Flags.STRICTFP);
    }

    public Flags createNewFlag(String name, Flags after) {
        Flags f = Flags.createFlag(name, name, name, after);
        flagsForName.put(name, f);
        return f;
    }

    public Flags NoFlags()      { return Flags.NONE; }
    public Flags Public()       { return Flags.PUBLIC; }
    public Flags Private()      { return Flags.PRIVATE; }
    public Flags Protected()    { return Flags.PROTECTED; }
    public Flags Static()       { return Flags.STATIC; }
    public Flags Final()        { return Flags.FINAL; }
    public Flags Synchronized() { return Flags.SYNCHRONIZED; }
    public Flags Transient()    { return Flags.TRANSIENT; }
    public Flags Native()       { return Flags.NATIVE; }
    public Flags Interface()    { return Flags.INTERFACE; }
    public Flags Abstract()     { return Flags.ABSTRACT; }
    public Flags Volatile()     { return Flags.VOLATILE; }
    public Flags StrictFP()     { return Flags.STRICTFP; }

    public Flags flagsForBits(int bits) {
        Flags f = Flags.NONE;

        if ((bits & Modifier.PUBLIC) != 0)       f = f.Public();
        if ((bits & Modifier.PRIVATE) != 0)      f = f.Private();
        if ((bits & Modifier.PROTECTED) != 0)    f = f.Protected();
        if ((bits & Modifier.STATIC) != 0)       f = f.Static();
        if ((bits & Modifier.FINAL) != 0)        f = f.Final();
        if ((bits & Modifier.SYNCHRONIZED) != 0) f = f.Synchronized();
        if ((bits & Modifier.TRANSIENT) != 0)    f = f.Transient();
        if ((bits & Modifier.NATIVE) != 0)       f = f.Native();
        if ((bits & Modifier.INTERFACE) != 0)    f = f.Interface();
        if ((bits & Modifier.ABSTRACT) != 0)     f = f.Abstract();
        if ((bits & Modifier.VOLATILE) != 0)     f = f.Volatile();
        if ((bits & Modifier.STRICT) != 0)       f = f.StrictFP();

        return f;
    }

    public Flags flagsForName(String name) {
        Flags f = (Flags) flagsForName.get(name);
        if (f == null) {
            throw new InternalCompilerError("No flag named \"" + name + "\".");
        }
        return f;
    }

    public String toString() {
        return StringUtil.getShortNameComponent(getClass().getName());
    }

}
