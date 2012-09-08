class Except1 {
  void method1() {}
  void method2() throws e1 {}
  void method3() throws e2 {}
  void method4() throws e1 {}
}

class Except1sub extends Except1 {
  void method1() throws e1 {}
  void method2() throws e2 {}
  void method3() throws e1 {}
  void method4() {}
}

class e1 extends java.lang.Exception {
  e1() { super(); }
  e1(String s) {super(s);}
}

class e2 extends e1 {
  e2() { super(); }
  e2(String s) {super(s);}
}
