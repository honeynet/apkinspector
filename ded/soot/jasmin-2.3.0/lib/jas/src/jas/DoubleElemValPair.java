/**
 * ElemValPairs are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class DoubleElemValPair extends ElemValPair{

    DoubleCP val;

    void resolve(ClassEnv e){
        super.resolve(e);
        e.addCPItem(val);
    }

    /**
    * Note: An annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public DoubleElemValPair(String name, char kind, double val) { //
        super(name, kind);
        this.val = new DoubleCP(val);
    }

    int size(){
        return super.size() + 2;
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
        super.write(e, out);
        out.writeShort(e.getCPIndex(val));
    }
}
