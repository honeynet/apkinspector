/* Test that we can only coerce an expression to a string if there
 * is a toString method.
 *
 */
public class ToString2 {

    public void foo() {
	String s = null;
	int i = 3;
	Object o = null;
	ToString2 o2 = null;

	s += s;
	s += o;
	s += i;
	s += o2;
       	o += s; // this should be ruled out.

    }
}
