/**
 * FieldCP's are used to refer to a field in a particular
 * class.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class FieldCP extends CP implements RuntimeConstants
{
  ClassCP clazz;
  NameTypeCP nt;

  /**
   * FieldCP's are created by specifying the class to which the 
   * field belongs, the name of the symbol, and its signature.
   * For instance, to refer to the field <tt>out</tt> in 
   * <tt>System.out</tt> use
   * <tt> new FieldCP("java/lang/System", "out", "Ljava/io/PrintStream;")</tt>
   *
   * @param clazz Name of class
   * @param name Name of symbol
   * @param sig Signature for symbol
   */

  public FieldCP(String clazz, String name, String sig)
  {
    uniq = (clazz + "&%$#&" + name + "*()#$" + sig).intern();
    this.clazz = new ClassCP(clazz);
    this.nt = new NameTypeCP(name, sig);
  }

  void resolve(ClassEnv e)
  {
    e.addCPItem(clazz);
    e.addCPItem(nt);
  }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeByte(CONSTANT_FIELD);
    out.writeShort(e.getCPIndex(clazz));
    out.writeShort(e.getCPIndex(nt));
  }
}
