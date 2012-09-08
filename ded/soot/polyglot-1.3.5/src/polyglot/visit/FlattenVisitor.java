package polyglot.visit;

import polyglot.ast.*;
import polyglot.types.*;

import java.util.*;

/**
 * The FlattenVisitor flattens the AST,
 */
public class FlattenVisitor extends NodeVisitor
{
    protected TypeSystem ts;
    protected NodeFactory nf;
    protected LinkedList stack;

    public FlattenVisitor(TypeSystem ts, NodeFactory nf) {
	this.ts = ts;
	this.nf = nf;
	stack = new LinkedList();
    }

    public Node override(Node n) {
	if (n instanceof FieldDecl || n instanceof ConstructorCall) {
	    return n;
	}

	return null;
    }

    static int count = 0;

    protected static String newID() {
	return "flat$$$" + count++;
    }

    protected Node noFlatten = null;

    /** 
     * When entering a BlockStatement, place a new StatementList
     * onto the stack
     */
    public NodeVisitor enter(Node n) {
	if (n instanceof Block) {
	    stack.addFirst(new LinkedList());
	}

	if (n instanceof Eval) {
	    // Don't flatten the expression contained in the statement, but
	    // flatten its subexpressions.
	    Eval s = (Eval) n;
	    noFlatten = s.expr();
	}

	return this;
    }

    /** 
     * Flatten complex expressions within the AST
     */
    public Node leave(Node old, Node n, NodeVisitor v) {
	if (n == noFlatten) {
	    noFlatten = null;
	    return n;
	}

	if (n instanceof Block) {
	    List l = (List) stack.removeFirst();
	    return ((Block) n).statements(l);
	}
	else if (n instanceof Stmt && ! (n instanceof LocalDecl)) {
	    List l = (List) stack.getFirst();
	    l.add(n);
	    return n;
	}
	else if (n instanceof Expr && ! (n instanceof Lit) &&
	      ! (n instanceof Special) && ! (n instanceof Local)) {

	    Expr e = (Expr) n;

	    if (e instanceof Assign) {
	        return n;
	    }

	    // create a local temp, initialized to the value of the complex
	    // expression

	    String name = newID();
	    LocalDecl def = nf.LocalDecl(e.position(), Flags.FINAL,
					 nf.CanonicalTypeNode(e.position(),
					                      e.type()),
					 name, e);
	    def = def.localInstance(ts.localInstance(e.position(), Flags.FINAL,
						     e.type(), name));

	    List l = (List) stack.getFirst();
	    l.add(def);

	    // return the local temp instead of the complex expression
	    Local use = nf.Local(e.position(), name);
	    use = (Local) use.type(e.type());
	    use = use.localInstance(ts.localInstance(e.position(), Flags.FINAL,
						     e.type(), name));
	    return use;
	}

	return n;
    }
}
