public class Anon1 {
  int x;

  static interface I {
    void m();
  }

  static void m() {
    I i = new I() {
      public void m() {
	x = 0;
      }
    };
  }
}

