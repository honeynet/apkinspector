package scm;

class Selfrep implements Obj
{ String val; double num;
  public Obj eval(Env e) { return this; }

  Selfrep(double num)
  { super(); this.num = num; val = null; }
  Selfrep(String s)
  { super(); val = s; }

  public String toString()
  {
    if (val == null)
      return ("Number: " + num);
    return ("String: " + val);
  }
}
