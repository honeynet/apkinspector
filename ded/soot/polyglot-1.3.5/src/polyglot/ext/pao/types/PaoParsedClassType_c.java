package polyglot.ext.pao.types;

import polyglot.ext.jl.types.ParsedClassType_c;
import polyglot.frontend.Source;
import polyglot.types.LazyClassInitializer;
import polyglot.types.Type;
import polyglot.types.TypeSystem;

/**
 * A PAO class type. This class overrides the method 
 * {@link #isCastValidImpl(Type) isCastValidImpl(Type)} to allow casting from
 * <code>Object</code> to primitives.
 */
public class PaoParsedClassType_c extends ParsedClassType_c {
    protected PaoParsedClassType_c() {
        super();
    }

    public PaoParsedClassType_c(TypeSystem ts, LazyClassInitializer init,
            Source fromSource) {
        super(ts, init, fromSource);
    }

    /**
     * Returns <code>true</code> if normal casting rules permit this cast, or
     * if this <code>ClassType</code> is <code>Object</code> and the 
     * <code>toType</code> is a primitive.
     * 
     * @see polyglot.ext.jl.types.ClassType_c#isCastValidImpl(Type)
     */
    public boolean isCastValidImpl(Type toType) {
        return toType.isPrimitive() && ts.equals(this, ts.Object())
                || super.isCastValidImpl(toType);
    }
}