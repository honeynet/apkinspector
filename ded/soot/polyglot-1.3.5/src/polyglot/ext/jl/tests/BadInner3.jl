class BadInner3 {
}

class E extends BadInner3 {
  class D {
    BadInner3 m() { return BadInner3.this; }
  }
}
