class O2 {
  class D {
	class Member {
	  class A { class B { class C {
	    Member m = new Member();
	    Member n = new Member() { A m() { return new A(); } };
	  }}}
	}
  }

}
