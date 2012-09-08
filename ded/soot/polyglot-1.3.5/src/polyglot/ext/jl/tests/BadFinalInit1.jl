public class BadFinalInit1 {
    void foo() {
	final int i;
	int j = i + 1; // bad: no initialization of final variable
    }
}
