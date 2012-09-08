class Constants8 {
  final boolean t = true;
  void foo(int j) {
    switch (j) {
      case 0:
      case ((boolean) t ? 1 : 0):
    }
  }
}
