public class Init1 {
  void m1() {
    int x;
    if ((x = 1) == x) { // OK
    }
    x++; // OK
  }
}
