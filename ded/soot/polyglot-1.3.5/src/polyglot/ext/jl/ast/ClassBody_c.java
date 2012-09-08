package polyglot.ext.jl.ast;

import java.util.*;

import polyglot.ast.ClassBody;
import polyglot.ast.ClassDecl;
import polyglot.ast.ClassMember;
import polyglot.ast.Node;
import polyglot.ast.Term;
import polyglot.frontend.Job;
import polyglot.frontend.Pass;
import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;

/**
 * A <code>ClassBody</code> represents the body of a class or interface
 * declaration or the body of an anonymous class.
 */
public class ClassBody_c extends Term_c implements ClassBody
{
    protected List members;

    public ClassBody_c(Position pos, List members) {
        super(pos);
        this.members = TypedList.copyAndCheck(members, ClassMember.class, true);
    }

    public List members() {
        return this.members;
    }

    public ClassBody members(List members) {
        ClassBody_c n = (ClassBody_c) copy();
        n.members = TypedList.copyAndCheck(members, ClassMember.class, true);
        return n;
    }

    public ClassBody addMember(ClassMember member) {
        ClassBody_c n = (ClassBody_c) copy();
        List l = new ArrayList(this.members.size() + 1);
        l.addAll(this.members);
        l.add(member);
        n.members = TypedList.copyAndCheck(l, ClassMember.class, true);
        return n;
    }

    protected ClassBody_c reconstruct(List members) {
        if (! CollectionUtil.equals(members, this.members)) {
            ClassBody_c n = (ClassBody_c) copy();
            n.members = TypedList.copyAndCheck(members,
                                               ClassMember.class, true);
            return n;
        }

        return this;
    }

    public Node visitChildren(NodeVisitor v) {
        List members = visitList(this.members, v);
        return reconstruct(members);
    }

    public NodeVisitor disambiguateEnter(AmbiguityRemover ar) throws SemanticException {
        // We can't clean-super any member classes yet until we are finished
        // with this class and all at the same nesting level.
        // Delay until the clean-sigs pass.
        if (ar.kind() == AmbiguityRemover.SUPER ||
            ar.kind() == AmbiguityRemover.SIGNATURES) {
            return ar.bypassChildren(this);
        }

        /*
        // Skip clean-sigs for member classes; this will be done when we leave
        // the node, but only after clean-super is performed.
        if (ar.kind() == AmbiguityRemover.SIGNATURES) {
            for (Iterator i = members.iterator(); i.hasNext(); ) {
                ClassMember n = (ClassMember) i.next();

                if (n instanceof ClassDecl) {
                    ar = (AmbiguityRemover) ar.bypass(n);
                }
            }

            return ar;
        }
        */

        return ar;
    }

    public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
        // Now we can clean-super on the member classes.
        if (ar.kind() == AmbiguityRemover.SIGNATURES) {
            List l = new ArrayList(members.size());

            Job j = ar.job();

            for (Iterator i = members.iterator(); i.hasNext(); ) {
                ClassMember n = (ClassMember) i.next();

                if (n instanceof ClassDecl) {
                    Job sj = j.spawn(ar.context(), n,
                                     Pass.CLEAN_SUPER, Pass.CLEAN_SUPER_ALL);

                    if (! sj.status()) {                       
                        if (! sj.reportedErrors()) {
                            throw new SemanticException("Could not disambiguate " +
                                                        "class member.",
                                                        n.position());
                        }
                        throw new SemanticException();
                    }

                    ClassDecl m = (ClassDecl) sj.ast();
                    l.add(m.visit(ar.visitChildren()));
                }
                else {
                    l.add(n.visit(ar.visitChildren()));
                }
            }

            return members(l);
        }

        return this;
    }

    public String toString() {
        return "{ ... }";
    }

    protected void duplicateFieldCheck(TypeChecker tc) throws SemanticException {
        ClassType type = tc.context().currentClass();

        ArrayList l = new ArrayList(type.fields());

        for (int i = 0; i < l.size(); i++) {
            FieldInstance fi = (FieldInstance) l.get(i);

            for (int j = i+1; j < l.size(); j++) {
                FieldInstance fj = (FieldInstance) l.get(j);

                if (fi.name().equals(fj.name())) {
                    throw new SemanticException("Duplicate field \"" + fj + "\".", fj.position());
                }
            }
        }
    }

    protected void duplicateConstructorCheck(TypeChecker tc) throws SemanticException {
        ClassType type = tc.context().currentClass();

        ArrayList l = new ArrayList(type.constructors());

        for (int i = 0; i < l.size(); i++) {
            ConstructorInstance ci = (ConstructorInstance) l.get(i);

            for (int j = i+1; j < l.size(); j++) {
                ConstructorInstance cj = (ConstructorInstance) l.get(j);

                if (ci.hasFormals(cj.formalTypes())) {
                    throw new SemanticException("Duplicate constructor \"" + cj + "\".", cj.position());
                }
            }
        }
    }

    protected void duplicateMethodCheck(TypeChecker tc) throws SemanticException {
        ClassType type = tc.context().currentClass();
        TypeSystem ts = tc.typeSystem();

        ArrayList l = new ArrayList(type.methods());

        for (int i = 0; i < l.size(); i++) {
            MethodInstance mi = (MethodInstance) l.get(i);

            for (int j = i+1; j < l.size(); j++) {
                MethodInstance mj = (MethodInstance) l.get(j);

                if (isSameMethod(ts, mi, mj)) {
                    throw new SemanticException("Duplicate method \"" + mj + "\".", mj.position());
                }
            }
        }
    }

    protected void duplicateMemberClassCheck(TypeChecker tc) throws SemanticException {
        ClassType type = tc.context().currentClass();
        TypeSystem ts = tc.typeSystem();

        ArrayList l = new ArrayList(type.memberClasses());

        for (int i = 0; i < l.size(); i++) {
            ClassType mi = (ClassType) l.get(i);

            for (int j = i+1; j < l.size(); j++) {
                ClassType mj = (ClassType) l.get(j);

                if (mi.name().equals(mj.name())) {
                    throw new SemanticException("Duplicate member type \"" + mj + "\".", mj.position());
                }
            }
        }
    }

    protected boolean isSameMethod(TypeSystem ts, MethodInstance mi,
                                   MethodInstance mj) {
        return mi.isSameMethod(mj);
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
        duplicateFieldCheck(tc);
        duplicateConstructorCheck(tc);
        duplicateMethodCheck(tc);
        duplicateMemberClassCheck(tc);

        return this;
    }

    public NodeVisitor exceptionCheckEnter(ExceptionChecker ec) throws SemanticException {
        return ec.pushNew();
    }

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        if (!members.isEmpty()) {
            w.newline(4);
            w.begin(0);

            for (Iterator i = members.iterator(); i.hasNext(); ) {
                ClassMember member = (ClassMember) i.next();
                printBlock(member, w, tr);
                if (i.hasNext()) {
                    w.newline(0);
                    w.newline(0);
                }
            }

            w.end();
            w.newline(0);
        }
    }

    /**
     * Return the first (sub)term performed when evaluating this
     * term.
     */
    public Term entry() {
        // Do _not_ visit class members.
        return this;
    }

    /**
     * Visit this term in evaluation order.
     */
    public List acceptCFG(CFGBuilder v, List succs) {
        return succs;
    }

    protected static final Collection TOPICS = 
                CollectionUtil.list(Report.types, Report.context);
     
}
