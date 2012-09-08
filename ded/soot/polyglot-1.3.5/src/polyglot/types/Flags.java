package polyglot.types;

import polyglot.util.InternalCompilerError;
import polyglot.util.Enum;

import java.io.Serializable;
import java.util.*;

/**
 * <code>Flags</code> is an immutable set of class, method, or field modifiers.
 * We represent package scope as the absence of private, public and protected
 * scope modifiers.
 */
public class Flags implements Serializable
{
    /** List of all flag bits in order in which they should be printed. */
    protected static final int[] print_order = new int[64];
    protected static int next_bit = 0;

    /** Names of 1-bit flags indexed by flag bit. */
    protected static final String[] flag_names = new String[64];
    protected static final String[] c_body_flag_names = new String[64];
    protected static final String[] c_head_flag_names = new String[64];

    public static final Flags NONE         = new Flags(0);
    public static final Flags PUBLIC       = createFlag("public", "", "", null);
    public static final Flags PRIVATE      = createFlag("private", "", "", null);
    public static final Flags PROTECTED    = createFlag("protected", "", "", null);
    public static final Flags STATIC       = createFlag("static", "static", "", null);
    public static final Flags FINAL        = createFlag("final", "", "", null);
    public static final Flags SYNCHRONIZED = createFlag("synchronized", "", "", null);
    public static final Flags TRANSIENT    = createFlag("transient", "", "", null);
    public static final Flags NATIVE       = createFlag("native", "", "", null);
    public static final Flags INTERFACE    = createFlag("interface", "", "", null);
    public static final Flags ABSTRACT     = createFlag("abstract", "", "", null);
    public static final Flags VOLATILE     = createFlag("volatile", "", "", null);
    public static final Flags STRICTFP     = createFlag("strictfp", "", "", null);

    /** All access flags. */
    protected static final Flags ACCESS_FLAGS = PUBLIC.set(PRIVATE).set(PROTECTED);

    /** Bit set use to implement a flag set. */
    protected long bits;

    /**
     * Return a new Flags object with a new name.  Should be called only once
     * per name.
     *
     * @param name the name of the new flag in Java
     * @param after the flags after which this flag should be printed;
     *        Flags.NONE to print before all other flags, null
     *        if we should print at the end.
     */
    public static Flags createFlag(String name, Flags after) {
        return createFlag(name, "", "", after);
    }

    /**
     * Return a new Flags object with a new name.  Should be called only once
     * per name.
     *
     * @param name the name of the new flag in Java
     * @param cHeadName the name of the flag to print in the C++ header file
     * @param cBodyName the name of the flag to print in the C++ body file 
     * @param after the flags after which this flag should be printed;
     *        Flags.NONE to print before all other flags, null
     *        if we should print at the end.
     */
    public static Flags createFlag(String name, String cHeadName, String cBodyName, Flags after) {
        if (next_bit >= flag_names.length)
            throw new InternalCompilerError("too many flags");
        if (print_order[next_bit] != 0)
            throw new InternalCompilerError("print_order and next_bit " +
                                            "inconsistent");
        if (flag_names[next_bit] != null)
            throw new InternalCompilerError("flag_names and next_bit " +
                                            "inconsistent");

        int bit = next_bit++;
        flag_names[bit] = name;
        c_head_flag_names[bit] = cHeadName;
        c_body_flag_names[bit] = cBodyName;

        if (after == null) {
            print_order[bit] = bit;
        }
        else {
            for (int i = bit; i > 0; i--) {
                if ((after.bits & print_order[i]) != 0)
                    break;

                // shift up and fill in the gap with f
                print_order[i] = print_order[i-1];
                print_order[i-1] = bit;
            }
        }

        return new Flags(1L << bit);
    }

    /**
     * Effects: returns a new accessflags object with no accessflags set.
     */
    protected Flags(long bits) {
        this.bits = bits;
    }

    /**
     * Create new flags with the flags in <code>other</code> also set.
     */
    public Flags set(Flags other) {
        return new Flags(bits | other.bits);
    }

    /**
     * Create new flags with the flags in <code>other</code> cleared.
     */
    public Flags clear(Flags other) {
        return new Flags(bits & ~other.bits);
    }

    /**
     * Create new flags with only flags in <code>other</code> set.
     */
    public Flags retain(Flags other) {
        return new Flags(bits & other.bits);
    }

    /**
     * Check if <i>any</i> flags in <code>other</code> are set.
     */
    public boolean intersects(Flags other) {
        return (bits & other.bits) != 0;
    }

    /**
     * Check if <i>all</i> flags in <code>other</code> are set.
     */
    public boolean contains(Flags other) {
        return (bits & other.bits) == other.bits;
    }

    /**
     * Return a copy of this <code>this</code> with the <code>public</code>
     * flag set.
     */
    public Flags Public() {
	return set(PUBLIC);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>public</code>
     * flag clear.
     */
    public Flags clearPublic() {
	return clear(PUBLIC);
    }

    /**
     * Return true if <code>this</code> has the <code>public</code> flag set.
     */
    public boolean isPublic() {
	return contains(PUBLIC);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>private</code>
     * flag set.
     */
    public Flags Private() {
	return set(PRIVATE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>private</code>
     * flag clear.
     */
    public Flags clearPrivate() {
	return clear(PRIVATE);
    }

    /**
     * Return true if <code>this</code> has the <code>private</code> flag set.
     */
    public boolean isPrivate() {
	return contains(PRIVATE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>protected</code>
     * flag set.
     */
    public Flags Protected() {
	return set(PROTECTED);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>protected</code>
     * flag clear.
     */
    public Flags clearProtected() {
	return clear(PROTECTED);
    }

    /**
     * Return true if <code>this</code> has the <code>protected</code> flag set.
     */
    public boolean isProtected() {
	return contains(PROTECTED);
    }

    /**
     * Return a copy of this <code>this</code> with no access flags
     * (<code>public</code>, <code>private</code>, <code>protected</code>) set.
     */
    public Flags Package() {
        return clear(ACCESS_FLAGS);
    }

    /**
     * Return true if <code>this</code> has the no access flags
     * (<code>public</code>, <code>private</code>, <code>protected</code>) set.
     */
    public boolean isPackage() {
        return ! intersects(ACCESS_FLAGS);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>static</code>
     * flag set.
     */
    public Flags Static() {
	return set(STATIC);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>static</code>
     * flag clear.
     */
    public Flags clearStatic() {
	return clear(STATIC);
    }

    /**
     * Return true if <code>this</code> has the <code>static</code> flag set.
     */
    public boolean isStatic() {
	return contains(STATIC);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>final</code>
     * flag set.
     */
    public Flags Final() {
	return set(FINAL);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>final</code>
     * flag clear.
     */
    public Flags clearFinal() {
	return clear(FINAL);
    }

    /**
     * Return true if <code>this</code> has the <code>final</code> flag set.
     */
    public boolean isFinal() {
	return contains(FINAL);
    }

    /**
     * Return a copy of this <code>this</code> with the
     * <code>synchronized</code> flag set.
     */
    public Flags Synchronized() {
	return set(SYNCHRONIZED);
    }

    /**
     * Return a copy of this <code>this</code> with the
     * <code>synchronized</code> flag clear.
     */
    public Flags clearSynchronized() {
	return clear(SYNCHRONIZED);
    }

    /**
     * Return true if <code>this</code> has the <code>synchronized</code> flag
     * set.
     */
    public boolean isSynchronized() {
	return contains(SYNCHRONIZED);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>transient</code>
     * flag set.
     */
    public Flags Transient() {
	return set(TRANSIENT);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>transient</code>
     * flag clear.
     */
    public Flags clearTransient() {
	return clear(TRANSIENT);
    }

    /**
     * Return true if <code>this</code> has the <code>transient</code> flag set.
     */
    public boolean isTransient() {
	return contains(TRANSIENT);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>native</code>
     * flag set.
     */
    public Flags Native() {
	return set(NATIVE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>native</code>
     * flag clear.
     */
    public Flags clearNative() {
	return clear(NATIVE);
    }

    /**
     * Return true if <code>this</code> has the <code>native</code> flag set.
     */
    public boolean isNative() {
	return contains(NATIVE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>interface</code>
     * flag set.
     */
    public Flags Interface() {
	return set(INTERFACE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>interface</code>
     * flag clear.
     */
    public Flags clearInterface() {
	return clear(INTERFACE);
    }

    /**
     * Return true if <code>this</code> has the <code>interface</code> flag set.
     */
    public boolean isInterface() {
	return contains(INTERFACE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>abstract</code>
     * flag set.
     */
    public Flags Abstract() {
	return set(ABSTRACT);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>abstract</code>
     * flag clear.
     */
    public Flags clearAbstract() {
	return clear(ABSTRACT);
    }

    /**
     * Return true if <code>this</code> has the <code>abstract</code> flag set.
     */
    public boolean isAbstract() {
	return contains(ABSTRACT);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>volatile</code>
     * flag set.
     */
    public Flags Volatile() {
	return set(VOLATILE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>volatile</code>
     * flag clear.
     */
    public Flags clearVolatile() {
	return clear(VOLATILE);
    }

    /**
     * Return true if <code>this</code> has the <code>volatile</code> flag set.
     */
    public boolean isVolatile() {
	return contains(VOLATILE);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>strictfp</code>
     * flag set.
     */
    public Flags StrictFP() {
	return set(STRICTFP);
    }

    /**
     * Return a copy of this <code>this</code> with the <code>strictfp</code>
     * flag clear.
     */
    public Flags clearStrictFP() {
	return clear(STRICTFP);
    }

    /**
     * Return true if <code>this</code> has the <code>strictfp</code> flag set.
     */
    public boolean isStrictFP() {
	return contains(STRICTFP);
    }

    /**
     * Return true if <code>this</code> has more restrictive access flags than
     * <code>f</code>.
     */
    public boolean moreRestrictiveThan(Flags f) {
        if (isPrivate() && (f.isProtected() || f.isPackage() || f.isPublic())) {
            return true;
        }

        if (isPackage() && (f.isProtected() || f.isPublic())) {
            return true;
        }

        if (isProtected() && f.isPublic()) {
            return true;
        }

        return false;
    }

    public String toString() {
        return translate().trim();
    }

    /**
     * Return "" if no flags set, or toString() + " " if some flags are set.
     */
    public String translate() {
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < next_bit; i++) {
            int bit = print_order[i];
            if ((bits & (1L << bit)) != 0) {
                sb.append(flag_names[bit]);
                sb.append(" ");
            }
        }

        return sb.toString();
    }
    
    /**
     * Returns the flag information appropriate for C++ functions
     */
    public String translateCBody() {
      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < next_bit; i++) {
          int bit = print_order[i];
          if ((bits & (1L << bit)) != 0) {
              sb.append(c_body_flag_names[bit]);
              sb.append(" ");
          }
      }

      return sb.toString();
    }
    
    /**
     * Returns the flag information appropriate for C++ header files
     */
    public String translateCHead() {
      StringBuffer sb = new StringBuffer();

      for (int i = 0; i < next_bit; i++) {
          int bit = print_order[i];
          if ((bits & (1L << bit)) != 0) {
              sb.append(c_head_flag_names[bit]);
              sb.append(" ");
          }
      }

      return sb.toString();
    }

    public int hashCode() {
        return (int) (bits >> 32 | bits) * 37;
    }

    public boolean equals(Object o) {
	return o instanceof Flags && bits == ((Flags) o).bits;
    }
}
