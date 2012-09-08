class ForwardRef2 extends A.D { } // legal

class C extends B {
	// B must be clean so that we can search for D in A
	D d;
}

class B extends A {
}

class A {
	static class D { }
}

