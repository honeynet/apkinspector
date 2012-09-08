public class NoInit7 {
  void m7a() {
    int x;
    x = 1; // OK
  }

  void m7() {
    int x;
    x++; // BAD
  }
}

