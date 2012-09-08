
class Constants3 {
    void foo(int i) {
        switch (i) {
            case 0:
            case ((true) ? 1 : 0):
            case (((short)(1*2*3*4*5*6) == 720) ? 2 : 0):
            case ((Integer.MAX_VALUE / 2 == 0x3fffffff) ? 3 : 0):
            case ((2.0 * Math.PI == 6.283185307179586) ? 4 : 0):
        }
    }
}
