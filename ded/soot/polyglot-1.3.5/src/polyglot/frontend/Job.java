package polyglot.frontend;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.frontend.Compiler;

import java.util.*;

/**
 * A <code>Job</code> encapsulates work done by the compiler. <code>Job</code>s
 * are typically associated either with a nested class (<code>InnerJob</code>s)
 * or a source file (<code>SourceJob</code>s). A Job contains all information
 * carried between phases of the compiler. A Job consists of a set of scheduled
 * passes over the associated code.
 */
public abstract class Job
{
    /** Field used for storing extension-specific information. */
    protected JobExt ext;

    /** The language extension used for this job. */
    protected ExtensionInfo lang;

    /** The AST constructed from the source file. */
    protected Node ast;

    /** List of passes to be run on this job. */
    protected ArrayList passes;

    /** Index of the next pass to run. */
    protected int nextPass;

    /** The currently running pass, or null. */
    protected Pass runningPass;

    /** True if all passes run so far have been successful. */
    protected boolean status;

    /** Map from pass id to pass. */
    protected Map passMap;

    /** Initial count of errors before running the job. */
    protected int initialErrorCount;

    /** True if the the job has reported an error. */
    protected boolean reportedErrors;

    public Job(ExtensionInfo lang, JobExt ext, Node ast) {
        this.lang = lang;
        this.ext = ext;
	this.ast = ast;

	this.passes = null;
        this.passMap = null;
        this.nextPass = 0;
        this.runningPass = null;
        this.status = true;
        this.initialErrorCount = 0;
        this.reportedErrors = false;
    }

    public JobExt ext() {
      return ext;
    }

    /**
     * Return the last <code>BarrierPass</code> that this job completed; 
     * return <code>null</code> if no <code>BarrierPass</code>es have
     * yet been completed
     */
    public BarrierPass lastBarrier() {
        for (int i = nextPass - 1; i >= 0; i--) {
            Pass pass = (Pass) passes.get(i);

            if (pass instanceof BarrierPass) {
                return (BarrierPass)pass;
            }
        }

        return null;
    }

    public void setRunningPass(Pass pass) {
        // The pass is not-null iff the job is running
        if (pass != null) {
            // We're starting to run the pass. 
            // Record the initial error count.
            this.initialErrorCount = compiler().errorQueue().errorCount();
        }
        else {
            // We've stopped running a pass. 
            // Check if the error count changed.
            int errorCount = compiler().errorQueue().errorCount();

            if (errorCount > initialErrorCount) {
                reportedErrors = true;
            }
        }

        runningPass = pass;
    }

    public boolean isRunning() {
        return runningPass != null;
    }

    public Pass runningPass() {
        return runningPass;
    }

    /** Get the state's AST. */
    public Node ast() {
	return ast;
    }

    /** Set the state's AST. */
    public void ast(Node ast) {
        this.ast = ast;
    }

    /** True if some pass reported an error. */
    public boolean reportedErrors() {
        return reportedErrors;
    }

    public void dump(CodeWriter cw) {
	if (ast != null) {
	    ast.dump(cw);
	}
    }

    /**
     * By default, a <code>Job</code> does not have a context associated 
     * with it. Subclasses may override this method.
     */
    public Context context() {
        return null;
    }
    
    /**
     * Get the <code>SourceJob</code> associated with this <code>Job</code>.
     * If this <code>Job</code> is a <code>SourceJob</code>, then this 
     * object should be returned; otherwise the most suitable 
     * <code>SourceJob</code> should be returned. See subclasses' documentation
     * for more details of what the most suitable <code>SourceJob</code> is.
     */
    public abstract SourceJob sourceJob();

    /**
     * Return the <code>Source</code> associated with the 
     * <code>SourceJob</code> returned by <code>sourceJob</code>.
     */
    public Source source() {
        return this.sourceJob().source();
    }
    
    /**
     * Returns whether the source for this job was explicitly specified
     * by the user, or if it was drawn into the compilation process due
     * to some dependency.
     */
    public boolean userSpecified() {
        return this.source().userSpecified();
    }

    /**
     * Get the initial list of passes that this <code>Job</code> should go 
     * through.
     * This method is called only once, from <code>init</code>.
     */
    protected abstract List getPasses();

    /**
     * Get the list of passes that this <code>Job</code> needs to go through.
     * This list is initialized with the list returned by 
     * <code>getPasses</code>.
     */
    public final List passes() {
        if (passes == null) {
            init();
	}

        return passes;
    }

    private Map passMap() {
        if (passMap == null) {
            init();
        }
        return passMap;
    }

    /**
     * Initialize the <code>passes</code> field and the 
     * <code>passMap</code> field.
     */
    protected void init() {
        passes = new ArrayList(getPasses());
        passMap = new HashMap();
        for (int i = 0; i < passes.size(); i++) {
            Pass pass = (Pass) passes.get(i);
            passMap.put(pass.id(), new Integer(i));
        }
    }

    /**
     * Return true if all of the passes been completed.
     */
    public boolean completed() {
        return pendingPasses().isEmpty();
    }

    /**
     * Return a list of passes that have been completed so far.
     * The list returned by <code>completedPasses</code> concatenated with the 
     * list returned by <code>pendingPasses</code> should be equivalent to the
     * list returned by <code>passes</code>. 
     */
    public List completedPasses() {
	return passes().subList(0, nextPass);
    }

    /**
     * Return a list of passes that still have to be performed.
     * The list returned by <code>completedPasses</code> concatenated with the 
     * list returned by <code>pendingPasses</code> should be equivalent to the
     * list returned by <code>passes</code>. 
     */
    public List pendingPasses() {
	return passes().subList(nextPass, passes.size());
    }

    /**
     * Return true if the pass <code>id</code> has been completed.
     */
    public boolean completed(Pass.ID id) {
        Integer i = (Integer) passMap().get(id);
        return i != null && i.intValue() < nextPass;
    }

    /**
     * Return true if the pass <code>id</code> is still pending.
     */
    public boolean pending(Pass.ID id) {
        Integer i = (Integer) passMap().get(id);
        return i != null && i.intValue() >= nextPass;
    }

    /**
     * Get the pass identified by <code>id</code>.
     */
    public Pass passByID(Pass.ID id) {
        Integer i = (Integer) passMap().get(id);

        if (i != null) {
            return (Pass) passes().get(i.intValue());
        }

        throw new InternalCompilerError("No pass named \"" + id + "\".");
    }

    /**
     * Return the pass immediately before the pass identified by <code>id</code>.
     * Return <code>null</code> if no such pass exists. 
     */
    public Pass getPreviousTo(Pass.ID id) {
        Integer i = (Integer) passMap().get(id);

        if (i != null) {
            if (i.intValue() == 0) 
                return null;
            return (Pass) passes().get(i.intValue() - 1);
        }

        throw new InternalCompilerError("No pass named \"" + id + "\".");
    }
    
    /**
     * Return the next pass to be performed. Return null if there are no
     * passes left to be performed.
     */
    public Pass nextPass() {
        if (nextPass < passes().size()) {
            Pass pass = (Pass) passes().get(nextPass);
            return pass;
        }
        else {
            return null;
        }
    }

    
    public boolean status() {
        return status;
    }

    /**
     * Inform this <code>Job</code> that pass <code>p</code> has finished. 
     * If <code>okay</code> is <code>true</code>, then the pass
     * was completed successfully; if it is <code>false</code> the pass was not
     * completed successfully.
     * 
     * Pass <code>p</code> may be any pending pass.
     */
    public void finishPass(Pass p, boolean okay) {
        List passes = passes();

        status &= okay;

        for (int i = nextPass; i < passes.size(); i++) {
	    Pass pass = (Pass) passes.get(i);

	    if (pass == p) {
                nextPass = i + 1;
	      	return;
	    }
	}
    
        throw new InternalCompilerError("Pass " + p + " was not a pending " +
                            "pass.");
    }

    public ExtensionInfo extensionInfo() {
	return lang;
    }

    public Compiler compiler() {
	return lang.compiler();
    }

    /**
     * Spawn a new job. All passes between the pass <code>begin</code>
     * and <code>end</code> inclusive will be performed immediately on
     * the AST <code>ast</code>, and the resulting AST returned.
     * 
     * Spawn a new job. All passes between the pass <code>begin</code>
     * and <code>end</code> inclusive will be performed immediately on
     * the AST <code>ast</code>.
     *
     * @param c the context that the AST occurs in
     * @param ast the AST the new Job is for.
     * @param begin the first pass to perform for this job.
     * @param end the last pass to perform for this job.
     * @return the new job.  The caller can check the result with
     * <code>j.status()</code> and get the ast with <code>j.ast()</code>.
     */
    public Job spawn(Context c, Node ast, Pass.ID begin, Pass.ID end) {
        return lang.spawnJob(c, ast, this, begin, end);
    }
}
