package polyglot.types.reflect;

import java.util.*;
import java.io.*;

/**
 * Exceptions describes the types of exceptions that a method may throw.
 * The Exceptions attribute stores a list of indices into the constant
 * pool of the types of exceptions thrown by the method.
 *
 * @see polyglot.types.reflect Method
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class Exceptions extends Attribute {
  int[] exceptions;
  ClassFile clazz;

  /**
   * Constructor for create an <code>Exceptions</code> from scratch.
   *
   * @param nameIndex
   *        The index of the UTF8 string "Exceptions" in the class's
   *        constant pool
   * @param exceptions
   *        A non-<code>null</code> array of indices into the constant
   *        pool for the types of the exceptions
   */
  public Exceptions(ClassFile clazz, int nameIndex, int[] exceptions) {
    super(nameIndex, (2 * exceptions.length) + 2);
    this.clazz = clazz;
    this.exceptions = exceptions;
  }

  /**
   * Constructor.  Create an Exceptions attribute from a data stream.
   *
   * @param in
   *        The data stream of the class file.
   * @param nameIndex
   *        The index into the constant pool of the name of the attribute.
   * @param length
   *        The length of the attribute, excluding the header.
   * @exception IOException
   *        If an error occurs while reading.
   */
  public Exceptions(ClassFile clazz, DataInputStream in,
		    int nameIndex, int length) throws IOException
  {
    super(nameIndex, length);

    this.clazz = clazz;

    int count = in.readUnsignedShort();

    exceptions = new int[count];

    for (int i = 0; i < count; i++) {
      exceptions[i] = in.readUnsignedShort();
    }
  }
}
