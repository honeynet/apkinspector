package polyglot.visit;

import java.util.Stack;

import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.*;

/** Visitor which traverses the AST constructing type objects. */
public class TypeBuilder extends HaltingVisitor
{
    protected ImportTable importTable;
    protected Job job;
    protected TypeSystem ts;
    protected NodeFactory nf;
    protected TypeBuilder outer;
    protected boolean inCode; // true if the last scope pushed as not a class.
    protected boolean global; // true if all scopes pushed have been classes.
    protected ParsedClassType type; // last class pushed.

    public TypeBuilder(Job job, TypeSystem ts, NodeFactory nf) {
        this.job = job;
        this.ts = ts;
        this.nf = nf;
        this.outer = null;
    }

    public TypeBuilder push() {
        TypeBuilder tb = (TypeBuilder) this.copy();
        tb.outer = this;
        return tb;
    }

    public TypeBuilder pop() {
        return outer;
    }

    public Job job() {
        return job;
    }

    public ErrorQueue errorQueue() {
        return job.compiler().errorQueue();
    }

    public NodeFactory nodeFactory() {
        return nf;
    }

    public TypeSystem typeSystem() {
        return ts;
    }

    public NodeVisitor begin() {
        // Initialize the stack from the context.
        Context context = job.context();

        if (context == null) {
            return this;
        }

        Stack s = new Stack();

        for (ParsedClassType ct = context.currentClassScope(); ct != null; ) {
            s.push(ct);

            if (ct.isNested()) {
                ct = (ParsedClassType) ct.outer();
            }
            else {
                ct = null;
            }
        }

        if (context.importTable() != null) {
            setImportTable(context.importTable());
        }

        TypeBuilder tb = this;

        while (! s.isEmpty()) {
            ParsedClassType ct = (ParsedClassType) s.pop();

            try {
                tb = tb.pushClass(ct);
            }
            catch (SemanticException e) {
                errorQueue().enqueue(ErrorInfo.SEMANTIC_ERROR,
                                     e.getMessage(), ct.position());
                return null;
            }

            if (ct.isLocal() || ct.isAnonymous()) {
                tb = tb.pushCode();
            }
        }

        return tb;
    }

    public NodeVisitor enter(Node n) {
        try {
	    return n.del().buildTypesEnter(this);
	}
	catch (SemanticException e) {
	    Position position = e.position();

	    if (position == null) {
		position = n.position();
	    }

            if (e.getMessage() != null) {
                errorQueue().enqueue(ErrorInfo.SEMANTIC_ERROR,
                                    e.getMessage(), position);
            }
                            
            return this;
	}
    }

    public Node leave(Node old, Node n, NodeVisitor v) {
	try {
	    return n.del().buildTypes((TypeBuilder) v);
	}
	catch (SemanticException e) {
	    Position position = e.position();

	    if (position == null) {
		position = n.position();
	    }

            if (e.getMessage() != null) {
                errorQueue().enqueue(ErrorInfo.SEMANTIC_ERROR,
                                    e.getMessage(), position);
            }

	    return n;
	}
    }

    public TypeBuilder pushCode() {
        if (Report.should_report(Report.visit, 4))
	    Report.report(4, "TB pushing code: " + this);
        TypeBuilder tb = push();
        tb.inCode = true;
        tb.global = false;
        return tb;
    }

    protected TypeBuilder pushClass(ParsedClassType type) throws SemanticException {
        if (Report.should_report(Report.visit, 4))
	    Report.report(4, "TB pushing class " + type + ": " + this);

        TypeBuilder tb = push();
        tb.type = type;
        tb.inCode = false;

	// Make sure the import table finds this class.
        if (importTable() != null && type.isTopLevel()) {
	    tb.importTable().addClassImport(type.fullName());
	}
        
        return tb;
    }

    protected ParsedClassType newClass(Position pos, Flags flags, String name)
        throws SemanticException
    {
	TypeSystem ts = typeSystem();

        ParsedClassType ct = ts.createClassType(this.job.source());

	if (inCode) {
            ct.kind(ClassType.LOCAL);
	    ct.outer(currentClass());
	    ct.flags(flags);
	    ct.name(name);
	    ct.position(pos);

	    if (currentPackage() != null) {
	      	ct.package_(currentPackage());
	    }

	    return ct;
	}
	else if (currentClass() != null) {
            ct.kind(ClassType.MEMBER);
	    ct.outer(currentClass());
	    ct.flags(flags);
	    ct.name(name);
	    ct.position(pos);

	    currentClass().addMemberClass(ct);

	    if (currentPackage() != null) {
	      	ct.package_(currentPackage());
	    }

            // if all the containing classes for this class are member
            // classes or top level classes, then add this class to the
            // parsed resolver.
            ClassType container = ct.outer();
            boolean allMembers = (container.isMember() || container.isTopLevel());
            while (container.isMember()) {
                container = container.outer();
                allMembers = allMembers && 
                        (container.isMember() || container.isTopLevel());
            }

            if (allMembers) {
                typeSystem().parsedResolver().addNamed(
                    typeSystem().getTransformedClassName(ct), ct);
            }

	    return ct;
	}
	else {
            ct.kind(ClassType.TOP_LEVEL);
	    ct.flags(flags);
	    ct.name(name);
	    ct.position(pos);

	    if (currentPackage() != null) {
	      	ct.package_(currentPackage());
	    }

            Named dup = ((CachingResolver) typeSystem().systemResolver()).check(ct.fullName());

            if (dup != null && dup.fullName().equals(ct.fullName())) {
                throw new SemanticException("Duplicate class \"" +
                                            ct.fullName() + "\".", pos);
            }

            typeSystem().parsedResolver().addNamed(ct.fullName(), ct);
            ((CachingResolver) typeSystem().systemResolver()).addNamed(ct.fullName(), ct);

	    return ct;
	}
    }

    public TypeBuilder pushAnonClass(Position pos) throws SemanticException {
        if (Report.should_report(Report.visit, 4))
	    Report.report(4, "TB pushing anon class: " + this);

        if (! inCode) {
            throw new InternalCompilerError(
                "Can only push an anonymous class within code.");
        }

	TypeSystem ts = typeSystem();

        ParsedClassType ct = ts.createClassType(this.job().source());
        ct.kind(ClassType.ANONYMOUS);
        ct.outer(currentClass());
        ct.position(pos);

        if (currentPackage() != null) {
            ct.package_(currentPackage());
        }

        return pushClass(ct);
    }

    public TypeBuilder pushClass(Position pos, Flags flags, String name)
    	throws SemanticException {

        ParsedClassType t = newClass(pos, flags, name);
        return pushClass(t);
    }

    public ParsedClassType currentClass() {
        return this.type;
    }

    public Package currentPackage() {
        if (importTable() == null) return null;
	return importTable.package_();
    }

    public ImportTable importTable() {
        return importTable;
    }

    public void setImportTable(ImportTable it) {
        this.importTable = it;
    }

    public String toString() {
        return "(TB " + type +
                (inCode ? " inCode" : "") +
                (global ? " global" : "") +
                (outer == null ? ")" : " " + outer.toString() + ")");
    }
}
