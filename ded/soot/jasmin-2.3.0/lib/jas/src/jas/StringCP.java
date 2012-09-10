package jas;

import java.io.*;


/**
 * Wrap an String constant reference with this CPE.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

public class StringCP extends CP implements RuntimeConstants
{
  AsciiCP val;

  /**
   * @param s Value for String constant
   */
  public StringCP(String s)
  {
    uniq = ("String: @#$" + s).intern();
    val = new AsciiCP(s);
  }
  void resolve(ClassEnv e)  { e.addCPItem(val); }
  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeByte(CONSTANT_STRING);
    out.writeShort(e.getCPIndex(val));
  }
}
