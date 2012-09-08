package polyglot.util;

import polyglot.main.Report;
import polyglot.types.*;

import java.util.*;
import java.io.*;

/** Input stream for reading type objects. */
public class TypeInputStream extends ObjectInputStream
{
  protected TypeSystem ts;
  protected Map cache;

  public TypeInputStream( InputStream in, TypeSystem ts) 
    throws IOException
  {
    super( in);
    enableResolveObject(true);
    this.ts = ts;
    this.cache = new HashMap();
  }

  public TypeSystem getTypeSystem()
  {
    return ts;
  }

  protected Object resolveObject(Object o) {
    String s = "";
    if (Report.should_report(Report.serialize, 2)) {
      try {
        s = o.toString();
      } catch (NullPointerException e) {
        s = "<NullPointerException thrown>";
      }
    }	  
    if (o instanceof PlaceHolder) {
      Object k = new IdentityKey(o);
      TypeObject t = (TypeObject) cache.get(k);
      if (t == null) {
        t = ((PlaceHolder) o).resolve(ts);
        cache.put(k, t);
      }
      if (Report.should_report(Report.serialize, 2)) {
        Report.report(2, "- Resolving " + s + " : " + o.getClass()
          + " to " + t + " : " + t.getClass());      	
      }
      return t;
    }
    else if (o instanceof Enum) {
      if (Report.should_report(Report.serialize, 2)) {    
        Report.report(2, "- Interning " + s + " : " + o.getClass());
      }
      return ((Enum) o).intern();
    }
    else {
      if (Report.should_report(Report.serialize, 2)) {    
        Report.report(2, "- " + s + " : " + o.getClass());
      }
      return o;
    }
  }
}
