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
public class SilentOutputController extends OutputController{
    public SilentOutputController(PrintStream out) {
        super(out);
    }
     
    protected void startScriptTestSuite(ScriptTestSuite sts) { }
    protected void startSourceFileTest(SourceFileTest sft) { }
    protected void finishScriptTestSuite(ScriptTestSuite sts) { }
    protected void finishSourceFileTest(SourceFileTest sft, ErrorQueue eq) { }
    public void displayTestSuiteResults(String suiteName, TestSuite ts) { }
    public void displayTestResults(TestResult tr, String testName) { }
}
