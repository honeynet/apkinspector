package polyglot.frontend;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.types.reflect.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.main.Options;
import polyglot.main.Report;

import java.io.*;
import java.util.*;

/**
 * This is an abstract <code>ExtensionInfo</code>.
 */
public abstract class AbstractExtensionInfo implements ExtensionInfo {
    protected Compiler compiler;
    private Options options;
    protected TypeSystem ts = null;
    protected NodeFactory nf = null;
    protected SourceLoader source_loader = null;
    protected TargetFactory target_factory = null;
    protected Stats stats;

    /**
     * A list of all active (that is, uncompleted) <code>SourceJob</code>s.
     */
    protected LinkedList worklist;

    /**
     * A map from <code>Source</code>s to <code>SourceJob</code>s or to
     * the <code>COMPLETED_JOB</code> object if the SourceJob previously existed
     * but has now finished. The map contains entries for all
     * <code>Source</code>s that have had <code>Job</code>s added for them.
     */
    protected Map jobs;

    protected static final Object COMPLETED_JOB = "COMPLETED JOB";

    /** The currently running job, or null if no job is running. */
    protected Job currentJob;

    public Options getOptions() {
        if (this.options == null) {
            this.options = createOptions();
        }
        return options;
    }

    protected Options createOptions() {
        return new Options(this);
    }

    /** Return a Stats object to accumulate and report statistics. */
    public Stats getStats() {
        if (this.stats == null) {
            this.stats = new Stats(this);
        }
        return stats;
    }

    public Compiler compiler() {
        return compiler;
    }

    public void initCompiler(Compiler compiler) {
	this.compiler = compiler;
        jobs = new HashMap();
        worklist = new LinkedList();

        // Register the extension with the compiler.
        compiler.addExtension(this);

        currentJob = null;

        // Create the type system and node factory.
        typeSystem();
        nodeFactory();

        initTypeSystem();
    }

    protected abstract void initTypeSystem();

    /**
     * Run all jobs in the work list (and any children they have) to
     * completion. This method returns <code>true</code> if all jobs were
     * successfully completed. If all jobs were successfully completed, then
     * the worklist will be empty.
     *
     * The scheduling of <code>Job</code>s uses two methods to maintain
     * scheduling invariants: <code>selectJobFromWorklist</code> selects
     * a <code>SourceJob</code> from <code>worklist</code> (a list of
     * jobs that still need to be processed); <code>enforceInvariants</code> is
     * called before a pass is performed on a <code>SourceJob</code> and is
     * responsible for ensuring all dependencies are satisfied before the
     * pass proceeds, i.e. enforcing any scheduling invariants.
     */
    public boolean runToCompletion() {
        boolean okay = true;

        while (okay && ! worklist.isEmpty()) {
            SourceJob job = selectJobFromWorklist();

            if (Report.should_report(Report.frontend, 1)) {
		Report.report(1, "Running job " + job);
            }

            okay &= runAllPasses(job);

            if (job.completed()) {
                // the job has finished. Let's remove it from the map so it
                // can be garbage collected, and free up the AST.
                jobs.put(job.source(), COMPLETED_JOB);

                if (Report.should_report(Report.frontend, 1)) {
                    Report.report(1, "Completed job " + job);
                }
            }
            else {
                // the job is not yet completed (although, it really
                // should be...)
                if (Report.should_report(Report.frontend, 1)) {
                    Report.report(1, "Failed to complete job " + job);
                }
                worklist.add(job);
            }
        }

        if (Report.should_report(Report.frontend, 1))
	    Report.report(1, "Finished all passes -- " +
                        (okay ? "okay" : "failed"));

        return okay;
    }

    /**
     * Select and remove a <code>SourceJob</code> from the non-empty
     * <code>worklist</code>. Return the selected <code>SourceJob</code>
     * which will be scheduled to run all of its remaining passes.
     */
    protected SourceJob selectJobFromWorklist() {
        return (SourceJob)worklist.remove(0);
    }

    /**
     * Read a source file and compile it up to the the current job's last
     * barrier.
     */
    public boolean readSource(FileSource source) {
        // Add a new SourceJob for the given source. If a Job for the source
        // already exists, then we will be given the existing job.
        SourceJob job = addJob(source);

        if (job == null) {
            // addJob returns null if the job has already been completed, in
            // which case we can just ignore the request to read in the source.
            return true;
        }

        // Run the new job up to the currentJob's SourceJob's last barrier, to
        // make sure that dependencies are satisfied.
        Pass.ID barrier;

        if (currentJob != null) {
            if (currentJob.sourceJob().lastBarrier() == null) {
                throw new InternalCompilerError("A Source Job which has " +
                            "not reached a barrier cannot read another " +
                            "source file.");
            }

            barrier = currentJob.sourceJob().lastBarrier().id();
        }
        else {
            barrier = Pass.FIRST_BARRIER;
        }

        // Make sure we reach at least the first barrier defined
        // in the base compiler.  This forces types to be constructed.
        // If FIRST_BARRIER is before "barrier",
        // then the second runToPass will just return true.
        return runToPass(job, barrier) && runToPass(job, Pass.FIRST_BARRIER);
    }

    /**
     * Run all pending passes on <code>job</code>.
     */
    public boolean runAllPasses(Job job) {
        List pending = job.pendingPasses();

        // Run until there are no more passes.
        if (!pending.isEmpty()) {
            Pass lastPass = (Pass)pending.get(pending.size() - 1);
            return runToPass(job, lastPass);
        }

        return true;
    }

    /**
     * Run a job until the <code>goal</code> pass completes.
     */
    public boolean runToPass(Job job, Pass.ID goal) {
        if (Report.should_report(Report.frontend, 1))
            Report.report(1, "Running " + job + " to pass named " + goal);

        if (job.completed(goal)) {
            return true;
        }

        Pass pass = job.passByID(goal);

        return runToPass(job, pass);
    }

    /**
     * Run a job up to the <code>goal</code> pass.
     */
    public boolean runToPass(Job job, Pass goal) {
        if (Report.should_report(Report.frontend, 1))
	    Report.report(1, "Running " + job + " to pass " + goal);

        while (! job.pendingPasses().isEmpty()) {
            Pass pass = (Pass) job.pendingPasses().get(0);

            try {
                runPass(job, pass);
            }
            catch (CyclicDependencyException e) {
                // cause the pass to fail.
                job.finishPass(pass, false);
            }

            if (pass == goal) {
                break;
            }
        }

        if (job.completed()) {
            if (Report.should_report(Report.frontend, 1))
                Report.report(1, "Job " + job + " completed");
        }

        return job.status();
    }

    /**
     * Run the pass <code>pass</code> on the job. Before running the pass on
     * the job, if the job is a <code>SourceJob</code>, then this method will
     * ensure that the scheduling invariants are enforced by calling
     * <code>enforceInvariants</code>.
     */
    protected void runPass(Job job, Pass pass) throws CyclicDependencyException
    {
        // make sure that all scheduling invariants are satisfied before running
        // the next pass. We may thus execute some other passes on other
        // jobs running the given pass.
        try {
            enforceInvariants(job, pass);
        }
        catch (CyclicDependencyException e) {
            // A job that depends on this job is still running 
            // an earlier pass.  We cannot continue this pass,
            // but we can just silently fail since the job we're
            // that depends on this one will eventually try
            // to run this pass again when it reaches a barrier.
            return;
        }

        if (getOptions().disable_passes.contains(pass.name())) {
            if (Report.should_report(Report.frontend, 1))
                Report.report(1, "Skipping pass " + pass);
            job.finishPass(pass, true);
            return;
        }

        if (Report.should_report(Report.frontend, 1))
            Report.report(1, "Trying to run pass " + pass + " in " + job);

        if (job.isRunning()) {
            // We're currently running.  We can't reach the goal.
            throw new CyclicDependencyException(job +
                                            " cannot reach pass " +
                                            pass);
        }

        pass.resetTimers();

        boolean result = false;
        if (job.status()) {
            Job oldCurrentJob = this.currentJob;
            this.currentJob = job;
            Report.should_report.push(pass.name());

            // Stop the timer on the old pass. */
            Pass oldPass = oldCurrentJob != null
                         ? oldCurrentJob.runningPass()
                         : null;

            if (oldPass != null) {
                oldPass.toggleTimers(true);
            }

            job.setRunningPass(pass);
            pass.toggleTimers(false);

            result = pass.run();

            pass.toggleTimers(false);
            job.setRunningPass(null);

            Report.should_report.pop();
            this.currentJob = oldCurrentJob;

            // Restart the timer on the old pass. */
            if (oldPass != null) {
                oldPass.toggleTimers(true);
            }

            // pretty-print this pass if we need to.
            if (getOptions().print_ast.contains(pass.name())) {
                System.err.println("--------------------------------" +
                                   "--------------------------------");
                System.err.println("Pretty-printing AST for " + job +
                                   " after " + pass.name());

                PrettyPrinter pp = new PrettyPrinter();
                pp.printAst(job.ast(), new CodeWriter(System.err, 78));
            }

            // dump this pass if we need to.
            if (getOptions().dump_ast.contains(pass.name())) {
                System.err.println("--------------------------------" +
                                   "--------------------------------");
                System.err.println("Dumping AST for " + job +
                                   " after " + pass.name());

		NodeVisitor dumper =
		  new DumpAst(new CodeWriter(System.err, 78));
		dumper = dumper.begin();
		job.ast().visit(dumper);
		dumper.finish();
            }

            // This seems to work around a VM bug on linux with JDK
            // 1.4.0.  The mark-sweep collector will sometimes crash.
            // Running the GC explicitly here makes the bug go away.
            // If this fails, maybe run with bigger heap.

            // System.gc();
        }

        Stats stats = getStats();
        stats.accumPassTimes(pass.id(), pass.inclusiveTime(),
                             pass.exclusiveTime());

        if (Report.should_report(Report.time, 2)) {
            Report.report(2, "Finished " + pass +
                          " status=" + str(result) + " inclusive_time=" +
                          pass.inclusiveTime() + " exclusive_time=" +
                          pass.exclusiveTime());
        }
        else if (Report.should_report(Report.frontend, 1)) {
            Report.report(1, "Finished " + pass +
                          " status=" + str(result));
        }

        job.finishPass(pass, result);
    }

    /**
     * Before running <code>Pass pass</code> on <code>SourceJob job</code>
     * make sure that all appropriate scheduling invariants are satisfied,
     * to ensure that all passes of other jobs that <code>job</code> depends
     * on will have already been done.
     *
     */
    protected void enforceInvariants(Job job, Pass pass) throws CyclicDependencyException {
        SourceJob srcJob = job.sourceJob();
        if (srcJob == null) {
            return;
        }

        BarrierPass lastBarrier = srcJob.lastBarrier();
        if (lastBarrier != null) {
            // make sure that _all_ dependent jobs have completed at least up to
            // the last barrier (not just children).
            //
            // Ideally the invariant should be that only the source jobs that
            // job _depends on_ should be brought up to the last barrier.
            // This is work to be done in the future...
            List allDependentSrcs = new ArrayList(srcJob.dependencies());
            Iterator i = allDependentSrcs.iterator();
            while (i.hasNext()) {
                Source s = (Source)i.next();
                Object o = jobs.get(s);
                if (o == COMPLETED_JOB) continue;
                if (o == null) {
                    throw new InternalCompilerError("Unknown source " + s);
                }
                SourceJob sj = (SourceJob)o;
                if (sj.pending(lastBarrier.id())) {
                    // Make the job run up to the last barrier.
                    // We ignore the return result, since even if the job
                    // fails, we will keep on going and see
                    // how far we get...
                    if (Report.should_report(Report.frontend, 3)) {
                        Report.report(3, "Running " + sj +
                                  " to " + lastBarrier.id() + " for " + srcJob);
                    }
                    runToPass(sj, lastBarrier.id());
                }
            }
        }

        if (pass instanceof GlobalBarrierPass) {
            // need to make sure that _all_ jobs have completed just up to
            // this global barrier.

            // If we hit a cyclic dependency, ignore it and run the other
            // jobs up to that pass.  Then try again to run the cyclic
            // pass.  If we hit the cycle again for the same job, stop.
            LinkedList barrierWorklist = new LinkedList(jobs.values());

            while (! barrierWorklist.isEmpty()) {
                Object o = barrierWorklist.removeFirst();
                if (o == COMPLETED_JOB) continue;
                SourceJob sj = (SourceJob)o;
                if (sj.completed(pass.id()) ||
                    sj.nextPass() == sj.passByID(pass.id())) {
                    // the source job has either done this global pass
                    // (which is possible if the job was loaded late in the
                    // game), or is right up to the global barrier.
                    continue;
                }

                // Make the job run up to just before the global barrier.
                // We ignore the return result, since even if the job
                // fails, we will keep on going and see
                // how far we get...
                Pass beforeGlobal = sj.getPreviousTo(pass.id());
                if (Report.should_report(Report.frontend, 3)) {
                    Report.report(3, "Running " + sj +
                              " to " + beforeGlobal.id() + " for " + srcJob);
                }

                // Don't use runToPass, since that catches the
                // CyclicDependencyException that we should report
                // back to the caller.
                while (! sj.pendingPasses().isEmpty()) {
                    Pass p = (Pass) sj.pendingPasses().get(0);

                    runPass(sj, p);

                    if (p == beforeGlobal) {
                        break;
                    }
                }
            }
        }
    }

    private static String str(boolean okay) {
        if (okay) {
            return "done";
        }
        else {
            return "failed";
        }
    }

    /**
     * Get the file name extension of source files.  This is
     * either the language extension's default file name extension
     * or the string passed in with the "-sx" command-line option.
     */
    public String[] fileExtensions() {
	String[] sx = getOptions() == null ? null : getOptions().source_ext;

	if (sx == null) {
	    sx = defaultFileExtensions();
        }

        if (sx.length == 0) {
            return defaultFileExtensions();
        }

        return sx;
    }

    /** Get the default list of file extensions. */
    public String[] defaultFileExtensions() {
        String ext = defaultFileExtension();
        return new String[] { ext };
    }

    /** Get the source file loader object for this extension. */
    public SourceLoader sourceLoader() {
        if (source_loader == null) {
            source_loader = new SourceLoader(this, getOptions().source_path);
        }

        return source_loader;
    }

    /** Get the target factory object for this extension. */
    public TargetFactory targetFactory() {
        if (target_factory == null) {
            target_factory = new TargetFactory(getOptions().output_directory,
                                               getOptions().output_ext,
                                               getOptions().output_stdout);
        }

        return target_factory;
    }

    /** Create the type system for this extension. */
    protected abstract TypeSystem createTypeSystem();

    /** Get the type system for this extension. */
    public TypeSystem typeSystem() {
	if (ts == null) {
	    ts = createTypeSystem();
	}
	return ts;
    }

    /** Create the node factory for this extension. */
    protected abstract NodeFactory createNodeFactory();

    /** Get the AST node factory for this extension. */
    public NodeFactory nodeFactory() {
	if (nf == null) {
	    nf = createNodeFactory();
	}
	return nf;
    }

    /**
     * Get the job extension for this language extension.  The job
     * extension is used to extend the <code>Job</code> class
     * without subtyping.
     */
    public JobExt jobExt() {
      return null;
    }

    /**
     * Adds a dependency from the current job to the given Source.
     */
    public void addDependencyToCurrentJob(Source s) {
        if (s == null)
            return;
        if (currentJob != null) {
            Object o = jobs.get(s);
            if (o != COMPLETED_JOB) {
                if (Report.should_report(Report.frontend, 2)) {
                    Report.report(2, "Adding dependency from " +
                            currentJob.source() + " to " +
                            s);
                }
                currentJob.sourceJob().addDependency(s);
            }
        }
        else {
            throw new InternalCompilerError("No current job!");
        }
    }

    /**
     * Add a new <code>SourceJob</code> for the <code>Source source</code>.
     * A new job will be created if
     * needed. If the <code>Source source</code> has already been processed,
     * and its job discarded to release resources, then <code>null</code>
     * will be returned.
     */
    public SourceJob addJob(Source source) {
        return addJob(source, null);
    }

    /**
     * Add a new <code>SourceJob</code> for the <code>Source source</code>,
     * with AST <code>ast</code>.
     * A new job will be created if
     * needed. If the <code>Source source</code> has already been processed,
     * and its job discarded to release resources, then <code>null</code>
     * will be returned.
     */
    public SourceJob addJob(Source source, Node ast) {
        Object o = jobs.get(source);
        SourceJob job = null;
        if (o == COMPLETED_JOB) {
            // the job has already been completed.
            // We don't need to add a job
            return null;
        }
        else if (o == null) {
            // No appropriate job yet exists, we will create one.

            job = this.createSourceJob(source, ast);

            // record the job in the map and the worklist.
            jobs.put(source, job);
            worklist.addLast(job);

            if (Report.should_report(Report.frontend, 3)) {
                Report.report(3, "Adding job for " + source + " at the " +
                    "request of job " + currentJob);
            }
        }
        else {
            job = (SourceJob)o;
        }

        // if the current source job caused this job to load, record the
        // dependency.
        if (currentJob instanceof SourceJob) {
           ((SourceJob)currentJob).addDependency(source);
        }

        return job;
    }

    /**
     * Create a new <code>SourceJob</code> for the given source and AST.
     * In general, this method should only be called by <code>addJob</code>.
     */
    protected SourceJob createSourceJob(Source source, Node ast) {
        return new SourceJob(this, jobExt(), source, ast);
    }

    /**
     * Create a new non-<code>SourceJob</code> <code>Job</code>, for the
     * given AST. In general this method should only be called by
     * <code>spawnJob</code>.
     *
     * @param ast the AST the new Job is for.
     * @param context the context that the AST occurs in
     * @param outer the <code>Job</code> that spawned this job.
     * @param begin the first pass to perform for this job.
     * @param end the last pass to perform for this job.
     */
    protected Job createJob(Node ast, Context context, Job outer, Pass.ID begin, Pass.ID end) {
	return new InnerJob(this, jobExt(), ast, context, outer, begin, end);
    }

    /**
     * Spawn a new job. All passes between the pass <code>begin</code>
     * and <code>end</code> inclusive will be performed immediately on
     * the AST <code>ast</code>.
     *
     * @param c the context that the AST occurs in
     * @param ast the AST the new Job is for.
     * @param outerJob the <code>Job</code> that spawned this job.
     * @param begin the first pass to perform for this job.
     * @param end the last pass to perform for this job.
     * @return the new job.  The caller can check the result with
     * <code>j.status()</code> and get the ast with <code>j.ast()</code>.
     */
    public Job spawnJob(Context c, Node ast, Job outerJob,
                           Pass.ID begin, Pass.ID end) {
        Job j = createJob(ast, c, outerJob, begin, end);

        if (Report.should_report(Report.frontend, 1))
            Report.report(1, this +" spawning " + j);

        // Run all the passes
        runAllPasses(j);

        // Return the job.  The caller can check the result with j.status().
        return j;
    }

    /** Get the parser for this language extension. */
    public abstract Parser parser(Reader reader, FileSource source,
                                  ErrorQueue eq);

    /**
     * Replace the pass named <code>id</code> in <code>passes</code> with
     * the list of <code>newPasses</code>.
     */
    public void replacePass(List passes, Pass.ID id, List newPasses) {
        for (ListIterator i = passes.listIterator(); i.hasNext(); ) {
          Pass p = (Pass) i.next();

          if (p.id() == id) {
            if (p instanceof BarrierPass) {
              throw new InternalCompilerError("Cannot replace a barrier pass.");
            }

            i.remove();

            for (Iterator j = newPasses.iterator(); j.hasNext(); ) {
              i.add(j.next());
            }

            return;
          }
        }

        throw new InternalCompilerError("Pass " + id + " not found.");
    }

    /**
     * Remove the pass named <code>id</code> from <code>passes</code>.
     */
    public void removePass(List passes, Pass.ID id) {
        for (ListIterator i = passes.listIterator(); i.hasNext(); ) {
          Pass p = (Pass) i.next();

          if (p.id() == id) {
            if (p instanceof BarrierPass) {
              throw new InternalCompilerError("Cannot remove a barrier pass.");
            }

            i.remove();
            return;
          }
        }

        throw new InternalCompilerError("Pass " + id + " not found.");
    }

    /**
     * Insert the list of <code>newPasses</code> into <code>passes</code>
     * immediately before the pass named <code>id</code>.
     */
    public void beforePass(List passes, Pass.ID id, List newPasses) {
        for (ListIterator i = passes.listIterator(); i.hasNext(); ) {
          Pass p = (Pass) i.next();

          if (p.id() == id) {
            // Backup one position.
            i.previous();

            for (Iterator j = newPasses.iterator(); j.hasNext(); ) {
              i.add(j.next());
            }

            return;
          }
        }

        throw new InternalCompilerError("Pass " + id + " not found.");
    }

    /**
     * Insert the list of <code>newPasses</code> into <code>passes</code>
     * immediately after the pass named <code>id</code>.
     */
    public void afterPass(List passes, Pass.ID id, List newPasses) {
        for (ListIterator i = passes.listIterator(); i.hasNext(); ) {
          Pass p = (Pass) i.next();

          if (p.id() == id) {
            for (Iterator j = newPasses.iterator(); j.hasNext(); ) {
              i.add(j.next());
            }

            return;
          }
        }

        throw new InternalCompilerError("Pass " + id + " not found.");
    }

    /**
     * Replace the pass named <code>id</code> in <code>passes</code> with
     * the pass <code>pass</code>.
     */
    public void replacePass(List passes, Pass.ID id, Pass pass) {
        replacePass(passes, id, Collections.singletonList(pass));
    }

    /**
     * Insert the pass <code>pass</code> into <code>passes</code>
     * immediately before the pass named <code>id</code>.
     */
    public void beforePass(List passes, Pass.ID id, Pass pass) {
        beforePass(passes, id, Collections.singletonList(pass));
    }

    /**
     * Insert the pass <code>pass</code> into <code>passes</code>
     * immediately after the pass named <code>id</code>.
     */
    public void afterPass(List passes, Pass.ID id, Pass pass) {
        afterPass(passes, id, Collections.singletonList(pass));
    }

    /**
     * Get the complete list of passes for the job.
     */
    public abstract List passes(Job job);

    /**
     * Get the sub-list of passes for the job between passes
     * <code>begin</code> and <code>end</code>, inclusive.
     */
    public List passes(Job job, Pass.ID begin, Pass.ID end) {
        List l = passes(job);
        Pass p = null;

        Iterator i = l.iterator();

        while (i.hasNext()) {
            p = (Pass) i.next();
            if (begin == p.id()) break;
            if (! (p instanceof BarrierPass)) i.remove();
        }

        while (p.id() != end && i.hasNext()) {
            p = (Pass) i.next();
        }

        while (i.hasNext()) {
            p = (Pass) i.next();
            i.remove();
        }

        return l;
    }

    public String toString() {
        return getClass().getName() + " worklist=" + worklist;
    }

    public ClassFile createClassFile(File classFileSource, byte[] code){
        return new ClassFile(classFileSource, code, this);
    }
}
