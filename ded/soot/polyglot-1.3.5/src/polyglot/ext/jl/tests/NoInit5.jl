// This tests whether we detect variables which are used before they get
// initialized.

public class NoInit5 {
  void foo(int i) {}

  void m5a() {
    int i;
    boolean b = true;
    if (b)
      i = 4;
    else 
      i = 7;
    foo(i); // OK
  }

  void m5b() {
    int i;
    if (true)
      i = 1;
    i = 0;
  }

  void m5() {
    int i;
    boolean b = true;
    if (b)
      i = 7;
    foo(i); // BAD (m5)
  }
}

