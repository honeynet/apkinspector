class BadFinalInit13 {
    static final int x;
    static {
	BadFinalInit13.x = 4; // BAD (javac doesn't like it, although it probably should...)
    }
    
}

