package polyglot.lex;

import polyglot.util.Position;

/** A token class for literals. */
public abstract class Literal extends Token
{
  public Literal(Position position, int sym) { super(position, sym); }
}
