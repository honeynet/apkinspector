package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * An <code>AmbExpr</code> is an ambiguous AST node composed of a single
 * identifier that must resolve to an expression.
 */
public class AmbExpr_c extends Expr_c implements AmbExpr
{
  protected String name;

  public AmbExpr_c(Position pos, String name) {
    super(pos);
    this.name = name;
  }

  /** Get the precedence of the field. */
  public Precedence precedence() {
    return Precedence.LITERAL;
  }

  /** Get the name of the expression. */
  public String name() {
    return this.name;
  }

  /** Set the name of the expression. */
  public AmbExpr name(String name) {
    AmbExpr_c n = (AmbExpr_c) copy();
    n.name = name;
    return n;
  }

  /** Disambiguate the expression. */
  public Node disambiguate(AmbiguityRemover ar) throws SemanticException {
    Node n = ar.nodeFactory().disamb().disambiguate(this, ar, position(),
                                                    null, name);

    if (n instanceof Expr) {
      return n;
    }

    throw new SemanticException("Could not find field or local " +
                                "variable \"" + name + "\".", position());
  }

  /** Type check the expression. */
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    throw new InternalCompilerError(position(),
                                    "Cannot type check ambiguous node "
                                    + this + ".");
  } 

  /** Check exceptions thrown by the expression. */
  public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
    throw new InternalCompilerError(position(),
                                    "Cannot exception check ambiguous node "
                                    + this + ".");
  } 

  /** Write the expression to an output file. */
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
    w.write(name);
  }

  public String toString() {
    return name + "{amb}";
  }

  /**
   * Return the first (sub)term performed when evaluating this
   * term.
   */
  public Term entry() {
      return this;
  }

  /**
   * Visit this term in evaluation order.
   */
  public List acceptCFG(CFGBuilder v, List succs) {
      return succs;
  }

  public void dump(CodeWriter w) {
    super.dump(w);
    w.allowBreak(4, " ");
    w.begin(0);
    w.write("(name \"" + name + "\")");
    w.end();
  }
}
