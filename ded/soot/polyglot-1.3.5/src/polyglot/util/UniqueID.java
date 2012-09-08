package polyglot.util;

/** A unique identifier generator. */
public class UniqueID {
    private static int count = 0;
    private static int icount = 0;

    public static String newID(String s) {
	String uid = s + "$" + count;
	count++;
	return uid;
    }

    public static int newIntID() {
	return icount++;
    }
}
