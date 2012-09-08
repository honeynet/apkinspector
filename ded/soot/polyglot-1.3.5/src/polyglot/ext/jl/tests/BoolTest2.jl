public class BoolTest2 {
    public static void main(String[] args) {
	boolean b;
	boolean c = true;
	if (c || (b = true)) {
	    System.out.println(b);
	}
    }
}
