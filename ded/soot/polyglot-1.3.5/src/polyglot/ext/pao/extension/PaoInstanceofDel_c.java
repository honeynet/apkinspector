package polyglot.ext.pao.extension;

import polyglot.ast.Instanceof;
import polyglot.ast.Node;
import polyglot.ext.jl.ast.JL_c;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.visit.TypeChecker;

/**
 * The implementation of the delegate for the 
 * <code>InstanceOf</code> AST node. Overrides the 
 * {@link #typeCheck(TypeChecker) typeCheck(TypeChecker)} method.
 */
public class PaoInstanceofDel_c extends JL_c {
	/**
	 * Removes the restriction that the compare type must be a 
	 * <code>ReferenceType</code>. 
	 * @see polyglot.ast.NodeOps#typeCheck(TypeChecker)
	 * @see polyglot.ext.jl.ast.Instanceof_c#typeCheck(TypeChecker)
	 */
	public Node typeCheck(TypeChecker tc) throws SemanticException {
		Instanceof n = (Instanceof) node();
		Type rtype = n.compareType().type();
		Type ltype = n.expr().type();
		
		if (! tc.typeSystem().isCastValid(ltype, rtype)) {
			throw new SemanticException(
					"Left operand of \"instanceof\" must be castable to "
					+ "the right operand.");
		}
		
		return n.type(tc.typeSystem().Boolean());
	}
}
