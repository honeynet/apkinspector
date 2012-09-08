class A {
    class B {}
}

class C extends A {
    class D extends A.B {
	D() {
	    super(); // OK: D has an enclosing instance of a subtype of A
	}
    }
}
