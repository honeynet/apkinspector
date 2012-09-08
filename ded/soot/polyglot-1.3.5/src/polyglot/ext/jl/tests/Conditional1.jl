class Conditional1 {
    void foo(byte b) throws Exception {}
    void foo(char c) throws Exception {}
    void foo(int i) {}
    void bar(byte b) {
        foo(true ? b : '0');
        foo(false ? b : '0');
        foo(true ? '0' : b);
        foo(false ? '0' : b);
    }
}
