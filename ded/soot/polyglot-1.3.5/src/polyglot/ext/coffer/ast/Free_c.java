package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import java.util.*;

/**
 * This statement revokes the key associated with a tracked expression.
 * The expression cannot be evaluated after this statement executes.
 */
public class Free_c extends Stmt_c implements Free
{
    protected Expr expr;

    public Free_c(Position pos, Expr expr) {
        super(pos);
        this.expr = expr;
    }

    public Expr expr() {
        return expr;
    }

    public Free expr(Expr expr) {
        Free_c n = (Free_c) copy();
        n.expr = expr;
        return n;
    }

    public Free_c reconstruct(Expr expr) {
        if (this.expr != expr) {
            Free_c n = (Free_c) copy();
            n.expr = expr;
            return n;
        }
        return this;
    }

    public Node visitChildren(NodeVisitor v) {
        Expr expr = (Expr) visitChild(this.expr, v);
        return reconstruct(expr);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        CofferContext c = (CofferContext) tc.context();

        Type t = expr.type();

        if (! (t instanceof CofferClassType)) {
            throw new SemanticException("Cannot free expression of non-tracked type \"" + t + "\".", position());
        }

        return this;
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        w.write("free ");
        print(expr, w, tr);
        w.write(";");
    }

    public void translate(CodeWriter w, Translator tr) {
        w.write(";");
    }

    public String toString() {
        return "free " + expr + ";";
    }

    public Term entry() {
        return expr.entry();
    }

    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFG(expr, this);
        return succs;
    }
}
