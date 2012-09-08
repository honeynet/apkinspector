public class Constants12 {
  int m(int x) {
    switch (x) {
      case (0-Integer.MAX_VALUE):
        System.out.println("0"); break;
      case -2147483647:
        System.out.println("1"); break; // error: duplicate label
    }
    switch (x) {
      case ((int)(-1 << -1)):
        System.out.println("0"); break;
      case -2147483648:
        System.out.println("1"); break; // error: duplicate label
    }
    switch (x) {
      case ((int)(1 << -1)):
        System.out.println("1"); break;
      case -2147483648:
        System.out.println("2"); break; // error: duplicate label
    }
    switch (x) {
      case ((int)(-1 >>> 1)):
        System.out.println("0"); break;
      case 2147483647:
        System.out.println("1"); break; // error: duplicate label
    }
    switch (x) {
      case (int) (0L-Long.MAX_VALUE):
        System.out.println("0"); break;
      case (int) -9223372036854775807L:
        System.out.println("1"); break; // error: duplicate label
    }

    return 0;
  }
}
