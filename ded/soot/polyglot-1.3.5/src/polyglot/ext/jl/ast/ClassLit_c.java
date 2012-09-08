package polyglot.ext.jl.ast;

import polyglot.ast.ClassLit;
import polyglot.ast.Expr;
import polyglot.ast.Node;
import polyglot.ast.TypeNode;
import polyglot.types.SemanticException;
import polyglot.util.CodeWriter;
import polyglot.util.Position;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.TypeChecker;

/**
 * A <code>ClassLit</code> represents a class literal expression. 
 * A class literal expressions is an expression consisting of the 
 * name of a class, interface, array, or primitive type followed by a period (.) 
 * and the token class. 
 */
public class ClassLit_c extends Lit_c implements ClassLit
{
  protected TypeNode typeNode;

  public ClassLit_c(Position pos, TypeNode typeNode) {
    super(pos);
    this.typeNode = typeNode;
  }

  public TypeNode typeNode() {
    return this.typeNode;
  }

  public ClassLit typeNode(TypeNode typeNode) {
      if (this.typeNode == typeNode) {
          return this;
      }
    ClassLit_c n = (ClassLit_c) copy();
    n.typeNode = typeNode;
    return n;
  }
    
  /**
   * Cannot return the correct object (except for maybe
   * some of the primitive arrays), so we just return null here. 
   */
  public Object objValue() {
    return null;
  }

  public Node visitChildren(NodeVisitor v) {
    TypeNode tn = (TypeNode)visitChild(this.typeNode, v);
    return this.typeNode(tn);
  }

  /** Type check the expression. */
  public Node typeCheck(TypeChecker tc) throws SemanticException {
    return type(tc.typeSystem().Class());
  }

  public String toString() {
    return typeNode.toString() + ".class";
  }

  /** Write the expression to an output file. */
  public void prettyPrint(CodeWriter w, PrettyPrinter tr)
  {
    w.begin(0);
    print(typeNode, w, tr);
    w.write(".class");
    w.end();
  }

  /**
   * According to the JLS 2nd Ed, sec 15.28, a class literal 
   * is not a compile time constant.
   */
  public boolean isConstant() {
    return false;
  }

  public Object constantValue() {
    return null;
  }
}
