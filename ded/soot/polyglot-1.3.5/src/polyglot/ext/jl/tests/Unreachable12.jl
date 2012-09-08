// This tests whether we detect reachable code properly.

public class Unreachable12 {
  void m12() {
    while (true) {
    }
    return;
  }
}
