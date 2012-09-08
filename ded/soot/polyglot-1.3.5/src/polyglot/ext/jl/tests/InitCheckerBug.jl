/*
   This code causes a NullPointerException in the base compiler's InitChecker.
   I just confirmed this with a fresh install of polyglot 1.3.3 (which prints
   jlc version 1.3.0).
  */
public class InitCheckerBug {
  static boolean flip = true;
  static void f() {
    int a = 2;
    int b = 3;
    while(true) {
      try {
        //if(flip)
        break;
      } finally {
          a = b; 
      }
    }
  }     
}

