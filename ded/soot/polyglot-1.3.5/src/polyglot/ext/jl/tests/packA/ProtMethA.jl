package packA;

public class ProtMethA {
    protected void m(Object x) { System.out.println("ProtMethA"); }
}

class ProtMethAA {
    public static final void main(String[] args) {
	ProtMethA a = new packB.ProtMethB();
	a.m(null); // OK

	packB.ProtMethB b = (packB.ProtMethB)a;
	b.m(null); // BAD: the method ProtMethB.m(Object) is not 
                   // accessible from this class.
                   // ProtMethA.m(Object) cannot be invoked as
                   // it is overridden by ProtMethB.m(Object)
    }
}
