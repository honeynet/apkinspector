public class BadFinalInit7 {
    final int i;
    BadFinalInit7() {
	i = 3;
    }

    void foo() {
	i = 4; // bad: assignment to final field outside of constructor
    }
}
