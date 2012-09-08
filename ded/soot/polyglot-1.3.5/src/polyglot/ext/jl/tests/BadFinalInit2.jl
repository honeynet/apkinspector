public class BadFinalInit2 {
    void foo() {
	final int i;
	i = 1;
	i = 2; // bad: i might have already been assigned to.
    }
}
