/**
 * InnerClass attributes are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class InnerClassAttr
{
    static CP attr = new AsciiCP("InnerClasses");
    short attr_length = 0;
    short num = 0;
    ArrayList list;


    /**
    * Note: An inner class attr is associated with a <em>class</em>, so you
    * need to create a new InnerClassAttr for each class you create
    */
    public InnerClassAttr() { //
        list = new ArrayList();
    }

    void resolve(ClassEnv e){
        e.addCPItem(attr); 
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                ((InnerClassSpecAttr)it.next()).resolve(e);
            }
        }
    }

    int size(){
        return	
            (2 +			// name_idx
            4 +			// attr_len
            2 +			// inner class len spec
            8*(list.size()));		// table
    }

    public void addInnerClassSpec(InnerClassSpecAttr attr){
        list.add(attr);
    }

    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError {
    
        out.writeShort(e.getCPIndex(attr));
        //out.writeInt(size());
        out.writeInt(2+8*list.size());
        out.writeShort(list.size());
        Iterator it = list.iterator();
        while(it.hasNext()){
            ((InnerClassSpecAttr)it.next()).write(e, out);
        }
    /*for (Enumeration en = pc.elements(), ien = line.elements();
	 en.hasMoreElements();)
      {
	Label l = (Label)(en.nextElement());
	Integer i = (Integer)(ien.nextElement());
	l.writeOffset(ce, null, out);
	out.writeShort((int) i.intValue());*/
    }
}
