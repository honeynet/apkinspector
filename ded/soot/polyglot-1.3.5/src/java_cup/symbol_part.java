package java_cup;

/** This class represents a part of a production which is a symbol (terminal
 *  or non terminal).  This simply maintains a reference to the symbol in 
 *  question.
 *
 * @see     java_cup.production
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public class symbol_part extends production_part {

  /*-----------------------------------------------------------*/
  /*--- Constructor(s) ----------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Full constructor. 
   * @param sym the symbol that this part is made up of.
   * @param lab an optional label string for the part.
   */
  public symbol_part(symbol sym, String lab) throws internal_error
    {
      super(lab);

      if (sym == null)
	throw new internal_error(
	  "Attempt to construct a symbol_part with a null symbol");
      _the_symbol = sym;
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Constructor with no label. 
   * @param sym the symbol that this part is made up of.
   */
  public symbol_part(symbol sym) throws internal_error
    {
      this(sym,null);
    }

  /*-----------------------------------------------------------*/
  /*--- (Access to) Instance Variables ------------------------*/
  /*-----------------------------------------------------------*/

  /** The symbol that this part is made up of. */
  protected symbol _the_symbol;

  /** The symbol that this part is made up of. */
  public symbol the_symbol() {return _the_symbol;}

  /*-----------------------------------------------------------*/
  /*--- General Methods ---------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Respond that we are not an action part. */
  public boolean is_action() { return false; }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Equality comparison. */
  public boolean equals(symbol_part other)
    {
      return other != null && super.equals(other) && 
	     the_symbol().equals(other.the_symbol());
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Generic equality comparison. */
  public boolean equals(Object other)
    {
      if (!(other instanceof symbol_part))
	return false;
      else
	return equals((symbol_part)other);
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Produce a hash code. */
  public int hashCode()
    {
      return super.hashCode() ^ 
	     (the_symbol()==null ? 0 : the_symbol().hashCode());
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Convert to a string. */
  public String toString()
    {
      if (the_symbol() != null)
	return super.toString() + the_symbol();
      else
	return super.toString() + "$$MISSING-SYMBOL$$";
    }

  /*-----------------------------------------------------------*/

}
