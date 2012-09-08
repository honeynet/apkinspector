package polyglot.lex;

import polyglot.util.Position;
 
/** The base class of all tokens. */
public abstract class Token {
  Position position;
  int symbol;

  public Token(Position position, int symbol)
  {
    this.position = position;
    this.symbol = symbol;
  }

  public Position getPosition()
  {
    return position;
  }

  public java_cup.runtime.Symbol symbol() {
      return new java_cup.runtime.Symbol(symbol, this);
  }

  protected static String escape(String s) {
    StringBuffer sb = new StringBuffer();
    for (int i=0; i<s.length(); i++)
      switch(s.charAt(i)) {
      case '\t': sb.append("\\t"); break;
      case '\f': sb.append("\\f"); break;
      case '\n': sb.append("\\n"); break;
      default:
	if ((int)s.charAt(i)<0x20 ||
              ((int)s.charAt(i) > 0x7e && (int)s.charAt(i) < 0xFF))
	  sb.append("\\"+Integer.toOctalString((int)s.charAt(i)));
	else
	  sb.append(s.charAt(i));
      }
    return sb.toString();
  }
}
