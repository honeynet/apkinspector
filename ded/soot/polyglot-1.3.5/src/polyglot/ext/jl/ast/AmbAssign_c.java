package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * A <code>AmbAssign</code> represents a Java assignment expression to
 * an as yet unknown expression.
 */
public class AmbAssign_c extends Assign_c implements AmbAssign
{
  public AmbAssign_c(Position pos, Expr left, Operator op, Expr right) {
    super(pos, left, op, right);
  }

  public Term entry() {
    if (operator() != Assign.ASSIGN) {
      return left();
    }

    return right().entry();
  }
  
  protected void acceptCFGAssign(CFGBuilder v) {
      v.visitCFG(right(), this);
  }
  
  protected void acceptCFGOpAssign(CFGBuilder v) {
      v.edge(left(), right().entry());
      v.visitCFG(right(), this);
  }
  
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
      Assign n = (Assign) super.disambiguate(ar);
      
      if (n.left() instanceof Local) {
          return ar.nodeFactory().LocalAssign(n.position(), (Local)left(), operator(), right());
      }
      else if (n.left() instanceof Field) {
          return ar.nodeFactory().FieldAssign(n.position(), (Field)left(), operator(), right());
      } 
      else if (n.left() instanceof ArrayAccess) {
          return ar.nodeFactory().ArrayAccessAssign(n.position(), (ArrayAccess)left(), operator(), right());
      }

      throw new SemanticException("Could not disambiguate left side of assignment!", n.position());
  }
  
}
