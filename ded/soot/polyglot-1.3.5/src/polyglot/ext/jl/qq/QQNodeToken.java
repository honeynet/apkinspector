package polyglot.ext.jl.qq;

import polyglot.lex.*;
import polyglot.ast.*;
import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** A token class for int literals. */
public class QQNodeToken extends Token {
  Node node;

  public QQNodeToken(Position position, Node node, int sym) {
      super(position, sym);
      this.node = node;
  }

  public Node node() {
      return node;
  }

  public String toString() {
      return "qq" + symbol() + "(" + node + ")";
  }
}
