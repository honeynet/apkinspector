/**
 * Line number table attributes are embedded into Code attributes
 * and used for further debugging information.
 * @see CodeAttr#setLineTable
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class LineTableAttr
{
  static CP attr = new AsciiCP("LineNumberTable");

  Vector line, pc;

  /**
   * Note: A line table is associated with a <em>method</em>, so you
   * need to create a new LineTableAttr for each method for which you add
   * debugging information.
   * @see CodeAttr#setLineTable
   */
  public LineTableAttr()
  { line = new Vector(); pc = new Vector(); }

  /**
   * Add a pc to line number entry to the line table.
   * @param Label to the location in the class file
   * @param line Corresponding line number in the source file
   */

  public void addEntry(Label l, int line)
  {
    pc.addElement(l);
    this.line.addElement(new Integer(line));
  }

  void resolve(ClassEnv e)
  { e.addCPItem(attr); }

  int size()
  { return	
      (2 +			// name_idx
       4 +			// attr_len
       2 +			// line table len spec
       4*(pc.size()));		// table
  }

  void write(ClassEnv e, CodeAttr ce, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(e.getCPIndex(attr));
    out.writeInt(2 + 4*(pc.size()));
    out.writeShort(pc.size());
    for (Enumeration en = pc.elements(), ien = line.elements();
	 en.hasMoreElements();)
      {
	Label l = (Label)(en.nextElement());
	Integer i = (Integer)(ien.nextElement());
	l.writeOffset(ce, null, out);
	out.writeShort((int) i.intValue());
      }
  }
}
