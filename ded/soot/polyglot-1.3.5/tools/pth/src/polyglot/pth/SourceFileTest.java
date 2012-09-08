/*
 * Author : Stephen Chong
 * Created: Nov 21, 2003
 */
package polyglot.pth;

import java.io.File;
import java.util.*;

import polyglot.util.ErrorInfo;
import polyglot.util.SilentErrorQueue;

/**
 * 
 */
public class SourceFileTest extends AbstractTest {
    protected final List sourceFilenames;
    protected String extensionClassname = null;
    protected String[] extraArgs;
    protected final SilentErrorQueue eq;
    protected String destDir;
    
    protected List expectedFailures;
        
    public SourceFileTest(String filename) {
        super(new File(filename).getName());
        this.sourceFilenames = Collections.singletonList(filename);
        this.eq = new SilentErrorQueue(100, this.getName()); 

    }

    public SourceFileTest(List filenames) {
        super(filenames.toString());
        this.sourceFilenames = filenames;
        this.eq = new SilentErrorQueue(100, this.getName()); 
    }

    public SourceFileTest(String[] filenames) {
        this(Arrays.asList(filenames).toString());
    }
    
    public void setExpectedFailures(List expectedFailures) {
        this.expectedFailures = expectedFailures;
    }

    protected boolean runTest() {
        for (Iterator i = sourceFilenames.iterator(); i.hasNext(); ) {
            File sourceFile = new File((String)i.next());
            if (!sourceFile.exists()) {
                setFailureMessage("File not found.");
                return false;
            }
        }
        
        // invoke the compiler on the file.
        try {
            invokePolyglot(getSourceFileNames());
        }
        catch (polyglot.main.Main.TerminationException e) {
            if (e.getMessage() != null) {
                setFailureMessage(e.getMessage());
                return false;
            }
            else {
                if (!eq.hasErrors()) {
                    setFailureMessage("Failed to compile for unknown reasons: " + 
                             e.toString());
                    return false;
                }                    
            }
        }        
        return checkErrorQueue(eq);
    }
    
    protected void postRun() {
        output.finishTest(this, eq);
    }

    protected boolean checkErrorQueue(SilentErrorQueue eq) {
        List errors = new ArrayList(eq.getErrors());
        
        boolean swallowRemainingFailures = false;
        for (Iterator i = expectedFailures.iterator(); i.hasNext(); ) {
            ExpectedFailure f = (ExpectedFailure)i.next();
            if (f instanceof AnyExpectedFailure) {
                swallowRemainingFailures = true;
                continue;
            }
            
            boolean found = false;
            for (Iterator j = errors.iterator(); j.hasNext(); ) {
                ErrorInfo e =(ErrorInfo)j.next();
                if (f.matches(e)) {
                    // this error info has been matched. remove it.
                    found = true;
                    j.remove();
                    break;
                }
            }
            if (!found) {
                setFailureMessage("Expected to see " + f.toString());
                return false;
            }            
        }
        
        // are there any unaccounted for errors?
        if (!errors.isEmpty() && !swallowRemainingFailures) {
            StringBuffer sb = new StringBuffer();
            for (Iterator iter = errors.iterator(); iter.hasNext(); ) {
                ErrorInfo err = (ErrorInfo)iter.next();                        
                sb.append(err.getMessage());
                if (err.getPosition() != null) {
                    sb.append(" (");
                    sb.append(err.getPosition());
                    sb.append(")");
                }
                if (iter.hasNext()) sb.append("; ");
            }
            setFailureMessage(sb.toString());
        }            
        return errors.isEmpty() || swallowRemainingFailures;
    }
    
    protected String[] getSourceFileNames() {
        return (String[])sourceFilenames.toArray(new String[0]);
    }
    
    protected void invokePolyglot(String[] files) 
        throws polyglot.main.Main.TerminationException 
    {
        File tmpdir = new File("pthOutput");

        int i = 1;
        while (tmpdir.exists()) {
            tmpdir = new File("pthOutput." + i);
            i++;
        }

        tmpdir.mkdir();

        setDestDir(tmpdir.getPath());
                
        String[] cmdLine = buildCmdLine(files);
        polyglot.main.Main polyglotMain = new polyglot.main.Main();

        try {
            polyglotMain.start(cmdLine, eq);
        }
        finally {
            if (Main.options.deleteOutputFiles) {
                deleteDir(tmpdir);
            }

            setDestDir(null);
        }
    }

    protected void deleteDir(File dir) {
        File[] list = dir.listFiles();
        for (int i = 0; i < list.length; i++) {
            if (list[i].isDirectory()) {
                deleteDir(list[i]);
            }
            else {
                list[i].delete();
            }
        }
        dir.delete();
    }

    protected String[] buildCmdLine(String[] files) {
        ArrayList args = new ArrayList();
        
        String s;
        String[] sa;
        
        if ((s = getExtensionClassname()) != null) {
            args.add("-extclass");
            args.add(s);
        }
        
        if ((s = getAdditionalClasspath()) != null) {
            args.add("-cp");
            args.add(s);                        
        }

        if ((s = getDestDir()) != null) {
            args.add("-d");
            args.add(s);                        
        }

        if ((s = getSourceDir()) != null) {
            args.add("-sourcepath");
            args.add(s);                        
        }

        if ((s = Main.options.extraArgs) != null) {
            sa = breakString(Main.options.extraArgs);
            for (int i = 0; i < sa.length; i++) {
                args.add(sa[i]);
            }
        }

        if ((sa = getExtraCmdLineArgs()) != null) {
            for (int i = 0; i < sa.length; i++) {
                args.add(sa[i]);
            }
        }
        
        args.addAll(Arrays.asList(files));
        
        return (String[])args.toArray(new String[0]);
    }
        
    protected String getExtensionClassname() {
        return extensionClassname;
    }

    protected void setExtensionClassname(String extClassname) {
        this.extensionClassname = extClassname;
    }
    
    protected String[] getExtraCmdLineArgs() {
        return this.extraArgs;
    }
    
    protected static String[] breakString(String s) {
        StringTokenizer st = new StringTokenizer(s);
        ArrayList l = new ArrayList(st.countTokens());
        while (st.hasMoreTokens()) {
            l.add(st.nextToken());
        }
            
        return (String[])l.toArray(new String[l.size()]);
    }
    protected void setExtraCmdLineArgs(String args) {
        if (args != null) {
            this.extraArgs = breakString(args);
        } 
    }
    protected String getAdditionalClasspath() {
        return Main.options.classpath;
    }
    protected void setDestDir(String dir) {
        this.destDir = dir;
    }
    protected String getDestDir() {
        return destDir;
    }
    protected String getSourceDir() {
        return null;
    }

}
