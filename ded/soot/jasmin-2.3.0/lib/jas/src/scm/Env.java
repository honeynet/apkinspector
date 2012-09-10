package scm;

                                // an environment for the underlying
                                // evaluator

import java.util.*;

class Env
{
  Hashtable bindings=null;      // bindings are lazily created
                                // during a set variable
  static Object MAGIC_KLUDGE="**jas-nil-internal";
                                // record "null" as this.

  Env parent;

  Obj lookup(Symbol cvar)
  {
    Object ret;
    Env f;
    Hashtable b;

    for (f=this, ret=null, b=bindings;
         (ret == null) && (f != null);
         f = f.parent)
      {
        b = f.bindings;
        if (b != null) { ret = b.get(cvar); }
      }
    if (ret == null)
      { throw new SchemeError("Unbound variable " + cvar); }
    if (ret == MAGIC_KLUDGE)
      return null;
    else
      return ((Obj)ret);
  }

  void setvar(Symbol cvar, Obj val)
  {
    Object ret;
    Env f;
    Hashtable b;

    for (f=this, ret=null, b=bindings;
         (ret == null) && (f != null);
         f = f.parent)
      {
        b = f.bindings;
        if (b != null) { ret = b.get(cvar); }
      }
    if (ret == null)
      { throw new SchemeError("Attempted to set unbound variable " + cvar); }
    if (val == null)
      { b.put(cvar, MAGIC_KLUDGE); }
    else
      { b.put(cvar, val); }
  }

  void definevar(Symbol v, Obj val)
  {
    if (bindings == null) bindings = new Hashtable();
    if (val == null)
      { bindings.put(v, MAGIC_KLUDGE); }
    else
      { bindings.put(v, val); }
  }

  Env extendenv(Cell formals, Cell params)
  {
    Symbol thissym; Obj thisval;
    Env ret = new Env();

    ret.parent = this;
    if (formals == null)
      {
        if (params != null)
          { throw new SchemeError("mismatched arglist to entend env"); } }
    else
      {
        ret.bindings = new Hashtable();
        while (formals != null)
          {
            thissym = (Symbol) formals.car;
            thisval = params.car;
            if (thisval == null)
              {ret.bindings.put(thissym, MAGIC_KLUDGE);}
            else
              {ret.bindings.put(thissym, thisval);}
            formals = formals.cdr;
            params =  params.cdr;
          }
        if (params != null)
          { throw new SchemeError("mismatched arglist to extend env"); } }
    return ret;
  }
  public String toString()
  {
    String ret;

    return ("Binding is " + bindings + "\nparent is " + parent);
  }
}
