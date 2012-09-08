// This tests whether we detect variables which are used before they get
// initialized.

public class NoInit2 {
  void foo(int i) {}

  void m2() {
    int i;
    while (true) {
      if (1==1) {
	break;
      }
      i = 3; // BAD (m2) -- unreachable
    }
    foo(i);
  } 
}
