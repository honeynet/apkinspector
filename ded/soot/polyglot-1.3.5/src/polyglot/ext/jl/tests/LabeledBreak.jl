class LabeledBreak {
    public static void main(String[] args) {
        int i = 0;
        a: do {
            break a;
        } while (++i<10);
        System.out.println("i = " + i); // prints "i = 0"
    }
}
