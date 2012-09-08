// This tests whether we detect reachable code properly.

public class Unreachable6 {
  void m6() {
    int i = 4;
    switch (i) {
    case 3:
      break;
      System.out.println("no good"); // BAD (m6)
    case 4:
    default: 
      return;
    }
    System.out.println("okay.");
  }
}
