/**
 * Annotation attributes are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class AnnotationAttr {

    AsciiCP type;
    ArrayList list = new ArrayList();

    void resolve(ClassEnv e){
        e.addCPItem(type);
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                ((ElemValPair)it.next()).resolve(e);
            }
        }
    }

    /**
    * Note: An annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public AnnotationAttr(String type, ArrayList elems) { //
        this.type = new AsciiCP(type);
        this.list = elems;
    }
    
    public AnnotationAttr() { //
    }

    public void setType(String type){
        this.type = new AsciiCP(type);
    }

    public void addElemValPair(ElemValPair pair){
        list.add(pair);
    }

    int size(){
        int i = 4;
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                i += ((ElemValPair)it.next()).size();
            }
        }
        return i;
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
        
        out.writeShort(e.getCPIndex(type));
        if (list == null){
            out.writeShort(0);
        }
        else {
            out.writeShort(list.size()); // fixed length
        }
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                ((ElemValPair)it.next()).write(e, out);
            }
        }
    }
}
