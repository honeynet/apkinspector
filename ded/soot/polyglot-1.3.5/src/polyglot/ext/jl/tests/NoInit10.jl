public class NoInit10 {
  boolean b;

  void m10a() {
    int x;
    do {
      x = 1;
    } while (b);
    x++; // OK
  }

  void m10() {
    int x;
    while (b) {
      x = 1;
    }
    x++; // BAD
  }
}
