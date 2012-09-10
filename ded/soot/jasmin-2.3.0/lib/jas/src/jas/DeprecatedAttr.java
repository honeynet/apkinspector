/**
 * This is used to represent the deprecated attr
 *
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;

public class DeprecatedAttr
{
  static CP attr = new AsciiCP("Deprecated");

  /**
   * Create a new deprecated attribute
   */

  public DeprecatedAttr()
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

  
