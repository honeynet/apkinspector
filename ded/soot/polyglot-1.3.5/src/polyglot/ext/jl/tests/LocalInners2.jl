public class LocalInners2 {
    public static void main(final String[] args) {
        class Local {
            class Inner0 extends Local {
            }
            class Inner1 extends Local {
                int arglen = args.length;
            }
        }
        new Local().new Inner0();
        new Local().new Inner1();
        foo(new Local().new Inner1().arglen);
    }

    static void foo(int i) {}
}
