package scm;

                                // This is the (usual) compound procedure
                                // object
/**
 * This is a container class that is overidden
 * by primitives. It can be generated through
 * @see jas.Lambda procedures.
 */

class Procedure implements Obj
{
  Cell body;                    // the seq of expressions constituting
                                // the body of the procedure
  Cell formals;                 // the arglist expected by the procedure
  Env procenv;                  // env in which the proc was created


  Env extendargs(Cell args, Env f)
    throws Exception
  {
    Cell params = null;
    Cell tail = null;
    while (args != null)
      {
        Obj now = args.car;
        if (now != null) 
          { now = now.eval(f); } // eval args in context of old expression
        if (tail != null)
          {
            tail.cdr = new Cell(now, null);
            tail = tail.cdr;
          }
        else
          {
            params = new Cell(now, params);
            tail = params;
          }
        args = args.cdr;
      }
                                // make new frame, with appropriate
                                // bindings. The enclosing frame
                                // is the env in which the procedure
                                // was created.
    return (procenv.extendenv(formals, params));
  }

  Obj apply(Cell args, Env f)
    throws Exception
  {
    Env newEnv = extendargs(args, f);
    Cell expr = body;
    Obj ret = null;
                                // eval body with new bindings
    while (expr != null)
      {
        ret = expr.car;
        if (ret != null)
          { ret = ret.eval(newEnv); }
        expr =  expr.cdr;
      }
    return (ret);
  }
  public Obj eval(Env e)
  { throw new SchemeError("Cant eval procedures directly"); }

  public String toString()
  {
    return ("<lambda generated> " + body);
  }
}

                                // do a few primitives here
/**
 * Add two integers
 * <code> (+ int1 int2) </code>
 */

class Plus extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Obj l1 = args.car.eval(f);
    Obj l2 = args.cdr.car.eval(f);

    return (new Selfrep(((Selfrep)l1).num + ((Selfrep)l2).num));
  }
  public String toString()
  {
    return ("<#plus#>");
  }
}
/**
 * Subtract integers
 * <code>(- int1 int2)
 */

class Minus extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Obj l1 = args.car.eval(f);
    Obj l2 = args.cdr.car.eval(f);

    return (new Selfrep(((Selfrep)l1).num - ((Selfrep)l2).num));
  }
  public String toString()
  {
    return ("<#minus#>");
  }
}
/**
 * Multiply integers
 * <code> (* int1 int2) </code>
 */

class Mult extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Obj l1 = args.car.eval(f);
    Obj l2 = args.cdr.car.eval(f);

    return (new Selfrep(((Selfrep)l1).num * ((Selfrep)l2).num));
  }
  public String toString()
  {
    return ("<#mult#>");
  }
}

/**
 * divide integers
 * <code> (/ int1 int2) </code>
 */

class Div extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Obj l1 = args.car.eval(f);
    Obj l2 = args.cdr.car.eval(f);

    return (new Selfrep(((Selfrep)l1).num / ((Selfrep)l2).num));
  }
  public String toString()
  {
    return ("<#div#>");
  }
}
/**
 * Bitwise or of integers
 * <code> (| int1 int2) </code>
 */

class Or extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Obj l1 = args.car.eval(f);
    Obj l2 = args.cdr.car.eval(f);

    return (new Selfrep
            ((int)(Math.round(((Selfrep)l1).num)) |
             (int)(Math.round(((Selfrep)l2).num))));
  }
  public String toString()
  {
    return ("<#or#>");
  }
}

/**
 * Yup. just as it says.
 * <code> (car (quote (a b)))
 * => a
 * </code>
 */

class Car extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Cell tmp = (Cell) args.car.eval(f);
    return (tmp.car);
  }
  public String toString()
  { return ("<#car#>"); }
}

/**
 * More lispisms.
 * <code> (cdr (quote (a b)))
 * => (b)
 * </code>
 */

class Cdr extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Cell tmp = (Cell) args.car.eval(f);
    return (tmp.cdr);
  }
  public String toString()
  { return ("<#cdr#>"); }
}

/**
 * Generate new list
 * <code> (cons (quote a) (quote (b c))) => (a b c) </code>
 */

 
class Cons extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Obj ncar = args.car.eval(f);
    Obj ncdr = args.cdr.car.eval(f);
    return (new Cell(ncar, (Cell) ncdr));
  }
  public String toString()
  { return ("<#cons#>"); }
}

/**
 * Prevent from evaluation.
 * <code> (quote a) => a </code>
 */

class Quote extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    if (args == null)
      { throw new SchemeError("null args to Quote"); }
    return args.car;
  }
  public String toString()
  { return ("<#Quote#>"); }
}

/**
 * bind a value to a symbol.<p>
 *
 * <code> (define some-new-symbol "some thing") => "some thing"</code><br>
 * <code> some-new-symbol => "some thing" </code>
 */

class Define extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Symbol v;                   // (symbol value)
    if (args == null)
      { throw new SchemeError("null args to define"); }
    if (args.car instanceof Symbol)
      { v = (Symbol) args.car; }
    else
      { throw new SchemeError("bad argtype to define" + args.car); }

    if (v == null)
      { throw new SchemeError("null symbol value"); }

    Cell val =  args.cdr;
    if (val == null)
      { throw new SchemeError("not enough args to define"); }
    Obj ret = val.car;
    if (ret != null) 
      { ret = ret.eval(f); }
    f.definevar(v, ret);
    return ret;
  }
  public String toString()
  { return ("<#define#>"); }
}
/**
 * reset a value to a symbol.<p>
 *
 * <code> (set! some-old-symbol "xyz")
 */

class Setvar extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Symbol v;                   // (symbol value)
    if (args == null)
      { throw new SchemeError("null args to define"); }
    if (args.car instanceof Symbol)
      { v = (Symbol) args.car; }
    else
      { throw new SchemeError("bad argtype to set!" + args.car); }

    if (v == null)
      { throw new SchemeError("null symbol value"); }

    Cell val =  args.cdr;
    if (val == null)
      { throw new SchemeError("not enough args to set!"); }
    Obj ret = val.car;
    if (ret != null) 
      { ret = ret.eval(f); }
    f.setvar(v, ret);
    return ret;
  }
  public String toString()
  { return ("<#set!#>"); }
}

/**
 * (cond (condition body) (condition body)...)
 */

class Cond extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Cell t = args;

    while (t != null)
      {
                                // examine condition part
        if (t.car == null)
          { throw new SchemeError("null clause for cond"); }
        Obj clause = t.car;
        if (!(clause instanceof Cell))
          { throw new SchemeError("need a condition body for cond clause"); }
        Obj result = (((Cell)clause).car);
        if (result != null) { result = result.eval(f); }
        if (result == null)
          { t = t.cdr; continue; }
                                // Got a non nill, so do body and
                                // return.
        Obj body = (((Cell)clause).cdr).car;
        return (body.eval(f));
      }
    return null;
  }
  public String toString()
  { return ("<#cond#>"); }
}

/**
 * (num? thing)
 */

class NumP extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    if (args == null) return null;

    Obj target = args.car;
    if (target != null) target = target.eval(f);
    if (target == null) return null;
    if ((target instanceof Selfrep) &&
        (((Selfrep)target).val == null))
      return target;
    return null;
  }
  public String toString()
  { return ("<#num?#>"); }
}
/**
 * <
 */

class LessP extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    if (args == null)
      { throw new SchemeError("< expects a pair of arguments"); }

    Obj target1 = args.car;
    if (target1 != null) target1 = target1.eval(f);
    args = args.cdr;
    Obj target2 = args.car;
    if (target2 != null) target2 = target2.eval(f);

    if ((target1 == null) ||
        (target2 == null))
      { throw new SchemeError("< expects a pair of arguments"); }
    if (!(target1 instanceof Selfrep) ||
        !(target2 instanceof Selfrep))
      { throw new SchemeError("< expects a pair of numbers as args"); }
    if ((((Selfrep)target1).num) < (((Selfrep)target2).num))
      { return target1; }
    return null;
  }
  public String toString()
  { return ("<#<#>"); }
}
/**
 * >
 */

class MoreP extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    if (args == null)
      { throw new SchemeError("> expects a pair of arguments"); }

    Obj target1 = args.car;
    if (target1 != null) target1 = target1.eval(f);
    args = args.cdr;
    Obj target2 = args.car;
    if (target2 != null) target2 = target2.eval(f);

    if ((target1 == null) ||
        (target2 == null))
      { throw new SchemeError("> expects a pair of arguments"); }
    if (!(target1 instanceof Selfrep) ||
        !(target2 instanceof Selfrep))
      { throw new SchemeError("> expects a pair of numbers as args"); }
    if ((((Selfrep)target1).num) > (((Selfrep)target2).num))
      { return target1; }
    return null;
  }
  public String toString()
  { return ("<#>#>"); }
}
/**
 * (eq? obj1 obj2)
 */

class EqP extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    if (args == null) return null;

    Obj target1 = args.car;
    if (target1 != null) target1 = target1.eval(f);
    args = args.cdr;
    Obj target2 = args.car;
    if (target2 != null) target2 = target2.eval(f);

    if ((target1 == null) &&
        (target2 == null)) return (new Selfrep(1));
    if ((target1 == null) ||
        (target2 == null))
      { return null; }
    
    if (target1 == target2)
      {
        return (target1);
      }
        
    if ((target1 instanceof Selfrep) &&
        (target2 instanceof Selfrep))
      {
        if ((((Selfrep)target1).val) == null)
          {
            if ((((Selfrep)target1).num) == (((Selfrep)target2).num))
              { return new Selfrep(1); }
          }
        else
          {
            if ((((Selfrep)target1).val).equals((((Selfrep)target2).val)))
              { return new Selfrep(1); }
          }            
      }
    return null;
  }
  public String toString()
  { return ("<#eq?#>"); }
}

/**
 * (string? thing)
 */

class StringP extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    if (args == null) return null;

    Obj target = args.car;
    if (target != null) target = target.eval(f);
    if (target == null) return null;
    if ((target instanceof Selfrep) &&
        (((Selfrep)target).val != null))
      return target;
    return null;
  }
  public String toString()
  { return ("<#string?#>"); }
}

/**
 * (progn body1 body2 ...)
 */

class Progn extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Cell t = args;
    Obj result = null;
    while (t != null)
      {
        if (t.car == null)
          {
            result = null;
          }
        else
          {
            result = t.car.eval(f);
          }
        t = t.cdr;
      }
    return result;
  }
  public String toString()
  { return ("<#progn#>"); }
}

/**
 * (mapcar function (args1 args2 ...))
 */

class Mapcar extends Procedure implements Obj
{
  Obj apply(Cell args, Env f)
    throws Exception
  {
    Obj ftmp = args.car;
    if (ftmp != null) ftmp = ftmp.eval(f);
    if (ftmp == null)
      { throw new SchemeError("null function for mapcar"); }
    if (!(ftmp instanceof Procedure))
      { throw new SchemeError("expected a procedure for mapcar"); }
    Procedure fn = (Procedure) ftmp;

    Cell t = (Cell)((args.cdr.car).eval(f));
    Cell res = null;
    Cell tail = null;
    while (t != null)
      {
        if (tail == null)
          { 
            res =
              new Cell
              (fn.apply
               (new Cell((t.car), null), f),
              null);
            tail = res;
          }
        else
          {
            tail.cdr =
              new Cell
              (fn.apply
               (new Cell((t.car), null), f),
               null);
          }
        t = t.cdr;
      }
    return res;
  }
}
