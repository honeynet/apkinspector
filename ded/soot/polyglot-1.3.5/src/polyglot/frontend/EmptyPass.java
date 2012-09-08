package polyglot.frontend;

/**
 * An <code>EmptyPass</code> does nothing.
 */
public class EmptyPass extends AbstractPass
{
    public EmptyPass(Pass.ID id) {
      	super(id);
    }

    public boolean run() {
	return true;
    }
}
