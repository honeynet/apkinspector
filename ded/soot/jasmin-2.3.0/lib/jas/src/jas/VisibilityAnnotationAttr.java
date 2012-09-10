/**
 * Visibility annotation attributes are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class VisibilityAnnotationAttr {

    AsciiCP attr;
    ArrayList list = new ArrayList();
	protected String kind;

    void resolve(ClassEnv e){
        e.addCPItem(attr);
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                ((AnnotationAttr)it.next()).resolve(e);
            }
        }
    }

    /**
    * Note: A visibility annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public VisibilityAnnotationAttr(String kind, ArrayList annotations) { //
        attr = new AsciiCP(kind+"Annotations");
        list = annotations;
        this.kind = kind;
    }

    public VisibilityAnnotationAttr() { //
    }

    public void setKind(String k){
        this.kind = k;
        attr = new AsciiCP(k+"Annotations");
    }
    
    public void addAnnotation(AnnotationAttr annot){
        list.add(annot);
    }

    public ArrayList getList(){
        return list;
    }
    
    int size(){
        int i = 2;
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                i += ((AnnotationAttr)it.next()).size();
            }
        }
        return i;
    }


    void write(ClassEnv e, DataOutputStream out)
        throws IOException, jasError
        {
       
        out.writeShort(e.getCPIndex(attr));
        out.writeInt(size()); // fixed length
        if (list == null){
            out.writeShort(0);
        }
        else {
            out.writeShort(list.size());
        }
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                ((AnnotationAttr)it.next()).write(e, out);
            }
        }
    }

	public String getKind() {
		return kind;
	}
}
