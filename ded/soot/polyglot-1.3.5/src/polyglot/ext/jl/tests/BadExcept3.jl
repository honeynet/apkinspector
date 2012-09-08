class BadExcept3 {
  int x;
  Object m(BadExcept3 a) {
    try {
      a.x = 0;
      throw new Exception();
    }
    catch (NullPointerException e) {
      return e;
    }
  }
}

