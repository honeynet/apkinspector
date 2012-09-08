package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>ArrayAccessAssign_c</code> represents a Java assignment expression
 * to an array element.  For instance, <code>A[3] = e</code>.
 * 
 * The class of the <code>Expr</code> returned by
 * <code>ArrayAccessAssign_c.left()</code>is guaranteed to be an
 * <code>ArrayAccess</code>.
 */
public class ArrayAccessAssign_c extends Assign_c implements ArrayAccessAssign
{
  public ArrayAccessAssign_c(Position pos, ArrayAccess left, Operator op, Expr right) {
    super(pos, left, op, right);
  }

  public Assign left(Expr left) {
      ArrayAccessAssign_c n = (ArrayAccessAssign_c)super.left(left);
      n.assertLeftType();
      return n;
  }
  
  private void assertLeftType() {
      if (!(left() instanceof ArrayAccess)) {
          throw new InternalCompilerError("left expression of an ArrayAccessAssign must be an array access");
      }
  }
  
  public Term entry() {
      return left().entry();
  }
  
  protected void acceptCFGAssign(CFGBuilder v) {
      ArrayAccess a = (ArrayAccess)left();
      
      //    a[i] = e: visit a -> i -> e -> (a[i] = e)
      v.visitCFG(a.array(), a.index().entry());
      v.visitCFG(a.index(), right().entry());
      v.visitCFG(right(), this);
  }
  protected void acceptCFGOpAssign(CFGBuilder v) {
      ArrayAccess a = (ArrayAccess)left();
      
      // a[i] OP= e: visit a -> i -> a[i] -> e -> (a[i] OP= e)
      v.visitCFG(a.array(), a.index().entry());
      v.visitCFG(a.index(), a);
      v.visitThrow(a);
      v.edge(a, right().entry());
      v.visitCFG(right(), this);
  }

  public List throwTypes(TypeSystem ts) {
      List l = new ArrayList(super.throwTypes(ts));
      
      if (throwsArrayStoreException()) {
          l.add(ts.ArrayStoreException());
      }
      
      l.add(ts.NullPointerException());
      l.add(ts.OutOfBoundsException());
      
      return l;
  }
  
  /** Get the throwsArrayStoreException of the expression. */
  public boolean throwsArrayStoreException() {
    return op == ASSIGN && left.type().isReference();
  }

}
