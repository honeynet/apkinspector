package java_cup;

/** This abstract class serves as the base class for grammar symbols (i.e.,
 * both terminals and non-terminals).  Each symbol has a name string, and
 * a string giving the type of object that the symbol will be represented by
 * on the runtime parse stack.  In addition, each symbol maintains a use count
 * in order to detect symbols that are declared but never used, and an index
 * number that indicates where it appears in parse tables (index numbers are
 * unique within terminals or non terminals, but not across both).
 *
 * @see     java_cup.terminal
 * @see     java_cup.non_terminal
 * @version last updated: 7/3/96
 * @author  Frank Flannery
 */
public abstract class symbol {
   /*-----------------------------------------------------------*/
   /*--- Constructor(s) ----------------------------------------*/
   /*-----------------------------------------------------------*/

   /** Full constructor.
    * @param nm the name of the symbol.
    * @param tp a string with the type name.
    */
   public symbol(String nm, String tp)
     {
       /* sanity check */
       if (nm == null) nm = "";

       /* apply default if no type given */
       if (tp == null) tp = "Object";

       _name = nm;
       _stack_type = tp;
     }

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

   /** Constructor with default type. 
    * @param nm the name of the symbol.
    */
   public symbol(String nm)
     {
       this(nm, null);
     }

   /*-----------------------------------------------------------*/
   /*--- (Access to) Instance Variables ------------------------*/
   /*-----------------------------------------------------------*/

   /** String for the human readable name of the symbol. */
   protected String _name; 
 
   /** String for the human readable name of the symbol. */
   public String name() {return _name;}

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

   /** String for the type of object used for the symbol on the parse stack. */
   protected String _stack_type;

   /** String for the type of object used for the symbol on the parse stack. */
   public String stack_type() {return _stack_type;}

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

   /** Count of how many times the symbol appears in productions. */
   protected int _use_count = 0;

   /** Count of how many times the symbol appears in productions. */
   public int use_count() {return _use_count;}

   /** Increment the use count. */ 
   public void note_use() {_use_count++;}
 
  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/
 
  /** Index of this symbol (terminal or non terminal) in the parse tables.
   *  Note: indexes are unique among terminals and unique among non terminals,
   *  however, a terminal may have the same index as a non-terminal, etc. 
   */
   protected int _index;
 
  /** Index of this symbol (terminal or non terminal) in the parse tables.
   *  Note: indexes are unique among terminals and unique among non terminals,
   *  however, a terminal may have the same index as a non-terminal, etc. 
   */
   public int index() {return _index;}

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Indicate if this is a non-terminal.  Here in the base class we
   *  don't know, so this is abstract.  
   */
  public abstract boolean is_non_term();

  /*. . . . . . . . . . . . . . . . . . . . . . . . . . . . . .*/

  /** Convert to a string. */
  public String toString()
    {
      return name();
    }

  /*-----------------------------------------------------------*/

}
