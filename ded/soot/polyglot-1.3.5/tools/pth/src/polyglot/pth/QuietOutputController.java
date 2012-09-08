/*
 * Author : Stephen Chong
 * Created: Feb 5, 2004
 */
package polyglot.pth;

import java.io.PrintStream;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.regex.Pattern;

import polyglot.util.ErrorQueue;

/**
 * 
 */
public class QuietOutputController extends StdOutputController{
    private final boolean showStartScript;
    private final boolean showFinishScript;
    private final boolean showScriptProgress;
    private final boolean showStartFile;
    private final boolean showFinishFile;
    private int count = 0;
    
    private static final char PROGRESS_PASS_CHAR = '.';
    private static final char PROGRESS_FAIL_CHAR = '!';
    private static final int PROGRESS_LIMIT = 50;
    
    public QuietOutputController(PrintStream out, 
                                 boolean showStartScript,
                                 boolean showFinishScript,
                                 boolean showStartFile,
                                 boolean showFinishFile,
                                 boolean showScriptProgress) {
        super(out);
        this.showStartScript = showStartScript;
        this.showFinishScript = showFinishScript;
        this.showScriptProgress = showScriptProgress;
        this.showStartFile = showStartFile;
        this.showFinishFile = showFinishFile;
    }
     
    protected void finishScriptTestSuite(ScriptTestSuite sts) {
        if (showScriptProgress) {
            out.println();
        }
        if (showFinishScript) {
            super.finishScriptTestSuite(sts);
        }
    }

    protected void finishSourceFileTest(SourceFileTest sft, ErrorQueue eq) {
        if (showFinishFile) {
            super.finishSourceFileTest(sft, eq);
        }
        else if (showScriptProgress) {
            out.print(sft.success() ? PROGRESS_PASS_CHAR : PROGRESS_FAIL_CHAR);
            if (count++ >= PROGRESS_LIMIT) {
                out.println();
                count = 0;                
            }
        }
    }

    protected void startScriptTestSuite(ScriptTestSuite sts) {
        if (showStartScript) {
            super.startScriptTestSuite(sts);
        }
    }

    protected void startSourceFileTest(SourceFileTest sft) {
        if (showStartFile) {
            super.startSourceFileTest(sft);
        }
    }

}
