class BadSwitch1 {
  int x = 0;
  void m(int i) {
    switch (i) {
      case 0: System.out.println("0");
      case 1: System.out.println("1");
      case 2: System.out.println("2");
      case x: System.out.println("x");
      default: break;
    }
  }
}
