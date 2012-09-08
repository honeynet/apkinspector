package polyglot.lex;

import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** Token class for char literals. */
public class CharacterLiteral extends Literal {
  Character val;

  public CharacterLiteral(Position position, char c, int sym)
  {
    super(position, sym); 
    this.val = new Character(c); 
  }
  
  public Character getValue() 
  {
    return val;
  }

  public String getEscapedValue()
  {
    return Token.escape( String.valueOf( val));
  }

  public String toString() 
  {
    return "char literal '" + getEscapedValue() + "'";
  }
}
