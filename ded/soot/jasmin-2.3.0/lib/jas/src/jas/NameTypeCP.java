/**
 * Create a name/type entry constant pool entry
 * 
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class NameTypeCP extends CP implements RuntimeConstants
{
  AsciiCP name, sig;

  /**
   * Name/type entries are used to specify the type associated
   * with a symbol name.
   *
   * @param name Name of symbol
   * @param sig Signature of symbol
   */
  public NameTypeCP(String name, String sig)
  {
    uniq = ("NT : @#$%" + name + "SD#$"+ sig).intern();
    this.name = new AsciiCP(name);
    this.sig = new AsciiCP(sig);
  }

  void resolve(ClassEnv e)
  {
    e.addCPItem(name);
    e.addCPItem(sig);
  }
    
  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeByte(CONSTANT_NAMEANDTYPE);
    out.writeShort(e.getCPIndex(name));
    out.writeShort(e.getCPIndex(sig));
  }
}
