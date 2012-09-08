/*
 * The following ternary operation wasn't working:
 *
 *   byte b = (byte)2;
 *   boolean t = true;
 *   byte next = t ? 1: b;
 */
public class TernaryPrimitiveOps {
    public static void main(String[] args) {
        new TernaryPrimitiveOps().realMain(args);
    }
    public void realMain(String[] args) {
        boolean t = true;
        boolean f = false;

        final int SIZE = 200;

        byte[]   _bytes   = new byte  [SIZE];
        char[]   _chars   = new char  [SIZE];
        double[] _doubles = new double[SIZE];
        int[]    _ints    = new int   [SIZE];
        float[]  _floats  = new float [SIZE];
        long[]   _longs   = new long  [SIZE];
        short[]  _shorts  = new short [SIZE];

        for (int i = 0; i < _bytes.length; i++)   _bytes[i] =    (byte)-1;
        for (int i = 0; i < _chars.length; i++)   _chars[i] =     (char)-1;
        for (int i = 0; i < _doubles.length; i++) _doubles[i] = (double)-1;
        for (int i = 0; i < _ints.length; i++)    _ints[i] =       (int)-1;
        for (int i = 0; i < _floats.length; i++)  _floats[i] =   (float)-1;
        for (int i = 0; i < _longs.length; i++)   _longs[i] =     (long)-1;
        for (int i = 0; i < _shorts.length; i++)  _shorts[i] =   (short)-1;

        int i;
        
        i = 0;
        _bytes[i++] = (byte)(t ?       1 :       0);
        _bytes[i++] =        t ? (byte)1 :       0 ;
        _bytes[i++] =        t ? (byte)1 : (byte)0 ;
        _bytes[i++] =        t ? (byte)1 :  (int)0 ;
        _bytes[i++] = (byte)(f ?       0 :       1);
        _bytes[i++] =        f ? (byte)0 :       1 ;
        _bytes[i++] =        f ? (byte)0 : (byte)1 ;
        _bytes[i++] =        f ? (byte)0 :  (int)1 ;


        i = 0;
        _shorts[i++] = (short)(t ?        1 :        0);
        _shorts[i++] =         t ? (short)1 :        0 ;
        _shorts[i++] =         t ? (short)1 : (short)0 ;
        _shorts[i++] =         t ? (short)1 :  (byte)0 ;
        _shorts[i++] =         t ? (short)1 :   (int)0 ;
        _shorts[i++] =         t ?  (byte)1 :        0 ;
        _shorts[i++] =         t ?  (byte)1 : (short)0 ;
        _shorts[i++] =         t ?  (byte)1 :  (byte)0 ;
        _shorts[i++] =         t ?  (byte)1 :   (int)0 ;
        _shorts[i++] = (short)(f ?        0 :        1);
        _shorts[i++] =         f ? (short)0 :        1 ;
        _shorts[i++] =         f ? (short)0 : (short)1 ;
        _shorts[i++] =         f ? (short)0 :  (byte)1 ;
        _shorts[i++] =         f ? (short)0 :   (int)1 ;
        _shorts[i++] =         f ?  (byte)0 :        1 ;
        _shorts[i++] =         f ?  (byte)0 : (short)1 ;
        _shorts[i++] =         f ?  (byte)0 :  (byte)1 ;
        _shorts[i++] =         f ?  (byte)0 :   (int)1 ;

        i = 0;
        _longs[i++] = t ?         1 :        0;
        _longs[i++] = t ?   (long)1 :        0;
        _longs[i++] = t ?   (long)1 :  (long)0;
        _longs[i++] = t ?   (long)1 :  (byte)0;
        _longs[i++] = t ?   (long)1 : (short)0;
        _longs[i++] = t ?   (long)1 :  (char)0;
        _longs[i++] = t ?   (long)1 :   (int)0;
        _longs[i++] = t ?   (byte)1 :        0;
        _longs[i++] = t ?   (byte)1 :  (long)0;
        _longs[i++] = t ?   (byte)1 :  (byte)0;
        _longs[i++] = t ?   (byte)1 : (short)0;
        _longs[i++] = t ?   (byte)1 :  (char)0;
        _longs[i++] = t ?   (byte)1 :   (int)0;
        _longs[i++] = t ?  (short)1 :        0;
        _longs[i++] = t ?  (short)1 :  (long)0;
        _longs[i++] = t ?  (short)1 :  (byte)0;
        _longs[i++] = t ?  (short)1 : (short)0;
        _longs[i++] = t ?  (short)1 :  (char)0;
        _longs[i++] = t ?  (short)1 :   (int)0;
        _longs[i++] = t ?   (char)1 :        0;
        _longs[i++] = t ?   (char)1 :  (long)0;
        _longs[i++] = t ?   (char)1 :  (byte)0;
        _longs[i++] = t ?   (char)1 : (short)0;
        _longs[i++] = t ?   (char)1 :  (char)0;
        _longs[i++] = t ?   (char)1 :   (int)0;
        _longs[i++] = t ?    (int)1 :        0;
        _longs[i++] = t ?    (int)1 :  (long)0;
        _longs[i++] = t ?    (int)1 :  (byte)0;
        _longs[i++] = t ?    (int)1 : (short)0;
        _longs[i++] = t ?    (int)1 :  (char)0;
        _longs[i++] = t ?    (int)1 :   (int)0;
        _longs[i++] = t ?         1 :        0;
        _longs[i++] = t ?         1 :  (byte)0;
        _longs[i++] = t ?         1 : (short)0;
        _longs[i++] = t ?         1 :  (char)0;
        _longs[i++] = t ?         1 :   (int)0;
        _longs[i++] = t ?   (byte)1 :        0;
        _longs[i++] = t ?  (short)1 :        0;
        _longs[i++] = t ?   (char)1 :        0;
        _longs[i++] = t ?    (int)1 :        0;
        _longs[i++] = f ?         0 :        1;
        _longs[i++] = f ?   (long)0 :        1;
        _longs[i++] = f ?   (long)0 :  (long)1;
        _longs[i++] = f ?   (long)0 :  (byte)1;
        _longs[i++] = f ?   (long)0 : (short)1;
        _longs[i++] = f ?   (long)0 :  (char)1;
        _longs[i++] = f ?   (long)0 :   (int)1;
        _longs[i++] = f ?   (byte)0 :        1;
        _longs[i++] = f ?   (byte)0 :  (long)1;
        _longs[i++] = f ?   (byte)0 :  (byte)1;
        _longs[i++] = f ?   (byte)0 : (short)1;
        _longs[i++] = f ?   (byte)0 :  (char)1;
        _longs[i++] = f ?   (byte)0 :   (int)1;
        _longs[i++] = f ?  (short)0 :        1;
        _longs[i++] = f ?  (short)0 :  (long)1;
        _longs[i++] = f ?  (short)0 :  (byte)1;
        _longs[i++] = f ?  (short)0 : (short)1;
        _longs[i++] = f ?  (short)0 :  (char)1;
        _longs[i++] = f ?  (short)0 :   (int)1;
        _longs[i++] = f ?   (char)0 :        1;
        _longs[i++] = f ?   (char)0 :  (long)1;
        _longs[i++] = f ?   (char)0 :  (byte)1;
        _longs[i++] = f ?   (char)0 : (short)1;
        _longs[i++] = f ?   (char)0 :  (char)1;
        _longs[i++] = f ?   (char)0 :   (int)1;
        _longs[i++] = f ?    (int)0 :        1;
        _longs[i++] = f ?    (int)0 :  (long)1;
        _longs[i++] = f ?    (int)0 :  (byte)1;
        _longs[i++] = f ?    (int)0 : (short)1;
        _longs[i++] = f ?    (int)0 :  (char)1;
        _longs[i++] = f ?    (int)0 :   (int)1;
        _longs[i++] = f ?         0 :        1;
        _longs[i++] = f ?         0 :  (byte)1;
        _longs[i++] = f ?         0 : (short)1;
        _longs[i++] = f ?         0 :  (char)1;
        _longs[i++] = f ?         0 :   (int)1;
        _longs[i++] = f ?   (byte)0 :        1;
        _longs[i++] = f ?  (short)0 :        1;
        _longs[i++] = f ?   (char)0 :        1;
        _longs[i++] = f ?    (int)0 :        1;

        i = 0;
        _ints[i++] = t ?         1 :        0;
        _ints[i++] = t ?    (int)1 :        0;
        _ints[i++] = t ?    (int)1 :   (int)0;
        _ints[i++] = t ?    (int)1 :  (byte)0;
        _ints[i++] = t ?    (int)1 : (short)0;
        _ints[i++] = t ?    (int)1 :  (char)0;
        _ints[i++] = t ?   (byte)1 :        0;
        _ints[i++] = t ?   (byte)1 :  (byte)0;
        _ints[i++] = t ?   (byte)1 : (short)0;
        _ints[i++] = t ?   (byte)1 :  (char)0;
        _ints[i++] = t ?   (byte)1 :   (int)0;
        _ints[i++] = t ?  (short)1 :        0;
        _ints[i++] = t ?  (short)1 :  (byte)0;
        _ints[i++] = t ?  (short)1 : (short)0;
        _ints[i++] = t ?  (short)1 :  (char)0;
        _ints[i++] = t ?  (short)1 :   (int)0;
        _ints[i++] = t ?   (char)1 :        0;
        _ints[i++] = t ?   (char)1 :  (byte)0;
        _ints[i++] = t ?   (char)1 : (short)0;
        _ints[i++] = t ?   (char)1 :  (char)0;
        _ints[i++] = t ?   (char)1 :   (int)0;
        _ints[i++] = t ?    (int)1 :        0;
        _ints[i++] = t ?    (int)1 :  (byte)0;
        _ints[i++] = t ?    (int)1 : (short)0;
        _ints[i++] = t ?    (int)1 :  (char)0;
        _ints[i++] = t ?    (int)1 :   (int)0;
        _ints[i++] = t ?         1 :  (byte)0;
        _ints[i++] = t ?         1 : (short)0;
        _ints[i++] = t ?         1 :  (char)0;
        _ints[i++] = t ?   (byte)1 :        0;
        _ints[i++] = t ?  (short)1 :        0;
        _ints[i++] = t ?   (char)1 :        0;
        _ints[i++] = f ?         0 :        1;
        _ints[i++] = f ?    (int)0 :        1;
        _ints[i++] = f ?    (int)0 :   (int)1;
        _ints[i++] = f ?    (int)0 :  (byte)1;
        _ints[i++] = f ?    (int)0 : (short)1;
        _ints[i++] = f ?    (int)0 :  (char)1;
        _ints[i++] = f ?   (byte)0 :        1;
        _ints[i++] = f ?   (byte)0 :  (byte)1;
        _ints[i++] = f ?   (byte)0 : (short)1;
        _ints[i++] = f ?   (byte)0 :  (char)1;
        _ints[i++] = f ?   (byte)0 :   (int)1;
        _ints[i++] = f ?  (short)0 :        1;
        _ints[i++] = f ?  (short)0 :  (byte)1;
        _ints[i++] = f ?  (short)0 : (short)1;
        _ints[i++] = f ?  (short)0 :  (char)1;
        _ints[i++] = f ?  (short)0 :   (int)1;
        _ints[i++] = f ?   (char)0 :        1;
        _ints[i++] = f ?   (char)0 :  (byte)1;
        _ints[i++] = f ?   (char)0 : (short)1;
        _ints[i++] = f ?   (char)0 :  (char)1;
        _ints[i++] = f ?   (char)0 :   (int)1;
        _ints[i++] = f ?    (int)0 :        1;
        _ints[i++] = f ?    (int)0 :  (byte)1;
        _ints[i++] = f ?    (int)0 : (short)1;
        _ints[i++] = f ?    (int)0 :  (char)1;
        _ints[i++] = f ?    (int)0 :   (int)1;
        _ints[i++] = f ?         0 :  (byte)1;
        _ints[i++] = f ?         0 : (short)1;
        _ints[i++] = f ?         0 :  (char)1;
        _ints[i++] = f ?   (byte)0 :        1;
        _ints[i++] = f ?  (short)0 :        1;
        _ints[i++] = f ?   (char)0 :        1;        

        i = 0;
        _floats[i++] = t ?            1 :          0;
        _floats[i++] = t ?            1 :    (byte)0;
        _floats[i++] = t ?            1 :   (short)0;
        _floats[i++] = t ?            1 :    (char)0;
        _floats[i++] = t ?            1 :     (int)0;
        _floats[i++] = t ?            1 :    (long)0;
        _floats[i++] = t ?            1 :   (float)0;
        _floats[i++] = t ?     (float)1 :          0;
        _floats[i++] = t ?     (float)1 :    (byte)0;
        _floats[i++] = t ?     (float)1 :   (short)0;
        _floats[i++] = t ?     (float)1 :    (char)0;
        _floats[i++] = t ?     (float)1 :     (int)0;
        _floats[i++] = t ?     (float)1 :    (long)0;
        _floats[i++] = t ?     (float)1 :   (float)0;
        _floats[i++] = t ?      (byte)1 :          0;
        _floats[i++] = t ?      (byte)1 :    (byte)0;
        _floats[i++] = t ?      (byte)1 :   (short)0;
        _floats[i++] = t ?      (byte)1 :    (char)0;
        _floats[i++] = t ?      (byte)1 :     (int)0;
        _floats[i++] = t ?      (byte)1 :    (long)0;
        _floats[i++] = t ?      (byte)1 :   (float)0;
        _floats[i++] = t ?     (short)1 :          0;
        _floats[i++] = t ?     (short)1 :    (byte)0;
        _floats[i++] = t ?     (short)1 :   (short)0;
        _floats[i++] = t ?     (short)1 :    (char)0;
        _floats[i++] = t ?     (short)1 :     (int)0;
        _floats[i++] = t ?     (short)1 :    (long)0;
        _floats[i++] = t ?     (short)1 :   (float)0;
        _floats[i++] = t ?      (char)1 :          0;
        _floats[i++] = t ?      (char)1 :    (byte)0;
        _floats[i++] = t ?      (char)1 :   (short)0;
        _floats[i++] = t ?      (char)1 :    (char)0;
        _floats[i++] = t ?      (char)1 :     (int)0;
        _floats[i++] = t ?      (char)1 :    (long)0;
        _floats[i++] = t ?      (char)1 :   (float)0;
        _floats[i++] = t ?       (int)1 :          0;
        _floats[i++] = t ?       (int)1 :    (byte)0;
        _floats[i++] = t ?       (int)1 :   (short)0;
        _floats[i++] = t ?       (int)1 :    (char)0;
        _floats[i++] = t ?       (int)1 :     (int)0;
        _floats[i++] = t ?       (int)1 :    (long)0;
        _floats[i++] = t ?       (int)1 :   (float)0;
        _floats[i++] = t ?      (long)1 :          0;
        _floats[i++] = t ?      (long)1 :    (byte)0;
        _floats[i++] = t ?      (long)1 :   (short)0;
        _floats[i++] = t ?      (long)1 :    (char)0;
        _floats[i++] = t ?      (long)1 :     (int)0;
        _floats[i++] = t ?      (long)1 :    (long)0;
        _floats[i++] = t ?      (long)1 :   (float)0;
        _floats[i++] = f ?            0 :          1;
        _floats[i++] = f ?            0 :    (byte)1;
        _floats[i++] = f ?            0 :   (short)1;
        _floats[i++] = f ?            0 :    (char)1;
        _floats[i++] = f ?            0 :     (int)1;
        _floats[i++] = f ?            0 :    (long)1;
        _floats[i++] = f ?            0 :   (float)1;
        _floats[i++] = f ?     (float)0 :          1;
        _floats[i++] = f ?     (float)0 :    (byte)1;
        _floats[i++] = f ?     (float)0 :   (short)1;
        _floats[i++] = f ?     (float)0 :    (char)1;
        _floats[i++] = f ?     (float)0 :     (int)1;
        _floats[i++] = f ?     (float)0 :    (long)1;
        _floats[i++] = f ?     (float)0 :   (float)1;
        _floats[i++] = f ?      (byte)0 :          1;
        _floats[i++] = f ?      (byte)0 :    (byte)1;
        _floats[i++] = f ?      (byte)0 :   (short)1;
        _floats[i++] = f ?      (byte)0 :    (char)1;
        _floats[i++] = f ?      (byte)0 :     (int)1;
        _floats[i++] = f ?      (byte)0 :    (long)1;
        _floats[i++] = f ?      (byte)0 :   (float)1;
        _floats[i++] = f ?     (short)0 :          1;
        _floats[i++] = f ?     (short)0 :    (byte)1;
        _floats[i++] = f ?     (short)0 :   (short)1;
        _floats[i++] = f ?     (short)0 :    (char)1;
        _floats[i++] = f ?     (short)0 :     (int)1;
        _floats[i++] = f ?     (short)0 :    (long)1;
        _floats[i++] = f ?     (short)0 :   (float)1;
        _floats[i++] = f ?      (char)0 :          1;
        _floats[i++] = f ?      (char)0 :    (byte)1;
        _floats[i++] = f ?      (char)0 :   (short)1;
        _floats[i++] = f ?      (char)0 :    (char)1;
        _floats[i++] = f ?      (char)0 :     (int)1;
        _floats[i++] = f ?      (char)0 :    (long)1;
        _floats[i++] = f ?      (char)0 :   (float)1;
        _floats[i++] = f ?       (int)0 :          1;
        _floats[i++] = f ?       (int)0 :    (byte)1;
        _floats[i++] = f ?       (int)0 :   (short)1;
        _floats[i++] = f ?       (int)0 :    (char)1;
        _floats[i++] = f ?       (int)0 :     (int)1;
        _floats[i++] = f ?       (int)0 :    (long)1;
        _floats[i++] = f ?       (int)0 :   (float)1;
        _floats[i++] = f ?      (long)0 :          1;
        _floats[i++] = f ?      (long)0 :    (byte)1;
        _floats[i++] = f ?      (long)0 :   (short)1;
        _floats[i++] = f ?      (long)0 :    (char)1;
        _floats[i++] = f ?      (long)0 :     (int)1;
        _floats[i++] = f ?      (long)0 :    (long)1;
        _floats[i++] = f ?      (long)0 :   (float)1;

        i = 0;
        _doubles[i++] = t ?            1 :          0;
        _doubles[i++] = t ?            1 :    (byte)0;
        _doubles[i++] = t ?            1 :   (short)0;
        _doubles[i++] = t ?            1 :    (char)0;
        _doubles[i++] = t ?            1 :     (int)0;
        _doubles[i++] = t ?            1 :    (long)0;
        _doubles[i++] = t ?            1 :   (float)0;
        _doubles[i++] = t ?            1 :  (double)0;
        _doubles[i++] = t ?    (double)1 :          0;
        _doubles[i++] = t ?    (double)1 :    (byte)0;
        _doubles[i++] = t ?    (double)1 :   (short)0;
        _doubles[i++] = t ?    (double)1 :    (char)0;
        _doubles[i++] = t ?    (double)1 :     (int)0;
        _doubles[i++] = t ?    (double)1 :    (long)0;
        _doubles[i++] = t ?    (double)1 :   (float)0;
        _doubles[i++] = t ?    (double)1 :  (double)0;
        _doubles[i++] = t ?      (byte)1 :          0;
        _doubles[i++] = t ?      (byte)1 :    (byte)0;
        _doubles[i++] = t ?      (byte)1 :   (short)0;
        _doubles[i++] = t ?      (byte)1 :    (char)0;
        _doubles[i++] = t ?      (byte)1 :     (int)0;
        _doubles[i++] = t ?      (byte)1 :    (long)0;
        _doubles[i++] = t ?      (byte)1 :   (float)0;
        _doubles[i++] = t ?      (byte)1 :  (double)0;
        _doubles[i++] = t ?     (short)1 :          0;
        _doubles[i++] = t ?     (short)1 :    (byte)0;
        _doubles[i++] = t ?     (short)1 :   (short)0;
        _doubles[i++] = t ?     (short)1 :    (char)0;
        _doubles[i++] = t ?     (short)1 :     (int)0;
        _doubles[i++] = t ?     (short)1 :    (long)0;
        _doubles[i++] = t ?     (short)1 :   (float)0;
        _doubles[i++] = t ?     (short)1 :  (double)0;
        _doubles[i++] = t ?      (char)1 :          0;
        _doubles[i++] = t ?      (char)1 :    (byte)0;
        _doubles[i++] = t ?      (char)1 :   (short)0;
        _doubles[i++] = t ?      (char)1 :    (char)0;
        _doubles[i++] = t ?      (char)1 :     (int)0;
        _doubles[i++] = t ?      (char)1 :    (long)0;
        _doubles[i++] = t ?      (char)1 :   (float)0;
        _doubles[i++] = t ?      (char)1 :  (double)0;
        _doubles[i++] = t ?       (int)1 :          0;
        _doubles[i++] = t ?       (int)1 :    (byte)0;
        _doubles[i++] = t ?       (int)1 :   (short)0;
        _doubles[i++] = t ?       (int)1 :    (char)0;
        _doubles[i++] = t ?       (int)1 :     (int)0;
        _doubles[i++] = t ?       (int)1 :    (long)0;
        _doubles[i++] = t ?       (int)1 :   (float)0;
        _doubles[i++] = t ?       (int)1 :  (double)0;
        _doubles[i++] = t ?      (long)1 :          0;
        _doubles[i++] = t ?      (long)1 :    (byte)0;
        _doubles[i++] = t ?      (long)1 :   (short)0;
        _doubles[i++] = t ?      (long)1 :    (char)0;
        _doubles[i++] = t ?      (long)1 :     (int)0;
        _doubles[i++] = t ?      (long)1 :    (long)0;
        _doubles[i++] = t ?      (long)1 :   (float)0;
        _doubles[i++] = t ?      (long)1 :  (double)0;
        _doubles[i++] = t ?     (float)1 :          0;
        _doubles[i++] = t ?     (float)1 :    (byte)0;
        _doubles[i++] = t ?     (float)1 :   (short)0;
        _doubles[i++] = t ?     (float)1 :    (char)0;
        _doubles[i++] = t ?     (float)1 :     (int)0;
        _doubles[i++] = t ?     (float)1 :    (long)0;
        _doubles[i++] = t ?     (float)1 :   (float)0;
        _doubles[i++] = t ?     (float)1 :  (double)0;
        _doubles[i++] = f ?            0 :          1;
        _doubles[i++] = f ?            0 :    (byte)1;
        _doubles[i++] = f ?            0 :   (short)1;
        _doubles[i++] = f ?            0 :    (char)1;
        _doubles[i++] = f ?            0 :     (int)1;
        _doubles[i++] = f ?            0 :    (long)1;
        _doubles[i++] = f ?            0 :   (float)1;
        _doubles[i++] = f ?            0 :  (double)1;
        _doubles[i++] = f ?    (double)0 :          1;
        _doubles[i++] = f ?    (double)0 :    (byte)1;
        _doubles[i++] = f ?    (double)0 :   (short)1;
        _doubles[i++] = f ?    (double)0 :    (char)1;
        _doubles[i++] = f ?    (double)0 :     (int)1;
        _doubles[i++] = f ?    (double)0 :    (long)1;
        _doubles[i++] = f ?    (double)0 :   (float)1;
        _doubles[i++] = f ?    (double)0 :  (double)1;
        _doubles[i++] = f ?      (byte)0 :          1;
        _doubles[i++] = f ?      (byte)0 :    (byte)1;
        _doubles[i++] = f ?      (byte)0 :   (short)1;
        _doubles[i++] = f ?      (byte)0 :    (char)1;
        _doubles[i++] = f ?      (byte)0 :     (int)1;
        _doubles[i++] = f ?      (byte)0 :    (long)1;
        _doubles[i++] = f ?      (byte)0 :   (float)1;
        _doubles[i++] = f ?      (byte)0 :  (double)1;
        _doubles[i++] = f ?     (short)0 :          1;
        _doubles[i++] = f ?     (short)0 :    (byte)1;
        _doubles[i++] = f ?     (short)0 :   (short)1;
        _doubles[i++] = f ?     (short)0 :    (char)1;
        _doubles[i++] = f ?     (short)0 :     (int)1;
        _doubles[i++] = f ?     (short)0 :    (long)1;
        _doubles[i++] = f ?     (short)0 :   (float)1;
        _doubles[i++] = f ?     (short)0 :  (double)1;
        _doubles[i++] = f ?      (char)0 :          1;
        _doubles[i++] = f ?      (char)0 :    (byte)1;
        _doubles[i++] = f ?      (char)0 :   (short)1;
        _doubles[i++] = f ?      (char)0 :    (char)1;
        _doubles[i++] = f ?      (char)0 :     (int)1;
        _doubles[i++] = f ?      (char)0 :    (long)1;
        _doubles[i++] = f ?      (char)0 :   (float)1;
        _doubles[i++] = f ?      (char)0 :  (double)1;
        _doubles[i++] = f ?       (int)0 :          1;
        _doubles[i++] = f ?       (int)0 :    (byte)1;
        _doubles[i++] = f ?       (int)0 :   (short)1;
        _doubles[i++] = f ?       (int)0 :    (char)1;
        _doubles[i++] = f ?       (int)0 :     (int)1;
        _doubles[i++] = f ?       (int)0 :    (long)1;
        _doubles[i++] = f ?       (int)0 :   (float)1;
        _doubles[i++] = f ?       (int)0 :  (double)1;
        _doubles[i++] = f ?      (long)0 :          1;
        _doubles[i++] = f ?      (long)0 :    (byte)1;
        _doubles[i++] = f ?      (long)0 :   (short)1;
        _doubles[i++] = f ?      (long)0 :    (char)1;
        _doubles[i++] = f ?      (long)0 :     (int)1;
        _doubles[i++] = f ?      (long)0 :    (long)1;
        _doubles[i++] = f ?      (long)0 :   (float)1;
        _doubles[i++] = f ?      (long)0 :  (double)1;
        _doubles[i++] = f ?     (float)0 :          1;
        _doubles[i++] = f ?     (float)0 :    (byte)1;
        _doubles[i++] = f ?     (float)0 :   (short)1;
        _doubles[i++] = f ?     (float)0 :    (char)1;
        _doubles[i++] = f ?     (float)0 :     (int)1;
        _doubles[i++] = f ?     (float)0 :    (long)1;
        _doubles[i++] = f ?     (float)0 :   (float)1;
        _doubles[i++] = f ?     (float)0 :  (double)1;        

        for (int _i = 0; _i < _bytes.length; _i++) {
            if (_bytes[_i] != (byte)-1) {
                Tester.check(_bytes[_i] == (byte)1,
                              _bytes[_i] + " != 1");
            }
        }
        for (int _i = 0; _i < _chars.length; _i++) {
            if (_chars[_i] != (char)-1) {
                Tester.check(_chars[_i] == (char)1,
                              _chars[_i] + " != 1");
            }
        }
        for (int _i = 0; _i < _doubles.length; _i++) {
            if (_doubles[_i] != (double)-1) {
                Tester.check(_doubles[_i] == (double)1,
                              _doubles[_i] + " != 1");
            }
        }
        for (int _i = 0; _i < _ints.length; _i++) {
            if (_ints[_i] != (int)-1) {
                Tester.check(_ints[_i] == (int)1,
                              _ints[_i] + " != 1");
            }
        }
        for (int _i = 0; _i < _floats.length; _i++) {
            if (_floats[_i] != (float)-1) {
                Tester.check(_floats[_i] == (float)1,
                              _floats[_i] + " != 1");
            }
        }
        for (int _i = 0; _i < _longs.length; _i++) {
            if (_longs[_i] != (long)-1) {
                Tester.check(_longs[_i] == (long)1,
                              _longs[_i] + " != 1");
            }
        }
        for (int _i = 0; _i < _shorts.length; _i++) {
            if (_shorts[_i] != (short)-1) {
                Tester.check(_shorts[_i] == (short)1,
                              _shorts[_i] + " != 1");
            }
        }

        /*
byte
char
double
int
float
long
short
         */
    }
}

class Tester {
        public static void check(boolean b, String s)
          { if (!b) System.out.println("Error " + s); }
}
