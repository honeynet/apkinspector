// This tests whether we detect variables which are used before they get
// initialized.

class NoInit6 {
  void m6() {
    int i;
    try {
      try {
        throw new NullPointerException();
      }
      finally {
        // i may not be initialized at the return below because we could
        // through a RuntimeException here, before evaluating this assignment
        // no really!
        i = 1;
      }
    }
    catch (Exception e) {}
    // i is really initialized here, but we should be conservative.
    i++; // BAD uninitialized (m11)
  }
}
