  class B {
      private void m(boolean x) {
            System.out.println("A.B.m");
      }
  }

class A {
  void m(int x) {
        System.out.println("A.m");
  }

  static class C extends B {
    static class D {
      void f() {
        m(1);
      }
    }
  }

  public static void main(String[] args) {
    new A.C.D().f();
  }
}

        
