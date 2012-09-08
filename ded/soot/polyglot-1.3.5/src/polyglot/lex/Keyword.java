package polyglot.lex;

import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** A token class for keywords. */
public class Keyword extends Token {
  String keyword;

  public Keyword(Position position, String s, int sym) {
      super(position, sym);
      keyword = s;
  }

  public String toString() {
      return keyword;
  }
}
