package scm;

import jas.*;

class InsnProcedure extends Procedure implements Obj, jas.RuntimeConstants
{
  int opc;

  Obj apply(Cell args, Env f)
    throws Exception
  {
    if (args == null) 
      { return new primnode(new Insn(opc)); }
    Obj t = (args.car).eval(f);
    if (t instanceof Selfrep)
      {
                                // Single integer arg
        int val = (int) (((Selfrep)t).num);
        return new primnode(new Insn(opc, val));
      }
    if (t instanceof primnode)
      {
        Object tprime = ((primnode)t).val;
        if (tprime instanceof CP)
          {
                                // Single CP argument
            return new primnode(new Insn(opc, (CP)tprime));
          }
                                // Labels
        if (tprime instanceof Label)
          {
            return new primnode(new Insn(opc, (Label)tprime));
          }
      }
    throw new SchemeError("Sorry, not yet implemented " + this.toString());
  }

  InsnProcedure(int opc) { this.opc = opc; }
  public String toString()
  { return ("<#insn "+opcNames[opc]+"#>"); }
}
