class BadReferences2 {
    public void main(String[] args) {
        int x = C.x;
    }
}


class C implements I1, I2 { }

interface I1 {
    public static int x = 1;
}
interface I2 {
    public static int x = 2;
}
