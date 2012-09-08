package polyglot.ext.coffer.ast;

import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.ext.coffer.extension.*;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/** An implementation of the <code>CofferConstructorDecl</code> interface.
 * <code>ConstructorDecl</code> is extended with pre- and post-conditions.
 */
public class CofferConstructorDecl_c extends ConstructorDecl_c implements CofferConstructorDecl
{
    protected KeySetNode entryKeys;
    protected KeySetNode returnKeys;
    protected List throwConstraints;

    public CofferConstructorDecl_c(Position pos, Flags flags, String name, List formals, KeySetNode entryKeys, KeySetNode returnKeys, List throwConstraints, Block body) {
	super(pos, flags, name, formals, Collections.EMPTY_LIST, body);
	this.entryKeys = entryKeys;
        this.returnKeys = returnKeys;
	this.throwConstraints = TypedList.copyAndCheck(throwConstraints, ThrowConstraintNode.class, true);
    }

    public KeySetNode entryKeys() {
	return this.entryKeys;
    }

    public CofferConstructorDecl entryKeys(KeySetNode entryKeys) {
	CofferConstructorDecl_c n = (CofferConstructorDecl_c) copy();
	n.entryKeys = entryKeys;
	return n;
    }

    public KeySetNode returnKeys() {
        return this.returnKeys;
    }

    public CofferConstructorDecl returnKeys(KeySetNode returnKeys) {
	CofferConstructorDecl_c n = (CofferConstructorDecl_c) copy();
	n.returnKeys = returnKeys;
	return n;
    }

    public List throwConstraints() {
	return this.throwConstraints;
    }

    public List throwTypes() {
        return new CachingTransformingList(throwConstraints, new GetType());
    }

    public class GetType implements Transformation {
        public Object transform(Object o) {
            return ((ThrowConstraintNode) o).type();
        }
    }

    public ConstructorDecl throwTypes(List l) {
        throw new InternalCompilerError("unimplemented");
    }

    public CofferConstructorDecl throwConstraints(List throwConstraints) {
	CofferConstructorDecl_c n = (CofferConstructorDecl_c) copy();
	n.throwConstraints = TypedList.copyAndCheck(throwConstraints, ThrowConstraintNode.class, true);
	return n;
    }

    /*
    public Context enterScope(Context context) {
        CofferContext c = (CofferContext) super.enterScope(context);
        c = (CofferContext) c.pushBlock();

        if (entryKeys != null) {
            for (Iterator i = entryKeys.keys().iterator(); i.hasNext(); ) {
                Key key = (Key) i.next();
                c.addHeldKey(key);
            }
        }

        return c;
    }
    */

    protected CofferConstructorDecl_c reconstruct(List formals, KeySetNode entryKeys, KeySetNode returnKeys, List throwConstraints, Block body) {
	if (entryKeys != this.entryKeys || returnKeys != this.returnKeys || !CollectionUtil.equals(throwConstraints, this.throwConstraints)) {
	    CofferConstructorDecl_c n = (CofferConstructorDecl_c) copy();
	    n.entryKeys = entryKeys;
	    n.returnKeys = returnKeys;
	    n.throwConstraints = TypedList.copyAndCheck(throwConstraints, ThrowConstraintNode.class, true);
	    return (CofferConstructorDecl_c) n.reconstruct(formals, Collections.EMPTY_LIST, body);
	}

	return (CofferConstructorDecl_c) super.reconstruct(formals, Collections.EMPTY_LIST, body);
    }

    public Node visitChildren(NodeVisitor v) {
        List formals = visitList(this.formals, v);
        KeySetNode entryKeys = (KeySetNode) visitChild(this.entryKeys, v);
        KeySetNode returnKeys = (KeySetNode) visitChild(this.returnKeys, v);
	List throwConstraints = visitList(this.throwConstraints, v);
	Block body = (Block) visitChild(this.body, v);
	return reconstruct(formals, entryKeys, returnKeys, throwConstraints, body);
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        CofferNodeFactory nf = (CofferNodeFactory) tb.nodeFactory();
        CofferConstructorDecl n = (CofferConstructorDecl) super.buildTypes(tb);
        CofferConstructorInstance ci =
            (CofferConstructorInstance) n.constructorInstance();

        if (n.entryKeys() == null) {
            n = n.entryKeys(nf.CanonicalKeySetNode(position(),
                                                   ci.entryKeys()));
        }

        if (n.returnKeys() == null) {
            n = n.returnKeys(nf.CanonicalKeySetNode(position(),
                                                    ci.returnKeys()));
        }

        List l = new LinkedList();
        boolean changed = false;

        for (Iterator i = n.throwConstraints().iterator(); i.hasNext(); ) {
            ThrowConstraintNode cn = (ThrowConstraintNode) i.next();
            if (cn.keys() == null) {
                cn = cn.keys(n.entryKeys());
                changed = true;
            }
            l.add(cn);
        }

        if (changed) {
            n = n.throwConstraints(l);
        }

        return n;
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        CofferClassType ct = (CofferClassType) tc.context().currentClass();

        CofferConstructorInstance ci =
            (CofferConstructorInstance) this.constructorInstance();

        if (ct.key() != null) {
            if (ci.entryKeys().contains(ct.key())) {
                throw new SemanticException("Constructor cannot hold key \"" +
                                            ct.key() + "\" (associated with " +
                                            "this) on entry.", position());
            }

            if (! ci.returnKeys().contains(ct.key())) {
                throw new SemanticException("Constructor must hold key \"" +
                                            ct.key() + "\" (associated with " +
                                            "this) on exit.", position());
            }
        }

        return super.typeCheck(tc);
    }

    protected ConstructorInstance makeConstructorInstance(ClassType ct,
                                                          TypeSystem ts)
    	throws SemanticException
    {
	CofferConstructorInstance ci = (CofferConstructorInstance)
	    super.makeConstructorInstance(ct, ts);

	CofferTypeSystem vts = (CofferTypeSystem) ts;

        KeySet entryKeys;
        KeySet returnKeys;

	if (this.entryKeys == null) {
            entryKeys = vts.emptyKeySet(position());
        }
        else {
            entryKeys = this.entryKeys.keys();
        }

	if (this.returnKeys == null) {
            returnKeys = vts.emptyKeySet(position());

            if (ct instanceof CofferClassType) {
                CofferClassType vct = (CofferClassType) ct;
                if (vct.key() != null)
                    returnKeys = returnKeys.add(vct.key());
            }
        }
        else {
            returnKeys = this.returnKeys.keys();
        }

        ci = (CofferConstructorInstance) ci.entryKeys(entryKeys);
        ci = (CofferConstructorInstance) ci.returnKeys(returnKeys);

	List throwConstraints = new ArrayList(this.throwConstraints.size());
	for (Iterator i = this.throwConstraints.iterator(); i.hasNext(); ) {
	    ThrowConstraintNode cn = (ThrowConstraintNode) i.next();

            if (cn.constraint().keys() != null) {
                throwConstraints.add(cn.constraint());
            }
            else {
                throwConstraints.add(cn.constraint().keys(entryKeys));
            }
	}

	return (CofferConstructorInstance) ci.throwConstraints(throwConstraints);
    }

    /** Write the constructor to an output file. */
    public void prettyPrintHeader(CodeWriter w, PrettyPrinter tr) {
	w.begin(0);
	w.write(flags().translate());

	w.write(name);
	w.write("(");

	w.begin(0);

	for (Iterator i = formals.iterator(); i.hasNext(); ) {
	    Formal f = (Formal) i.next();
	    print(f, w, tr);

	    if (i.hasNext()) {
		w.write(",");
		w.allowBreak(0, " ");
	    }
	}

	w.end();
	w.write(")");

	if (! throwConstraints.isEmpty()) {
	    w.allowBreak(6);
	    w.write("throws ");

	    for (Iterator i = throwConstraints.iterator(); i.hasNext(); ) {
	        ThrowConstraintNode cn = (ThrowConstraintNode) i.next();
		print(cn, w, tr);

		if (i.hasNext()) {
		    w.write(",");
		    w.allowBreak(4, " ");
		}
	    }
	}

	w.end();
    }
}
