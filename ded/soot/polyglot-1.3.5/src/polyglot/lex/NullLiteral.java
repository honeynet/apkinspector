package polyglot.lex;

import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** A token class for null literals. */
public class NullLiteral extends Literal {
  public NullLiteral(Position position, int sym) { super(position, sym); }
  public String toString() { return "literal null"; }
}
