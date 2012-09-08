public class BadFinalInit4 {
    void foo(final int i) {
	if (false) {
	    i = 1; // bad: i might have already been assigned to, as it is a formal
	}
    }
}
