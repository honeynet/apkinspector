// This tests whether we detect reachable code properly.

public class Unreachable13 {
  void m13() {
    while (1 < 2) {
    }
    return;
  }
}
