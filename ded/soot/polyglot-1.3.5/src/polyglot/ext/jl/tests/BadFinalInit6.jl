public class BadFinalInit6 {
    final int i;
    BadFinalInit6() {
	i = 3;
	i = 4; // bad: more than one assignment to i.
    }
}
