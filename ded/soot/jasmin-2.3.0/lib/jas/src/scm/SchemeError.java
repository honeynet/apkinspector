package scm;

public class SchemeError extends RuntimeException {
    public SchemeError() {
	super();
    }

    public SchemeError(String s) {
	super(s);
    }
}
