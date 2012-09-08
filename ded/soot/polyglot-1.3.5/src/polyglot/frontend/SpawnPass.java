package polyglot.frontend;
 
import polyglot.ast.*;
import polyglot.util.InternalCompilerError;

/**
 * The <code>SpawnPass</code> spawns a new job and runs it on the current
 * job's top-level context and ast.  This allows passes to be re-run.
 */
public class SpawnPass extends AbstractPass {
    Job job;
    Pass.ID begin;
    Pass.ID end;
     
    public SpawnPass(Pass.ID id, Job job, Pass.ID begin, Pass.ID end) {
        super(id);
	this.job = job;
	this.begin = begin;
	this.end = end;
    }
       
    public boolean run() {
        if (job.ast() == null) {
            throw new InternalCompilerError("Null AST.");
        }

        Job j = job.spawn(job.context(), job.ast(), begin, end);
        return j.status();
    }
}
