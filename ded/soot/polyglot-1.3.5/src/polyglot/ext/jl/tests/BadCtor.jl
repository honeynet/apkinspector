class C {
  B() { }
  C m() { return new C() { C() { } }; }
}
