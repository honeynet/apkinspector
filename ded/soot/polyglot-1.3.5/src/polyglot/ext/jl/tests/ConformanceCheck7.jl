class C {
    void m() {}
}

abstract class D extends C {
    abstract void m();
}

class E extends D { // BAD: should be abstract
}
