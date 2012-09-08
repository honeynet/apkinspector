package polyglot.util;


/**
 * A <code>StdErrorQueue</code> handles outputing error messages.
 */
public abstract class AbstractErrorQueue implements ErrorQueue
{
    protected boolean flushed;
    protected int errorCount;
    protected final int limit;
    protected final String name;
    
    public AbstractErrorQueue(int limit, String name) {
	this.errorCount = 0;
	this.limit = limit;
	this.name = name;
        this.flushed = true;
    }

    public final void enqueue(int type, String message) {
	enqueue(type, message, null);
    }

    public final void enqueue(int type, String message, Position position) {
	enqueue(new ErrorInfo(type, message, position));
    }

    public final void enqueue(ErrorInfo e) {
	if (e.getErrorKind() != ErrorInfo.WARNING) {
	    errorCount++;
	}

	flushed = false;

        displayError(e);

	if (errorCount >= limit) {
	    tooManyErrors(e);
	    flush();
	    throw new ErrorLimitError();
	}
    }

    protected abstract void displayError(ErrorInfo error);
    
    /**
     * This method is called when we have had too many errors. This method
     * give subclasses the opportunity to output appropriate messages, or
     * tidy up.
     * 
     * @param lastError the last error that pushed us over the limit
     */
    protected void tooManyErrors(ErrorInfo lastError) {
    }
    
    /**
     * This method is called to flush the error queue. Subclasses may want to
     * print summary information in this method.
     */
    public void flush() {
        flushed = true;
    }

    public final boolean hasErrors() {
      return errorCount > 0;
    }

    public final int errorCount() {
        return errorCount;
    }
}
