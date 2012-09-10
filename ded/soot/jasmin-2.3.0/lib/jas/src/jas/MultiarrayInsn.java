/**
 * Some instructions are perniticky enough that its simpler
 * to write them separately instead of smushing them with
 * all the rest. the multiarray instruction is one of them.
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;


public class MultiarrayInsn extends Insn implements RuntimeConstants
{
  /**
   * @param cpe CP item for the array type
   * @param sz number of dimensions for the array
   */
  public MultiarrayInsn(CP cpe, int sz)
  {
    opc = opc_multianewarray;
    operand = new MultiarrayOperand(cpe, sz);
  }
}
