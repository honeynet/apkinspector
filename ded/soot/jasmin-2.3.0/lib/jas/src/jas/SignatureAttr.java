/**
 * Signature attributes are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class SignatureAttr {

    static CP attr = new AsciiCP("Signature");
    AsciiCP sig;

    void resolve(ClassEnv e){
        e.addCPItem(attr);
        e.addCPItem(sig);
    }

    /**
    * Note: A signature attr is associated with a <em>class</em>,
    * method or field so you need to create a new SignatureAttr for 
    */
    public SignatureAttr(String s) { //
        sig = new AsciiCP(s);
    }

    int size(){
        return 2;
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
        
        out.writeShort(e.getCPIndex(attr));
        out.writeInt(2); // fixed length
        out.writeShort(e.getCPIndex(sig));
    }
}
