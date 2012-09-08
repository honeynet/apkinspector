public class FinalInit3 {
    void foo() { // may not initialize final local i, but OK
	final int i;
	if (true) {
	    i = 1;
	}
    }
}
