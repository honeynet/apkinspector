class Except6 {
    void m() {
        try {
            throw new Exception();
        } finally {
            return;
        }
    }

}
