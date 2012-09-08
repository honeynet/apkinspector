/* The Polyglot compiler as at Sept 29th 2003 fails to compile this code.
 * In particular, it cannot find an appropriate method foo.
 *
 */
class C {
    void m() {
	Object o = new D() {
            public void bar()
            {		
                foo(5); // cannot find foo.
            }
		
	 };
    }
}

class D {
    void foo(int i) { }
}
