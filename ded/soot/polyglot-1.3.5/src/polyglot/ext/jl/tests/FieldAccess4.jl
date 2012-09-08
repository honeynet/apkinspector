interface A {
    final static int x = 0;
}

interface B extends A {
    final static int x = 1;
}

class C implements A,B {
}


class FieldAccess2 {
    int m(C c) {
      return C.x;
    }
}
