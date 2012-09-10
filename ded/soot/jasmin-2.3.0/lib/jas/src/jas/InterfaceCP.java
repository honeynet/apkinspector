/**
 * An InterfaceCP is used to refer to an interface specification
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class InterfaceCP extends CP implements RuntimeConstants
{
  ClassCP clazz;
  NameTypeCP nt;

  /**
   * @param cname Name of class defining the interface
   * @param varname symbol for the interface method
   * @param sig Signature for method
   */
  public InterfaceCP(String cname, String varname, String sig)
  {
    uniq = (cname + "&%$#&" + varname + "*()#$" + sig).intern();
    clazz = new ClassCP(cname);
    nt = new NameTypeCP(varname, sig);
  }

  void resolve(ClassEnv e)
  {
    e.addCPItem(clazz);
    e.addCPItem(nt);
  }
    
  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeByte(CONSTANT_INTERFACEMETHOD);
    out.writeShort(e.getCPIndex(clazz));
    out.writeShort(e.getCPIndex(nt));
  }
}
