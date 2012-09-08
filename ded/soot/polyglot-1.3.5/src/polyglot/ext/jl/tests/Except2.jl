class Except2 {
  int x;
  Object m(Except2 a) throws Exception {
    try {
      a.x = 0;
      throw new Exception();
    }
    catch (NullPointerException e) {
      return e;
    }
  }
}

