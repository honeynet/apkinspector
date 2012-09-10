package jas;

import java.io.*;


/**
 * Wrap an Double constant reference with this CPE.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

public class DoubleCP extends CP implements RuntimeConstants
{
  double val;

  /**
   * @param n Value for Double constant
   */
  public DoubleCP(double n)
  {
    uniq = ("Double: @#$" + n).intern();
    val = n;
  }
  void resolve(ClassEnv e) { return; }
  void write(ClassEnv e, DataOutputStream out)
    throws IOException
  {
    out.writeByte(CONSTANT_DOUBLE);
    out.writeDouble(val);
  }
}
