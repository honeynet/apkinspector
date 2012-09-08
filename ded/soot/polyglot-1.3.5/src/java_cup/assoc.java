package java_cup;

/* Defines integers that represent the associativity of terminals
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */

public class assoc {

  /* various associativities, no_prec being the default value */
  public final static int left = 0;
  public final static int right = 1;
  public final static int nonassoc = 2;
  public final static int no_prec = -1;

}