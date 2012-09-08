package polyglot.util;

import polyglot.main.Report;
import polyglot.types.*;

import java.io.*;
import java.util.*;

/** Output stream for writing type objects. */
public class TypeOutputStream extends ObjectOutputStream
{
  protected TypeSystem ts;
  protected Set roots;
  protected Map placeHolders;
  
  public TypeOutputStream( OutputStream out, TypeSystem ts, Type root) 
    throws IOException
  {
    super( out);

    this.ts = ts;
    this.roots = ts.getTypeEncoderRootSet(root);
    this.placeHolders = new HashMap();

    if (Report.should_report(Report.serialize, 2)) {
        Report.report(2, "Began TypeOutputStream with roots: " + roots);
    }
    
    enableReplaceObject( true);
  }

  protected Object placeHolder(TypeObject o) {
      Object k = new IdentityKey(o);
      Object p = placeHolders.get(k);
      if (p == null) {
          p = ts.placeHolder(o, roots);
          placeHolders.put(k, p);
      }
      return p;
  }

  protected Object replaceObject(Object o) throws IOException
  {
    if (roots.contains(o)) {
      if (Report.should_report(Report.serialize, 2)) {
	Report.report(2, "+ In roots: " + o + " : " + o.getClass());
      }
      return o;
    }
    else if (o instanceof TypeObject) {
      Object r = placeHolder((TypeObject) o);
      if (Report.should_report(Report.serialize, 2)) {
        if (r != o) {
          Report.report(2, "+ Replacing: " + o + " : " + o.getClass()
	    + " with " + r);
        } 
	else {
	  Report.report(2, "+ " + o + " : " + o.getClass());
        }
      }
      return r;
    }
    else {
      if (Report.should_report(Report.serialize, 2)) {
	Report.report(2, "+ " + o + " : " + o.getClass());
      }
      return o;
    }
  }
}
