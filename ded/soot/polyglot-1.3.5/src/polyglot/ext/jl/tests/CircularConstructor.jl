// I believe that this test case should be illegal, based on the JLS 2nd ed,
// Section 8.8.5: "It is a compile-time error for a constructor to directly
// or indirectly invoke itself through a series of one or more explicit
// constructor invocations involving <this>."
//
// javac does not catch this, although it does catch a constructor that
// calls itself, e.g.
//    CircularConstructor(String s) { this(s); }
//
// Correction: javac 1.4.1_01, at least, does catch this
//
class CircularConstructor {
    CircularConstructor(String s) {
	this(5);
    }
    CircularConstructor(int i) {
	this("s");
    }
    public static void main(String[] args) {
	new CircularConstructor("df");
    }
}
