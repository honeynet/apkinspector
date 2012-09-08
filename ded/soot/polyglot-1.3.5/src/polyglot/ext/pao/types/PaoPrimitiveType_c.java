package polyglot.ext.pao.types;

import polyglot.ext.jl.types.PrimitiveType_c;
import polyglot.types.Type;
import polyglot.types.TypeSystem;

/**
 * A PAO primitive type.  In the PAO extension, primitives are considered a 
 * subtype of <code>Object</code>.
 */
public class PaoPrimitiveType_c extends PrimitiveType_c
{
    protected PaoPrimitiveType_c() { 
        super();
    }

    public PaoPrimitiveType_c(TypeSystem ts, Kind kind) {
        super(ts, kind);
    }

    /**
     * Returns <code>true</code> if <code>ancestor</code> is the 
     * <code>Object</code> type, as primitives are considered a
     * subtype of <code>Object</code>.
     */
    public boolean descendsFromImpl(Type ancestor) {
        return ts.equals(ancestor, ts.Object());
    }

    /**
     * Returns <code>true</code> if the normal rules for implicit casts
     * hold, or if casting a primitive to <code>Object</code>, as primitives
     * are considered a subtype of <code>Object</code>. 
     */
    public boolean isImplicitCastValidImpl(Type toType) {
        return ts.equals(toType, ts.Object()) ||
               super.isImplicitCastValidImpl(toType);
    }

    /**
     * Returns <code>true</code> if the normal rules for casts
     * hold, or if casting a primitive to <code>Object</code>, as primitives
     * are considered a subtype of <code>Object</code>. 
     */
    public boolean isCastValidImpl(Type toType) {
        return ts.equals(toType, ts.Object()) || super.isCastValidImpl(toType);
    }
}
