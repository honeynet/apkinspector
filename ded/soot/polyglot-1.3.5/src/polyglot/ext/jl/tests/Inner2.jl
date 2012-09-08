class Outer {
    static class Member { }
}

class Outer5 extends Outer {
}

class Outer6 {
    Outer5.Member foo() {
	return new Outer5.Member();
    }
}
