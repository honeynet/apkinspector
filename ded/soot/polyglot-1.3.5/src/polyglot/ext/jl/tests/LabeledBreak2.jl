class LabeledBreak {
    public static void main(String[] args) {
        int i;
        // Polyglot says ++i is unreachable; javac says it's reachable,
        // but the JLS doesn't say which is correct.
        a: for (i=0; i<10; ++i) {
            break a;
        }
        System.out.println("i = " + i); // prints "i = 0"
    }
}
