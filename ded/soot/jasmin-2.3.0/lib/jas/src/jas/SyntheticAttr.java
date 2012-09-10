/**
 * This is used to represent the synthetic attr
 *
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;

public class SyntheticAttr
{
  static CP attr = new AsciiCP("Synthetic");

  /**
   * Create a new synthetic attribute
   */

  public SyntheticAttr()
  { }

  void resolve(ClassEnv e)
  {
    e.addCPItem(attr);
  }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(e.getCPIndex(attr));
    out.writeInt(0);
  }
}

  
