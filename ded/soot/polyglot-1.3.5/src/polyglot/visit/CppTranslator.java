package polyglot.visit;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.SourceFile;
import polyglot.ast.TopLevelDecl;
import polyglot.ast.TypeNode;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.main.Options;
import polyglot.types.ArrayType;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.Named;
import polyglot.types.Package;
import polyglot.types.PrimitiveType;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.util.ErrorInfo;
import polyglot.util.InternalCompilerError;

public class CppTranslator extends TypedTranslator {

    protected Context context;

    public CppTranslator(Job job, TypeSystem ts, NodeFactory nf,
            TargetFactory tf) {
        super(job, ts, nf, tf);
    }

    protected static boolean cppBackend() {return false;}
    
    protected static HashMap createdFiles = new HashMap();
    public static HashMap getFileNames() { return createdFiles; }
    
    /** Create a new <code>Translator</code> identical to <code>this</code>,
     * except: a) wrapped inside a HeaderTranslator object, and b) with
     * a new context <code>c</code>
     * @param c - the new context to use
     * @return - a header translator identical to this one, but with new context.
     */
    public HeaderTranslator headerContext(Context c) {
      HeaderTranslator ht = new HeaderTranslator(this);
      ht.context = c;
      return ht;      
    } 

    /**
     * Turns a package or class name from Java "x.y.z" format 
     * into a C-style scope ("x::y::z")
     * @param s the input package or class name
     * @return A c-scoped version of s
     */
    public static String cScope(String s)
    {
      String out = "";
      
      for(int i = 0; i < s.length(); i++)
      {
        char c = s.charAt(i);
        if(c == '.')
          out = out + "::";
        else
          out = out + c;
      }
      
      return out;      
    }    
    
    public String cTypeString(Type type) {
        if (type instanceof ArrayType) {
            ArrayType at = (ArrayType) type;
            String s = "jmatch_array< " + cTypeString(at.base());
            if(at.base().isReference())
                s = s + "*";
            return s + " > ";
        }
        else if (type instanceof ClassType) {
            ClassType ct = (ClassType) type;
            if (ct.isTopLevel() && ct.package_() != null) {
                return cScope(ct.package_().translate(this.context) + "." + ct.name());
            }
        }
        else if (type instanceof PrimitiveType) {
            PrimitiveType pt = (PrimitiveType) type;
            if (pt.kind() == PrimitiveType.BOOLEAN) {
                return "bool";
            }
        }
        return type.translate(this.context);
    }
    
    public void print(Node parent, Node child, CodeWriter w) {
        if (cppBackend()) {
            if (child instanceof TypeNode) {
                TypeNode tn = (TypeNode) child;
                Type type = tn.type();
                w.write(cTypeString(type));
            }
        }
        super.print(parent, child, w);
    }

    /** Transate a single SourceFile node */
    protected boolean translateSource(SourceFile sfn) {
        TypeSystem ts = typeSystem();
        NodeFactory nf = nodeFactory();
        TargetFactory tf = this.tf;
        int outputWidth = job.compiler().outputWidth();
        Collection outputFiles = job.compiler().outputFiles();

        // Find the public declarations in the file.  We'll use these to
        // derive the names of the target files.  There will be one
        // target file per public declaration.  If there are no public
        // declarations, we'll use the source file name to derive the
        // target file name.
        List exports = exports(sfn);

        try {
            File of, headerFile;
            Writer ofw, headerWriter = null;            
            CodeWriter w;
            CodeWriter wH = null;

            String pkg = "";

            if (sfn.package_() != null) {
                Package p = sfn.package_().package_();
                pkg = p.toString();
            }


            TopLevelDecl first = null;

            if (exports.size() == 0) {
                // Use the source name to derive a default output file name.
                of = tf.outputFile(pkg, sfn.source());
            }
            else {
                first = (TopLevelDecl) exports.get(0);
                of = tf.outputFile(pkg, first.name(), sfn.source());
            }

            String opfPath = of.getPath();
            if (!opfPath.endsWith("$")) outputFiles.add(of.getPath());
            ofw = tf.outputWriter(of);
            w = new CodeWriter(ofw, outputWidth);
            createdFiles.put(of.getPath(), null);

            if(cppBackend()) {
              //if we're generating a c++ class, we need to also generate 
              //the .h file              
              headerFile = new File(tf.headerNameForFileName(of.getPath()));
              headerWriter = tf.outputWriter(headerFile);
              wH = new CodeWriter(headerWriter, outputWidth);
              
              String className = null;
              if(!exports.isEmpty())
              {
                first = (TopLevelDecl) exports.get(0);
                className = first.name();
              }
              else
              {
                String name;
                name = sfn.source().name();
                className = name.substring(0, name.lastIndexOf('.'));                
              }
              
              writeHFileHeader(sfn, className, wH);
            }

            writeHeader(sfn, w);

            for (Iterator i = sfn.decls().iterator(); i.hasNext(); ) {
                TopLevelDecl decl = (TopLevelDecl) i.next();

                if (decl.flags().isPublic() && decl != first 
                    && !cppBackend()) {
                    // We hit a new exported declaration, open a new file.
                    // But, first close the old file.
                    w.flush();
                    ofw.close();

                    of = tf.outputFile(pkg, decl.name(), sfn.source());
                    outputFiles.add(of.getPath());
                    ofw = tf.outputWriter(of);
                    w = new CodeWriter(ofw, outputWidth);

                    writeHeader(sfn, w);
                }

                translateTopLevelDecl(w, sfn, decl);
                
                if(cppBackend()) {
                    Context c = sfn.enterScope(decl, this.context);
                  decl.del().translate(wH, this.headerContext(c));
                }

                if (i.hasNext()) {
                    w.newline(0);
                }
            }

            writeFooter(sfn, w);
            if(cppBackend())
            {
              writeHFileFooter(sfn, wH);
              wH.flush();
              headerWriter.close();
            }
            
            w.flush();
            ofw.close();
            return true;
        }
        catch (IOException e) {
            job.compiler().errorQueue().enqueue(ErrorInfo.IO_ERROR,
                      "I/O error while translating: " + e.getMessage());
            return false;
        }
    }
    

    
    /** 
     * Write the opening lines of the header file for a given class
     * @param sfn - representation of the source file we're compiling; used for Imports.
     * @param className - The name of the class we're describing
     * @param w - The CodeWriter to write it all out to (the .h file)
     */
    protected void writeHFileHeader(SourceFile sfn, String className, CodeWriter w) {
      String pkg = null;
      
      if(sfn.package_() != null)
      {
        Package p = sfn.package_().package_();
        pkg = p.fullName();
      }
      
      if(pkg == null || pkg.equals(""))
        pkg = "jmatch_primary";
      
      String macroName = "_" + macroEscape(pkg) + "_" + macroEscape(className) + "_H";
      w.write("#ifndef " + macroName);
      w.newline(0);
      w.write("#define " + macroName);
      w.newline(0);

      if(sfn.package_() != null)
        sfn.package_().del().translate(w, this);
      else
        w.write("namespace " + cScope(pkg) + " {");
      w.newline(0);      
      
      w.write("using namespace jmatch_primary;");
      w.newline(0);
      w.write("using namespace java::lang;");
      w.newline(0);
      
      //now make any more imports.
      for(Iterator i = sfn.imports().iterator(); i.hasNext(); ) {
        Import imp = (Import)i.next();
        imp.del().translate(w, this);
        w.newline(0);
      }      
    }
    
    /**
     * Write the footer of the .h file if we're in C++ mode
     * @param w
     */
    protected void writeHFileFooter(SourceFile sfn, CodeWriter w) {
     
      
      int packageDepth = 0;
      int i;
      if(null != sfn.package_())
      {          
        Package p = sfn.package_().package_();
        String pkgName = p.toString();

        if(pkgName.length() > 0)
          packageDepth++;
        
        for(i = 0; i < pkgName.length(); i++)
          if(pkgName.charAt(i) == '.')
            packageDepth++;

        w.write("/* closing namespace */");
        w.newline(0);
        for(i = 0; i < packageDepth; i++)
          w.write("}");

        w.newline(0);
        w.newline(0);
      }        
      
      if(packageDepth == 0)
      {
        w.newline(0);
        w.write("} /* namespace */");
        w.newline(0);
        w.newline(0);
      }
      
      w.write("#endif");
      w.newline(0);
      w.newline(0);
    }

    /** 
     * C++ files also require a footer terminal '}' because they
     * need to close the namespace they're opening.
     */
    protected void writeFooter(SourceFile sfn, CodeWriter w) {
      if(cppBackend()) {
        int packageDepth = 0;
        int i;
        if(null != sfn.package_())
        {          
          Package p = sfn.package_().package_();
          String pkgName = p.toString();

          if(pkgName.length() > 0)
            packageDepth++;
          
          for(i = 0; i < pkgName.length(); i++)
            if(pkgName.charAt(i) == '.')
              packageDepth++;

          w.write("/* closing namespace */");
          w.newline(0);
          for(i = 0; i < packageDepth; i++)
            w.write("}");

          w.newline(0);
          w.newline(0);
        }        
        
        if(packageDepth == 0)
        {
          w.newline(0);
          w.write("} /* namespace */");
          w.newline(0);
          w.newline(0);
        }
      }
    }
    
    /** Write the package and import declarations for a source file. */
    protected void writeHeader(SourceFile sfn, CodeWriter w) {
      if(cppBackend())
      {
        //package --> namespace and imports --> header includes
        
        String pkg = "";

        if (sfn.package_() != null) {
            Package p = sfn.package_().package_();
            pkg = p.toString() + ".";
        }
        
        int i = 0;
        int dots = 0;
        for(i = 0; i < pkg.length(); i++)
          if(pkg.charAt(i) == '.')
            dots++;
        
        //start out with global project include
        w.write("#include\"");
        for(i = 0; i < dots; i++)
          w.write("../");        
        w.write("mainproj.h\"");
        w.newline(0);
        
        //in C++, open the package (namespace), and then 'using' others:
        if(null != sfn.package_())
        {          
                sfn.package_().del().translate(w, this);
                w.newline(0);
                w.newline(0);
        }
        else
        {
          w.write("namespace jmatch_primary {");
          w.newline(0);
          w.newline(0);
        }
        
        //we always are using the global jmatch namespace
        w.write("using namespace jmatch_primary;");
        w.newline(0);
        
        //and java.lang.*;
        w.write("using namespace java::lang;");
        w.newline(0);
        
        //now make any more imports.
        for(Iterator it = sfn.imports().iterator(); it.hasNext(); ) {
          Import imp = (Import)it.next();
          imp.del().translate(w, this);
          w.newline(0);
        }
        
        
      }
      else
      {
        //standard Java header
                if (sfn.package_() != null) {
                    w.write("package ");
                    sfn.package_().del().translate(w, this);
                    w.write(";");
                    w.newline(0);
                    w.newline(0);
                }
        
                boolean newline = false;
        
                for (Iterator i = sfn.imports().iterator(); i.hasNext(); ) {
                    Import imp = (Import) i.next();
                    imp.del().translate(w, this);
                    newline = true;
                }
        
                if (newline) {
                    w.newline(0);
                }
      }
    }

}
