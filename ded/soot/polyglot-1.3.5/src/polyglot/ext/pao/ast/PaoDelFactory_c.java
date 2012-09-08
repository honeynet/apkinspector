package polyglot.ext.pao.ast;

import polyglot.ast.JL;
import polyglot.ext.jl.ast.AbstractDelFactory_c;
import polyglot.ext.pao.extension.PaoInstanceofDel_c;

/**
 * Delegate factory for the pao extension. The delegate factory 
 * is responsible for creating <code>JL</code> delegate objects, and is
 * used only by the <code>NodeFactory</code>. 
 */
public class PaoDelFactory_c extends AbstractDelFactory_c {

	/**
	 * @see AbstractDelFactory_c#delInstanceofImpl()
	 */
	protected JL delInstanceofImpl() {
		return new PaoInstanceofDel_c();
	}
}
