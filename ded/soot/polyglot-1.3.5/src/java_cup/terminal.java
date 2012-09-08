package java_cup;

import java_cup.assoc;
import java.util.Hashtable;
import java.util.Enumeration;

/** This class represents a terminal symbol in the grammar.  Each terminal 
 *  has a textual name, an index, and a string which indicates the type of 
 *  object it will be implemented with at runtime (i.e. the class of object 
 *  that will be returned by the scanner and pushed on the parse stack to 
 *  represent it). 
 *
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */
public class terminal extends symbol {

  /*-----------------------------------------------------------*/
  /*--- Constructor(s) ----------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Full constructor.
   * @param nm the name of the terminal.
   * @param tp the type of the terminal.
   */
  public terminal(String nm, String tp, int precedence_side, int precedence_num) 
    {
      /* superclass does most of the work */
      super(nm, tp);

      /* add to set of all terminals and check for duplicates */
      Object conflict = _all.put(nm,this);
      if (conflict != null)
	// can't throw an execption here because this is used in static 
	// initializers, so we do a crash instead
	// was:
	// throw new internal_error("Duplicate terminal (" + nm + ") created");
	(new internal_error("Duplicate terminal (" + nm + ") created")).crash();

      /* assign a unique index */
      _index = next_index++;

      /* set the precedence */
      _precedence_num = precedence_num;
      _precedence_side = precedence_side;

      /* add to by_index set */
      _all_by_index.put(new Integer(_index), this);
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Constructor for non-precedented terminal
    */ 

  public terminal(String nm, String tp) 
    {
      this(nm, tp, assoc.no_prec, -1);
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Constructor with default type. 
   * @param nm the name of the terminal.
   */
  public terminal(String nm) 
    {
      this(nm, null);
    }

  /*-----------------------------------------------------------*/
  /*-------------------  Class Variables  ---------------------*/
  /*-----------------------------------------------------------*/

  private int _precedence_num;
  private int _precedence_side;

  /*-----------------------------------------------------------*/
  /*--- (Access to) Static (Class) Variables ------------------*/
  /*-----------------------------------------------------------*/

  /** Table of all terminals.  Elements are stored using name strings as 
   *  the key 
   */
  protected static Hashtable _all = new Hashtable();

  /** Access to all terminals. */
  public static Enumeration all() {return _all.elements();}

  /** Lookup a terminal by name string. */ 
  public static terminal find(String with_name)
    {
      if (with_name == null)
	return null;
      else 
	return (terminal)_all.get(with_name);
    }


  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Table of all terminals indexed by their index number. */
  protected static Hashtable _all_by_index = new Hashtable();

  /** Lookup a terminal by index. */
  public static terminal find(int indx)
    {
      Integer the_indx = new Integer(indx);

      return (terminal)_all_by_index.get(the_indx);
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Total number of terminals. */
  public static int number() {return _all.size();}

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 
  /** Static counter to assign unique index. */
  protected static int next_index = 0;

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Special terminal for end of input. */
  public static final terminal EOF = new terminal("EOF");

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** special terminal used for error recovery */
  public static final terminal error = new terminal("error");

  /*-----------------------------------------------------------*/
  /*--- General Methods ---------------------------------------*/
  /*-----------------------------------------------------------*/

  /** Report this symbol as not being a non-terminal. */
  public boolean is_non_term() 
    {
      return false;
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Convert to a string. */
  public String toString()
    {
      return super.toString() + "[" + index() + "]";
    }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** get the precedence of a terminal */
  public int precedence_num() {
    return _precedence_num;
  }
  public int precedence_side() {
    return _precedence_side;
  }

  /** set the precedence of a terminal */
  public void set_precedence(int p, int new_prec) {
    _precedence_side = p;
    _precedence_num = new_prec;
  }

  /*-----------------------------------------------------------*/

}
