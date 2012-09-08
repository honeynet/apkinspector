abstract class AbstractClass {
  public abstract void abstractMethod ();
}

class AbstractSuperMethod extends AbstractClass {
    public void abstractMethodCall () {
        super.abstractMethod (); // Error
    }
    public void abstractMethod() {}
}
