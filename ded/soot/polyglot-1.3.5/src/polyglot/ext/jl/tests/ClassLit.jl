class ClassLit {
    void m() {
      Class c = Object.class;
      c = int.class;
      c = boolean.class;
      c = long.class;
      c = void.class;
      c = boolean[].class;
      c = Class[].class;
      c = ClassLit.class;
      c = ClassLitInt.class;
    }
}

interface ClassLitInt {}
