/**
 * Used to make up new field entries. Fields for a class can have
 * an additional "ConstantValue" attribute associated them,
 * which the java compiler uses to represent things like
 * static final int blah = foo;
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

package jas;

import java.io.*;
import java.util.*;


public class Var
{
  short var_acc;
  CP name, sig;
  ConstAttr const_attr;
  SyntheticAttr synth_attr = null;
  DeprecatedAttr dep_attr = null;
  SignatureAttr sig_attr = null;
  VisibilityAnnotationAttr vis_annot_attr = null;
  VisibilityAnnotationAttr invis_annot_attr = null;
  
  Vector genericAttrs = new Vector();
    

  /**
   * @param vacc access permissions for the field
   * @param name name of the field
   * @param sig type of the field
   * @param cattr Extra constant value information. Passing this as
   * null will not include this information for the record.
   * @see RuntimeConstants
   */

  public Var(short vacc, CP name, CP sig, ConstAttr cattr)
  {
    var_acc = vacc; this.name = name;
    this.sig = sig; const_attr = cattr;
  }

  public Var(short vacc, CP name, CP sig, ConstAttr cattr, SyntheticAttr sattr){
    var_acc = vacc; 
    this.name = name;
    this.sig = sig; 
    const_attr = cattr;
    synth_attr = sattr;
  }
  
    public void addGenericAttr(GenericAttr g)
    {
        genericAttrs.addElement(g);
    }

    public void addDeprecatedAttr(DeprecatedAttr d){
        dep_attr = d;
    }

    public void addSignatureAttr(SignatureAttr s){
        sig_attr = s;
    }

    public void addVisibilityAnnotationAttrVis(VisibilityAnnotationAttr v){
        vis_annot_attr = v;
    }

    public void addVisibilityAnnotationAttrInvis(VisibilityAnnotationAttr v){
        invis_annot_attr = v;
    }

  void resolve(ClassEnv e)
  {
    e.addCPItem(name);
    e.addCPItem(sig);
    if (const_attr != null)
      { const_attr.resolve(e); }
    if (synth_attr != null){
        synth_attr.resolve(e);
    }
    if (dep_attr != null){
        dep_attr.resolve(e);
    }
    if (sig_attr != null){
        sig_attr.resolve(e);
    }
    if (vis_annot_attr != null){
        vis_annot_attr.resolve(e);
    }
    if (invis_annot_attr != null){
        invis_annot_attr.resolve(e);
    }
  }

  void write(ClassEnv e, DataOutputStream out)
    throws IOException, jasError
  {
    out.writeShort(var_acc);
    out.writeShort(e.getCPIndex(name));
    out.writeShort(e.getCPIndex(sig));
    int attrCnt = genericAttrs.size();

    if (const_attr != null) attrCnt++;
    if (synth_attr != null) attrCnt++;
    if (dep_attr != null) attrCnt++;
    if (sig_attr != null) attrCnt++;
    if (vis_annot_attr != null) attrCnt ++;
    if (invis_annot_attr != null) attrCnt ++;
    
    out.writeShort(attrCnt);
    
    if (const_attr != null){
        const_attr.write(e, out);
    }
    if (synth_attr != null){
        synth_attr.write(e, out);
    }
    if (dep_attr != null){
        dep_attr.write(e, out);
    }
    if (sig_attr != null){
        sig_attr.write(e, out);
    }
    if (vis_annot_attr != null){
        vis_annot_attr.write(e,out);
    }
    if (invis_annot_attr != null){
        invis_annot_attr.write(e,out);
    }
    /*if (const_attr != null)
      { out.writeShort(attrCnt +1); const_attr.write(e, out); }
    else
      { out.writeShort(attrCnt); }*/

    for(Enumeration enu = genericAttrs.elements(); enu.hasMoreElements();) {
        ((GenericAttr)enu.nextElement()).write(e, out);
    }
  }
}
