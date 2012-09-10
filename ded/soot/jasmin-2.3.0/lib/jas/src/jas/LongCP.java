package jas;

import java.io.*;


/**
 * Wrap an Long constant reference with this CPE.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

public class LongCP extends CP implements RuntimeConstants
{
  long val;

  /**
   * @param n Value for Long constant
   */
  public LongCP(long n)
  {
    uniq = ("Long: @#$" + n).intern();
    val = n;
  }
  void resolve(ClassEnv e) { return; }
  void write(ClassEnv e, DataOutputStream out)
    throws IOException
  {
    out.writeByte(CONSTANT_LONG);
    out.writeLong(val);
  }
}
