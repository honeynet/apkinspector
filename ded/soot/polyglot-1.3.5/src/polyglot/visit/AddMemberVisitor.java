package polyglot.visit;

import polyglot.ast.*;
import polyglot.frontend.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.types.Package;
import polyglot.main.Report;

import java.io.IOException;
import java.util.*;

/** Visitor which traverses the AST constructing type objects. */
public class AddMemberVisitor extends ContextVisitor
{
    public AddMemberVisitor(Job job, TypeSystem ts, NodeFactory nf) {
        super(job, ts, nf);
    }

    protected NodeVisitor enterCall(Node n) throws SemanticException {
        if (Report.should_report(Report.visit, 4))
	    Report.report(4, ">> AddMemberVisitor::enter " + n);
        return n.del().addMembersEnter(this);
    }

    protected Node leaveCall(Node old, Node n, NodeVisitor v) throws SemanticException {
      if (Report.should_report(Report.visit, 4))
	Report.report(4, "<< AddMemberVisitor::leave " + n);
        return n.del().addMembers((AddMemberVisitor) v);
    }
}
