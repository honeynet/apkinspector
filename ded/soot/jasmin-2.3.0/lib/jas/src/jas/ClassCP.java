/**
 * This is used to create a Class constant pool item
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class ClassCP extends CP implements RuntimeConstants
{
  AsciiCP name;

  /**
   * @param name Name of the class
   */
  public ClassCP(String name)
  {
    uniq = ("CLASS: #$%^#$" + name).intern();
    this.name = new AsciiCP(name);
  }

  void resolve(ClassEnv e)
  { e.addCPItem(name); }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeByte(CONSTANT_CLASS);
    out.writeShort(e.getCPIndex(name));
  }
}
