public class BadFinalInit8 {
    final int i = 3;
    BadFinalInit8() {
	i = 4; // bad: more than one assignment to i.
    }
}
