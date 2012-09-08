// From Robert F. St\"ark, Joachim Schmid, "Java bytecode verification is not
// possible (extended abstract). Formal Methods and Tools for Computer Science,
// Eurocast 2001. Universidad de Las Palmas de Gran Canaria. 
//
// This is a legal Java program that fails verification.

class Init5 {
  int m(boolean b) {
    int i;
    L: {
      try {
        if (b) return 1;
        i = 2;
        if (b) break L;
      }
      finally {
        if (b) i = 3;
      }
      i = 4;
    }
    return i;
  }
}

