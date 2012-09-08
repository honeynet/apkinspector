class Base extends Derived { //CE cyclic inheritance
}

class Derived extends Base {} // should be an error here
