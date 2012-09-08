import java.io.File;
import java.util.*;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.MatchingTask;
import org.apache.tools.ant.types.Commandline;
import org.apache.tools.ant.types.Path;
import org.apache.tools.ant.types.Reference;
import org.apache.tools.ant.util.GlobPatternMapper;
import org.apache.tools.ant.util.SourceFileScanner;

import polyglot.main.Main.TerminationException;

/**
 * An ANT <code>Task</code> to facilitate the building of
 * of code written in language extensions within the ANT framework. This task
 * can be used similarly way to the <code>JavaC</code> task. 
 * 
 * <p> This class can
 * optionally be subclassed by extensions (to provide compilation for 
 * a particular language). In that case, subclasses should override the
 * init method to set the source file extension 
 * ({@link PolyglotAntTask#srcExt srcExt}) and extension name
 * or class ({@link PolyglotAntTask#extensionName extensionName} or 
 * {@link PolyglotAntTask#extensionClass extensionClass}) appropriately, and 
 * override the setter methods for these fields to prevent the user from modifying
 * these fields.
 * 
 * <b>This class has not been fully tested.</b>
 * 
 * @see org.apache.tools.ant.Task
 */
public class PolyglotAntTask extends MatchingTask {
    /**
     * Name of the extension, like the <code>-ext</code> command line option.
     */
    protected String extensionName;
    /**
     * Class of the extension, like the <code>-extclass</code> command line 
     * option.
     */
    protected Class extensionClass;
    /**
     * Destination directory, like the <code>-d</code> command line option.
     */
    protected File destDir;
    /**
     * The path for source compilation.
     */
    protected Path src;
    
    /**
     * Bootclasspath, like the <code>-bootclasspath</code> command line option.
     */
    protected Path bootclasspath;
    /**
     * The file extension for sourcefiles, like the <code>-sx</code>
     * command line option.
     */
    protected String srcExt;
    /**
     * The file extensions for output files, like the <code>-ox</code>
     * command line option.
     */
    protected String outputExt;
    /**
     * A file containing additional command line options to parse, like
     * the <code>@&lt;file$gt;</code> command line option.
     */
    protected File optionsFile;
    
    /**
     * The post compiler to invoke, like the <code>-post</code>
     * command line option.
     */
    protected String post;
    /**
     * Indiciates if the Polyglot compiler should produce serialized type
     * information in the output files, like the <code>-noserial</code>
     * command line option.
     */
    protected boolean noSerial;
    /**
     * Indiciates if the Polyglot compiler should delete the output files
     * after compilation, like the <code>-nooutput</code> command line option.
     */
    protected boolean noOutput;
    /**
     * Indiciates if the Polyglot compiler should use fully qualified
     * class names, like the <code>-fqcn</code>
     * command line option.
     */
    protected boolean fqcn;
    /**
     * Indiciates if the Polyglot compiler should use only compile to the 
     * output files, like the <code>-c</code>
     * command line option.
     */
    protected boolean onlyToJava;
    
    /**
     * A <code>List</code> of {@link org.apache.tools.ant.types.Commandline.Arguments 
     * Commandline.Arguments}, to allow the user to specify command line 
     * options that we do not deal with explicitly.
     */
    protected List additionalArgs; 
    
    /**
     * The classpath to be used for compilation, like the <code>-classpath</code>
     * command line option.
     */
    protected Path compileClasspath;
    /**
     * The path for finding source files, like the <code>-sourcepath</code>
     * command line option.
     */
    protected Path compileSourcepath;

    public PolyglotAntTask() { }
    
    public void init() { 
        super.init();
        extensionName = null;
        extensionClass = null;
        destDir = null;
        src = null;
        bootclasspath = null;
        srcExt = "java";
        outputExt = "java";
        optionsFile = null;
        post = null;
        noSerial = false;
        noOutput = false;
        fqcn = false;
        onlyToJava = false;
        additionalArgs = null;
        compileClasspath = null;
        compileSourcepath = null;
    }
    
    public void execute() throws BuildException {
        dumpDetails();
        checkParameters();
        // scan source directories and dest directory to build up
        File[] compileFiles = getCompileFiles();
        
        if (compileFiles.length == 0) {
            // nothing to do. no files to compile
            log("No files to compile", 
                Project.MSG_INFO);
            return;
        }
        
        // build up the argument list
        String[] args = constructArgList(compileFiles);
        
        log("Invoking polyglot with the following arguments: " + 
            "(enclosing single quotes are not part of the arguments):", 
            Project.MSG_VERBOSE);
        for (int i = 0; i < args.length; i++) {
            log("  '" + args[i] + "'", Project.MSG_VERBOSE);                
        }
        // invoke the polyglot compiler.
        compile(args);
    }
    
    protected File[] getCompileFiles() {
        File[] compileFiles = new File[0];
        String[] srcList = src.list();
        for (int i = 0; i < srcList.length; i++) {
            File srcDir = getProject().resolveFile(srcList[i]);
            if (!srcDir.exists()) {
                throw new BuildException("srcdir \""
                                         + srcDir.getPath()
                                         + "\" does not exist!", getLocation());
            }

            DirectoryScanner ds = this.getDirectoryScanner(srcDir);
            String[] files = ds.getIncludedFiles();

            compileFiles = scanDir(compileFiles, srcDir, destDir != null ? destDir : srcDir, files);
        }
        return compileFiles;
    }
    
    /**
     * Scans the directory looking for source files to be compiled.
     * The results are returned in the class variable compileList
     */
    protected File[] scanDir(File[] compileFiles, File srcDir, File destDir, String[] files) {
        GlobPatternMapper m = new GlobPatternMapper();
        m.setFrom("*." + srcExt);
        m.setTo("*." + destFileExt());
        SourceFileScanner sfs = new SourceFileScanner(this);
        File[] newFiles = sfs.restrictAsFiles(files, srcDir, destDir, m);

        if (newFiles.length > 0) {
            File[] newCompileList = new File[compileFiles.length + 
                newFiles.length];
            System.arraycopy(compileFiles, 0, newCompileList, 0,
                compileFiles.length);
            System.arraycopy(newFiles, 0, newCompileList,
                compileFiles.length, newFiles.length);
            compileFiles = newCompileList;
        }
        return compileFiles;
    }

    /**
     * The file extension of the destination files. Used by the global pattern
     * matcher to determine which files need to be compiled.
     */
    protected String destFileExt() {
        return onlyToJava ? outputExt : "class";
    }
    
    /**
     * Check to make sure the paramters are consistent with invoking the 
     * Polyglot compiler. 
     * @throws BuildException if the parameters are inconsistent.
     */
    protected void checkParameters() throws BuildException {
        // at most one of extenstionClass and extensionName is set.
        if (extensionClass != null && extensionName != null) {
            throw new BuildException("At most one of extclass and " + 
                                     "extname can be set.", getLocation());
        }
        
        if (src == null) {
            throw new BuildException("srcdir attribute must be set!",
                                     getLocation());
        }
        if (src.size() == 0) {
            throw new BuildException("srcdir attribute must be set!",
                                     getLocation());
        }

        if (destDir != null && !destDir.isDirectory()) {
            throw new BuildException("destination directory \"" 
                                     + destDir 
                                     + "\" does not exist "
                                     + "or is not a directory", getLocation());
        }
    }

    /**
     * Construct the argument list that will be given to the Polyglot
     * compiler.
     * @param files the files to compile
     */
    protected String[] constructArgList(File[] files) {
        List argList = new ArrayList();
        
        // set up command line args
        if (optionsFile != null) {
            argList.add("@" + optionsFile.getPath());
        }
        if (extensionName != null) {
            argList.add("-ext");
            argList.add(extensionName);
        }
        if (extensionClass != null) {
            argList.add("-extclass");
            argList.add(extensionClass.getName());
        }
        if (noSerial) {
            argList.add("-noserial");
        }
        if (noOutput) {
            argList.add("-nooutput");
        }
        if (fqcn) {
            argList.add("-fqcn");
        }
        if (onlyToJava) {
            argList.add("-c");
        }
        if (post != null) {
            argList.add("-post");
            argList.add(post);
        }
        if (srcExt != null) {
            argList.add("-sx");
            argList.add(srcExt);
        }
        if (outputExt != null) {
            argList.add("-ox");
            argList.add(outputExt);
        }
        if (destDir != null) {
            argList.add("-d");
            argList.add(destDir.getPath());
        }
        if (compileSourcepath != null) {
            argList.add("-sourcepath");
            argList.add(compileSourcepath.toString());
        }
        else if (src != null) {
            argList.add("-sourcepath");
            argList.add(src.toString());
        }
        if (bootclasspath != null) {
            argList.add("-bootclasspath");
            argList.add(bootclasspath.toString());
        }
        if (compileClasspath != null) {
            argList.add("-classpath");
            argList.add(compileClasspath.toString());
        }
        if (additionalArgs != null) {
            for (Iterator i = additionalArgs.iterator(); i.hasNext(); ) {
                Commandline.Argument arg = (Commandline.Argument)i.next();
                String[] parts = arg.getParts();
                for (int j = 0; j < parts.length; j++) {
                    argList.add(parts[j]);
                }
            }
        }
        
        // add the files to compile.
        int argListSize = argList.size();
        String[] args = new String[argListSize + files.length];
        argList.toArray(args);
        
        for (int i = 0; i < files.length; ++i) {
            args[argListSize + i] = files[i].getPath();
        }
        
        return args;
    }
    
    /**
     * Invoke the Polyglot compiler, with the given command line arguments.
     */
    protected void compile(String[] args) {
        try {
            new polyglot.main.Main().start(args);
        }
        catch (TerminationException e) {
            throw new BuildException(e.getMessage(), e);
        }
    }
     
    /**
     * Dump the settings of the parameters, for debugging purposes.
     */  
    protected void dumpDetails() {
        log("extensionName = " + extensionName, Project.MSG_DEBUG);
        log("extensionClass = " + extensionClass, Project.MSG_DEBUG);
        log("destdir = " + destDir, Project.MSG_DEBUG);
        log("src = " + src, Project.MSG_DEBUG);
        log("bootclasspath = " + bootclasspath, Project.MSG_DEBUG);
        log("srcExt = " + srcExt, Project.MSG_DEBUG);
        log("outputExt = " + outputExt, Project.MSG_DEBUG);
        log("optionsFile = " + optionsFile, Project.MSG_DEBUG);
        log("post = " + post, Project.MSG_DEBUG);
        log("noSerial = " + noSerial, Project.MSG_DEBUG);
        log("noOutput = " + noOutput, Project.MSG_DEBUG);
        log("fqcn = " + fqcn, Project.MSG_DEBUG);
        log("onlyToJava = " + onlyToJava, Project.MSG_DEBUG);
        log("additionalArgs = ", Project.MSG_DEBUG);
        if (additionalArgs != null) {
            for (Iterator i = additionalArgs.iterator(); i.hasNext(); ) {
                StringBuffer sb = new StringBuffer();
                Commandline.Argument arg = (Commandline.Argument)i.next();
                String[] parts = arg.getParts();
                sb.append("   '");
                for (int j = 0; j < parts.length; j++) {
                    sb.append(parts[j]);
                }
                sb.append("'");
                log(sb.toString(), Project.MSG_DEBUG);
            }            
        }
        log("compileClasspath = " + compileClasspath, Project.MSG_DEBUG);
        log("compileSourcepath = " + compileSourcepath, Project.MSG_DEBUG);
        log("fileset = " + Arrays.asList(this.getCompileFiles()), Project.MSG_DEBUG);

    }
    /**
     * Adds a command-line argument.
     */
    public Commandline.Argument createArg() {
        Commandline.Argument argument = new Commandline.Argument();
        if (additionalArgs == null) {
            additionalArgs = new LinkedList();
        }
        additionalArgs.add(argument);
        return argument;
    }

    public void setExtname(String extensionName) {
        this.extensionName = extensionName;
    }

    public void setExtclass(Class extensionClass) {
        this.extensionClass = extensionClass;
    }

    public void setDestdir(File destdir) {
        this.destDir = destdir;
    }

    public Path createSrc() {
        if (src == null) {
            src = new Path(getProject());
        }
        return src.createPath();
    }
    public void setSrcdir(Path srcDir) {
        if (src == null) {
            src = srcDir;
        } else {
            src.append(srcDir);
        }
    }

    public void setBootclasspath(Path bootclasspath) {
        if (this.bootclasspath == null) {
            this.bootclasspath = bootclasspath;
        } else {
            this.bootclasspath.append(bootclasspath);
        }
    }
    
    public Path createBootclasspath() {
        if (bootclasspath == null) {
            bootclasspath = new Path(getProject());
        }
        return bootclasspath.createPath();
    }
    
    /**
     * Adds a reference to a classpath defined elsewhere.
     */
    public void setBootClasspathRef(Reference r) {
        createBootclasspath().setRefid(r);
    }

    public void setSx(String srcExt) {
        this.srcExt = srcExt;
    }

    public void setOx(String outExt) {
        this.outputExt = outExt;
    }
    public void setOptions(File optionsFile) {
        this.optionsFile = optionsFile;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setNoserial(boolean noSerial) {
        this.noSerial = noSerial;
    }

    public void setNooutput(boolean noOutput) {
        this.noOutput = noOutput;
    }

    public void setFqcn(boolean fqcn) {
        this.fqcn = fqcn;
    }

    public void setOnlytojava(boolean onlyToJava) {
        this.onlyToJava = onlyToJava;
    }
    
    public void setClasspath(Path classpath) {
        if (compileClasspath == null) {
            compileClasspath = classpath;
        } else {
            compileClasspath.append(classpath) ; 
        }
    }

    /**
     * Adds a path to the classpath.
     */
    public Path createClasspath() {
        if (compileClasspath == null) {
            compileClasspath = new Path(getProject());
        }
        return compileClasspath.createPath();
    }

    /**
     * Adds a reference to a classpath defined elsewhere.
     */
    public void setClasspathRef(Reference r) {
        createClasspath().setRefid(r);
    }

    /**
     * Set the sourcepath to be used for this compilation.
     */
    public void setSourcepath(Path sourcepath) {
        if (compileSourcepath == null) {
            compileSourcepath = sourcepath;
        } else {
            compileSourcepath.append(sourcepath);
        }
    }

    /** Gets the sourcepath to be used for this compilation. */
    public Path getSourcepath() {
        return compileSourcepath;
    }

    /**
     * Adds a path to sourcepath.
     */
    public Path createSourcepath() {
        if (compileSourcepath == null) {
            compileSourcepath = new Path(getProject());
        }
        return compileSourcepath.createPath();
    }

    /**
     * Adds a reference to a source path defined elsewhere.
     */
    public void setSourcepathRef(Reference r) {
        createSourcepath().setRefid(r);
    }

}
