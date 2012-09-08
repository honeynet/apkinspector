class StaticContext2 {
  class Z {
    Z(Object x) { }
  }
  class Y extends Z {
    Y() {
	super(StaticContext2.this); // legal
    }

    static Object f() {
	return StaticContext2.this; // illegal
    }
  }
}
