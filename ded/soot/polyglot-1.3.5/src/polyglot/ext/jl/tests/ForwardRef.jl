class ForwardRef extends A {
  long m(int x, A y) { return f; }

  Object o = new C();
}

class A {
  private int m(int x, A y) { return f * x; }
  public int f;
  public int g;

  class C {
    int g;
    int m() { return f; }
    int n() { return g; }
  }
}
