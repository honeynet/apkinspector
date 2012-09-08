// Test that a class inherited through a diamond is unambiguous
// Contrast with BadReferences.jl
class DiamondInh {
    public void main(String[] args) {
        Object o = new C.Inner();
    }
}


class C implements I1, I2 { }

interface I0 { 
    public static class Inner { }
}
interface I1 extends I0 { }
interface I2 extends I0 { }
