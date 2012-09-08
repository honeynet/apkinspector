package polyglot.ext.coffer.ast;

import polyglot.ext.jl.ast.*;
import polyglot.ext.param.types.*;
import polyglot.ext.coffer.types.*;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * An implementation of the <code>CofferClassDecl</code> interface.
 * <code>ClassDecl</code> is extended with a possibly-null key name.
 */
public class CofferClassDecl_c extends ClassDecl_c implements CofferClassDecl
{
    protected KeyNode key;

    public CofferClassDecl_c(Position pos, Flags flags, String name,
	    KeyNode key, TypeNode superClass, List interfaces,
	    ClassBody body) {
	super(pos, flags, name, superClass, interfaces, body);
        this.key = key;
    }

    public KeyNode key() {
	return this.key;
    }

    public CofferClassDecl key(KeyNode key) {
	CofferClassDecl_c n = (CofferClassDecl_c) copy();
	n.key = key;
	return n;
    }

    protected CofferClassDecl_c reconstruct(KeyNode key, TypeNode superClass,
                                           List interfaces, ClassBody body) {
        CofferClassDecl_c n = this;

	if (this.key != key) {
	    n = (CofferClassDecl_c) copy();
	    n.key = key;
	}

        return (CofferClassDecl_c) n.reconstruct(superClass, interfaces, body);
    }

    public Node visitChildren(NodeVisitor v) {
	KeyNode key = (KeyNode) visitChild(this.key, v);
	TypeNode superClass = (TypeNode) visitChild(this.superClass, v);
	List interfaces = visitList(this.interfaces, v);
	ClassBody body = (ClassBody) visitChild(this.body, v);
	return reconstruct(key, superClass, interfaces, body);
    }

    public Context enterScope(Context context) {
        CofferContext c = (CofferContext) context;
        CofferTypeSystem ts = (CofferTypeSystem) c.typeSystem();

        CofferParsedClassType ct = (CofferParsedClassType) this.type;
        CofferClassType inst = (CofferClassType) ct;

        c = (CofferContext) c.pushClass(ct, inst);

        if (key != null)
            c.addKey(key.key());

        return c;
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        CofferClassDecl_c n = (CofferClassDecl_c) super.buildTypes(tb);

        CofferTypeSystem ts = (CofferTypeSystem) tb.typeSystem();

        CofferParsedClassType ct = (CofferParsedClassType) n.type;

        MuPClass pc = ts.mutablePClass(ct.position());
        ct.setInstantiatedFrom(pc);
        pc.clazz(ct);

        if (key != null) {
            ct.setKey(key.key());
        }

        return n;
    }

    public void prettyPrintHeader(CodeWriter w, PrettyPrinter tr) {
        if (flags.isInterface()) {
            w.write(flags.clearInterface().clearAbstract().translate());
        }
        else {
            w.write(flags.translate());
        }

        if (key != null) {
            w.write("tracked(");
            print(key, w, tr);
            w.write(") ");
        }

        if (flags.isInterface()) {
            w.write("interface ");
        }
        else {
            w.write("class ");
        }

        w.write(name);

        if (superClass() != null) {
            w.write(" extends ");
            print(superClass(), w, tr);
        }

        if (! interfaces.isEmpty()) {
            if (flags.isInterface()) {
                w.write(" extends ");
            }
            else {
                w.write(" implements ");
            }

            for (Iterator i = interfaces().iterator(); i.hasNext(); ) {
                TypeNode tn = (TypeNode) i.next();
                print(tn, w, tr);

                if (i.hasNext()) {
                    w.write (", ");
                }
            }
        }

        w.write(" {");
    }

    public void translate(CodeWriter w, Translator tr) {
        ((CofferClassDecl_c) key(null)).superTranslate(w, tr);
    }

    public void superTranslate(CodeWriter w, Translator tr) {
        super.translate(w, tr);
    }
}
