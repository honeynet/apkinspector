class Base implements Type.Reflexive { //CE cyclic inheritance
    public interface I {}
}

class Type extends Derived {
    public interface Reflexive {
    }
}

class Derived extends Base {} // should be an error here
