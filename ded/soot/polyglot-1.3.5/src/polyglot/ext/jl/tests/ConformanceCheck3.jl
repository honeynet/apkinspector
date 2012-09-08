class Foo { public void m() { } }
interface Bar { void m(); }
class Foobar extends Foo implements Bar {   } // accept, since Foo.m() satisfies Bar.m()

