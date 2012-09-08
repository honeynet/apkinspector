//
//  Checks for meaningful errors on misplaced explicit constructor calls.
//

class BadNew { 
  BadNew() {
    int i;
    super();
  }
  void m() {
    this();
  }
}
