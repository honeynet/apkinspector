public class BadFinalInit10 {
    // declaration of field is after the constructor.
    BadFinalInit10() {
	i = 2; // Bad
    }
   final int i = 0;

}
