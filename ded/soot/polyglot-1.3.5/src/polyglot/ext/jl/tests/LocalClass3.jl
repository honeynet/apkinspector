class LocalClass3 {
    void m() {
        final Object i = null;
        new Object() {
            {
                Object o = i;
                int i;
                i = 1;
            }
        };
    }
}
