public class BadInner8 {
    
  int zzz;

  public static void bar() {
    class BBB {
      public void bar2() {
        BadInner8.this.zzz = 0; // BAD, no enclosing instance of BadInner8
      }
    }
  }   
}
