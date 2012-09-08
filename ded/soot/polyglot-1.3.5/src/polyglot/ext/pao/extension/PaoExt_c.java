package polyglot.ext.pao.extension;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ext.jl.ast.Ext_c;
import polyglot.ext.pao.types.PaoTypeSystem;

/**
 * Default implementation of <code>PaoExt</code>.
 */
public class PaoExt_c extends Ext_c implements PaoExt {
	/**
	 * Default implementation of <code>rewrite</code>, returns the node
	 * unchanged.
	 * 
	 * @see PaoExt#rewrite(PaoTypeSystem, NodeFactory)
	 */
    public Node rewrite(PaoTypeSystem ts, NodeFactory nf) {
        return node();
    }
}
