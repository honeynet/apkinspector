class C {
  C() { }
  C m() { return new C() { { } }; }
}
