// This tests whether we detect variables which are used before they get
// initialized.

public class NoInit3 {
  void foo(int i) {}

  void m3() {
    int i;
  l1:
    while(true) {
    l2:
      while(true) {
	break l1;
      }
      i = 3; // BAD (m3) -- unreachable
    }
    foo(i);
  } 
}
