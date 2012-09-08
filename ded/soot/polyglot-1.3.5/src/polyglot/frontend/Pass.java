package polyglot.frontend;

import polyglot.util.Enum;

/** A <code>Pass</code> represents a compiler pass.
 * A <code>Job</code> runs a series of passes over the AST. 
 * Each pass has an ID that is used to identify similar passes across
 * several jobs.  For example, most jobs contain a pass named PARSE
 * that returns an AST for a source file and a pass TYPE_CHECK
 * that performs type checking on the job's AST.
 */
public interface Pass
{
    /** Pass identifiers. These should be unique within a Job. */
    public static class ID extends Enum {
        public ID(String name) { super(name); }
    }

    /** Return the identifier for the pass.
     * The identifier should be unique within the pass's Job. 
     */
    public ID id();

    /** Return a user-readable name for the pass. */
    public String name();

    /** Run the pass. */
    public boolean run();

    /** Reset the pass timers to 0. */
    public void resetTimers();

    /** Start/stop the pass timers. */
    public void toggleTimers(boolean exclusive_only);

    /** The total accumulated time in ms since the last timer reset
      * that the pass was running, including spawned passes. */
    public long inclusiveTime();

    /** The total accumulated time in ms since the last timer reset
      * that the pass was running, excluding spawned passes. */
    public long exclusiveTime();

    // Below are the IDs of all the passes of the base compiler,
    // plus a few optional passes that extensions may use.

    /** ID of the parser pass.  See Parser. */
    public static final ID PARSE = new ID("parse");

    /** ID of the build-types pass.  See TypeBuilder. */
    public static final ID BUILD_TYPES = new ID("build-types");

    /** ID of the barrier after build-types. */
    public static final ID BUILD_TYPES_ALL = new ID("build-types-barrier");

    /** ID of the clean-super pass.  See AmbiguityRemover. */
    public static final ID CLEAN_SUPER = new ID("clean-super");

    /** ID of the barrier after clean-super. */
    public static final ID CLEAN_SUPER_ALL = new ID("clean-super-barrier");

    /** ID of the clean-sigs pass.  See AmbiguityRemover. */
    public static final ID CLEAN_SIGS = new ID("clean-sigs");

    /** ID of the add-members pass.  See AddMemberVisitor. */
    public static final ID ADD_MEMBERS = new ID("add-members");

    /** ID of the barrier after add-members. */
    public static final ID ADD_MEMBERS_ALL = new ID("add-members-barrier");

    /** ID of the disambiguate pass.  See AmbiguityRemover. */
    public static final ID DISAM = new ID("disam");

    /** ID of the barrier after disam. */
    public static final ID DISAM_ALL = new ID("disam-barrier");

    /** ID of the type-check pass.  See TypeChecker. */
    public static final ID TYPE_CHECK = new ID("type-check");

    /** ID of the set-expected-types pass.  See AscriptionVisitor. */
    public static final ID SET_EXPECTED_TYPES = new ID("set-expected-types");

    /** ID of the exception-check pass.  See ExceptionChecker. */
    public static final ID EXC_CHECK = new ID("exc-check");

    /** ID of the constant-fold pass.  See ConstantFolder. */
    public static final ID FOLD = new ID("fold");

    /** ID of the initialization-check pass.  See InitChecker. */
    public static final ID INIT_CHECK = new ID("init-check");

    /** ID of the constructor-check pass.  See ConstructorChecker. */
    public static final ID CONSTRUCTOR_CHECK = new ID("constructor-check");

    /** ID of the forward-reference-check pass.  See FwdReferenceChecker. */
    public static final ID FWD_REF_CHECK = new ID("fwd-reference-check");

    /** ID of the reachability-check pass.  See ReachChecker. */
    public static final ID REACH_CHECK = new ID("reach-check");

    /** ID of the exit-check pass.  See ExitChecker. */
    public static final ID EXIT_CHECK = new ID("exit-check");

    /** ID of the AST dumper pass.  See DumpAST. */
    public static final ID DUMP = new ID("dump");

    /** ID of the barrier before output. */
    public static final ID PRE_OUTPUT_ALL = new ID("pre-output-barrier");

    /** ID of the class serialzation pass.  See ClassSerializer. */
    public static final ID SERIALIZE = new ID("serialization");

    /** ID of the output pass.  See OutputPass. */
    public static final ID OUTPUT = new ID("output");

    /** ID of the first barrier pass. */
    public static final ID FIRST_BARRIER = BUILD_TYPES_ALL;
}
