public class NoInit11 {
  int m11() {
    int x;
    for (int i = 0; i < 10; i++) {
      x++;
    }
    return x; // BAD
  }
}
