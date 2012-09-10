/**
 * This attribute is used to embed Source file information into
 * a class file.
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;

public class SourceAttr
{
  static CP attr = new AsciiCP("SourceFile");

  CP name;

  /**
   * Create a source file attribute.
   * @param name Name of the source file
   * @see ClassEnv#setSource
   */

  public SourceAttr(String name)
  { this.name = new AsciiCP(name); }

  /**
   * Create a source file attribute, with more control over attribute name
   * @param cp CP to be associated as the name of the source file
   * @see ClassEnv#setSource
   */
  public SourceAttr(CP name)
  { this.name = name; }

  void resolve(ClassEnv e)
  { e.addCPItem(attr); e.addCPItem(name); }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(e.getCPIndex(attr));
    out.writeInt(2);
    out.writeShort(e.getCPIndex(name));
  }
}

