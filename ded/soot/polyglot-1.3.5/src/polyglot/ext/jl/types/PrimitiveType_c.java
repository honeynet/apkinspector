package polyglot.ext.jl.types;

import polyglot.types.*;
import polyglot.util.InternalCompilerError;
import polyglot.visit.Translator;
import polyglot.main.Options;

import java.util.*;

/**
 * An <code>PrimitiveType_c</code> represents a primitive type.
 */
public class PrimitiveType_c extends Type_c implements PrimitiveType
{
    protected Kind kind;

    /** Used for deserializing types. */
    protected PrimitiveType_c() { }

    public PrimitiveType_c(TypeSystem ts, Kind kind) {
            super(ts);
            this.kind = kind;
    }

    public Kind kind() {
            return kind;
    }

    public String toString() {
            return kind.toString();
    }

    public String translate(Resolver c) {
            String s = kind.toString();
            
            return s;
    }

    public boolean isCanonical() { return true; }
    public boolean isPrimitive() { return true; }
    public PrimitiveType toPrimitive() { return this; }

    public boolean isVoid() { return kind == VOID; }
    public boolean isBoolean() { return kind == BOOLEAN; }
    public boolean isChar() { return kind == CHAR; }
    public boolean isByte() { return kind == BYTE; }
    public boolean isShort() { return kind == SHORT; }
    public boolean isInt() { return kind == INT; }
    public boolean isLong() { return kind == LONG; }
    public boolean isFloat() { return kind == FLOAT; }
    public boolean isDouble() { return kind == DOUBLE; }
    public boolean isIntOrLess() { return kind == CHAR || kind == BYTE || kind == SHORT || kind == INT; }
    public boolean isLongOrLess() { return isIntOrLess() || kind == LONG; }
    public boolean isNumeric() { return isLongOrLess() || kind == FLOAT || kind == DOUBLE; }

    public int hashCode() {
            return kind.hashCode();
    }

    public boolean equalsImpl(TypeObject t) {
        if (t instanceof PrimitiveType) {
            PrimitiveType p = (PrimitiveType) t;
            return kind() == p.kind();
        }
        return false;
    }

    public String wrapperTypeString(TypeSystem ts) {
            return ts.wrapperTypeString(this);
    }
    
    public String name() {
            return toString();	
    }
    
    public String fullName() {
            return name();
    }

    public boolean descendsFromImpl(Type ancestor) {
        return false;
    }

    public boolean isImplicitCastValidImpl(Type toType) {
        if (! toType.isPrimitive()) return false;

        PrimitiveType t = toType.toPrimitive();
        PrimitiveType f = this;

        if (t.isVoid()) return false;
        if (f.isVoid()) return false;

        if (ts.equals(t, f)) return true;

        if (t.isBoolean()) return f.isBoolean();
        if (f.isBoolean()) return false;

        if (! f.isNumeric() || ! t.isNumeric()) return false;

        if (t.isDouble()) return true;
        if (f.isDouble()) return false;

        if (t.isFloat()) return true;
        if (f.isFloat()) return false;

        if (t.isLong()) return true;
        if (f.isLong()) return false;

        if (t.isInt()) return true;
        if (f.isInt()) return false;

        if (t.isShort()) return f.isShort() || f.isByte();
        if (f.isShort()) return false;

        if (t.isChar()) return f.isChar();
        if (f.isChar()) return false;

        if (t.isByte()) return f.isByte();
        if (f.isByte()) return false;

        return false;
    }

    /**
     * Requires: all type arguments are canonical.  ToType is not a NullType.
     *
     * Returns true iff a cast from this to toType is valid; in other
     * words, some non-null members of this are also members of toType.
     **/
    public boolean isCastValidImpl(Type toType) {
	if (isVoid() || toType.isVoid()) return false;
        if (ts.equals(this, toType)) return true;
	if (isNumeric() && toType.isNumeric()) return true;
        return false;
    }

    /**
     * Returns true if literal value <code>value</code> can be converted to
     * this primitive type.  This method should be removed.  It is kept
     * for backward compatibility.
     */
    public boolean numericConversionValidImpl(long value) {
        return numericConversionValidImpl(new Long(value));
    }

    /**
     * Returns true if literal value <code>value</code> can be converted to
     * this primitive type.
     */
    public boolean numericConversionValidImpl(Object value) {
        if (value == null)
            return false;
        if (value instanceof Float || value instanceof Double)
            return false;

        long v;

        if (value instanceof Number) {
            v = ((Number) value).longValue();
        }
        else if (value instanceof Character) {
            v = ((Character) value).charValue();
        }
        else {
            return false;
        }

        if (isLong())
            return true;
        if (isInt())
            return Integer.MIN_VALUE <= v && v <= Integer.MAX_VALUE;
        if (isChar())
            return Character.MIN_VALUE <= v && v <= Character.MAX_VALUE;
        if (isShort())
            return Short.MIN_VALUE <= v && v <= Short.MAX_VALUE;
        if (isByte())
            return Byte.MIN_VALUE <= v && v <= Byte.MAX_VALUE;

        return false;
    }
}
