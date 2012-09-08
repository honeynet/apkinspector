/*
 * Author : Stephen Chong
 * Created: Nov 21, 2003
 */
package polyglot.pth;

import java.util.Date;

/**
 * 
 */
public abstract class AbstractTest implements Test {
    protected String name;
    protected String description;
    protected boolean success = false;
    protected boolean hasRun = false;
    protected String failureMessage = null;
    protected TestResult testResult;
    
    protected OutputController output;

    public AbstractTest(String name) {
        this.name = name;
    }
    
    public void setOutputController(OutputController output) {
        this.output = output;
    }
    
    public final boolean run() {
        preRun();
        this.success = this.runTest();
        
        this.hasRun = true;
        Date lastSuccess = null;
        if (this.getTestResult() != null) {
            lastSuccess = this.getTestResult().dateLastSuccess;
        }
        this.setTestResult(this.createTestResult(lastSuccess));
        postRun();
        return success(); 
    }
    
    
    protected abstract boolean runTest();
    
    protected void preRun() {
        output.startTest(this);
    }
    protected void postRun() {
        output.finishTest(this, null);
    }
    
    protected TestResult createTestResult(Date lastSuccess) {
        Date lastRun = new Date();
        if (this.success()) {
            lastSuccess = lastRun;
        }
        return new TestResult(this, lastRun, lastSuccess);
    }
    
    public boolean success() {
        return success;
    }
    
    public String getFailureMessage() {
        return failureMessage;
    }

    public void setFailureMessage(String failureMessage) {
        this.failureMessage= failureMessage;
    }
    
    public String getDescription() {
        return description;
    }
    public String getName() {
        return name;
    }
    public void setDescription(String string) {
        description = string;
    }
    public void setName(String string) {
        name = string;
    }    
    public TestResult getTestResult() {
        return testResult;
    }
    public void setTestResult(TestResult tr) {
        testResult = tr;
    }
}
