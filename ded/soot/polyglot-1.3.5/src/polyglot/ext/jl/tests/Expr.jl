
package test;

import java.util.*;

public class Expr extends Constants
{
  public void eval()
  {
    int i = 1;
    byte b1 = 2, b2;
    boolean b;
    String g;

    if( b) {
      g = "Hello ";
      String h = "World!", k;

      k = g + h;
    }

    if( i & 0x0F) {
      // whoops!
      i = "hello";
    }

    // works!
    i = i + 1;
   
    // doesn't work!
    boolean j;
    j = i + 1;

    // b1 promoted to int.
    b2 = b1 << 2;
  }
}
