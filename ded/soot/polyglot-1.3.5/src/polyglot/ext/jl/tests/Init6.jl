class C {
  void m() {
    Object o;
    for (;;) {
      boolean b = false;
      if (b || (o = null)==null) { break; }
      Object m = o; // as of release 1.2.0, jlc incorrectly says that o is not initialized here.
    }
  }
}
