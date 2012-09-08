// From Robert F. St\"ark, Joachim Schmid, "Java bytecode verification is not
// possible (extended abstract). Formal Methods and Tools for Computer Science,
// Eurocast 2001. Universidad de Las Palmas de Gran Canaria. 
//
// This is a legal Java program that fails verification.

class Init4 {
  int m(boolean b) {
    int i;
    try {
      if (b) return 1;
      i = 2;
    }
    finally {
      if (b) i = 3;
    }
    return i;
  }
}

