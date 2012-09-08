// This tests whether we detect variables which are used before they get
// initialized.

public class NoInit4 {
  void foo(int i) {}

  /*
  void m4a() {
    int i = 5;
    int j;
    switch (i) {
    case 3:
      j = 1;
      break;
    case 4:
    default: 
      j = 3;
      break;
    }
    foo(j); // OK
  }
  */
  
  void m4() {
    int i = 5;
    int j;
    switch (i) {
    case 3:
      j = 4;
      break;
    case 4:
      j = 6;
    }
    foo(j); // BAD (m4)
  }
}
