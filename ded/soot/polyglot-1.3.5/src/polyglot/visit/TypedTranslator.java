package polyglot.visit;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.SourceFile;
import polyglot.ast.TopLevelDecl;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.Context;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;

public class TypedTranslator extends Translator {

    protected Context context;

    public TypedTranslator(Job job, TypeSystem ts, NodeFactory nf,
            TargetFactory tf) {
        super(job, ts, nf, tf);
        this.context = job.context();

        if (this.context == null) {
            this.context = ts.createContext();
        }
    }


    /** Get the current context in which we are translating. */
    public Context context() {
        return context;
    }
    
    /** Create a new <code>Translator</code> identical to <code>this</code> but
     * with new context <code>c</code> */
    public Translator context(Context c) {
        if (c == this.context) {
            return this;
        }
        TypedTranslator tr = (TypedTranslator) copy();
        tr.context = c;
        return tr;
    }
    
    public void translateTopLevelDecl(CodeWriter w, SourceFile parent, TopLevelDecl decl) {
        Context c = parent.del().enterScope(context);
        decl.del().translate(w, this.context(c));
    }

    public void print(Node parent, Node child, CodeWriter w) {
        Translator tr = this;

        if (parent == null) {
            Context c = child.del().enterScope(context);
            tr = this.context(c);
        }
        else {
            Context c = parent.del().enterScope(child, context);
            tr = this.context(c);
        }

        child.del().translate(w, tr);

        if (parent != null) {
            parent.addDecls(context);
        }
    }

}
