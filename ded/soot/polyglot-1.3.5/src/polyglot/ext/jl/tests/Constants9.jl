class C {
    public static final int X = 7;
}
class D {
    private static final int Y = C.X;
    void m() {
	int i = 0;
	switch (i) {
	case Y: break;
	default: 
	}
    }
}
