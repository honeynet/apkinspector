class Except3 {
  int x;
  Object m(Except3 a) throws Exception {
    try {
      a.x = 0;
      throw new Exception();
    }
    catch (NullPointerException e) {
      return e;
    }
  }
}

