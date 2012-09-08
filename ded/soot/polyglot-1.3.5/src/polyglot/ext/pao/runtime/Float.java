package polyglot.ext.pao.runtime;

/**
 * Boxed floats.
 */
public class Float extends Double {
    public Float(float value) {
        super(value);
    }

    public float floatValue() {
        return (float)value;
    }
}