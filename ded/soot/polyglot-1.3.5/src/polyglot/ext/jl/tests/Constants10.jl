class F {
    private static final int X = E.X;
    void m() {
	int i = 0;
	switch (i) {
	case X: break;
	default: 
	}
    }
}

class E {
    public static final int X = D.X;
}
class D {
    public static final int X = C.X;
}
class C {
    public static final int X = 7;
}
