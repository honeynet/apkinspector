// This tests whether we detect reachable code properly.

public class Unreachable4 {
  void m4() {
    int i = 0;
    do {
      i++;
      continue;
      return; // BAD (m4)
    } while (i != 3);
  }
}
