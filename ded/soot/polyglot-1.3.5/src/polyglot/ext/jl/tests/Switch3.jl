class Switch3 {
  void m(int i) {
    switch (i) {
      case 0: System.out.println("0");
      case 1: System.out.println("1");
      case 2: System.out.println("2");
      case Switch3a.x: System.out.println("x");
      default: break;
    }
  }
}

class Switch3a {
  static final int x = 3;
}
