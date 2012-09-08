package polyglot.ext.pao.runtime;

/**
 * Boxed shorts.
 */
public class Short extends Integer {
    public Short(short value) {
        super(value);
    }

    public short shortValue() {
        return (short)value;
    }
}