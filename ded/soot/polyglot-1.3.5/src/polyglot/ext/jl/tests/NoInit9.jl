public class NoInit9 {
  boolean b;

  int m9() {
    int x;
    if (b) x = 1;
    return x; // BAD
  }

  void m9a() {
    int x;
    if (b) x = 1;
    else x = 2;
    x++; // OK
  }

  void m9b() {
    int x;
    if (b) x = 1;
    else return;
    x++; // OK
  }
}
