class A {
    class B {}
}

class D extends A.B {
    D() {
	super(); // BAD! D needs to have an enclosing instance of A, or a subtype of A.
    }
}

