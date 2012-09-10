
package jas;

import java.io.*;
import java.util.Hashtable;
import java.util.Enumeration;
import java.util.Vector;

/**
 * This is the place where all information about the class to
 * be created resides.
 *
 * @author $Author: fqian $
 * @version $Revision: 1.1 $
 */

public class ClassEnv implements RuntimeConstants
{
  int magic;
  short version_lo, version_hi;
  CP this_class, super_class;
  short class_access;
  Hashtable cpe, cpe_index;
  Vector interfaces;
  Vector vars;
  Vector methods;
  SourceAttr source;
  Vector generic;
  boolean hasSuperClass;
  InnerClassAttr inner_class_attr;
  boolean classSynth;
  SyntheticAttr synthAttr;
  DeprecatedAttr deprAttr = null;
  SignatureAttr sigAttr = null;
  VisibilityAnnotationAttr visAnnotAttr = null;
  VisibilityAnnotationAttr invisAnnotAttr = null;
  EnclMethAttr encl_meth_attr;
  boolean highVersion = false;   
  
  public ClassEnv()
  {
                                // Fill in reasonable defaults
    magic = JAVA_MAGIC;
    version_lo = (short) JAVA_MINOR_VERSION;
    version_hi = (short) JAVA_VERSION;
                                // Initialize bags
    cpe = new Hashtable();
    cpe_index = null;
    interfaces = new Vector();
    vars = new Vector();
    methods = new Vector();
    generic = new Vector();
  }

  public void setHighVersion(boolean b){
    highVersion = b;
    //System.out.println("setting high version number");
    version_lo = (short) JAVA_MINOR_HIGH_VERSION;
    version_hi = (short) JAVA_HIGH_VERSION;
  }
  
  /**
   * Define this class to have this name.
   * @param name CPE representing name for class. (This is usually
   * a ClassCP)
   */
  public void setClass(CP name)
  {  this_class = name; addCPItem(name); }
  
  /**
   * Define this class to have no superclass.  Should only ever
   * be used for java.lang.Object
   */
  public void setNoSuperClass()
  {  
    hasSuperClass = false;
  }

  /**
   * Define this class to have this superclass
   * @param name CPE representing name for class. (This is usually
   * a ClassCP)
   */
  public void setSuperClass(CP name)
  {
    hasSuperClass = true;   
    super_class = name; 
    addCPItem(name);
  }

  /**
   * Set the class access for this class. Constants understood
   * by this are present along with the java Beta distribution.
   * @param access number representing access permissions for
   *        the entire class.
   * @see RuntimeConstants
   */
  public void setClassAccess(short access)
  { class_access  = access; }

  /**
   * Add this CP to the list of interfaces supposedly implemented by
   * this class. Note that the CP ought to be a ClassCP to make
   * sense to the VM.
   */

  public void addInterface(CP ifc)
  {
    addCPItem(ifc);
    interfaces.addElement(ifc);
  }

  /**
   * Add this to the list of interfaces supposedly implemented
   * by this class. Note that each CP is usually a ClassCP.
   * @param ilist An array of CP items representing the 
   *          interfaces implemented by this class.
   */
  public void addInterface(CP ilist[])
  {
    for (int i=0; i<ilist.length; i++)
      {
        interfaces.addElement(ilist[i]);
        addCPItem(ilist[i]);
      }
  }

  public void addField(Var v)
  {
    vars.addElement(v);
    v.resolve(this);
  }
  /**
   * Write the contents of the class.
   *
   * @param out DataOutputStream on which the contents are written.
   */
  public void write(DataOutputStream out)
    throws IOException, jasError
  {
				// Headers
    out.writeInt(magic);
    out.writeShort(version_lo);
    out.writeShort(version_hi);

				// cpe items
    int curidx = 1;
				// make up indices for entries
    cpe_index = new Hashtable();
    for (Enumeration e = cpe.elements(); e.hasMoreElements();)
      {
        CP tmp = (CP)(e.nextElement());
        cpe_index.put(tmp.getUniq(), new Integer(curidx));
        curidx++;
        if ((tmp instanceof LongCP) ||
            (tmp instanceof DoubleCP))
          curidx++;
      }
    out.writeShort((short)curidx);

				// Now write out all the entries
    for (Enumeration e = cpe.elements(); e.hasMoreElements();)
      {
        CP now = (CP) (e.nextElement());
        now.write(this, out);
      }

				// Class hierarchy/access
    out.writeShort(class_access);
    out.writeShort(getCPIndex(this_class));
    if (hasSuperClass)
      { out.writeShort(getCPIndex(super_class)); }
    else
      { out.writeShort(0); }
                                // interfaces
    out.writeShort(interfaces.size());
    for (Enumeration e = interfaces.elements(); e.hasMoreElements();)
      {
        CP c = (CP)(e.nextElement());
        out.writeShort(getCPIndex(c));
      }
                                // variables
    out.writeShort(vars.size());
    for (Enumeration e = vars.elements(); e.hasMoreElements();)
      {
        Var v = (Var)(e.nextElement());
        v.write(this, out);
      }

                                // methods
    out.writeShort(methods.size());
    for (Enumeration e = methods.elements(); e.hasMoreElements();)
      {
        Method m = (Method)(e.nextElement());
        m.write(this, out);
      }
                                // additional attributes
    short numExtra = 0;
    if (source != null)
      { numExtra = 1; }
    numExtra += generic.size();
    if (inner_class_attr != null) {
        numExtra++;
    }
    if (isClassSynth()){
        numExtra++;
    }
    if (deprAttr != null){
        numExtra++;
    }
    if (sigAttr != null){
        numExtra++;
    }
    if (visAnnotAttr != null){
        numExtra++;
    }
    if (invisAnnotAttr != null){
        numExtra++;
    }
    if (encl_meth_attr != null){
        numExtra++;
    }
    
    out.writeShort(numExtra);
    if (source != null)
      { source.write(this, out); }
    for (Enumeration gen=generic.elements(); gen.hasMoreElements(); )
      {
	GenericAttr gattr = (GenericAttr)gen.nextElement();
	gattr.write(this, out);
      }
    
    // synthetic attr
    if (isClassSynth()){
        //System.out.println("class is synthetic");
        //SyntheticAttr sa = new SyntheticAttr();
        //sa.resolve(this);
        synthAttr.write(this, out);
    }
    // deprecated attr
    if (deprAttr != null){
        deprAttr.write(this, out);
    }
    // signature attr
    if (sigAttr != null){
        sigAttr.write(this, out);
    }
    // encl meth attr
    if (encl_meth_attr != null){
        encl_meth_attr.write(this, out);
    }
    // visibility annotation attr
    if (visAnnotAttr != null){
        visAnnotAttr.write(this, out);
    }
    // visibility annotation attr runtime invisible
    if (invisAnnotAttr != null){
        invisAnnotAttr.write(this, out);
    }
    // inner class attr
    if (inner_class_attr != null){
        inner_class_attr.write(this, out);
    }
    out.flush();
  }

  /**
   * This is the method to add CPE items to a class. CPE items for
   * a class are "uniquefied". Ie, if you add a CPE items whose
   * contents already exist in the class, only one entry is finally
   * written out when the class is written.
   *
   * @param cp Item to be added to the class
   */

  public void addCPItem(CP cp)
  {
    String uniq = cp.getUniq();
    CP intern;
    //System.out.println("adding CP: "+uniq);
    
    if ((intern = (CP)(cpe.get(uniq))) == null)
      {
				// add it
	cpe.put(uniq, cp);
    //System.out.println("added uniq to cpe: "+cpe.get(uniq));
				// resolve it so it adds anything
				// which it depends on
	cp.resolve(this);
    
      }
    //System.out.println("cp: "+cp);
  }

  /**
   * Here is where code gets added to a class.
   * @param acc method_access permissions, expressed with some combination
   *       of the values defined in RuntimeConstants
   * @param name Name of the method
   * @param sig Signature for the method
   * @param code Actual code for the method
   * @param ex Any exception attribute to be associated with method
   */
  public void
  addMethod(short acc, String name, String sig, CodeAttr code, ExceptAttr ex)
  {
    Method x = new Method(acc, new AsciiCP(name), new AsciiCP(sig),
                          code, ex);
    x.resolve(this);
    methods.addElement(x);
  }

 
  public void setClassSynth(boolean b){
    classSynth = b;
    synthAttr = new SyntheticAttr();
    synthAttr.resolve(this);
  }

  public boolean isClassSynth(){
    return classSynth;
  }

  public void setClassDepr(DeprecatedAttr d){
    deprAttr = d;
    deprAttr.resolve(this);
  }
  
  public void setClassSigAttr(SignatureAttr s){
    sigAttr = s;
    sigAttr.resolve(this);
  }
  
  public void setClassAnnotAttrVis(VisibilityAnnotationAttr s){
    visAnnotAttr = s;
    visAnnotAttr.resolve(this);
  }  

  public void setClassAnnotAttrInvis(VisibilityAnnotationAttr s){
	invisAnnotAttr = s;
	invisAnnotAttr.resolve(this);
  }

  public void finishInnerClassAttr(InnerClassAttr attr){
      inner_class_attr = attr;  
      inner_class_attr.resolve(this);
  }

  public void addEnclMethAttr(EnclMethAttr attr){
      encl_meth_attr = attr;
      encl_meth_attr.resolve(this);
  }
  
  /**
   * Add an attribute specifying the name of the source file
   * for the class
   * @param source SourceAttribute specifying the source for the file
   */

  public void setSource(SourceAttr source)
  { this.source = source; source.resolve(this); }

  /**
   * Add an attribute specifying the name of the source file
   * for the clas.
   * @param source String with the name of the class
   */
  public void setSource(String source)
  { this.source = new SourceAttr(source); this.source.resolve(this); }

  /**
   * Add a generic attribute to the class file. A generic attribute
   * contains a stream of uninterpreted bytes which is ignored by
   * the VM (as long as its name doesn't conflict with other names
   * for attributes that are understood by the VM)
   */
  public void addGenericAttr(GenericAttr g)
  { generic.addElement(g); g.resolve(this); }

  /**
   * This allows more control over generating CP's for methods
   * if you feel so inclined.
   */
  public void addMethod(Method m)
  {
    m.resolve(this);
    methods.addElement(m);
  }

  // LJH changed from short to int so that wide check works,
  // otherwise nums > 32K become negative and then not made wide
  /* short */ int getCPIndex(CP cp)
    throws jasError
  {
      if (cpe_index == null) {
	     throw new jasError("Internal error: CPE index has not been generated");
      }
    //System.out.println("cp uniq: "+cp.getUniq());
    //System.out.println("cpe_index: "+cpe_index);
    
    Integer idx = (Integer)(cpe_index.get(cp.getUniq()));
    // LJH -----------------------------
     //System.out.println("Getting idx " + idx);
    if (idx == null)
      throw new jasError("Item " + cp + " not in the class");
    return (idx.intValue());
  }
}
