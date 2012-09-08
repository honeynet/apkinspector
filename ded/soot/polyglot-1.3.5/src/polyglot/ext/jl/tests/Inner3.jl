class Outer {
    Member foo() {
	return this.new Member();
    }
    Member bar() {
	return new Member();
    }
    class Member { }
}

class Outer2 extends Outer {
    Member foo() {
	return this.new Member();
    }
    Member bar() {
	return new Member();
    }
}

class Outer3 extends Outer {
    class Member2 extends Outer2 {
	Member foo() {
	    return this.new Member();
	}
	Member bar() {
	    return new Member();
	}
    }
}

class Outer4 extends Outer {
    class Member2 {
	Member foo() {
	    return this.new Member();
	}
	Member bar() {
	    return new Member();
	}
	class Member { }
    }
}

class Outer5 extends Outer {
    static class Member3 { }
    Member foo() {
	return new Member();
    }
}

class Outer6 {
    Outer5.Member3 foo() {
	return new Outer5.Member3();
    }
}

class Outer7 {
    Outer.Member foo() {
	return new Outer().new Member();
    }
}
