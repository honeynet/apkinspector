package polyglot.frontend;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;

import java.util.*;

/**
 * An <code>InnerJob</code> encapsulates work done by the compiler for a
 * nested class. In general <code>InnerJob</code>s are transient, in that they
 * are not added to the worklist. An <code>InnerJob</code> will typically run 
 * only a few passes at a time.
 */
public class InnerJob extends Job
{
    /**
     * The job that this InnerJob was spawned by.
     */
    protected Job outer;
    
    /**
     * The context in which this Job was spawned.
     */
    protected Context context;
    
    /**
     * Only the passes between <code>begin</code> and <code>end</code> will
     * be performed.
     */
    protected Pass.ID begin;
    /**
     * Only the passes between <code>begin</code> and <code>end</code> will
     * be performed.
     */
    protected Pass.ID end;

    /** 
     * Constructor 
     */
    public InnerJob(ExtensionInfo lang, JobExt ext, Node ast, Context context,
                    Job outer, Pass.ID begin, Pass.ID end) {
        super(lang, ext, ast);

	this.context = context;
	this.outer = outer;
        this.begin = begin;
        this.end = end;
        if (ast == null) {
            throw new InternalCompilerError("Null ast");
        }
        if (outer == null) {
            throw new InternalCompilerError("Null outer job");
        }
    }

    public String toString() {
      String name = "inner-job[" + begin + ".." + end + "](code=" +
          context.currentCode() + " class=" + context.currentClass() + ") [" + status() + "]";
          
       return name + " (" +
            (isRunning() ? "running " : "before ") + 
                        nextPass() + ")" + " <<< passes = " + passes + " >>>";
    }

    /**
     * The initial list of passes is the list that the language extension
     * provides us with, limited to those between <code>begin</code> and 
     * <code>end</code> inclusive.
     */
    public List getPasses() {
      	List l = lang.passes(this, begin, end);

        for (int i = 0; i < l.size(); i++) {
            Pass pass = (Pass) l.get(i);
            if (pass.id() == begin) {
                nextPass = i;
            }
//            if (i == 0 && pass.id() != begin)
//                throw new InternalCompilerError("ExtensionInfo.passes returned incorrect list: " + l);
            if (i == l.size()-1 && pass.id() != end)
                throw new InternalCompilerError("ExtensionInfo.passes returned incorrect list: " + l);
        }

        return l;
    }

    public Context context() {
	return context;
    }

    /**
     * The <code>SourceJob</code> associated with our outer <code>Job</code>.
     */
    public SourceJob sourceJob() {
	return outer.sourceJob();
    }
}
