
class BadInit {
  void m() {
    int a = no_such_fn();
    int b = a;
    int c;
    c = no_such_fn();
    b = c;
  }
}
