package polyglot.ext.pao.visit;

import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ext.pao.extension.PaoExt;
import polyglot.ext.pao.types.PaoTypeSystem;
import polyglot.frontend.Job;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.NodeVisitor;

/**
 * Visitor that inserts boxing and unboxing code into the AST. 
 * <code>PaoBoxer</code> accomplishes this task by first inserting explicit
 * casts into the AST, where primitive values need to be treated as objects,
 * and then uses the {@link PaoExt#rewrite(PaoTypeSystem, NodeFactory) PaoExt.rewrite(PaoTypeSystem, NodeFactory)}
 * method to rewrite these explicit casts into appropriate boxing and
 * unboxing code.
 */
public class PaoBoxer extends AscriptionVisitor
{
    public PaoBoxer(Job job, TypeSystem ts, NodeFactory nf) {
        super(job, ts, nf);
    }

    /**
     * Inserts an explicit cast if the type of expressions <code>e</code> is
     * a primitive type, and the type <code>toType</code> is a reference type.
     * For example, <code>Integer i = 42</code> will be modified to
     * <code>Integer i = (Object)42</code>. The <code>leaveCall</code> method
     * will ensure that these explicit casts are rewritten to the correct
     * boxing and unboxing code.
     */
    public Expr ascribe(Expr e, Type toType) {
        Type fromType = e.type();

        if (toType == null) {
            return e;
        }

        Position p = e.position();

        // Insert a cast.  Translation of the cast will insert the
        // correct boxing/unboxing code.
        if (toType.isReference() && fromType.isPrimitive()) {
            return nf.Cast(p, nf.CanonicalTypeNode(p, ts.Object()), e);
        }

        return e;
    }

    /**
     * Calls the
     * {@link PaoExt#rewrite(PaoTypeSystem, NodeFactory) PaoExt.rewrite(PaoTypeSystem, NodeFactory)}
     * method to rewrite the explicit casts inserted by the 
     * <code>ascribe</code> method into correct boxing and unboxing code.
     */
    public Node leaveCall(Node old, Node n, NodeVisitor v) throws SemanticException {
        n = super.leaveCall(old, n, v);

        if (n.ext() instanceof PaoExt) {
            return ((PaoExt) n.ext()).rewrite((PaoTypeSystem) typeSystem(),
                                              nodeFactory());
        }

        return n;
    }
}
