package polyglot.lex;

import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** Token class for boolean literals. */
public class BooleanLiteral extends Literal {
  Boolean val;
  public BooleanLiteral(Position position, boolean b, int sym) {
      super(position, sym);
      this.val = Boolean.valueOf(b);
  }
  
  public Boolean getValue() { return val; }

  public String toString() { return "boolean literal " + val.toString(); }
}
