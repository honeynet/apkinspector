                                // Cons cell
package scm;

class Cell implements Obj
{
  Obj car;
  Cell cdr;			// this should be a Obj, but its
				// a mite quicker to bag the cast check,
				// and be unable to do (a . b)
  public Obj eval(Env e)
    throws Exception
  {
    Procedure p;

    if (car == null)
      { throw new SchemeError("null car cell trying to eval " + this); }

    if (car instanceof Procedure)
      { p = (Procedure) car; }
    else
      { p = (Procedure) car.eval(e); }

    return (p.apply(cdr, e));   // primitive ops override the default
                                // compound procedures by subclassing
  }

  Cell (Obj a, Cell b)
  { car = a; cdr = b; }

  public String toString()
  {
    return toString("");
  }
  public String toString(String s)
  {
    if (car == null) { s += "()"; }
    else 
      { s += car.toString(); }

    if (cdr == null)
      { return ( "(" + s + ")"); }
    else
      { return (cdr.toString(s + " ")); }
  }
}

