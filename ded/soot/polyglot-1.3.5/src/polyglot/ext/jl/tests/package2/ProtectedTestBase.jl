package package2;
import package1.ProtectedTest;

public class ProtectedTestBase {
    public static void main(String args[]) {
        return;
    }

    protected void prot() { return; }

    protected Object clone() {
        return new Object();
    }
}

class FinalizeContract {
    void testbar(ProtectedTest b)  {
        b.prot();
    }

    void testob(Object o) throws Throwable {
        o.clone();
    }
}
