class Prec2 {
    public static boolean runint(int x, int n) {
        System.out.println("x = " + x + " n = " + n);

        int z=((x^(x>>>8)^(x>>>31))&(n-1));

        int a = x>>>8;
        int b = x>>>31;
        int c = x ^ a;
        int d = c ^ b;
        int e = n - 1;
        int f = d & e;

        System.out.println("z = " + z + " f = " + f);

        if (z != f) {
            System.out.println("precedence error 1: " + z + " != " + f);
            return false;
        }

        // if (z!=1) return false;

        z=((x|(n-1))&1);

        int g = x | e;
        int h = g & 1;

        System.out.println("z = " + z + " h = " + h);

        if (z != h) {
            System.out.println("precedence error 2: " + z + " != " + h);
            return false;
        }

        // if (z!=1) return false;
        return true;
    }

    public static boolean runlong(long x, int n) {
        System.out.println("x = " + x + " n = " + n);

        long z=((x^(x>>>8)^(x>>>31))&(n-1));

        long a = x>>>8;
        long b = x>>>31;
        long c = x ^ a;
        long d = c ^ b;
        int e = n - 1;
        long f = d & e;

        System.out.println("z = " + z + " f = " + f);

        if (z != f) {
            System.out.println("precedence error 1: " + z + " != " + f);
            return false;
        }

        // if (z!=1) return false;

        z=((x|(n-1))&1);

        long g = x | e;
        long h = g & 1;

        System.out.println("z = " + z + " h = " + h);

        if (z != h) {
            System.out.println("precedence error 2: " + z + " != " + h);
            return false;
        }

        // if (z!=1) return false;
        return true;
    }

    public static void main(String[] args) {
        for (int i = 0; i < 32; i++) {
            runint(0xdeadbeef, i);
        }
        for (int i = 0; i < 64; i++) {
            runlong(0xdeadbeefcafebabel, i);
        }
    }
}
