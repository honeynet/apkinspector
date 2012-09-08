// This tests whether we detect variables which are used before they get
// initialized.

public class NoInit1 {
  void foo(int i) {}

  void m1() {
    int i;
    foo(i); // BAD (m1)
  } 
}

