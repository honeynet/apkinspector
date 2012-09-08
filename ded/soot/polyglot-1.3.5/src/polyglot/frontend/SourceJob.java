package polyglot.frontend;

import polyglot.ast.*;

import java.util.*;

/**
 * A <code>SourceJob</code> encapsulates work done by the compiler on behalf of
 * one source file.  It includes all information carried between phases
 * of the compiler.
 */
public class SourceJob extends Job
{
    /**
     * The <code>Source</code> that this <code>Job</code> is for.
     */
    protected Source source;

    /** 
     * Set of <code>Source</code>s that this SourceJob depends upon.
     * This will include, but is not limited to, the other Sources that
     * this SourceJob caused to load.
     */
    protected Set dependencies;

    /**
     * Set of <code>Source</code>s that depend on this job.
     */
    protected Set dependents;

    /** 
     * Constructor 
     */
    public SourceJob(ExtensionInfo lang, 
                     JobExt ext, 
                     Source source, 
                     Node ast) {
        super(lang, ext, ast);

        this.source = source;
        this.dependencies = new HashSet();
        this.dependents = new HashSet();

    }

    public Set dependencies() {
        return dependencies;
    }
    
    public Set dependents() {
        return dependents;
    }

    public void addDependent(Source s) {
        if (s != this.source()) {
            dependents.add(s);
        }
    }

    public void addDependency(Source s) {
        if (s != this.source()) {
            dependencies.add(s);
        }
    }

    /**
     * The initial list of passes is just the list that the language extension
     * provides us with.
     */
    public List getPasses() {
        return lang.passes(this);
    }

    public Source source() {
        return source;
    }

    public SourceJob sourceJob() {
	return this;
    }

    public String toString() {
        return source.toString() + " (" +
            (completed() ? "done"
                    : ((isRunning() ? "running "
                                    : "before ") + nextPass())) + ")";
    }
}
