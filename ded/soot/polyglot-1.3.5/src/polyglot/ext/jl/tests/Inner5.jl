interface Inner5 {
    static class Foo { }
    class Bar { }
    
}

class C implements Inner5 {
    Object o = new Inner5.Foo();
    Object p = new Inner5.Bar();
    Object q = new Foo();
    Object r = new Bar();
    
    
}
