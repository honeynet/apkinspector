
package java_cup;

/** This class represents one row (corresponding to one machine state) of the 
 *  reduce-goto parse table. 
 */
public class parse_reduce_row {
  /*-----------------------------------------------------------*/
  /*--- Constructor(s) ----------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Simple constructor. Note: this should not be used until the number
   *  of terminals in the grammar has been established.
   */
  public parse_reduce_row()
    {
      /* make sure the size is set */
      if (_size <= 0 )  _size = non_terminal.number();

      /* allocate the array */
      under_non_term = new lalr_state[size()];
    }

  /*-----------------------------------------------------------*/
  /*--- (Access to) Static (Class) Variables ------------------*/
  /*-----------------------------------------------------------*/

  /** Number of columns (non terminals) in every row. */
  protected static int _size = 0;

  /** Number of columns (non terminals) in every row. */
  public static int size() {return _size;}
   
  /*-----------------------------------------------------------*/
  /*--- (Access to) Instance Variables ------------------------*/
  /*-----------------------------------------------------------*/

  /** Actual entries for the row. */
  public lalr_state under_non_term[];
}

