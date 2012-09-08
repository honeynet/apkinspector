package polyglot.ext.pao.runtime;

/**
 * Boxed bytes.
 */
public class Byte extends Integer {
    public Byte(byte value) {
        super(value);
    }

    public byte byteValue() {
        return (byte)value;
    }
}