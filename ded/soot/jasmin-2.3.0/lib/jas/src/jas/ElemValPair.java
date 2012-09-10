/**
 * ElemValPairs are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class ElemValPair {

    AsciiCP name;
    byte kind;
    boolean noName;

    void resolve(ClassEnv e){
        e.addCPItem(name);
    }

    /**
    * Note: An annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public ElemValPair(String name, char kind) { //
        this.name = new AsciiCP(name);
        this.kind = (byte)kind;
    }

    int size(){
        if (noName) return 1;
        return 3;
    }

    public void setNoName(){
        noName = true;
    }
    
    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
   
        if (!noName){
            out.writeShort(e.getCPIndex(name));
        }
        out.writeByte(kind);
    }
    
}
