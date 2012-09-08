public class Outer6 {
    Outer5.Member foo() {
	return new Outer5.Member();
    }
}

class Outer {
  class Member { }
}

class Outer5 extends Outer {
  static class Member { }
}
