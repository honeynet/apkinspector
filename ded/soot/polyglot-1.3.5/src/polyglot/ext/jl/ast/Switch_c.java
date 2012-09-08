package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>Switch</code> is an immutable representation of a Java
 * <code>switch</code> statement.  Such a statement has an expression which
 * is evaluated to determine where to branch to, an a list of labels
 * and block statements which are conditionally evaluated.  One of the
 * labels, rather than having a constant expression, may be lablled
 * default.
 */
public class Switch_c extends Stmt_c implements Switch
{
    protected Expr expr;
    protected List elements;

    public Switch_c(Position pos, Expr expr, List elements) {
	super(pos);
	this.expr = expr;
	this.elements = TypedList.copyAndCheck(elements, SwitchElement.class, true);
    }

    /** Get the expression to switch on. */
    public Expr expr() {
	return this.expr;
    }

    /** Set the expression to switch on. */
    public Switch expr(Expr expr) {
	Switch_c n = (Switch_c) copy();
	n.expr = expr;
	return n;
    }

    /** Get the switch elements of the statement. */
    public List elements() {
	return Collections.unmodifiableList(this.elements);
    }

    /** Set the switch elements of the statement. */
    public Switch elements(List elements) {
	Switch_c n = (Switch_c) copy();
	n.elements = TypedList.copyAndCheck(elements, SwitchElement.class, true);
	return n;
    }

    /** Reconstruct the statement. */
    protected Switch_c reconstruct(Expr expr, List elements) {
	if (expr != this.expr || ! CollectionUtil.equals(elements, this.elements)) {
	    Switch_c n = (Switch_c) copy();
	    n.expr = expr;
	    n.elements = TypedList.copyAndCheck(elements, SwitchElement.class, true);
	    return n;
	}

	return this;
    }

    public Context enterScope(Context c) {
        return c.pushBlock();
    }

    /** Visit the children of the statement. */
    public Node visitChildren(NodeVisitor v) {
	Expr expr = (Expr) visitChild(this.expr, v);
	List elements = visitList(this.elements, v);
	return reconstruct(expr, elements);
    }

    /** Type check the statement. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();

        if (! ts.isImplicitCastValid(expr.type(), ts.Int())) {
            throw new SemanticException("Switch index must be an integer.",
                                        position());
        }

        Collection labels = new HashSet();

	for (Iterator i = elements.iterator(); i.hasNext();) {
	   SwitchElement s = (SwitchElement) i.next();

	   if (s instanceof Case) {
	       Case c = (Case) s;
	       Object key;
	       String str;

	       if (c.isDefault()) {
		   key = "default";
		   str = "default";
	       }
               else if (c.expr().isConstant()) {
		   key = new Long(c.value());
		   str = c.expr().toString() + " (" + c.value() + ")";
	       }
               else {
                    continue;
               }

	       if (labels.contains(key)) {
		   throw new SemanticException("Duplicate case label: " +
		       str + ".", c.position());
	       }

	       labels.add(key);
	   }
	}

	return this;
    }

    public Type childExpectedType(Expr child, AscriptionVisitor av) {
        TypeSystem ts = av.typeSystem();

        if (child == expr) {
            return ts.Int();
        }

        return child.type();
    }

    public String toString() {
	return "switch (" + expr + ") { ... }";
    }

    /** Write the statement to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	w.write("switch (");
	printBlock(expr, w, tr);
	w.write(") {");
        w.allowBreak(4, " ");
	w.begin(0);

        boolean lastWasCase = false;
        boolean first = true;

	for (Iterator i = elements.iterator(); i.hasNext();) {
            SwitchElement s = (SwitchElement) i.next();
            if (s instanceof Case) {
                if (lastWasCase) w.newline(0);
                else if (! first) w.allowBreak(0, " ");
                printBlock(s, w, tr);
                lastWasCase = true;
            }
            else {
                w.allowBreak(4," ");
                print(s, w, tr);
                lastWasCase = false;
            }

            first = false;
	}

	w.end();
        w.allowBreak(0, " ");
	w.write("}");
    }

    public Term entry() {
        return expr.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        SwitchElement prev = null;

        List cases = new LinkedList();
        boolean hasDefault = false;

        for (Iterator i = elements.iterator(); i.hasNext(); ) {
            SwitchElement s = (SwitchElement) i.next();

            if (s instanceof Case) {
                cases.add(s.entry());
                if (((Case) s).expr() == null) {
                    hasDefault = true;
                }
            }
        }

        // If there is no default case, add an edge to the end of the switch.
        if (! hasDefault) {
            cases.add(this);
        }

        v.visitCFG(expr, FlowGraph.EDGE_KEY_OTHER, cases);
        v.push(this).visitCFGList(elements, this);

        return succs;
    }
}
