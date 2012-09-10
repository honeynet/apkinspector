/**
 * ElemValPairs are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class EnumElemValPair extends ElemValPair {

    AsciiCP tval;
    AsciiCP cval;

    void resolve(ClassEnv e){
        super.resolve(e);
        e.addCPItem(tval);
        e.addCPItem(cval);
    }

    /**
    * Note: An annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public EnumElemValPair(String name, char kind, String tval, String cval) { //
        super(name, kind);
        this.tval = new AsciiCP(tval);
        this.cval = new AsciiCP(cval);
    }

    int size(){
        return super.size() + 4;
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
        super.write(e, out);
        out.writeShort(e.getCPIndex(tval));
        out.writeShort(e.getCPIndex(cval));
    }
}
