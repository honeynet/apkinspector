/*
 * Author : Stephen Chong
 * Created: Feb 6, 2004
 */
package polyglot.pth;

import java.io.File;
import java.util.Date;
import java.util.Map;

/**
 * 
 */
public final class TestSuiteResult extends TestResult {
    private static final String RESULT_FILE_SUFFIX = ".results";
    private static final String RESULT_FILE_PREFIX = "";
    /**
     * The name of the results file is the name of script file,
     * bracketed with RESULTS_FILE_PREFIX and RESULT_FILE_SUFFIX.
     */
    protected static String getResultsFileName(File script) {
        String parent = "";
        if (script.getParent() != null) {
            parent = script.getParent() + File.separator;
        }
        return parent + RESULT_FILE_PREFIX + script.getName() +
                        RESULT_FILE_SUFFIX;
    }
    public final Map testResults;
    public TestSuiteResult(Test t, Date dateTestRun, Map testResults, Date dateLastSuccess) {
        super(t, dateTestRun, dateLastSuccess);
        this.testResults = testResults;
    }    
}
