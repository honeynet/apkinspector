class Foo { void m() { } }
interface Bar { void m(); }
class Foobar extends Foo implements Bar {   } // reject since Foo.m() not public

