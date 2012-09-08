package polyglot.ext.pao.runtime;

/**
 * Boxed chars.
 */
public class Character extends Integer {
    public Character(char value) {
        super(value);
    }

    public char charValue() {
        return (char)value;
    }

    public String toString() {
        return "" + (char)value;
    }
}