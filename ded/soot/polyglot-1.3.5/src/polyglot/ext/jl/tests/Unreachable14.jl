// This tests whether we detect reachable code properly.

public class Unreachable14 {
  void m14() {
    while (x < y) {
    }
    return;
  }
  static final int x = 1;
  static final int y = 2;
}
