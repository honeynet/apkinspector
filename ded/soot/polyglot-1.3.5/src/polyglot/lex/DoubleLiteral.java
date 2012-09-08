package polyglot.lex;

import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** Token class for double literals. */
public class DoubleLiteral extends NumericLiteral {
  public DoubleLiteral(Position position, double d, int sym) {
      super(position, sym);
      this.val = new Double(d);
  }
}
