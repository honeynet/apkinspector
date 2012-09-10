/**
 * Labels are implemented as Insn's, but are special (read
 * unseemly blobs of hacked up code). First, they don't
 * actually cause any code to be written, and second, are
 * identified globally through a String label that is associated
 * with them when they are created.
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class Label extends Insn implements RuntimeConstants
{
  String id;

  /**
   * Create a new Label with this tag. Any label with this tag
   * will be treated as being identical to this one. You can
   * reuse labels if you like
   */
  public Label(String tag)
  {
    id = tag.intern();
    opc = opc_label;
    operand = null;
  }
                                // override the write method to do nothing.
  void write(ClassEnv e, CodeAttr ce, DataOutputStream out)
  { return; }
                                // and the size method appropriately
  int size(ClassEnv e, CodeAttr ce)
  { return 0; }
                                // This is called from the LabelOperand
  void writeOffset(CodeAttr ce, Insn source, DataOutputStream out)
    throws jasError, IOException
  {                             // write the offset (as a short)
                                // of source
    int pc, tpc;
    pc = ce.getPc(this);
    if (source == null)
      tpc = 0;
    else
      tpc = ce.getPc(source);
    short offset = (short) (pc - tpc);
    out.writeShort(offset);
  }
  void writeWideOffset(CodeAttr ce, Insn source, DataOutputStream out)
     throws IOException, jasError
  {
    int pc, tpc;
    pc = ce.getPc(this);
    if (source == null)
      tpc = 0;
    else
      tpc = ce.getPc(source);
    out.writeInt(pc - tpc);
  }
  public String toString()
  {
    return ("Label: " + id);
  }
}

