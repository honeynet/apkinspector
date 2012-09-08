// This tests whether we detect reachable code properly.

public class Unreachable3 {
  void m3a() {
    while (1==1) {
      if (1==2) {
	break;
      }
    }
    System.out.println("This one _should_ be legal.");
  }
  
  void m3() {
    while (true) {
      if (1==1) {
	break;
	return; // BAD (m3)
      }
    }
    System.out.println("This one _should_ be legal.");
  }
}
