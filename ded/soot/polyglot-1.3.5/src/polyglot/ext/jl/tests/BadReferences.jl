class BadReferences {
    public void main(String[] args) {
        Object o = new C.Inner();
    }
}


class C implements I1, I2 { }

interface I1 {
    public static class Inner { }
}
interface I2 {
    public static class Inner { }
}
