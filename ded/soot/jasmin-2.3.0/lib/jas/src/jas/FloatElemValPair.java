/**
 * ElemValPairs are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class FloatElemValPair extends ElemValPair{

    FloatCP val;

    void resolve(ClassEnv e){
        super.resolve(e);
        e.addCPItem(val);
    }

    /**
    * Note: An annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public FloatElemValPair(String name, char kind, float val) { //
        super(name, kind);
        this.val = new FloatCP(val);
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
