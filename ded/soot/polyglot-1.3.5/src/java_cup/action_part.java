
package java_cup;

/** 
 * This class represents a part of a production which contains an
 * action.  These are eventually eliminated from productions and converted
 * to trailing actions by factoring out with a production that derives the
 * empty string (and ends with this action).
 *
 * @see java_cup.production
 * @version last update: 11/25/95
 * @author Scott Hudson
 */

public class action_part extends production_part {

  /*-----------------------------------------------------------*/
  /*--- Constructors ------------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Simple constructor. 
   * @param code_str string containing the actual user code.
   */
  public action_part(String code_str)
    {
      super(/* never have a label on code */null);
      _code_string = code_str;
    }

  /*-----------------------------------------------------------*/
  /*--- (Access to) Instance Variables ------------------------*/
  /*-----------------------------------------------------------*/

  /** String containing code for the action in question. */
  protected String _code_string;

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** String containing code for the action in question. */
  public String code_string() {return _code_string;}

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Set the code string. */
  public void set_code_string(String new_str) {_code_string = new_str;}

  /*-----------------------------------------------------------*/
  /*--- General Methods ---------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Override to report this object as an action. */
  public boolean is_action() { return true; }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Equality comparison for properly typed object. */
  public boolean equals(action_part other)
    {
      /* compare the strings */
      return other != null && super.equals(other) && 
	     other.code_string().equals(code_string());
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Generic equality comparison. */
  public boolean equals(Object other)
    {
      if (!(other instanceof action_part)) 
	return false;
      else
	return equals((action_part)other);
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Produce a hash code. */
  public int hashCode()
    {
      return super.hashCode() ^ 
	     (code_string()==null ? 0 : code_string().hashCode());
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Convert to a string.  */
  public String toString()
    {
      return super.toString() + "{" + code_string() + "}";
    }

  /*-----------------------------------------------------------*/
}
