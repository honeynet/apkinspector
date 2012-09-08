package polyglot.ext.jl.ast;

import java.util.*;

import polyglot.ast.*;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.CodeWriter;
import polyglot.util.CollectionUtil;
import polyglot.util.Position;
import polyglot.util.SubtypeSet;
import polyglot.util.TypedList;
import polyglot.visit.*;
import polyglot.main.Options;

/**
 * A method declaration.
 */
public class MethodDecl_c extends Term_c implements MethodDecl
{
    protected Flags flags;
    protected TypeNode returnType;
    protected String name;
    protected List formals;
    protected List throwTypes;
    protected Block body;
    protected MethodInstance mi;

    public MethodDecl_c(Position pos, Flags flags, TypeNode returnType, String name, List formals, List throwTypes, Block body) {
	super(pos);
	this.flags = flags;
	this.returnType = returnType;
	this.name = name;
	this.formals = TypedList.copyAndCheck(formals, Formal.class, true);
	this.throwTypes = TypedList.copyAndCheck(throwTypes, TypeNode.class, true);
	this.body = body;
    }

    /** Get the flags of the method. */
    public Flags flags() {
	return this.flags;
    }

    /** Set the flags of the method. */
    public MethodDecl flags(Flags flags) {
	MethodDecl_c n = (MethodDecl_c) copy();
	n.flags = flags;
	return n;
    }

    /** Get the return type of the method. */
    public TypeNode returnType() {
	return this.returnType;
    }

    /** Set the return type of the method. */
    public MethodDecl returnType(TypeNode returnType) {
	MethodDecl_c n = (MethodDecl_c) copy();
	n.returnType = returnType;
	return n;
    }

    /** Get the name of the method. */
    public String name() {
	return this.name;
    }

    /** Set the name of the method. */
    public MethodDecl name(String name) {
	MethodDecl_c n = (MethodDecl_c) copy();
	n.name = name;
	return n;
    }

    /** Get the formals of the method. */
    public List formals() {
	return Collections.unmodifiableList(this.formals);
    }

    /** Set the formals of the method. */
    public MethodDecl formals(List formals) {
	MethodDecl_c n = (MethodDecl_c) copy();
	n.formals = TypedList.copyAndCheck(formals, Formal.class, true);
	return n;
    }

    /** Get the exception types of the method. */
    public List throwTypes() {
	return Collections.unmodifiableList(this.throwTypes);
    }

    /** Set the exception types of the method. */
    public MethodDecl throwTypes(List throwTypes) {
	MethodDecl_c n = (MethodDecl_c) copy();
	n.throwTypes = TypedList.copyAndCheck(throwTypes, TypeNode.class, true);
	return n;
    }

    /** Get the body of the method. */
    public Block body() {
	return this.body;
    }

    /** Set the body of the method. */
    public CodeDecl body(Block body) {
	MethodDecl_c n = (MethodDecl_c) copy();
	n.body = body;
	return n;
    }

    /** Get the method instance of the method. */
    public MethodInstance methodInstance() {
	return mi;
    }

    /** Set the method instance of the method. */
    public MethodDecl methodInstance(MethodInstance mi) {
	MethodDecl_c n = (MethodDecl_c) copy();
	n.mi = mi;
	return n;
    }

    public CodeInstance codeInstance() {
	return procedureInstance();
    }

    /** Get the procedure instance of the method. */
    public ProcedureInstance procedureInstance() {
	return mi;
    }

    /** Reconstruct the method. */
    protected MethodDecl_c reconstruct(TypeNode returnType, List formals, List throwTypes, Block body) {
	if (returnType != this.returnType || ! CollectionUtil.equals(formals, this.formals) || ! CollectionUtil.equals(throwTypes, this.throwTypes) || body != this.body) {
	    MethodDecl_c n = (MethodDecl_c) copy();
	    n.returnType = returnType;
	    n.formals = TypedList.copyAndCheck(formals, Formal.class, true);
	    n.throwTypes = TypedList.copyAndCheck(throwTypes, TypeNode.class, true);
	    n.body = body;
	    return n;
	}

	return this;
    }

    /** Visit the children of the method. */
    public Node visitChildren(NodeVisitor v) {
        List formals = visitList(this.formals, v);
	TypeNode returnType = (TypeNode) visitChild(this.returnType, v);
	List throwTypes = visitList(this.throwTypes, v);
	Block body = (Block) visitChild(this.body, v);
	return reconstruct(returnType, formals, throwTypes, body);
    }

    public NodeVisitor buildTypesEnter(TypeBuilder tb) throws SemanticException {
        return tb.pushCode();
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        TypeSystem ts = tb.typeSystem();

        List l = new ArrayList(formals.size());
        for (int i = 0; i < formals.size(); i++) {
          l.add(ts.unknownType(position()));
        }

        List m = new ArrayList(throwTypes().size());
        for (int i = 0; i < throwTypes().size(); i++) {
          m.add(ts.unknownType(position()));
        }

        MethodInstance mi = ts.methodInstance(position(), ts.Object(),
                                              Flags.NONE,
                                              ts.unknownType(position()),
                                              name, l, m);
        return methodInstance(mi);
    }

    /** Build type objects for the method. */
    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
        if (ar.kind() == AmbiguityRemover.SUPER) {
            return ar.bypassChildren(this);
        }
        else if (ar.kind() == AmbiguityRemover.SIGNATURES) {
            if (body != null) {
                return ar.bypass(body);
            }
        }

        return ar;
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        if (ar.kind() == AmbiguityRemover.SIGNATURES) {
            Context c = ar.context();
            TypeSystem ts = ar.typeSystem();

            ParsedClassType ct = c.currentClassScope();

            MethodInstance mi = makeMethodInstance(ct, ts);

            return flags(mi.flags()).methodInstance(mi);
        }

        return this;
    }

    public NodeVisitor addMembersEnter(AddMemberVisitor am) {
        ParsedClassType ct = am.context().currentClassScope();
        ct.addMethod(mi);
        return am.bypassChildren(this);
    }
     
    public Context enterScope(Context c) {
        if (Report.should_report(TOPICS, 5))
	    Report.report(5, "enter scope of method " + name);
        c = c.pushCode(mi);
        return c;
    }

    /** Type check the method. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
	TypeSystem ts = tc.typeSystem();

        if (tc.context().currentClass().flags().isInterface()) {
            if (flags().isProtected() || flags().isPrivate()) {
                throw new SemanticException("Interface methods must be public.",
                                            position());
            }
        }

        try {
            ts.checkMethodFlags(flags());
        }
        catch (SemanticException e) {
            throw new SemanticException(e.getMessage(), position());
        }

	if (body == null && ! (flags().isAbstract() || flags().isNative())) {
	    throw new SemanticException("Missing method body.", position());
	}

	if (body != null && flags().isAbstract()) {
	    throw new SemanticException(
		"An abstract method cannot have a body.", position());
	}

	if (body != null && flags().isNative()) {
	    throw new SemanticException(
		"A native method cannot have a body.", position());
	}

        for (Iterator i = throwTypes().iterator(); i.hasNext(); ) {
            TypeNode tn = (TypeNode) i.next();
            Type t = tn.type();
            if (! t.isThrowable()) {
                throw new SemanticException("Type \"" + t +
                    "\" is not a subclass of \"" + ts.Throwable() + "\".",
                    tn.position());
            }
        }

        // check that inner classes do not declare static methods
        if (flags().isStatic() &&
              methodInstance().container().toClass().isInnerClass()) {
            // it's a static method in an inner class.
            throw new SemanticException("Inner classes cannot declare " + 
                    "static methods.", this.position());             
        }

        overrideMethodCheck(tc);

	return this;
    }

    protected void overrideMethodCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();

        for (Iterator j = mi.implemented().iterator(); j.hasNext(); ) {
            MethodInstance mj = (MethodInstance) j.next();

            if (! ts.isAccessible(mj, tc.context())) {
                continue;
            }

            ts.checkOverride(mi, mj);
        }
    }

    /** Check exceptions thrown by the method. */
    public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
	TypeSystem ts = ec.typeSystem();

	SubtypeSet s = (SubtypeSet) ec.throwsSet();

	for (Iterator i = s.iterator(); i.hasNext(); ) {
	    Type t = (Type) i.next();

	    boolean throwDeclared = false;

	    if (! t.isUncheckedException()) {
		for (Iterator j = throwTypes().iterator(); j.hasNext(); ) {
		    TypeNode tn = (TypeNode) j.next();
		    Type tj = tn.type();

		    if (ts.isSubtype(t, tj)) {
			throwDeclared = true;
			break;
		    }
		}

		if (! throwDeclared) {
                    ec.throwsSet().clear();
                    Position pos = ec.exceptionPosition(t);
                    throw new SemanticException("The exception \"" + t + 
                        "\" must either be caught or declared to be thrown.",
		        pos==null?position():pos);
		}
	    }
	}

	ec.throwsSet().clear();

	return super.exceptionCheck(ec);
    }

    public String toString() {
	return flags.translate() + returnType + " " + name + "(...)";
    }

    /** Write the method to an output file. */
    public void prettyPrintHeader(Flags flags, CodeWriter w, PrettyPrinter tr) {
	w.begin(0);
	w.write(flags.translate());
	print(returnType, w, tr);
	w.write(" " + name + "(");

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

	if (! throwTypes().isEmpty()) {
	    w.allowBreak(6);
	    w.write("throws ");

	    for (Iterator i = throwTypes().iterator(); i.hasNext(); ) {
	        TypeNode tn = (TypeNode) i.next();
		print(tn, w, tr);

		if (i.hasNext()) {
		    w.write(",");
		    w.allowBreak(4, " ");
		}
	    }
	}

	w.end();
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
      
      prettyPrintHeader(flags(), w, tr);
      if (body != null) {
	      printSubStmt(body, w, tr);
      }
      else {
	    	w.write(";");
      }
    }

    public void translate(CodeWriter w, Translator tr) {
		Flags flags = flags();
	
	      prettyPrintHeader(flags(), w, tr);
	      if (body != null) {
		      printSubStmt(body, w, tr);
	      }
	      else {
		    	w.write(";");
	      }
    }

    public void dump(CodeWriter w) {
	super.dump(w);

	if (mi != null) {
	    w.allowBreak(4, " ");
	    w.begin(0);
	    w.write("(instance " + mi + ")");
	    w.end();
	}

        w.allowBreak(4, " ");
        w.begin(0);
        w.write("(name " + name + ")");
        w.end();
    }

    protected MethodInstance makeMethodInstance(ClassType ct, TypeSystem ts)
	throws SemanticException {

	List argTypes = new LinkedList();
	List excTypes = new LinkedList();

	for (Iterator i = formals.iterator(); i.hasNext(); ) {
	    Formal f = (Formal) i.next();
	    argTypes.add(f.declType());
	}

	for (Iterator i = throwTypes().iterator(); i.hasNext(); ) {
	    TypeNode tn = (TypeNode) i.next();
	    excTypes.add(tn.type());
	}

	Flags flags = this.flags;

	if (ct.flags().isInterface()) {
	    flags = flags.Public().Abstract();
	}

	return ts.methodInstance(position(),
	    			 ct, flags, returnType.type(), name,
	                         argTypes, excTypes);
    }

    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry() {
        return listEntry(formals(), (body()==null? this : body().entry()));
    }

    /**
     * Visit this term in evaluation order.
     */
    public List acceptCFG(CFGBuilder v, List succs) {
        if (body() == null) {
            v.visitCFGList(formals(), this);
        }
        else {
            v.visitCFGList(formals(), body().entry());
            v.visitCFG(body(), this);
        }
        return succs;
    }

    protected static final Collection TOPICS = 
            CollectionUtil.list(Report.types, Report.context);

}
