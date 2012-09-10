/**
 * InnerClassSpec attributes are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class InnerClassSpecAttr {
    
    String inner_class_name;
    String outer_class_name;
    String inner_name;
    short access;

    void resolve(ClassEnv e){
        e.addCPItem(new ClassCP(inner_class_name));
        if (!outer_class_name.equals("null")){
            e.addCPItem(new ClassCP(outer_class_name));
        }
        if (!inner_name.equals("null")){
            e.addCPItem(new AsciiCP(inner_name));
        }
    }

    /**
    * Note: An inner class attr is associated with a <em>class</em>, so you
    * need to create a new InnerClassAttr for each class you create
    */
    public InnerClassSpecAttr(String a, String b, String c, short d) { //
        inner_class_name = a;
        outer_class_name = b;
        inner_name = c;
        access = d;
    }

    int size(){
        return 8;
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
        
            out.writeShort(e.getCPIndex(new ClassCP(inner_class_name)));
            if (outer_class_name.equals("null")){
                out.writeShort(0);
            }
            else {
                out.writeShort(e.getCPIndex(new ClassCP(outer_class_name)));
            }
            if (inner_name.equals("null")){
                out.writeShort(0);
            }
            else {
                out.writeShort(e.getCPIndex(new AsciiCP(inner_name)));
            }
            out.writeShort(access);
            /*out.writeShort(e.getCPIndex(attr));
    out.writeInt(2 + 4*(pc.size()));
    out.writeShort(pc.size());
    for (Enumeration en = pc.elements(), ien = line.elements();
	 en.hasMoreElements();)
      {
	Label l = (Label)(en.nextElement());
	Integer i = (Integer)(ien.nextElement());
	l.writeOffset(ce, null, out);
	out.writeShort((int) i.intValue());*/
    }
}
