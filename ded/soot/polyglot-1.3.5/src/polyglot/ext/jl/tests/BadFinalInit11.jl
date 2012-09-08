public class BadFinalInit11 {
   final int i;

    BadFinalInit11() {
	i = 2; // Bad
    }
    // non-static initializer block is executed before the constructor,
    // and initializes the final variable.
    {
	i = 3;
    }

}
