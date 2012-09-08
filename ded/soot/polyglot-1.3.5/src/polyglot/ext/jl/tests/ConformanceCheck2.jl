class Foo { public void m() throws java.io.IOException { } }
interface Bar { void m(); }
class Foobar extends Foo implements Bar {   } // reject since Foo.m() throws too many exceptions

