// Test that a class inherited from an interface is found.
class InheritClass {
    public void main(String[] args) {
        Object o = new C.Inner();
    }
}


class C implements I0 { }

interface I0 { 
    public static class Inner { }
}
