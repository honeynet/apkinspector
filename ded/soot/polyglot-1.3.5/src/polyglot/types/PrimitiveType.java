package polyglot.types;

import polyglot.util.Enum;

/**
 * A <code>PrimitiveType</code> represents a type which may not be directly 
 * coerced to java.lang.Object (under the standard Java type system).    
 * <p>
 * This class should never be instantiated directly. Instead, you should
 * use the <code>TypeSystem.get*</code> methods.
 */
public interface PrimitiveType extends Type, Named
{
    /** The kind of the primitive type. */
    public class Kind extends Enum {
	public Kind(String name) { super(name); }
    }

    public static final Kind VOID    = new Kind("void");
    public static final Kind BOOLEAN = new Kind("boolean");
    public static final Kind BYTE    = new Kind("byte");
    public static final Kind CHAR    = new Kind("char");
    public static final Kind SHORT   = new Kind("short");
    public static final Kind INT     = new Kind("int");
    public static final Kind LONG    = new Kind("long");
    public static final Kind FLOAT   = new Kind("float");
    public static final Kind DOUBLE  = new Kind("double");

    /**
     * The kind of primitive.
     */
    Kind kind();

    /**
     * A string representing the type used to box this primitive.
     */
    String wrapperTypeString(TypeSystem ts);
}
