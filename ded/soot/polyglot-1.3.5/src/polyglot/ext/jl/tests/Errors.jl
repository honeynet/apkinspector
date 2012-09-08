class Errors {
  int m() { }

  int n() {
    return m(4) + (((null - 1) - 1) - (null - 1));
  }
}
