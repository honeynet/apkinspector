public class NoInit12 {
  boolean b;

  int m12() {
    int x;
    for (;b;) {
      x = 1;
    }
    return x; // BAD
  }
}

