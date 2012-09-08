interface BadInner2 {
  	static class Foo { }
  	class Bar { }
}

class C implements BadInner2 {
  Object o = this.new Foo();
  Object p = this.new Bar();
}
