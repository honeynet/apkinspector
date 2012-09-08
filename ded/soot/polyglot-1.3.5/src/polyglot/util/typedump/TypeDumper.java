
package polyglot.util.typedump;

import polyglot.util.*;
import polyglot.types.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.Map;
import java.util.Set;

class TypeDumper {
    static Set dontExpand;
    static {
	Object[] primitiveLike = {
	    Void.class,
	    Boolean.class,
	    Short.class,
	    Integer.class,
	    Long.class,
	    Float.class,
	    Double.class,
	    Class.class,
	    String.class,
	};
	dontExpand =
	    new java.util.HashSet(java.util.Arrays.asList(primitiveLike));
    }

    Type theType;
    String rawName;
    String compilerVersion;
    Date timestamp;
    TypeDumper(String rawName, Type t, String compilerVersion,
	       Long timestamp) {
	theType = t;
	this.rawName = rawName;
	this.compilerVersion = compilerVersion;
	this.timestamp = new Date(timestamp.longValue());
    }
    
    public static TypeDumper load(String name, TypeSystem ts)
	throws ClassNotFoundException, NoSuchFieldException, 
	       java.io.IOException, SecurityException
    {
	Class c = Class.forName(name);
	try {
	    Field jlcVersion = c.getDeclaredField("jlc$CompilerVersion");
	    Field jlcTimestamp = c.getDeclaredField("jlc$SourceLastModified");
	    Field jlcType = c.getDeclaredField("jlc$ClassType");
	    String t = (String)jlcType.get(null);
	    TypeEncoder te = new TypeEncoder(ts);
	    return new TypeDumper(name,
				  te.decode(t),
				  (String)jlcVersion.get(null),
				  (Long)jlcTimestamp.get(null));
	} catch (IllegalAccessException exn) {
	    throw new SecurityException("illegal access: "+exn.getMessage());
	}
    }

    public void dump(CodeWriter w) {
	Map cache = new java.util.HashMap();
	cache.put(theType, theType);
	w.write("Type "+rawName+ " {");
	w.allowBreak(2);
	w.begin(0);
	w.write("Compiled with polyglot version "+compilerVersion+".  ");
	w.allowBreak(0);
	w.write("Last modified: "+timestamp.toString()+".  ");
	w.allowBreak(0);
	w.write(theType.toString());
	w.allowBreak(4);
	w.write("<"+
		theType.getClass().toString()+">");
	w.allowBreak(0);
	dumpObject(w, theType, cache);
	w.allowBreak(0);
	w.end();
	w.allowBreak(0);
	w.write("}");
	w.newline(0);
    }

    protected void dumpObject(CodeWriter w, Object obj, Map cache) {
	w.write(" fields {");
	w.allowBreak(2);
	w.begin(0);
	try {
	    Field[] declaredFields =
		obj.getClass().getDeclaredFields();
	    java.lang.reflect.AccessibleObject.setAccessible(declaredFields,
							    true);
	    for (int i = 0; i < declaredFields.length; i++) {
		if (Modifier.isStatic(declaredFields[i].getModifiers()))
		    continue;
		w.begin(4);
		w.write(declaredFields[i].getName()+": ");
		w.allowBreak(0);
		try {
		    Object o = declaredFields[i].get(obj);
		    if (o != null) {
			Class rtType = o.getClass();
			w.write("<"+rtType.toString()+">:");
			w.allowBreak(0);
			w.write(o.toString());
			w.allowBreak(4);
			if (!Object.class.equals(rtType) &&
			    !dontDump(rtType) &&
			    !rtType.isArray() &&
			    !(cache.containsKey(o) &&
			      cache.get(o) == o)) {
			    cache.put(o, o);
			    dumpObject(w, o, cache);
			}
		    } else {
			w.write("null");
		    }
		} catch (IllegalAccessException exn) {
		    w.write("##["+exn.getMessage()+"]");
		}
		w.end();
		w.allowBreak(0);
	    }
	} catch (SecurityException exn) {
	} finally {
	    w.end();
	    w.allowBreak(0);
	    w.write("}");
	}
    }

    static boolean dontDump(Class c) {
	return dontExpand.contains(c);
    }

}

