package polyglot.types.reflect;

import polyglot.main.Report;
import polyglot.types.*;
import polyglot.util.*;
import java.io.*;
import java.util.*;
import polyglot.frontend.*;

/**
 * ClassFile basically represents a Java classfile as it is found on 
 * disk.  The classfile is modeled according to the Java Virtual Machine
 * Specification.  Methods are provided to edit the classfile at a very
 * low level.
 *
 * @see polyglot.types.reflect Attribute
 * @see polyglot.types.reflect Constant
 * @see polyglot.types.reflect Field
 * @see polyglot.types.reflect Method
 *
 * @author Nate Nystrom
 *         (<a href="mailto:nystrom@cs.purdue.edu">nystrom@cs.purdue.edu</a>)
 */
public class ClassFile implements LazyClassInitializer {
    protected Constant[] constants;       // The constant pool
    int modifiers;      // This class's modifer bit field
    int thisClass;              
    int superClass;             
    int[] interfaces;           
    protected Field[] fields;
    protected Method[] methods;
    protected Attribute[] attrs;
    protected InnerClasses innerClasses;
    File classFileSource;
    private ExtensionInfo extensionInfo;
    
    static Collection verbose = ClassFileLoader.verbose;
  
    /**
     * Constructor.  This constructor parses the class file from the byte array
     *
     * @param code
     *        A byte array containing the class data
     */
    public ClassFile(File classFileSource, byte[] code, ExtensionInfo ext) {
        this.classFileSource = classFileSource;
        this.extensionInfo = ext;

        try {
            ByteArrayInputStream bin = new ByteArrayInputStream(code);
            DataInputStream in = new DataInputStream(bin);
            read(in);
            in.close();
            bin.close();
        }
        catch (IOException e) {
            throw new InternalCompilerError("I/O exception on ByteArrayInputStream");
        }
    }

    public Method createMethod(DataInputStream in) throws IOException {
      Method m = new Method(in, this);
      m.initialize();
      return m;
    }

    public Field createField(DataInputStream in) throws IOException {
      Field f = new Field(in, this);
      f.initialize();
      return f;
    }

    public Attribute createAttribute(DataInputStream in, String name,
                                     int nameIndex, int length)
                                     throws IOException {
      if (name.equals("InnerClasses")) {
        innerClasses = new InnerClasses(in, nameIndex, length);
        return innerClasses;
      }
      return null;
    }

    public Constant[] constants() {
        return constants;
    }
   
    public boolean fromClassFile() { 
      return true;
    }

    JLCInfo getJLCInfo(String ts) {
      // Check if already set.
      JLCInfo jlc = (JLCInfo) jlcInfo.get(ts);

      if (jlc != null) {
        return jlc;
      }

      jlc = new JLCInfo();
      jlcInfo.put(ts, jlc);

      try {
        int mask = 0;

        for (int i = 0; i < fields.length; i++) {
          if (fields[i].name().equals("jlc$SourceLastModified$" + ts)) {
            jlc.sourceLastModified = fields[i].getLong();
            mask |= 1;
          }
          else if (fields[i].name().equals("jlc$CompilerVersion$" + ts)) {
            jlc.compilerVersion = fields[i].getString();
            mask |= 2;
          }
          else if (fields[i].name().equals("jlc$ClassType$" + ts)) {
            jlc.encodedClassType = fields[i].getString();
            mask |= 4;
          }
        }

        if (mask != 7) {
          // Not all the information is there.  Reset to default.
          jlc.sourceLastModified = 0;
          jlc.compilerVersion = null;
          jlc.encodedClassType = null;
        }
      }
      catch (SemanticException e) {
        jlc.sourceLastModified = 0;
        jlc.compilerVersion = null;
        jlc.encodedClassType = null;
      }

      return jlc;
    }

    /**
     * Get the encoded source modified time.
     */
    public long sourceLastModified(String ts) {
      JLCInfo jlc = getJLCInfo(ts);
      return jlc.sourceLastModified;
    }

    public long rawSourceLastModified() {
      return classFileSource.lastModified();
    }

    /**
     * Get the encoded compiler version used to compile the source.
     */
    public String compilerVersion(String ts) {
      JLCInfo jlc = getJLCInfo(ts);
      return jlc.compilerVersion;
    }

    /**
     * Get the encoded class type.
     */
    public String encodedClassType(String ts) {
      JLCInfo jlc = getJLCInfo(ts);
      return jlc.encodedClassType;
    }

    Map jlcInfo = new HashMap();

    /**
     * Read the class file.
     */
    void read(DataInputStream in) throws IOException {
        // Read in file contents from stream
        readHeader(in);
        readConstantPool(in);
        readAccessFlags(in);
        readClassInfo(in);
        readFields(in);
        readMethods(in);
        readAttributes(in);
    }

    /**
     * Extract the class type from the class file.
     */
    public ParsedClassType type(TypeSystem ts) throws SemanticException {
        ParsedClassType ct = createType(ts);

        if (ts.equals(ct, ts.Object())) {
            ct.superType(null);
        }
        else {
            String superName = classNameCP(superClass);

            if (superName != null) {
                ct.superType(typeForName(ts, superName));
            }
            else {
                ct.superType(ts.Object());
            }
        }

        return ct;
    }

    /**
     * Initialize <code>ct</code>'s member classes.
     */
    public void initMemberClasses(ParsedClassType ct) {
        if (innerClasses == null) {
            return;
        }

        TypeSystem ts = ct.typeSystem();

        for (int i = 0; i < innerClasses.classes.length; i++) {
            InnerClasses.Info c = innerClasses.classes[i];

            if (c.outerClassIndex == thisClass && c.classIndex != 0) {
                String name = classNameCP(c.classIndex);

                int index = name.lastIndexOf('$');

                // Skip local and anonymous classes.
                if (index >= 0 && Character.isDigit(name.charAt(index+1))) {
                    continue;
                }

                // A member class of this class
                ClassType t = quietTypeForName(ts, name);

                if (t.isMember()) {
		    if (Report.should_report(verbose, 3))
                        Report.report(3, "adding member " + t + " to " + ct);

                    ct.addMemberClass(t);
                    
                    // HACK: set the access flags of the member class
                    // using the modifier bits of the InnerClass attribute.
                    if (t instanceof ParsedClassType) {
                        ParsedClassType pt = (ParsedClassType) t;
                        pt.flags(ts.flagsForBits(c.modifiers));
                    }
                }
                else {
                    throw new InternalCompilerError(name + " should be a member class.");
                }
            }
        }
    }

    /**
     * Initialize <code>ct</code>'s interfaces.
     */
    public void initInterfaces(ParsedClassType ct) {
        TypeSystem ts = ct.typeSystem();

        for (int i = 0; i < interfaces.length; i++) {
            String name = classNameCP(interfaces[i]);
            ct.addInterface(quietTypeForName(ts, name));
        }
    }

    /**
     * Initialize <code>ct</code>'s fields.
     */
    public void initFields(ParsedClassType ct) {
        TypeSystem ts = ct.typeSystem();

        // Add the "class" field.
        LazyClassInitializer init = ts.defaultClassInitializer();
        init.initFields(ct);
  
        for (int i = 0; i < fields.length; i++) {
            if (! fields[i].name().startsWith("jlc$") &&
                ! fields[i].isSynthetic()) {
                FieldInstance fi = fields[i].fieldInstance(ts, ct);
		if (Report.should_report(verbose, 3))
		    Report.report(3, "adding " + fi + " to " + ct);
                ct.addField(fi);
            }
        }
    }

    /**
     * Initialize <code>ct</code>'s methods.
     */
    public void initMethods(ParsedClassType ct) {
        TypeSystem ts = ct.typeSystem();

        for (int i = 0; i < methods.length; i++) {
            if (! methods[i].name().equals("<init>") &&
                ! methods[i].name().equals("<clinit>") &&
                ! methods[i].isSynthetic()) {
                MethodInstance mi = methods[i].methodInstance(ts, ct);
		if (Report.should_report(verbose,3))
		    Report.report(3, "adding " + mi + " to " + ct);
                ct.addMethod(mi);
            }
        }
    }

    /**
     * Initialize <code>ct</code>'s constructors.
     */
    public void initConstructors(ParsedClassType ct) {
        TypeSystem ts = ct.typeSystem();

        for (int i = 0; i < methods.length; i++) {
            if (methods[i].name().equals("<init>") &&
                ! methods[i].isSynthetic()) {
                ConstructorInstance ci =
                    methods[i].constructorInstance(ts, ct, fields);
		if (Report.should_report(verbose,3))
		    Report.report(3, "adding " + ci + " to " + ct);
                ct.addConstructor(ci);
            }
        }
    }

    /**
     * Create an array of <code>t</code>.
     */
    Type arrayOf(Type t, int dims) {
        if (dims == 0) {
            return t;
        }
        else {
            return t.typeSystem().arrayOf(t, dims);
        }
    }

    /**
     * Convert a descriptor string into a list of types.
     */
    List typeListForString(TypeSystem ts, String str) {
        List types = new ArrayList();

        for (int i = 0; i < str.length(); i++) {
            int dims = 0;

            while (str.charAt(i) == '[') {
                dims++;
                i++;
            }

            switch (str.charAt(i)) {
                case 'Z': types.add(arrayOf(ts.Boolean(), dims));
                          break;
                case 'B': types.add(arrayOf(ts.Byte(), dims));
                          break;
                case 'S': types.add(arrayOf(ts.Short(), dims));
                          break;
                case 'C': types.add(arrayOf(ts.Char(), dims));
                          break;
                case 'I': types.add(arrayOf(ts.Int(), dims));
                          break;
                case 'J': types.add(arrayOf(ts.Long(), dims));
                          break;
                case 'F': types.add(arrayOf(ts.Float(), dims));
                          break;
                case 'D': types.add(arrayOf(ts.Double(), dims));
                          break;
                case 'V': types.add(arrayOf(ts.Void(), dims));
                          break;
                case 'L': {
                    int start = ++i;
                    while (i < str.length()) {
                        if (str.charAt(i) == ';') {
                            String s = str.substring(start, i);
                            s = s.replace('/', '.');
                            types.add(arrayOf(quietTypeForName(ts, s), dims));
                            break;
                        }

                        i++;
                    }
                }
            }
        }

	if (Report.should_report(verbose, 4))
	    Report.report(4, "parsed \"" + str + "\" -> " + types);

        return types;
    }

    /**
     * Convert a descriptor string into a type.
     */
    Type typeForString(TypeSystem ts, String str) {
        List l = typeListForString(ts, str);

        if (l.size() == 1) {
            return (Type) l.get(0);
        }

        throw new InternalCompilerError("Bad type string: \"" + str + "\"");
    }

    /**
     * Convert a String into a type.  Throws an InternalCompilerError
     * if this cannot be done.
     */
    ClassType quietTypeForName(TypeSystem ts, String name) {
	if (Report.should_report(verbose,2))
	    Report.report(2, "resolving " + name);

        try {
            return (ClassType) ts.systemResolver().find(name);
        }
        catch (SemanticException e) {
            throw new InternalCompilerError("could not load " + name);
        }
    }

    /**
     * Convert a String into a type.  Throws a SemanticException if this
     * cannot be done.
     */
    public ClassType typeForName(TypeSystem ts, String name) throws SemanticException {
	if (Report.should_report(verbose,2))
	    Report.report(2, "resolving " + name);
        return (ClassType) ts.systemResolver().find(name);
    }

    /**
     * Create the type for this class file.
     */
    ParsedClassType createType(TypeSystem ts) throws SemanticException {
        // The name is of the form "p.q.C$I$J".
        String name = classNameCP(thisClass);

	if (Report.should_report(verbose, 2))
	    Report.report(2, "creating ClassType for " + name);

        // Create the ClassType.
        ParsedClassType ct = ts.createClassType(this);
                
        

        ct.flags(ts.flagsForBits(modifiers));
        ct.position(position());

        // Add unresolved class into the cache to avoid circular resolving.
        ((CachingResolver) ts.systemResolver()).install(name, ct);

        // This is the "p.q" part.
        String packageName = StringUtil.getPackageComponent(name);

        // Set the ClassType's package.
        if (! packageName.equals("")) {
            ct.package_(ts.packageForName(packageName));
        }

        // This is the "C$I$J" part.
        String className = StringUtil.getShortNameComponent(name);

        String outerName; // This will be "p.q.C$I"
        String innerName; // This will be "J"

        outerName = name;
        innerName = null;

        while (true) {
            int dollar = outerName.lastIndexOf('$');

            if (dollar >= 0) {
                outerName = name.substring(0, dollar);
                innerName = name.substring(dollar+1);
            }
            else {
                outerName = name;
                innerName = null;
                break;
            }

            // Try loading the outer class.
            // This will recursively load its outer class, if any.
            try {
                if (Report.should_report(verbose,2))
                    Report.report(2, "resolving " + outerName + " for " + name);
                ct.outer(typeForName(ts, outerName));
                break;
            }
            catch (SemanticException e) {
                // Failed.  The class probably has a '$' in its name.
                if (Report.should_report(verbose,3))
                    Report.report(2, "error resolving " + outerName);
            }
        }

        ClassType.Kind kind = ClassType.TOP_LEVEL;

        if (innerName != null) {
            // A nested class.  Parse the class name to determine what kind. 
            StringTokenizer st = new StringTokenizer(className, "$");

            while (st.hasMoreTokens()) {
                String s = st.nextToken();

                if (Character.isDigit(s.charAt(0))) {
                    // Example: C$1
                    kind = ClassType.ANONYMOUS;
                }
                else if (kind == ClassType.ANONYMOUS) {
                    // Example: C$1$D
                    kind = ClassType.LOCAL;
                }
                else {
                    // Example: C$D
                    kind = ClassType.MEMBER;
                }
            }
        }

	if (Report.should_report(verbose, 3))
	    Report.report(3, name + " is " + kind);

        ct.kind(kind);

        if (ct.isTopLevel()) {
            ct.name(className);
        }
        else if (ct.isMember() || ct.isLocal()) {
            ct.name(innerName);
        }


        return ct;
    }

    /**
     * Create a position for the class file.
     */
    public Position position() {
        return new Position(name() + ".class");
    }

  /**
   * Get the class name at the given constant pool index.
   */
  String classNameCP(int index) {
    Constant c = constants[index];

    if (c != null && c.tag() == Constant.CLASS) {
      Integer nameIndex = (Integer) c.value();
      if (nameIndex != null) {
	c = constants[nameIndex.intValue()];
	if (c.tag() == Constant.UTF8) {
	  String s = (String) c.value();
          return s.replace('/', '.');
	}
      }
    }

    return null;
  }

  /**
   * Get the name of the class, including the package name.
   *
   * @return
   *        The name of the class.
   */
  public String name() {
    Constant c = constants[thisClass];
    if (c.tag() == Constant.CLASS) {
      Integer nameIndex = (Integer) c.value();
      if (nameIndex != null) {
	c = constants[nameIndex.intValue()];
	if (c.tag() == Constant.UTF8) {
	  return (String) c.value();
	}
      }
    }
    
    throw new ClassFormatError("Couldn't find class name in file"); 
  }
  
  /**
   * Read a constant from the constant pool.
   *
   * @param in
   *        The stream from which to read.
   * @return
   *        The constant.
   * @exception IOException
   *        If an error occurs while reading.
   */
  Constant readConstant(DataInputStream in)
       throws IOException
  {
    int tag = in.readUnsignedByte();
    Object value;
    
    switch (tag) 
      {
      case Constant.CLASS:
      case Constant.STRING:
	value = new Integer(in.readUnsignedShort());
	break;
      case Constant.FIELD_REF:
      case Constant.METHOD_REF:
      case Constant.INTERFACE_METHOD_REF:
      case Constant.NAME_AND_TYPE:
	value = new int[2];

	((int[]) value)[0] = in.readUnsignedShort();
	((int[]) value)[1] = in.readUnsignedShort();
	break;
      case Constant.INTEGER:
	value = new Integer(in.readInt());
	break;
      case Constant.FLOAT:
	value = new Float(in.readFloat());
	break;
      case Constant.LONG:
	// Longs take up 2 constant pool entries.
	value = new Long(in.readLong());
	break;
      case Constant.DOUBLE:
	// Doubles take up 2 constant pool entries.
	value = new Double(in.readDouble());
	break;
      case Constant.UTF8:
	value = in.readUTF();
	break;
      default:
	throw new ClassFormatError("Invalid constant tag: " + tag);
      }
    
    return new Constant(tag, value);
  }
  
  /**
   * Read the class file header.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  void readHeader(DataInputStream in)
       throws IOException
  {
    int magic = in.readInt();
    
    if (magic != 0xCAFEBABE) {
      throw new ClassFormatError("Bad magic number.");
    }
    
    int major = in.readUnsignedShort();
    int minor = in.readUnsignedShort();
  }
  
  /**
   * Read the class's constant pool.  Constants in the constant pool
   * are modeled by an array of <tt>reflect.Constant</tt>/
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   *
   * @see Constant
   * @see #constants
   */
  void readConstantPool(DataInputStream in)
       throws IOException
  {
    int count = in.readUnsignedShort();
    
    constants = new Constant[count];
    
    // The first constant is reserved for internal use by the JVM.
    constants[0] = null;
    
    // Read the constants.
    for (int i = 1; i < count; i++) {
      constants[i] = readConstant(in);
      
      switch (constants[i].tag()) {
	case Constant.LONG:
	case Constant.DOUBLE:
	  // Longs and doubles take up 2 constant pool entries.
          constants[++i] = null;
	  break;
      }
    }
  }
  
  /**
   * Read the class's access flags.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  void readAccessFlags(DataInputStream in)
       throws IOException
  {
    modifiers = in.readUnsignedShort();
  }
  
  /**
   * Read the class's name, superclass, and interfaces.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  void readClassInfo(DataInputStream in)
       throws IOException
  {
    int index;
    
    thisClass = in.readUnsignedShort();
    superClass = in.readUnsignedShort();
    
    int numInterfaces = in.readUnsignedShort();
    
    interfaces = new int[numInterfaces];
    
    for (int i = 0; i < numInterfaces; i++) {
      interfaces[i] = in.readUnsignedShort();
    }
  }
  
  /**
   * Read the class's fields.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  void readFields(DataInputStream in)
       throws IOException
  {
    int numFields = in.readUnsignedShort();
    
    fields = new Field[numFields];
    
    for (int i = 0; i < numFields; i++) {
      fields[i] = createField(in);
    }
  }
  
  /**
   * Read the class's methods.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  void readMethods(DataInputStream in)
       throws IOException
  {
    int numMethods = in.readUnsignedShort();
    
    methods = new Method[numMethods];
    
    for (int i = 0; i < numMethods; i++) {
      methods[i] = createMethod(in);
    }
  }
  
  /**
   * Read the class's attributes.  Since none of the attributes
   * are required, just read the length of each attribute and
   * skip that many bytes.
   *
   * @param in
   *        The stream from which to read.
   * @exception IOException
   *        If an error occurs while reading.
   */
  public void readAttributes(DataInputStream in)
       throws IOException
  {
    int numAttributes = in.readUnsignedShort();
    
    attrs = new Attribute[numAttributes];
    
    for (int i = 0; i < numAttributes; i++) {
      int nameIndex = in.readUnsignedShort();
      int length = in.readInt();
      String name = (String) constants[nameIndex].value();
      Attribute a = createAttribute(in, name, nameIndex, length);
      if (a != null) {
          attrs[i] = a;
      }
      else {
          long n = in.skip(length);
          if (n != length) {
              throw new EOFException();
          }
      }
    }
  }
}
