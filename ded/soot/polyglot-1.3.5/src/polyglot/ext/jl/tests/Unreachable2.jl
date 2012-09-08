// This tests whether we detect reachable code properly.

public class Unreachable2 {
  void m2() {
    throw new NullPointerException();
    System.out.println("Not likely."); // BAD (m2)
  }
}
