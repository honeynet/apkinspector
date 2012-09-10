/**
 * This is typically used to represent a constant value for
 * a field entry (as in static final int foo = 20).
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;

public class ConstAttr
{
  static CP attr = new AsciiCP("ConstantValue");
  CP val;

  /**
   * Create a new constant attribute whose constant value
   * is picked up from constant pool with the given entry.
   * @param val Constant pool item whose value is associated
   * with the constant value attribute
   */

  public ConstAttr(CP val)
  { this.val = val; }

  void resolve(ClassEnv e)
  {
    e.addCPItem(val);
    e.addCPItem(attr);
  }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(e.getCPIndex(attr));
    out.writeInt(2);
    out.writeShort(e.getCPIndex(val));
  }
}

  
