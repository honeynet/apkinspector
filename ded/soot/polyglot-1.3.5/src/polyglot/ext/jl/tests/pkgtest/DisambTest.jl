import B.*;
import A.*;

class Test {
    Object o = new Foo(); // Polyglot should fail on this, it is ambiguous as to whether it should be A.Foo or B.Foo
}
