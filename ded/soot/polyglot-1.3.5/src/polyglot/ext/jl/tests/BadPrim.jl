class BadPrim {
  void m(byte b) {
  }

  void p() {
    m(1);
    m((byte) -1);
  }
}
