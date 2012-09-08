package polyglot.types.reflect;

import polyglot.types.*;
import java.util.*;
import java.io.*;

/**
 * Field models a field (member variable) in a class.  The Field class
 * grants access to information such as the field's modifiers, its name
 * and type descriptor (represented as indices into the constant pool),
 * and any attributes of the field.  Static fields have a ConstantValue
 * attribute.
 *
 * @see polyglot.types.reflect ConstantValue
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class Field {
    DataInputStream in;
    ClassFile clazz; 
    int modifiers;
    int name;
    int type;
    Attribute[] attrs;
    ConstantValue constantValue;
    boolean synthetic;

    /**
     * Return true of t is java.lang.String.
     * We don't compare against ts.String() because ts.String() may not
     * yet be set.
     */
    boolean isString(Type t) {
      return t.isClass()
          && t.toClass().isTopLevel()
          && t.toClass().fullName().equals("java.lang.String");
    }

    public int modifiers(){
        return modifiers;
    }
    
    public FieldInstance fieldInstance(TypeSystem ts, ClassType ct) {
      String name = (String) clazz.constants[this.name].value();
      String type = (String) clazz.constants[this.type].value();

      FieldInstance fi = ts.fieldInstance(ct.position(), ct,
                                          ts.flagsForBits(modifiers),
                                          clazz.typeForString(ts, type), name);

      Constant c = constantValue();

      if (c != null) {
        Object o = null;

        try {
          switch (c.tag()) {
            case Constant.STRING: o = getString(); break;
            case Constant.INTEGER: o = new Integer(getInt()); break;
            case Constant.LONG: o = new Long(getLong()); break;
            case Constant.FLOAT: o = new Float(getFloat()); break;
            case Constant.DOUBLE: o = new Double(getDouble()); break;
          }
        }
        catch (SemanticException e) {
          throw new ClassFormatError("Unexpected constant pool entry.");
        }

        if (o != null) {
          return fi.constantValue(o);
        }
      }

      return fi;
    }

    boolean isSynthetic() {
      return synthetic;
    }

    Constant constantValue() {
      if (this.constantValue != null) {
        int index = this.constantValue.index;
        return clazz.constants[index];
      }

      return null;
    }

    int getInt() throws SemanticException {
      Constant c = constantValue();

      if (c != null && c.tag() == Constant.INTEGER) {
        Integer v = (Integer) c.value();
        return v.intValue();
      }

      throw new SemanticException("Could not find expected constant " +
                                  "pool entry with tag INTEGER.");
    }

    float getFloat() throws SemanticException {
      Constant c = constantValue();

      if (c != null && c.tag() == Constant.FLOAT) {
        Float v = (Float) c.value();
        return v.floatValue();
      }

      throw new SemanticException("Could not find expected constant " +
                                  "pool entry with tag FLOAT.");
    }

    double getDouble() throws SemanticException {
      Constant c = constantValue();

      if (c != null && c.tag() == Constant.DOUBLE) {
        Double v = (Double) c.value();
        return v.doubleValue();
      }

      throw new SemanticException("Could not find expected constant " +
                                  "pool entry with tag DOUBLE.");
    }

    long getLong() throws SemanticException {
      Constant c = constantValue();

      if (c != null && c.tag() == Constant.LONG) {
        Long v = (Long) c.value();
        return v.longValue();
      }

      throw new SemanticException("Could not find expected constant " +
                                  "pool entry with tag LONG.");
    }

    String getString() throws SemanticException {
      Constant c = constantValue();

      if (c != null && c.tag() == Constant.STRING) {
        Integer i = (Integer) c.value();
        c = clazz.constants[i.intValue()];

        if (c != null && c.tag() == Constant.UTF8) {
          String v = (String) c.value();
          return v;
        }
      }

      throw new SemanticException("Could not find expected constant " +
                                  "pool entry with tag STRING or UTF8.");
    }

    String name() {
      return (String) clazz.constants[this.name].value();
    }

    /**
     * Constructor.  Read a field from a class file.
     *
     * @param in
     *        The data stream of the class file.
     * @param clazz
     *        The class file containing the field.
     * @exception IOException
     *        If an error occurs while reading.
     */
    Field(DataInputStream in, ClassFile clazz)
        throws IOException
    {
        this.clazz = clazz;
        this.in = in;
    }

    public void initialize() throws IOException {
        modifiers = in.readUnsignedShort();

	name = in.readUnsignedShort();
	type = in.readUnsignedShort();

	int numAttributes = in.readUnsignedShort();

        attrs = new Attribute[numAttributes];

        for (int i = 0; i < numAttributes; i++) {
            int nameIndex = in.readUnsignedShort();
            int length = in.readInt();

            Constant name = clazz.constants[nameIndex];

            if (name != null) {
                if ("ConstantValue".equals(name.value())) {
                    constantValue = new ConstantValue(in, nameIndex, length);
                    attrs[i] = constantValue;
                }
                if ("Synthetic".equals(name.value())) {
                    synthetic = true;
                }
            }

	    if (attrs[i] == null) {
                long n = in.skip(length);
                if (n != length) {
                    throw new EOFException();
                }
            }
	}
    }
}
