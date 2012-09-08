// Polyglot as of Oct 1 03 doesn't compile this. The multiple 
// declarations in the for loop initialization seem to cause 
// problems.
//
class ForDecl {
    void m() {
	for (int i = 0, j = 0; i < 10; i++) {
	    boolean quad = false;
	}
    }
}
