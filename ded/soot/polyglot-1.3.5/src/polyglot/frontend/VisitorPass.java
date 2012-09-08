package polyglot.frontend;

import polyglot.ast.Node;
import polyglot.visit.NodeVisitor;
import polyglot.main.Report;
import polyglot.util.*;

/** A pass which runs a visitor. */
public class VisitorPass extends AbstractPass
{
    Job job;
    NodeVisitor v;

    public VisitorPass(Pass.ID id, Job job) {
	this(id, job, null);
    }

    public VisitorPass(Pass.ID id, Job job, NodeVisitor v) {
        super(id);
	this.job = job;
	this.v = v;
    }

    public void visitor(NodeVisitor v) {
	this.v = v;
    }

    public NodeVisitor visitor() {
	return v;
    }

    public boolean run() {
	Node ast = job.ast();

	if (ast == null) {
	    throw new InternalCompilerError("Null AST: did the parser run?");
	}

        NodeVisitor v_ = v.begin();

        if (v_ != null) {
	    ErrorQueue q = job.compiler().errorQueue();
	    int nErrsBefore = q.errorCount();

            if (Report.should_report(Report.frontend, 3))
                Report.report(3, "Running " + v_ + " on " + ast);

            ast = ast.visit(v_);
            v_.finish(ast);

            /*
            // if the ast did not change, there no need to stop even if there
            // are errors
            if (ast == job.ast()) {
                return true;
            }
            */

            int nErrsAfter = q.errorCount();

            job.ast(ast);

            return (nErrsBefore == nErrsAfter);
            // because, if they're equal, no new errors occurred,
            // so the run was successful.
        }

        return false;
    }

    public String toString() {
	return id.toString();
    }
}
