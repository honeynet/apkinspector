/**
 * This is an opaque attribute that lets you add an uninterpreted
 * stream of bytes into an attribute in a class file. This can be
 * used (for instance) to embed versioning or signatures into the
 * class file or method.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */
package jas;

import java.io.*;

public class GenericAttr
{
  CP attr_name;
  byte data[];

  /**
   * Make up a new attribute
   * @param name Name to be associated with the attribute
   * @data stream of bytes to be placed with the attribute
   * @see ClassEnv#addGenericAttr
   * @see CodeAttr#addGenericAttr
   */
  public GenericAttr(String name, byte data[])
  {
    attr_name = new AsciiCP(name);
    this.data = data;
  }
  /**
   * Make up a new attribute
   * @param name CP to be defined as the name of the attribute
   * @data stream of bytes to be placed with the attribute
   * @see ClassEnv#addGenericAttr
   * @see CodeAttr#addGenericAttr
   */
  public GenericAttr(CP name, byte data[])
  {
    attr_name = name;
    this.data = data;
  }

  void resolve(ClassEnv e)
  { e.addCPItem(attr_name); }

  int size()
  { return (2 + 4 + data.length); }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(e.getCPIndex(attr_name));
    out.writeInt(data.length);
    out.write(data);
  }
}

