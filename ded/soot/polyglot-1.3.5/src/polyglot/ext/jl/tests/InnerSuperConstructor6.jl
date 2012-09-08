class A {
    class B {}

    class D extends A.B {
	D() {
	    super(); // OK: D has an enclosing instance of A
	}
    }
}
