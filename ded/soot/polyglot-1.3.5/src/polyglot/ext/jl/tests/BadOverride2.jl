// 15.12.2.1-accessibility-method-3  A method must be applicable and accessible  FAILED

abstract class PackageScope {
    abstract void m(); // note non-public accessibility
}
interface PublicScope {
    void m();
}
public abstract class BadOverride2 extends PackageScope implements PublicScope {
    // inherits 2 versions of m()
}
    
