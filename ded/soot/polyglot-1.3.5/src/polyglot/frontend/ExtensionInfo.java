package polyglot.frontend;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.types.reflect.*;
import polyglot.util.*;
import java.io.*;
import java.util.*;
import polyglot.main.Options;

/**
 * <code>ExtensionInfo</code> is the main interface for defining language
 * extensions.  The frontend will load the <code>ExtensionInfo</code>
 * specified on the command-line.  It defines the type system, AST node
 * factory, parser, and other parameters of a language extension.
 */
public interface ExtensionInfo {
    /** The name of the compiler for usage messages */
    String compilerName();

    /** Report the version of the extension. */
    polyglot.main.Version version();

    /** 
     * Return an Options object, which will be given the command line to parse.
     */    
    Options getOptions();

    /**
     * Return a Stats object to accumulate and report statistics.
     */ 
    Stats getStats();

    /**
     * Initialize the extension with a particular compiler.  This must
     * be called after the compiler is initialized, but before the compiler
     * starts work.
     */
    void initCompiler(polyglot.frontend.Compiler compiler);

    Compiler compiler();

    /** The extensions that source files are expected to have.
     * Defaults to the array defaultFileExtensions. */
    String[] fileExtensions();

    /** The default extensions that source files are expected to have.
     * Defaults to an array containing defaultFileExtension */
    String[] defaultFileExtensions();

    /** The default extension that source files are expected to have. */
    String defaultFileExtension();

    /** Produce a type system for this language extension. */
    TypeSystem typeSystem();

    /** Produce a node factory for this language extension. */
    NodeFactory nodeFactory();

    /** Produce a source factory for this language extension. */
    SourceLoader sourceLoader();

    /**
     * Adds a dependency from the current job to the given Source. 
     */
    void addDependencyToCurrentJob(Source s);
    
    /** 
     * Produce a job for the given source. A new job will be created if
     * needed. If the <code>Source source</code> has already been processed,
     * and its job discarded to release resources, then <code>null</code> 
     * will be returned.
     */
    SourceJob addJob(Source source);

    /** 
     * Produce a job for a given source using the given AST.
     * A new job will be created if
     * needed. If the <code>Source source</code> has already been processed,
     * and its job discarded to release resources, then <code>null</code> 
     * will be returned.
     */
    SourceJob addJob(Source source, Node ast);

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
    Job spawnJob(Context c, Node ast, Job outerJob, Pass.ID begin, Pass.ID end);    

    /** Run all jobs to completion. */
    boolean runToCompletion();

    /** Run the given job up to a given pass. */
    boolean runToPass(Job job, Pass.ID goal) throws CyclicDependencyException;

    /** Run the given job to completion. */
    boolean runAllPasses(Job job);

    /** Read a source file and compile up to the current job's barrier. */
    boolean readSource(FileSource source);

    /**
     * Produce a target factory for this language extension.  The target
     * factory is responsible for naming and opening output files given a
     * package name and a class or source file name.
     */
    TargetFactory targetFactory();

    /** Get a parser for this language extension. */
    Parser parser(Reader reader, FileSource source, ErrorQueue eq);

    /** Get the list of passes for a given source job. */
    List passes(Job job);

    /** Get the sublist of passes for a given job. */
    List passes(Job job, Pass.ID begin, Pass.ID end);

    /** Add a pass before an existing pass. */
    void beforePass(List passes, Pass.ID oldPass, Pass newPass);

    /** Add a list of passes before an existing pass. */
    void beforePass(List passes, Pass.ID oldPass, List newPasses);

    /** Add a pass after an existing pass. */
    void afterPass(List passes, Pass.ID oldPass, Pass newPass);

    /** Add a list of passes after an existing pass. */
    void afterPass(List passes, Pass.ID oldPass, List newPasses);

    /** Replace an existing pass with a new pass. */
    void replacePass(List passes, Pass.ID oldPass, Pass newPass);

    /** Replace an existing pass with a list of new passes. */
    void replacePass(List passes, Pass.ID oldPass, List newPasses);

    /** Remove a pass.  The removed pass cannot be a barrier. */
    void removePass(List passes, Pass.ID oldPass);

    /** Create class file */ 
    ClassFile createClassFile(File classFileSource, byte[] code);
}
