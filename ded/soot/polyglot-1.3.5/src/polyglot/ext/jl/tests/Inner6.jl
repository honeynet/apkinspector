class Inner6 {
}

class E extends Inner6 {
  class D {
    Inner6 m() { return E.this; }
  }
}
