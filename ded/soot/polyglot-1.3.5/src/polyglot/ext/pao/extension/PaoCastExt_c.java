package polyglot.ext.pao.extension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import polyglot.ast.*;
import polyglot.ext.pao.types.PaoTypeSystem;
import polyglot.types.ConstructorInstance;
import polyglot.types.MethodInstance;
import polyglot.types.Type;

/**
 * The <code>PaoExt</code> implementation for the 
 * <code>Cast</code> AST node.
 */
public class PaoCastExt_c extends PaoExt_c {
    /**
     * Insert boxing and unboxing code for the casts inserted by the
     * <code>PaoBoxer</code>. 
     * @see PaoExt_c#rewrite(PaoTypeSystem, NodeFactory)
     * @see polyglot.ext.pao.visit.PaoBoxer
     */
	public Node rewrite(PaoTypeSystem ts, NodeFactory nf) {

        Cast c = (Cast) node();
        Type rtype = c.expr().type();
        Type ltype = c.castType().type();

        if (ltype.isPrimitive() && rtype.isReference()) {
        	// We have an expression with reference type being cast to
        	// a primitive type, added by the PaoBoxer visitor. 
        	// We need to unbox.
        	
        	// e.g., "(int)e", where e is of type Integer gets rewritten to
        	// "((polyglot.ext.pao.runtime.Integer)e).getInt()"

        	MethodInstance mi = ts.getter(ltype.toPrimitive());

            Cast x = nf.Cast(c.position(),
                             nf.CanonicalTypeNode(c.position(), mi.container()),
                             c.expr());
            x = (Cast) x.type(mi.container());

            Call y = nf.Call(c.position(), x, mi.name(),
                             Collections.EMPTY_LIST);
            y = (Call) y.type(mi.returnType());

            return y.methodInstance(mi);
        }
        else if (ltype.isReference() && rtype.isPrimitive()) {
        	// We have an expression with primitive type being cast to
        	// a reference type, added by the PaoBoxer visitor. 
        	// We need to box.
        	
        	// e.g., "(Integer)e", where e is of type int gets rewritten to
        	// "new polyglot.ext.pao.runtime.Integer(e)"

            ConstructorInstance ci = ts.wrapper(rtype.toPrimitive());

            List args = new ArrayList(1);
            args.add(c.expr());

            New x = nf.New(c.position(),
                           nf.CanonicalTypeNode(c.position(), ci.container()),
                           args);
            x = (New) x.type(ci.container());
            return x.constructorInstance(ci);
        }

        // Don't need to either box or unbox.
        return super.rewrite(ts, nf);
    }
}
