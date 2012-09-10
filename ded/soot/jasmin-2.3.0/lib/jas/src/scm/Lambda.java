package scm;
				// create a new procedure

class Lambda extends Procedure implements Obj
{
                                // massage arguments to a real procedure
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Procedure ret = new Procedure();

    if (args == null)
      { throw new SchemeError("null args to Lambda"); }
    ret.formals = (Cell) args.car;
    ret.body = args.cdr;
    ret.procenv = f;
    return ret;
  }
  public String toString()
  {
    return ("<#Lambda#>");
  }
}
