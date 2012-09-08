class StaticContext {
  class Z {
    Z(Object x) { }
  }
  class Y extends Z {
    Y() {
	super(StaticContext.this);
    }
  }
}
