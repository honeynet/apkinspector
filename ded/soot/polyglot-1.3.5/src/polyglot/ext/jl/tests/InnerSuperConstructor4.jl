class A {
    class B {}
}

class D extends A.B {
    D() {
	(new A()).super(); // OK: although D has no enclosing 
                           // instance of A or a subtype of A, 
                           // an explicit enclosing instance is provided.
    }
}

