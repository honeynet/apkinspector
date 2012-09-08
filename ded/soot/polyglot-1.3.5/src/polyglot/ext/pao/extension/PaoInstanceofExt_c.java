package polyglot.ext.pao.extension;

import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ext.pao.types.PaoTypeSystem;
import polyglot.types.Type;

/**
 * The <code>PaoExt</code> implementation for the 
 * <code>InstanceOf</code> AST node.
 */
public class PaoInstanceofExt_c extends PaoExt_c {
	/**
	 * Rewrites <code>instanceof</code> checks where the comparison type is
	 * a primitive type to use the boxed type instead. For example,
	 * "e instanceof int" gets rewritten to 
	 * "e instanceof polyglot.ext.pao.runtime.Integer".
	 * 
	 * @see PaoExt#rewrite(PaoTypeSystem, NodeFactory)
	 */
    public Node rewrite(PaoTypeSystem ts, NodeFactory nf) {
    	Instanceof n = (Instanceof) node();
    	Type rtype = n.compareType().type();

    	if (rtype.isPrimitive()) {
    		Type t = ts.boxedType(rtype.toPrimitive());
    		return n.compareType(nf.CanonicalTypeNode(n.compareType().position(),
                                                    t));
    	}

    	return n;
    }
}
