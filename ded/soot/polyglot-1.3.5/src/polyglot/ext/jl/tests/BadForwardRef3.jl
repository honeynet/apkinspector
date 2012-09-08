class BadForwardRef2 {
    static { i = j + 2; }
    static int i, j;
    static { j = 4; }
}
