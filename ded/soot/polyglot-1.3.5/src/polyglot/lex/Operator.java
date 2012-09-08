package polyglot.lex;

import java.util.Hashtable;
import polyglot.util.Position;
import java_cup.runtime.Symbol;

/** A token class for operators. */
public class Operator extends Token {
  String which;
  public Operator(Position position, String which, int sym) {
      super(position, sym);
      this.which = which;
  }

  public String toString() { return "operator " + which; }
}
