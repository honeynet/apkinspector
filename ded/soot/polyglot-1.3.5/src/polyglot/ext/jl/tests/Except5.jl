class Except5 {
    void m() {
        try {
            try {
                throw new Exception();
            }
            catch(Exception e) {
                throw e;
            }
            finally {
                throw new RuntimeException();
            }
        }
        catch(RuntimeException e) { }
    }
}
