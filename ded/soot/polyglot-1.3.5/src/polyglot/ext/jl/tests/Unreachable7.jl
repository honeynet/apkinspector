// This tests whether we detect reachable code properly.

public class Unreachable7 {
  void m7() {
    int i = 4;
    switch (i) {
    case 1:
      throw new NullPointerException();      
    default:
      return;
    }
    System.out.println("no good"); // BAD (m7)
  }
}
