class Outer {
  class Inner {
  }
}

class Outer2 {
  void m(Outer o) {
    Object x = o.new Inner();
  }
}

