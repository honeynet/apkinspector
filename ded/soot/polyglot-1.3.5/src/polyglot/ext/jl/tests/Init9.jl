
class Init9 {
    private void foo() {
        int size;
	boolean b = true;

        if (b && (size = 7) > 0) {
              while (b) {
		  foo();
		  //iterator.next();
              }
        }
    }
}
