package polyglot.types;

import polyglot.util.Enum;
import java.util.List;

/**
 * A <code>ClassType</code> represents a class, either loaded from a
 * classpath, parsed from a source file, or obtained from other source.
 * A <code>ClassType</code> is not necessarily named.
 */
public interface ClassType extends Importable, ReferenceType, MemberInstance
{
    public static class Kind extends Enum {
        public Kind(String name) {
            super(name);
        }
    }

    public static final Kind TOP_LEVEL = new Kind("top-level");
    public static final Kind MEMBER = new Kind("member");
    public static final Kind LOCAL = new Kind("local");
    public static final Kind ANONYMOUS = new Kind("anonymous");

    /** Get the class's kind. */
    Kind kind();

    /**
     * Return true if the class is top-level (i.e., not inner).
     * Equivalent to kind() == TOP_LEVEL.
     */
    boolean isTopLevel();

    /**
     * Return true if the class is an inner class.
     * Equivalent to kind() == MEMBER || kind() == LOCAL || kind() == ANONYMOUS.
     * @deprecated Was incorrectly defined. Use isNested for nested classes, 
     *          and isInnerClass for inner classes.
     */
    boolean isInner();

    /**
     * Return true if the class is a nested.
     * Equivalent to kind() == MEMBER || kind() == LOCAL || kind() == ANONYMOUS.
     */
    boolean isNested();

    /**
     * Return true if the class is an inner class, that is, it is a nested
     * class that is not explicitly or implicitly declared static; an interface
     * is never an inner class.
     */
    boolean isInnerClass();

    /**
     * Return true if the class is a member class.
     * Equivalent to kind() == MEMBER.
     */
    boolean isMember();

    /**
     * Return true if the class is a local class.
     * Equivalent to kind() == LOCAL.
     */
    boolean isLocal();

    /**
     * Return true if the class is an anonymous class.
     * Equivalent to kind() == ANONYMOUS.
     */
    boolean isAnonymous();

    /**
     * Return true if the class declaration occurs in a static context.
     * Is used to determine if a nested class is implicitly static.
     */
    boolean inStaticContext();
    
    /**
     * The class's constructors.
     * A list of <code>ConstructorInstance</code>.
     * @see polyglot.types.ConstructorInstance
     */
    List constructors();

    /**
     * The class's member classes.
     * A list of <code>ClassType</code>.
     * @see polyglot.types.ClassType
     */
    List memberClasses();

    /** Returns the member class with the given name, or null. */
    ClassType memberClassNamed(String name);

    /** Get a field by name, or null. */
    FieldInstance fieldNamed(String name);

    /** Return true if the class is strictly contained in <code>outer</code>. */
    boolean isEnclosed(ClassType outer);

    /**
     * Implementation of <code>isEnclosed</code>.
     * This method should only be called by the <code>TypeSystem</code>
     * or by a subclass.
     */
    boolean isEnclosedImpl(ClassType outer);

    /** Return true if an object of the class has
     * an enclosing instance of <code>encl</code>. */
    boolean hasEnclosingInstance(ClassType encl);

    /**
     * Implementation of <code>hasEnclosingInstance</code>.
     * This method should only be called by the <code>TypeSystem</code>
     * or by a subclass.
     */
    boolean hasEnclosingInstanceImpl(ClassType encl);

    /** The class's outer class if this is a nested class, or null. */
    ClassType outer();
}
