// This tests whether we detect reachable code properly.

public class Unreachable5 {
  void m5() {
  l1:
    while(true) {
    l2:
      while(true) {
	break l1;
      }
      System.out.println("No good"); // BAD. (m5)
    }
    System.out.println("Okay");
  }
}
