package polyglot.ext.jl.qq;

import polyglot.lex.*;
import polyglot.ast.*;
import java_cup.runtime.Symbol;
import polyglot.util.Position;
import java.util.List;

/** A token class for int literals. */
public class QQListToken extends Token {
  List list;

  public QQListToken(Position position, List list, int sym) {
      super(position, sym);
      this.list = list;
  }

  public List list() {
      return list;
  }

  public String toString() {
      return "qq(" + list + ")";
  }
}
