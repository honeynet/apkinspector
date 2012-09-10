/**
 * This is an entry to be used as part of a LocalVarTableAttr.
 * It defines the scope and location of a local variable for a method
 * and is used while debugging.
 */

package jas;

import java.io.*;

public class LocalVarEntry
{
  Label start, end;
  CP name, sig;
  int slot;

  /**
   * Create local variable scope information that can be used
   * while debugging.
   * @param startLabel beginning of scope for variable
   * @param endLabel   end of scope for variable
   * @param name       name of variable
   * @param sig        signature for variable
   * @param slot       index of the local variable in the local variables
   *                   registers of the VM where its value can be found
   */
  public LocalVarEntry
  (Label startLabel, Label endLabel, String name, String sig, int slot)
  {
    start = startLabel;
    end = endLabel;
    this.name = new AsciiCP(name);
    this.sig = new AsciiCP(sig);
    this.slot = slot;
  }

  /**
   * Create local variable scope information that can be used
   * while debugging.
   * @param startLabel beginning of scope for variable
   * @param endLabel   end of scope for variable
   * @param name       CP to be associated as name of variable
   * @param sig        CP to be associated as signature for variable
   * @param slot       The index of the local variable in the local
   *                   variables part of the VM where its value can be found
   */
  public LocalVarEntry
  (Label startLabel, Label endLabel, CP name, CP sig, int slot)
  {
    start = startLabel;
    end = endLabel;
    this.name = name;
    this.sig = sig;
    this.slot = slot;
  }

  void resolve(ClassEnv e)
  { e.addCPItem(name); e.addCPItem(sig); }

  void write(ClassEnv e, CodeAttr ce, DataOutputStream out)
    throws IOException, jasError
  {
    start.writeOffset(ce, null, out);
    end.writeOffset(ce, start, out); // This is the *length*,
				// not another offset

    out.writeShort(e.getCPIndex(name));
    out.writeShort(e.getCPIndex(sig));
    out.writeShort((short)slot);
  }
}
