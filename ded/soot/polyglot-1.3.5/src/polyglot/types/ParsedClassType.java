package polyglot.types;

import polyglot.frontend.Source;
import polyglot.util.Position;

/**
 * A <code>ParsedClassType</code> represents a class loaded from a source file.
 * <code>ParsedClassType</code>s are mutable.
 */
public interface ParsedClassType extends ClassType
{
    /**
     * Position of the type's declaration.
     */
    void position(Position pos);
    
    /**
     * The <code>Source</code> that this class type
     * was loaded from. Should be <code>null</code> if it was not loaded from
     * a <code>Source</code> during this compilation. 
     */
    Source fromSource();

    /**
     * Set the class's package.
     */
    void package_(Package p);

    /**
     * Set the class's super type.
     */
    void superType(Type t);

    /**
     * Add an interface to the class.
     */
    void addInterface(Type t);

    /**
     * Add a field to the class.
     */
    void addField(FieldInstance fi);

    /**
     * Add a method to the class.
     */
    void addMethod(MethodInstance mi);

    /**
     * Add a constructor to the class.
     */
    void addConstructor(ConstructorInstance ci);

    /**
     * Add a member class to the class.
     */
    void addMemberClass(ClassType t);

    /**
     * Set the flags of the class.
     */
    void flags(Flags flags);

    /**
     * Set the class's outer class.
     */
    void outer(ClassType t);

    /**
     * Set the name of the class.  Throws <code>InternalCompilerError</code>
     * if called on an anonymous class.
     */
    void name(String name);

    /**
     * Set the class's kind.
     */
    void kind(Kind kind);

    /**
     * Set whether the class was declared in a static context.
     */
    void inStaticContext(boolean inStaticContext);
}
