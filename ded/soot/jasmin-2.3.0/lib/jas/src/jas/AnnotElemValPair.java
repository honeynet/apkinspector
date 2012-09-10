/**
 * ElemValPairs are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class AnnotElemValPair extends ElemValPair {

    AnnotationAttr attr;

    void resolve(ClassEnv e){
        super.resolve(e);
        attr.resolve(e);
    }

    /**
    * Note: An annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public AnnotElemValPair(String name, char kind, AnnotationAttr attr) { //
        super(name, kind);
        this.attr = attr;
    }

    int size(){
        return super.size() + attr.size();
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
        super.write(e, out);
        attr.write(e, out);
    }
}
