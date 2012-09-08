package polyglot.types.reflect;

import java.util.*;
import java.io.*;

/**
 * Attribute is an abstract class for an attribute defined for a method,
 * field, or class.  An attribute consists of its name (represented as an
 * index into the constant pool) and its length.  Attribute is extended
 * to represent a constant value, code, exceptions, etc.
 *
 * @see polyglot.types.reflect ConstantValue
 * @see polyglot.types.reflect Exceptions
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public abstract class Attribute {
  protected int nameIndex;
  protected int length;

  /**
   * Constructor.
   *
   * @param nameIndex
   *        The index into the constant pool of the name of the attribute.
   * @param length
   *        The length of the attribute, excluding the header.
   */
  public Attribute(int nameIndex, int length) {
    this.nameIndex = nameIndex;
    this.length = length;
  }
}
