
package java_cup;

/** A specialized version of a production used when we split an existing
 *  production in order to remove an embedded action.  Here we keep a bit 
 *  of extra bookkeeping so that we know where we came from.
 * @version last updated: 11/25/95
 * @author  Scott Hudson
 */

public class action_production extends production {

  /** Constructor.
   * @param base       the production we are being factored out of.
   * @param lhs_sym    the LHS symbol for this production.
   * @param rhs_parts  array of production parts for the RHS.
   * @param rhs_len    how much of the rhs_parts array is valid.
   * @param action_str the trailing reduce action for this production.
   */ 
  public action_production(
    production      base,
    non_terminal    lhs_sym, 
    production_part rhs_parts[],
    int             rhs_len,
    String          action_str)
    throws internal_error
    {
      super(lhs_sym, rhs_parts, rhs_len, action_str);
      _base_production = base;
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** The production we were taken out of. */
  protected production _base_production;

  /** The production we were taken out of. */
  public production base_production() {return _base_production;}
}
