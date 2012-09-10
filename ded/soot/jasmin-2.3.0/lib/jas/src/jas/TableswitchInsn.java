/**
 * Some instructions are perniticky enough that its simpler
 * to write them separately instead of smushing them with
 * all the rest. The tableswitch instruction is one of them.
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class TableswitchInsn extends Insn implements RuntimeConstants
{
  /**
   * @param min minimum index value
   * @param max maximum index value
   * @param def default Label for switch
   * @param j array of Labels, one for each possible index.
   */

  public TableswitchInsn(int min, int max, Label def, Label j[])
  {
    opc = opc_tableswitch;
    operand = new TableswitchOperand(this, min, max, def, j);
  }
}
