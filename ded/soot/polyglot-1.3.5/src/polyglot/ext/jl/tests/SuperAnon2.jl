/* The Polyglot compiler as at Sept 29th 2003 fails to compile this code.
 * In particular, it cannot find an appropriate field j.
 *
 */
class C {
    void m() {
	Object o = new D() {
            public void bar()
            {		
                int i = j;
            }
		
	 };
    }
}

class D {
    int j = 5;
}
