package jas;

import java.io.*;


/**
 * Wrap an integer constant reference with this CPE.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

public class IntegerCP extends CP implements RuntimeConstants
{
  int val;

  /**
   * @param n Value for integer constant
   */
  public IntegerCP(int n)
  {
    uniq = ("Integer: @#$" + n).intern();
    val = n;
  }
  void resolve(ClassEnv e) { return; }
  void write(ClassEnv e, DataOutputStream out)
    throws IOException
  {
    out.writeByte(CONSTANT_INTEGER);
    out.writeInt(val);
  }
}
