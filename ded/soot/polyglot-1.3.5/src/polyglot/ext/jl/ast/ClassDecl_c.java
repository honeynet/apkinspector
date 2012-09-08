package polyglot.ext.jl.ast;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import polyglot.ast.*;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.CodeWriter;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.TypedList;
import polyglot.visit.*;
import polyglot.main.Options;

/**
 * A <code>ClassDecl</code> is the definition of a class, abstract class,
 * or interface. It may be a public or other top-level class, or an inner
 * named class, or an anonymous class.
 */
public class ClassDecl_c extends Term_c implements ClassDecl
{
    protected Flags flags;
    protected String name;
    protected TypeNode superClass;
    protected List interfaces;
    protected ClassBody body;

    protected ParsedClassType type;

    public ClassDecl_c(Position pos, Flags flags, String name,
                       TypeNode superClass, List interfaces, ClassBody body) {
	    super(pos);
	    this.flags = flags;
	    this.name = name;
	    this.superClass = superClass;
	    this.interfaces = TypedList.copyAndCheck(interfaces, TypeNode.class, true);
	    this.body = body;
    }

    public Named declaration() {
	    return type();
    }

    public ParsedClassType type() {
	    return type;
    }

    public ClassDecl type(ParsedClassType type) {
	    ClassDecl_c n = (ClassDecl_c) copy();
	    n.type = type;
	    return n;
    }

    public Flags flags() {
	    return this.flags;
    }

    public ClassDecl flags(Flags flags) {
	    ClassDecl_c n = (ClassDecl_c) copy();
	    n.flags = flags;
	    return n;
    }

    public String name() {
	    return this.name;
    }

    public ClassDecl name(String name) {
	    ClassDecl_c n = (ClassDecl_c) copy();
	    n.name = name;
	    return n;
    }

    public TypeNode superClass() {
	    return this.superClass;
    }

    public ClassDecl superClass(TypeNode superClass) {
	    ClassDecl_c n = (ClassDecl_c) copy();
	    n.superClass = superClass;
	    return n;
    }

    public List interfaces() {
	    return this.interfaces;
    }

    public ClassDecl interfaces(List interfaces) {
	    ClassDecl_c n = (ClassDecl_c) copy();
	    n.interfaces = TypedList.copyAndCheck(interfaces, TypeNode.class, true);
	    return n;
    }

    public ClassBody body() {
	    return this.body;
    }

    public ClassDecl body(ClassBody body) {
	    ClassDecl_c n = (ClassDecl_c) copy();
	    n.body = body;
	    return n;
    }

    protected ClassDecl_c reconstruct(TypeNode superClass, List interfaces, ClassBody body) {
	    if (superClass != this.superClass || ! CollectionUtil.equals(interfaces, this.interfaces) || body != this.body) {
		    ClassDecl_c n = (ClassDecl_c) copy();
		    n.superClass = superClass;
		    n.interfaces = TypedList.copyAndCheck(interfaces, TypeNode.class, true);
		    n.body = body;
		    return n;
	    }

	    return this;
    }

    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry() {
        return this.body().entry();
    }

    /**
     * Visit this term in evaluation order.
     */
    public List acceptCFG(CFGBuilder v, List succs) {
        v.visitCFG(this.body(), this);
        return succs;
    }

    public Node visitChildren(NodeVisitor v) {
	    TypeNode superClass = (TypeNode) visitChild(this.superClass, v);
	    List interfaces = visitList(this.interfaces, v);
	    ClassBody body = (ClassBody) visitChild(this.body, v);
	    return reconstruct(superClass, interfaces, body);
    }

    public NodeVisitor buildTypesEnter(TypeBuilder tb) throws SemanticException {
	tb = tb.pushClass(position(), flags, name);
        
        ParsedClassType ct = tb.currentClass();

        // Member classes of interfaces are implicitly public and static.
        if (ct.isMember() && ct.outer().flags().isInterface()) {
            ct.flags(ct.flags().Public().Static());
        }

        // Member interfaces are implicitly static. 
        if (ct.isMember() && ct.flags().isInterface()) {
            ct.flags(ct.flags().Static());
        }

        // Interfaces are implicitly abstract. 
        if (ct.flags().isInterface()) {
            ct.flags(ct.flags().Abstract());
        }

        return tb;
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
	ParsedClassType type = tb.currentClass();        
        if (type != null) {
            return type(type).flags(type.flags());
        }
        return this;
    }

    public Context enterScope(Node child, Context c) {
        if (child == this.body) {
            TypeSystem ts = c.typeSystem();
            c = c.pushClass(type, ts.staticTarget(type).toClass());
        }
        return super.enterScope(child, c);
    }

    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
        if (ar.kind() == AmbiguityRemover.SUPER) {
            return ar.bypass(body);
        }

        return ar;
    }

    protected void disambiguateSuperType(AmbiguityRemover ar) throws SemanticException {
        TypeSystem ts = ar.typeSystem();

        if (this.superClass != null) {
            Type t = this.superClass.type();

            if (! t.isCanonical()) {
                throw new SemanticException("Could not disambiguate super" +
                        " class of " + type + ".", superClass.position());
            }

            if (! t.isClass() || t.toClass().flags().isInterface()) {
                throw new SemanticException("Super class " + t + " of " +
                        type + " is not a class.", superClass.position());
            }

            if (Report.should_report(Report.types, 3))
                Report.report(3, "setting super type of " + this.type + " to " + t);

            this.type.superType(t);

            ts.checkCycles(t.toReference());
        }
        else if (ts.Object() != this.type && 
                 !ts.Object().fullName().equals(this.type.fullName())) {
            // the supertype was not specified, and the type is not the same
            // as ts.Object() (which is typically java.lang.Object)
            // As such, the default supertype is ts.Object().
            this.type.superType(ts.Object());
        }
        else {
            // the type is the same as ts.Object(), so it has no supertype.
            this.type.superType(null);
        }    
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (ar.kind() == AmbiguityRemover.SIGNATURES) {
            // make sure that the inStaticContext flag of the class is 
            // correct
            Context ctxt = ar.context();
            this.type().inStaticContext(ctxt.inStaticContext());


        }

        if (ar.kind() == AmbiguityRemover.SIGNATURES) {
            // make sure that this class has the correct dependencies
            // recorded for its super classes and interfaces.
            ar.addSuperDependencies(this.type());
        }

        if (ar.kind() != AmbiguityRemover.SUPER) {
            return this;
        }

        TypeSystem ts = ar.typeSystem();

        if (Report.should_report(Report.types, 2))
	    Report.report(2, "Cleaning " + type + ".");

        disambiguateSuperType(ar);
        
        for (Iterator i = this.interfaces.iterator(); i.hasNext(); ) {
            TypeNode tn = (TypeNode) i.next();
            Type t = tn.type();

            if (! t.isCanonical()) {
                throw new SemanticException("Could not disambiguate super" +
                        " class of " + type + ".", tn.position());
            }

            if (! t.isClass() || ! t.toClass().flags().isInterface()) {
                throw new SemanticException("Interface " + t + " of " +
                        type + " is not an interface.", tn.position());
            }

            if (Report.should_report(Report.types, 3))
		Report.report(3, "adding interface of " + this.type + " to " + t);

            if (!this.type.interfaces().contains(t)) this.type.addInterface(t);

            ts.checkCycles(t.toReference());
        }

        return this;
    }

    public Node addMembers(AddMemberVisitor tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();
	NodeFactory nf = tc.nodeFactory();
        return addDefaultConstructorIfNeeded(ts, nf);
    }

    protected Node addDefaultConstructorIfNeeded(TypeSystem ts,
                                                 NodeFactory nf) {
        if (defaultConstructorNeeded()) {
            return addDefaultConstructor(ts, nf);
        }
        return this;
    }

    protected boolean defaultConstructorNeeded() {
        if (flags().isInterface()) {
            return false;
        }
        return type().constructors().isEmpty();
    }

    protected Node addDefaultConstructor(TypeSystem ts, NodeFactory nf) {
        ConstructorInstance ci = ts.defaultConstructor(position(), this.type);
        this.type.addConstructor(ci);
        Block block = null;
        if (this.type.superType() instanceof ClassType) {
            ConstructorInstance sci = ts.defaultConstructor(position(),
                                                (ClassType) this.type.superType());
            ConstructorCall cc = nf.SuperCall(position(), 
                                              Collections.EMPTY_LIST);
            cc = cc.constructorInstance(sci);
            block = nf.Block(position(), cc);
        }
        else {
            block = nf.Block(position());
        }
        ConstructorDecl cd = nf.ConstructorDecl(position(), Flags.PUBLIC,
                                                name, Collections.EMPTY_LIST,
                                                Collections.EMPTY_LIST,
                                                block);
        cd = (ConstructorDecl) cd.constructorInstance(ci);
        return body(body.addMember(cd));
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        if (this.type().isNested() && (this.type() instanceof Named)) {
            // The class cannot have the same simple name as any enclosing class.
            ClassType container = this.type.outer();

            while (container instanceof Named) {
                if (!container.isAnonymous()) {
                    String name = ((Named) container).name();
    
                    if (name.equals(this.name)) {
                        throw new SemanticException("Cannot declare member " +
                                                    "class \"" + this.type +
                                                    "\" inside class with the " +
                                                    "same name.", position());
                    }
                }    
                if (container.isNested()) {
                    container = container.outer();
                }
                else {
                    break;
                }
            }
                        
            if (this.type().isLocal()) {
                // a local class name cannot be redeclared within the same
                // method, constructor or initializer, and within its scope                
                Context ctxt = tc.context();

                if (ctxt.isLocal(this.name)) {
                    // something with the same name was declared locally.
                    // (but not in an enclosing class)                                    
                    Named nm = ctxt.find(this.name);
                    if (nm instanceof Type) {
                        Type another = (Type)nm;
                        if (another.isClass() && another.toClass().isLocal()) {
                            throw new SemanticException("Cannot declare local " +
                                "class \"" + this.type + "\" within the same " +
                                "method, constructor or initializer as another " +
                                "local class of the same name.", position());
                        }
                    }
                }                
            }
        }

        // check that inner classes do not declare member interfaces
        if (type().isMember() && flags().isInterface() &&
              type().outer().isInnerClass()) {
            // it's a member interface in an inner class.
            throw new SemanticException("Inner classes cannot declare " + 
                    "member interfaces.", this.position());             
        }

        // Make sure that static members are not declared inside inner classes
        if (type().isMember() && type().flags().isStatic() 
               && type().outer().isInnerClass()) {
            throw new SemanticException("Inner classes cannot declare static " 
                                 + "member classes.", position());
        }
        
        if (type.superType() != null) {
            if (! type.superType().isClass()) {
                throw new SemanticException("Cannot extend non-class \"" +
                                            type.superType() + "\".",
                                            position());
            }

            if (type.superType().toClass().flags().isFinal()) {
                throw new SemanticException("Cannot extend final class \"" +
                                            type.superType() + "\".",
                                            position());
            }
        }

        TypeSystem ts = tc.typeSystem();

        try {
            if (type.isTopLevel()) {
                ts.checkTopLevelClassFlags(type.flags());
            }
            if (type.isMember()) {
                ts.checkMemberClassFlags(type.flags());
            }
            if (type.isLocal()) {
                ts.checkLocalClassFlags(type.flags());
            }
        }
        catch (SemanticException e) {
            throw new SemanticException(e.getMessage(), position());
        }
        
        // check the class implements all abstract methods that it needs to.
        ts.checkClassConformance(type);

        return this;
    }

    public String toString() {
	    return flags.clearInterface().translate() +
		       (flags.isInterface() ? "interface " : "class ") + name + " " + body;
    }

    public void prettyPrintHeader(CodeWriter w, PrettyPrinter tr) {
        if (flags.isInterface()) {
            w.write(flags.clearInterface().clearAbstract().translate());
        }
        else {
            w.write(flags.translate());
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

    public void prettyPrintFooter(CodeWriter w, PrettyPrinter tr) {
        w.write("}");
        w.newline(0);
    }
    
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
      //generate a Java file.
      prettyPrintHeader(w, tr);
      print(body(), w, tr);
      prettyPrintFooter(w, tr);
    }

    public void dump(CodeWriter w) {
            super.dump(w);

            w.allowBreak(4, " ");
            w.begin(0);
            w.write("(name " + name + ")");
            w.end();

            if (type != null) {
                    w.allowBreak(4, " ");
                    w.begin(0);
                    w.write("(type " + type + ")");
                    w.end();
            }
    }
}
