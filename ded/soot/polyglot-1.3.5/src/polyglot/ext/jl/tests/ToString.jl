/* Test that we can only coerce an expression to a string if there
 * is a toString method.
 *
 */
public class ToString {

    public void foo() {
	String s = null;
	int i = 3;
	Object o = null;
	ToString o2 = null;

	s = s + o + o2 + i; // all of these should be OK

    }
}
