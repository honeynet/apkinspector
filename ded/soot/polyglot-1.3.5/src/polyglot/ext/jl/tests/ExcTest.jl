class ExcTest {
    public void m1() throws Exception {
        exc();
        Object test = new Object() {
            protected static final boolean bln = true;
        };
    }
    public static void exc() throws Exception {}
}
