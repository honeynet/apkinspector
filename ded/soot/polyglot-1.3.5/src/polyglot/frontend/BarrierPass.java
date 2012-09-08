package polyglot.frontend;

import polyglot.main.Report;

/**
 * A <code>BarrierPass</code> is a special pass that ensures that
 * all jobs that a given job <code>J</code> depends on have completed at 
 * least up to the last <code>BarrierPass</code> that <code>J</code> has 
 * completed.
 */
public class BarrierPass extends AbstractPass
{
    Job job;

    public BarrierPass(Pass.ID id, Job job) {
      	super(id);
	this.job = job;
    }

    /** Run all the other jobs with the same parent up to this pass. */
    public boolean run() {
        if (Report.should_report(Report.frontend, 1))
	    Report.report(1, job + " at barrier " + id);
        if (Report.should_report(Report.frontend, 2))
	    Report.report(2, "dependencies of " + job.sourceJob() + 
                        " = " + job.sourceJob().dependencies());

        return true;
    }
}
