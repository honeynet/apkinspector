package polyglot.lex;

import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** A token class for float literals. */
public class FloatLiteral extends NumericLiteral {
  public FloatLiteral(Position position, float f, int sym) {
      super(position, sym);
      this.val = new Float(f);
  }
}
