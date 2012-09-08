// This tests whether we detect reachable code properly.

public class Unreachable8 {
  void m8() {
    try {
    } catch (Exception e) {}
    System.out.println("Just ducky."); 
    try { 
      return;
    } catch (Exception e) { return; }
    System.out.println("Won't happen."); // BAD (m8)
  }
}
