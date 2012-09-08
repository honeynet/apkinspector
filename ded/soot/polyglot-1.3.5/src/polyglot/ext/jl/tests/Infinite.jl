/**
 * The return statement in the try block never actually returns; the
 * program is an infinite loop.
 */
public class Infinite {
    public static void main(String[] args) {
	System.out.println(foo());
    }
    public static int foo() {
	while (true) {
	    try {
		return 1;
	    }
	    finally {
		continue;
	    }
	}
    }
}

