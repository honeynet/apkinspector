package polyglot.ext.pao.runtime;

/**
 * Boxed doubles.
 */
public class Double extends Primitive {
    protected double value;

    public Double(double value) {
        this.value = value;
    }

    public double doubleValue() {
        return value;
    }

    public int hashCode() {
        return (int)value;
    }

    public boolean equals(Object o) {
        if (o instanceof Double) {
            return ((Double)o).value == value;
        }
        if (o instanceof Long) {
            return ((Long)o).value == value;
        }
        return false;
    }

    public String toString() {
        return "" + value;
    }
}