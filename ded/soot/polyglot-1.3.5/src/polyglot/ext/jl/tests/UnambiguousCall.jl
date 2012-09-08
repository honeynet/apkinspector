// The call to bar() should not be ambiguous.
// Polyglot 1.3.2 incorrectly reports an ambiguous call.
abstract class A {
    public void bar() {
    }
}
class B extends A {
    public void bar() {
    }
}
class C extends B {
}
interface D {
    public void bar();
}
public class E extends C implements D {
    public void foo() {
        bar();
    }
}
