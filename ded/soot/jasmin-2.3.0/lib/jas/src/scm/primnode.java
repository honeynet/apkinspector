package scm;

                                // Encapsulate a random java class
                                // as a new type.

class primnode implements Obj
{
  Object val;

  public Obj eval(Env e) { return this; }

  primnode(Object thing) { this.val = thing; }

  public String toString() { return val.toString(); }
}

