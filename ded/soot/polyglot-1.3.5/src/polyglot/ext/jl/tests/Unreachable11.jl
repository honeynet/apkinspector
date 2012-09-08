// This tests whether we detect reachable code properly.

public class Unreachable11 {
  void m11() {
      try {
        throw new NullPointerException();
      }
      finally {
      }
      System.out.println("Bad"); // BAD (m11)
  }

  void m11a() {
    try {
      throw new NullPointerException();
    }
    catch (Exception e) {}
    return; // Should be ok.
  }

  void m11b() {
    try {
      try {
        throw new NullPointerException();
      }
      finally {
      }
    }
    catch (Exception e) {}
    return; // Should be OK.
  }


  int m11c() {
      try {
         new NullPointerException();
      }
      catch (Exception e) {}
      return 1; // Should be OK.
  }
}
