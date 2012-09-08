class E extends Exception {}
class BadExcept5 {
  void m() {
    try {} catch (RuntimeException e) {}
    try {} catch (Exception e) {}
    try {} catch (Error e) {}
    try {} catch (VirtualMachineError e) {}
    try {} catch (Throwable e) {}
    try {} catch (E e) {} // BAD
    /*
    int i = 0;
    do {
      i++;
      return;
    } while (i < 3);
    */
  }
}
