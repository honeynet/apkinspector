/**
 * MethodCP's are used to make references to methods in classes
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;
import java.io.*;


public class MethodCP extends CP implements RuntimeConstants
{
  ClassCP clazz;
  NameTypeCP nt;

  /**
   * @param cname Class in which method exists
   * @param varname name of method
   * @param sig Signature of method
   */
  public MethodCP(String cname, String varname, String sig)
  {
    uniq = cname + "&%$91&" + varname + "*(012$" + sig;
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
    out.writeByte(CONSTANT_METHOD);
    out.writeShort(e.getCPIndex(clazz));
    out.writeShort(e.getCPIndex(nt));
  }
}
