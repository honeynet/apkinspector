package polyglot.lex;

import java_cup.runtime.Symbol;
import polyglot.util.Position;

/** A token class for identifiers. */
public class Identifier extends Token {
  String identifier;
  public Identifier(Position position, String identifier, int sym)
  {
	super(position, sym);
	this.identifier=identifier;
  }

  public String getIdentifier() { return identifier; }

  public String toString() { return "identifier \"" + identifier + "\""; }
}
