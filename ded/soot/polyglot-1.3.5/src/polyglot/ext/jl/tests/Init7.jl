class Init7 {
    private void foo() {
	boolean match = false;
	int i = 0;
	while ( i < 6) {
	    match = false;
	    while (match && i>9) {
		if (i>0) {
		    match = true;
		}
	    }
	}
    }
}
