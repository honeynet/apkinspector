// This tests whether we detect reachable code properly.

public class Unreachable10 {
  void m10() {
    try {
      return;
    } finally {
      System.out.println("Okay");
    }
    System.out.println("Bad."); // BAD (m10)
  }
}
