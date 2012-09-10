package jas;

/**
 * Error thrown on problems encountered while running the
 * basic jas assembler itself.
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

public class jasError extends Exception
{
  public jasError() { super(); }
  public jasError(String s) { super(s); }
}
