// This tests whether we detect reachable code properly.

public class Unreachable9 {
  void m9() {
    try {
      System.out.println("hello world.");
    } finally {
      return;
    }
    System.out.println("Bad."); // BAD (m9)
  }
}
