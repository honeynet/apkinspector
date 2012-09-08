class Constants7 {
    final byte b = (byte) (float) 1.0D;
    final char c = (char) b;
    final short s = (short) c;
    final int i = (int) s;
    final long l = (long) i;
    void foo (int j) {
        switch (j) {
            case 0:
            case (((double) l == 1.0D) ? (int) l : 0):
            case 2:
        }
    }
}
