/**
 * ElemValPairs are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class ArrayElemValPair extends ElemValPair {

    ArrayList list;

    void resolve(ClassEnv e){
        super.resolve(e);
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
    public ArrayElemValPair(String name, char kind, ArrayList list) { //
        super(name, kind);
        this.list = list;
    }

    public void setNoName(){
        if (list == null) return;
        Iterator it = list.iterator();
        while (it.hasNext()){
            ((ElemValPair)it.next()).setNoName();
        }
    }

    public ArrayElemValPair(String name, char kind) { //
        super(name, kind);
    }

    public void addElemValPair(ElemValPair elem){
        if (list == null){
            list = new ArrayList();
        }
        list.add(elem);
    }

    int size(){
        int i = super.size();
        i += 2; // array elem count short
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
        super.write(e, out);
        if (list != null){
            out.writeShort(list.size());
        }
        else {
            out.writeShort(0);
        }
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                ((ElemValPair)it.next()).write(e, out);
            }
        }
    }
}
