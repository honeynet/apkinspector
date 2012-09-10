/**
 * This is a class to create Ascii CP entries.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class AsciiCP extends CP implements RuntimeConstants
{
  /**
   * @param s Name of the ascii constant pool entry
   */
  public AsciiCP(String s)
  { uniq = s.intern(); }
  void resolve(ClassEnv e)
  { return; }

  public String toString() { return "AsciiCP: " + uniq; }
  void write(ClassEnv e, DataOutputStream out)
    throws IOException
  {
    out.writeByte(CONSTANT_UTF8);
    out.writeUTF(uniq);
  }
}
