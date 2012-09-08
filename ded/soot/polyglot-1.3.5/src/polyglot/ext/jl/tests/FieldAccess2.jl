class A {
    int x;
}

class B extends A {
    private int x;
}

class C extends B {
}


class FieldAccess2 {
    int m(C c) {
      return c.x;
    }
}
