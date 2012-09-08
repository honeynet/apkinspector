package polyglot.ext.pao.extension;

import polyglot.ast.*;
import polyglot.ext.pao.types.PaoTypeSystem;
import polyglot.types.MethodInstance;

/**
 * The <code>PaoExt</code> implementation for the 
 * <code>Binary</code> AST node.
 */
public class PaoBinaryExt_c extends PaoExt_c {
    /**
     * Rewrite the binary operators <code>==</code> and <code>&excl;=</code> to 
     * invoke <code>Primitive.equals(o, p)</code>.
     * 
     * @see PaoExt#rewrite(PaoTypeSystem, NodeFactory)
     * @see polyglot.ext.pao.runtime.Primitive#equals(Object, Object)
     */
    public Node rewrite(PaoTypeSystem ts, NodeFactory nf) {
        Binary b = (Binary) node();
        Expr l = b.left();
        Expr r = b.right();

        if (b.operator() == Binary.EQ || b.operator() == Binary.NE) {
            MethodInstance mi = ((PaoTypeSystem) ts).primitiveEquals();

            // The container of mi, mi.container(), is the super class of
            // the runtime boxed representations of primitive values.
            if (ts.isSubtype(l.type(), mi.container()) ||
                ts.equals(l.type(), ts.Object())) {
                // The left operand is either a subtype of
            	// polyglot.ext.pao.runtime.Primitive, or it is an
            	// Object, and thus possibly a subtype of 
            	// polyglot.ext.pao.runtime.Primitive. Either way,
            	// it may be a boxed primitive.
                if (r.type().isReference()) {
                	// The right operand is a reference type, so replace the
                	// binary operation with a call to
                	// Primitive.equals(Object, Object).
                    TypeNode x = nf.CanonicalTypeNode(b.position(),
                                                      mi.container());
                    Call y = nf.Call(b.position(), x, mi.name(), l, r);
                    y = (Call) y.type(mi.returnType());
                    if (b.operator() == Binary.NE) {
                        return nf.Unary(b.position(), Unary.NOT, y).type(mi.returnType());
                    }
                    else {
                        return y;
                    }
                }
            }
        }
        
        // we do not need to rewrite the binary operator.
        return super.rewrite(ts, nf);
    }
}
