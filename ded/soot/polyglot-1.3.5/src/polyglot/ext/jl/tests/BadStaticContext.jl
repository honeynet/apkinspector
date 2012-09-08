class BadStaticContext {
  class Z {
    Z(Object x) { }
  }
  class Y extends Z {
    Y() {
	super(m());
    }
    
    BadStaticContext m() { return BadStaticContext.this; }
  }
}
