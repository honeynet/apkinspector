package polyglot.types.reflect;

import polyglot.types.*;
import java.util.*;
import java.io.*;

/**
 * Method represents a method in a Java classfile.  A method's name and
 * value (the types of its parameters and its return type) are modeled
 * as indices into it class's constant pool.  A method has modifiers 
 * that determine whether it is public, private, static, final, etc.
 * Methods have a number of attributes such as their Code and any
 * Exceptions they may throw.
 *
 * @see polyglot.types.reflect Code
 * @see polyglot.types.reflect Exceptions
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class Method
{
  protected ClassFile clazz; 
  protected DataInputStream in;
  
  protected int modifiers;
  protected int name;
  protected int type;
  protected Attribute[] attrs;
  protected Exceptions exceptions;
  protected boolean synthetic;

  /**
   * Constructor.  Read a method from a class file.
   *
   * @param in
   *        The data stream of the class file.
   * @param clazz
   *        The class file containing the method.
   * @exception IOException
   *        If an error occurs while reading.
   */
  public Method(DataInputStream in, ClassFile clazz) 
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
        if ("Exceptions".equals(name.value())) {
          exceptions = new Exceptions(clazz, in, nameIndex, length);
          attrs[i] = exceptions;
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

  boolean isSynthetic() {
    return synthetic;
  }

  String name() {
    return (String) clazz.constants[this.name].value();
  }

  public MethodInstance methodInstance(TypeSystem ts, ClassType ct) {
    String name = (String) clazz.constants[this.name].value();
    String type = (String) clazz.constants[this.type].value();

    if (type.charAt(0) != '(') {
        throw new ClassFormatError("Bad method type descriptor.");
    }

    int index = type.indexOf(')', 1);
    List argTypes = clazz.typeListForString(ts, type.substring(1, index));
    Type returnType = clazz.typeForString(ts, type.substring(index+1));

    List excTypes = new ArrayList();

    if (exceptions != null) {
        for (int i = 0; i < exceptions.exceptions.length; i++) {
            String s = clazz.classNameCP(exceptions.exceptions[i]);
            excTypes.add(clazz.quietTypeForName(ts, s));
        }
    }

    return ts.methodInstance(ct.position(), ct,
                             ts.flagsForBits(modifiers), returnType, name,
                             argTypes, excTypes);
  }

  public ConstructorInstance constructorInstance(TypeSystem ts, ClassType ct,
                                          Field[] fields) {
    // Get a method instance for the <init> method.
    MethodInstance mi = methodInstance(ts, ct);

    List formals = mi.formalTypes();

    if (ct.isInnerClass()) {
        // If an inner class, the first argument may be a reference to an
        // enclosing class used to initialize a synthetic field.

        // Count the number of synthetic fields.
        int numSynthetic = 0;

        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isSynthetic()) {
                numSynthetic++;
            }
        }

        // Ignore a number of parameters equal to the number of synthetic
        // fields.
        if (numSynthetic <= formals.size()) {
            formals = formals.subList(numSynthetic, formals.size());
        }
    }

    return ts.constructorInstance(mi.position(), ct,
                                  mi.flags(), formals,
                                  mi.throwTypes());
  }
}
