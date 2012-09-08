public class LocalClass6 {
    static class Local { }
    public static void main(String[] argv) {
          class Local extends Local { }
          // illegal: extends the local class, not the member class
    }
}

