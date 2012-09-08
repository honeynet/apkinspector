package package1;
import package2.ProtectedTestBase;

public class ProtectedTest extends ProtectedTestBase {
  public void pub() { this.prot(); }
  protected void prot() { ProtectedTestBase foo = new ProtectedTestBase();
                          foo.prot(); }
}
