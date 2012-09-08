class Constants11 {
    public static void main(String[] a) {
        String s1 = 1 + 2 + " fiddlers";
        String s2 = "fiddlers " + 1 + 2;
        String s3 = "" + Integer.MAX_VALUE + 1;
        String s4 = Integer.MAX_VALUE + 1 + "";

        System.out.println("s1 = " + s1 + " [3 fiddlers]");
        System.out.println("s2 = " + s2 + " [fiddlers 12]");
        System.out.println("s3 = " + s3 + " [" + Integer.MAX_VALUE + "1]");
        System.out.println("s4 = " + s4 + " [" + (Integer.MAX_VALUE + 1) + "]");
    }
}
