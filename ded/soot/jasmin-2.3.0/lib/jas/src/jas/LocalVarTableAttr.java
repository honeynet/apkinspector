/**
 * Local variable table attributes are embedded into Code attributes
 * and used for further debugging information.
 * @see CodeAttr#setLocalVar
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class LocalVarTableAttr
{
  static CP attr = new AsciiCP("LocalVariableTable");

  Vector vars;


  /**
   * Note: A local var is associated with a <em>method</em>, so you
   * need to create a new LocalVarTableAttr for each method for which you add
   * debugging information.
   * @see CodeAttr#setLocalVarTable
   */
  public LocalVarTableAttr()
  { vars = new Vector(); }

  /**
   * Add a LocalVar Entry to the attribute
   * @param entry Local variable Entry to be added to the table
   */

  public void addEntry(LocalVarEntry e)
  { vars.addElement(e); }

  void resolve(ClassEnv e)
  {
    e.addCPItem(attr);

    for (Enumeration en = vars.elements(); en.hasMoreElements();)
      {
        LocalVarEntry lv = (LocalVarEntry)(en.nextElement());
        lv.resolve(e);
      }
  }

  int size()
  { return      
      (2 +                      // name_idx
       4 +                      // attr_len
       2 +                      // line table len spec
       10*(vars.size()));       // table
  }

  void write(ClassEnv e, CodeAttr ce, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(e.getCPIndex(attr));
    out.writeInt(2 + 10*(vars.size()));
    out.writeShort(vars.size());
    for (Enumeration en = vars.elements(); en.hasMoreElements();)
      {
	LocalVarEntry lv = (LocalVarEntry)(en.nextElement());
	lv.write(e, ce, out);
      }
  }
}
