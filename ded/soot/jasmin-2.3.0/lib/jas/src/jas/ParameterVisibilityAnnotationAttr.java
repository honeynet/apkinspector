/**
 * Visibility annotation attributes are embedded into class files
 * and used for further ???
 * @author $Author: Jennifer Lhotak$
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;

public class ParameterVisibilityAnnotationAttr {

    AsciiCP attr;
    ArrayList list = new ArrayList();

    void resolve(ClassEnv e){
        e.addCPItem(attr);
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                ((VisibilityAnnotationAttr)it.next()).resolve(e);
            }
        }
    }

    /**
    * Note: A visibility annotation attr is associated with a <em>class</em>,
    * method or field so you need to create a new VisibilityAnnotationAttr for 
    */
    public ParameterVisibilityAnnotationAttr(String kind, ArrayList vis_annotations) { //
        attr = new AsciiCP(kind+"Annotations");
        list = vis_annotations;
    }

    public ParameterVisibilityAnnotationAttr() { //
    }

    public void setKind(String k){
        attr = new AsciiCP(k+"Annotations");
    }
    
    public void addAnnotation(VisibilityAnnotationAttr annot){
        list.add(annot);
    }

    int size(){
        int i = 2;
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                i += ((VisibilityAnnotationAttr)it.next()).size();
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
            //System.out.println("num params: "+list.size());
        }
        if (list != null){
            Iterator it = list.iterator();
            while (it.hasNext()){
                VisibilityAnnotationAttr vAttr = (VisibilityAnnotationAttr)it.next();
                if (vAttr.getList() == null){
                    out.writeShort(0);
                }
                else {
                    out.writeShort(vAttr.getList().size());
                    //System.out.println("num annots: "+vAttr.getList().size());
                }

                if (vAttr.getList() != null){
                    Iterator ait = vAttr.getList().iterator();
                    while (ait.hasNext()){
                        ((AnnotationAttr)ait.next()).write(e,out);
                    }
                }
            }
        }
    }
}
