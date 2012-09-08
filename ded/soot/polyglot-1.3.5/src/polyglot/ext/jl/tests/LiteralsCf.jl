public class LiteralsCf {
    public static void main(String[] args) {
        float f;
        double d;
        f = 3.4028235e+38f; //Why not error?
        f = 1.4023983e-45f; //Why not error?

        f = 1e39f; //ERR: rounds to +INF
        f = 0.0000000000000000000000000000000000000000000000001f; //ERR: rounds to 0

        f = -1234567890123456789012345678901234567890123f; //ERR: rounds to -INF

        f = 0e7f; //OK

        d = -1e310; //ERR: rounds to -INF
        d = 1e500; //ERR: rounds to +INF

        d = 0e19; //OK


        int i, i1, i2, i3;
        long l, l1, l2, l3;

        i = 25+-2147483648; //OK
        i = 25-2147483648; //ERR: too big
        i = 2147483648; //ERR: too big
        i = 2147483647; //OK
        i = -2147483648; //OK
        i = 0x1ffffffff; //ERR: too big
        i = 0x7fffffff; //OK
        i = 0xffffffff; //OK
        i = 01234567012345670; //ERR: too big
        i2 = 0x800000000; //ERR: too big
        i3 = 0200000000000; //ERR: too big
        i2 = 0x100000000; //ERR: too big
        i3 = 040000000000; //ERR: too big

        i = -2147483649; //ERR: too small

        l = 9223372036854775808L; //ERR: too big
        l = 9223372036854775807L; //OK
        l = -9223372036854775809L; //ERR: too small
        l = -9223372036854775808L; //OK
        l2 = 0x80000000000000000L;
        l2 = 0xffffffffffffffffL; //OK
        l2 = 0x1ffffffffffffffffL; //ERR: too big
        l2 = 0x10000000000000000L; //ERR: too big
        l3 = 010000000000000000000000L;
        l3 =  01777777777777777777777L; //OK
        l3 =  02000000000000000000000L; //ERR: too big
        l3 =  03777777777777777777777L; //ERR: too big

        i = 09; //ERR: illegal octal

    }
}
