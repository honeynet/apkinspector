class A  {
    void m() { }
}
abstract class BadInterfaceMethod extends A implements Int {
}

interface Int {
  void m();
}

