import A.*;
import B.*;

class Test {
    Object o = new A.Foo(); // Polyglot should suceed on this, but is pretty printing the type node wrong.
    // Note that the order of the import statements seem to be important.
}
