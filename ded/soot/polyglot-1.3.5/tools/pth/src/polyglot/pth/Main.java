package polyglot.pth;

import java.io.PrintStream;
import java.util.*;

/**
 * 
 */
public class Main {
    public static void main(String[] args) {
        new Main().start(args);
    }
    
    public static Options options;
     
    public void start(String[] args) {
        options = new Options();
        try {
            options.parseCommandLine(args);
        }
        catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
            
        if (options.inputFilenames.isEmpty()) {
            System.err.println("Need at least one script file.");
            System.exit(1);
        }
        
        OutputController outCtrl = createOutputController(options);
        
        for (Iterator iter = options.inputFilenames.iterator(); iter.hasNext(); ) {
            String filename = (String)iter.next();
            ScriptTestSuite t = new ScriptTestSuite(filename);
            t.setOutputController(outCtrl);
            if (options.showResultsOnly) {
                outCtrl.displayTestSuiteResults(t.getName(), t);
            }
            else {          
                t.run();
            }
        }
    }
    
    protected OutputController createOutputController(Options options) {
        switch (options.verbosity) {
            // More output controllers should be written, for varying degrees
            // of detail.
            case 0:
                return new SilentOutputController(System.out);
            case 1:
                return new QuietOutputController(System.out, false, true, false, false, false);
            case 2:
                return new QuietOutputController(System.out, false, true, false, false, true);
            case 3:
                return new QuietOutputController(System.out, true, true, false, false, true);
            case 8:
                return new VerboseOutputController(System.out, false);
            case 9:
                return new VerboseOutputController(System.out, true);

            default:
                return new StdOutputController(System.out);                
        }
    }    
    
}