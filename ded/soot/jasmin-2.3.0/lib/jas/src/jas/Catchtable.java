/**
 * This is used to make a table of catch handlers for a method.
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */
package jas;

import java.io.*;
import java.util.*;

public class Catchtable
{
  Vector entries;

  public Catchtable() { entries = new Vector(); }

  /**
   * add an entry to the catch table
   */

  public void addEntry(CatchEntry entry) { entries.addElement(entry); }

  /**
   * add an entry to the catch table
   * @param start Label marking the beginning of the area
   *       where the catch table is active.
   * @param end Label marking the end of the area where the
   *       table is active.
   * @param handler Label marking the entrypoint into the
   *  exception handling routine.
   * @param cat (usually a classCP) informing the VM to direct
   * any exceptions of this (or its subclasses) to the handler.
   */

  public void
  addEntry(Label start, Label end, Label handler, CP cat)
  { addEntry(new CatchEntry(start, end, handler, cat)); }

  void resolve(ClassEnv e)
  {
    for (Enumeration en=entries.elements(); en.hasMoreElements(); )
      {
        CatchEntry ce = (CatchEntry)(en.nextElement());
        ce.resolve(e);
      }
  }

  int size()
  { return (8*entries.size()); } // each entry is 8 bytes

  void write(ClassEnv e, CodeAttr ce, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(entries.size());
    for (Enumeration en = entries.elements(); en.hasMoreElements();)
      {
        CatchEntry entry = (CatchEntry)(en.nextElement());
        entry.write(e, ce, out);
      }
  }
}
