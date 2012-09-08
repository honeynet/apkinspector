class A {
    class B extends A {}
}

class AA extends A {
    class BB extends B {
	BB() {
	    super(); // BAD: since BB is a subtype of A, the
                     // supertype's enclosing instance of A,
	             // is the instance of BB, which is
	             // equivalent to "this.super()", which 
	             // is an illegal use of "this".
	             // It should be explicitly written 
	             // "(AA.this).super()"
	}
    }
}
