package polyglot.visit;

import polyglot.ast.*;
import polyglot.types.TypeSystem;
import polyglot.frontend.Job;
import polyglot.util.Position;

/** Visitor which performs constant folding. */
public class ConstantFolder extends NodeVisitor
{
    TypeSystem ts;
    NodeFactory nf;

    public ConstantFolder(TypeSystem ts, NodeFactory nf) {
        this.ts = ts;
        this.nf = nf;
    }

    public TypeSystem typeSystem() {
      return ts;
    }

    public NodeFactory nodeFactory() {
      return nf;
    }

    public Node leave(Node old, Node n, NodeVisitor v_) {
	if (! (n instanceof Expr)) {
	    return n;
	}
	
	Expr e = (Expr) n;
	
	if (! e.isConstant()) {
	    return e;
	}
	
	// Don't fold String +.  Strings are often broken up for better
	// formatting.
	if (e instanceof Binary) {
	    Binary b = (Binary) e;
	    
	    if (b.operator() == Binary.ADD &&
		b.left().constantValue() instanceof String &&
		b.right().constantValue() instanceof String) {
		
		return b;
	    }
	}
	
	Object v = e.constantValue();
	Position pos = e.position();

	if (v == null) {
	    return nf.NullLit(pos).type(ts.Null());
	}
	if (v instanceof String) {
	    return nf.StringLit(pos,
				(String) v).type(ts.String());
	}
	if (v instanceof Boolean) {
	    return nf.BooleanLit(pos,
				 ((Boolean) v).booleanValue()).type(ts.Boolean());
	}
	if (v instanceof Double) {
	    return nf.FloatLit(pos, FloatLit.DOUBLE,
			       ((Double) v).doubleValue()).type(ts.Double());
	}
	if (v instanceof Float) {
	    return nf.FloatLit(pos, FloatLit.FLOAT,
			       ((Float) v).floatValue()).type(ts.Float());
	}
	if (v instanceof Long) {
	    return nf.IntLit(pos, IntLit.LONG,
			     ((Long) v).longValue()).type(ts.Long());
	}
	if (v instanceof Integer) {
	    return nf.IntLit(pos, IntLit.INT,
			     ((Integer) v).intValue()).type(ts.Int());
	}
	if (v instanceof Character) {
	    return nf.CharLit(pos,
			      ((Character) v).charValue()).type(ts.Char());
	}
	
	return e;
    }
}
