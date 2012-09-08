// Test that a field inherited through a diamond is unambiguous
// Contrast with BadReferences2.jl
class DiamondInh2 {
    public void main(String[] args) {
        int x = C.x;
    }
}


class C implements I1, I2 { }

interface I0 { 
    public static final int x = 0;
}
interface I1 extends I0 { }
interface I2 extends I0 { }
