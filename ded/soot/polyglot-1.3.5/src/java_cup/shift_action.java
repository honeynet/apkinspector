
package java_cup;

/** This class represents a shift action within the parse table. 
 *  The action simply stores the state that it shifts to and responds 
 *  to queries about its type.
 *
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */
public class shift_action extends parse_action {

  /*-----------------------------------------------------------*/
  /*--- Constructor(s) ----------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Simple constructor. 
   * @param shft_to the state that this action shifts to.
   */
  public shift_action(lalr_state shft_to) throws internal_error
    {
      /* sanity check */
      if (shft_to == null)
	throw new internal_error(
	  "Attempt to create a shift_action to a null state");

      _shift_to = shft_to;
    }

  /*-----------------------------------------------------------*/
  /*--- (Access to) Instance Variables ------------------------*/
  /*-----------------------------------------------------------*/

  /** The state we shift to. */
  protected lalr_state _shift_to;

  /** The state we shift to. */
  public lalr_state shift_to() {return _shift_to;}

  /*-----------------------------------------------------------*/
  /*--- General Methods ---------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Quick access to type of action. */
  public int kind() {return SHIFT;}

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Equality test. */
  public boolean equals(shift_action other)
    {
      return other != null && other.shift_to() == shift_to();
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Generic equality test. */
  public boolean equals(Object other)
    {
      if (other instanceof shift_action)
	return equals((shift_action)other);
      else
       return false;
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Compute a hash code. */
  public int hashCode()
    {
      /* use the hash code of the state we are shifting to */
      return shift_to().hashCode();
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Convert to a string. */
  public String toString() {return "SHIFT(to state " + shift_to().index() + ")";}

  /*-----------------------------------------------------------*/

}
