package packA;
import packB.ProtectedAccess2;

public class ProtectedAccess1 {
  protected void x() {}
}

class Quux {
    void m(ProtectedAccess1 f, ProtectedAccess2 b) {
      f.x(); // OK
      b.x(); // Not OK!
    }
}
