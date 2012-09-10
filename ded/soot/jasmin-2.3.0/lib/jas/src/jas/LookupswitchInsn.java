/**
 * Some instructions are perniticky enough that its simpler
 * to write them separately instead of smushing them with
 * all the rest. The lookupswitch instruction is one of them.
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class LookupswitchInsn extends Insn implements RuntimeConstants
{
  /**
   * @param def default Label for switch
   * @param match array of match values for switch
   * @param target Label array of corresponding targets for each match
   */

  public LookupswitchInsn(Label def, int match[], Label target[])
  {
    opc = opc_lookupswitch;
    operand = new LookupswitchOperand(this, def, match, target);
  }
}
