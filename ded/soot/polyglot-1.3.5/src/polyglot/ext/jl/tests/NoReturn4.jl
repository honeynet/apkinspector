public class NoReturn4 {
  int m4() {
  l1:
    while(true) {
    l2:
      while(true) {
	break l1;
      }
    }
  } // BAD
}
