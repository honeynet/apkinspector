// This tests whether we detect reachable code properly.

public class Unreachable1 {
  void m1() {
    return;
    System.out.println("Never happens."); // BAD (m1)
  }
}
