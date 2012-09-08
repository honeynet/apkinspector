class Inner10 {
  class H extends I {
    class E extends F {
      class B extends D { int m() { return foo; } }
    }
    class F extends G {
      class D extends A { }
    }
  }
  class I {
    class G {
      class A { int foo; }
    }
  }
}
