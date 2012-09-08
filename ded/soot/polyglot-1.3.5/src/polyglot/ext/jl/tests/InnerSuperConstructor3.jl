class A {
    class B {}
}

class C extends A {
    static class D extends A.B {
	D() {
	    (new A()).super(); // OK: although D has no enclosing 
	    // instance of A or a subtype of A, 
	    // an explicit enclosing instance is provided.
	}
    }
}
