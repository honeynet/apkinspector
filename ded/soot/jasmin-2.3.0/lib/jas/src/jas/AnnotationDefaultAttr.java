/**
 * Signature attributes are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class AnnotationDefaultAttr {

    static CP attr = new AsciiCP("AnnotationDefault");
    ElemValPair elem;

    void resolve(ClassEnv e){
        e.addCPItem(attr);
        elem.resolve(e);
    }

    /**
    * Note: A signature attr is associated with a <em>class</em>,
    * method or field so you need to create a new SignatureAttr for 
    */
    public AnnotationDefaultAttr(ElemValPair s) { //
        elem = s;
    }

    int size(){
        return elem.size();
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
        
        out.writeShort(e.getCPIndex(attr));
        out.writeInt(size()); // fixed length
        elem.write(e, out);
    }
}
