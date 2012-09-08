package polyglot.visit;

import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.*;

import polyglot.ast.Import;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.ast.SourceCollection;
import polyglot.ast.SourceFile;
import polyglot.ast.TopLevelDecl;
import polyglot.ast.JL;
import polyglot.frontend.Job;
import polyglot.frontend.TargetFactory;
import polyglot.types.ClassType;
import polyglot.types.Context;
import polyglot.types.Package;
import polyglot.types.TypeSystem;
import polyglot.util.CodeWriter;
import polyglot.util.Copy;
import polyglot.util.ErrorInfo;
import polyglot.util.InternalCompilerError;
import polyglot.main.*;

/**
 * A Translator generates output code from the processed AST.
 * Output is sent to one or more java file in the directory
 * <code>Options.output_directory</code>.  Each SourceFile in the AST
 * is output to exactly one java file.  The name of that file is
 * determined as follows:
 * <ul>
 * <li> If the SourceFile has a declaration of a public top-level class "C",
 * file name is "C.java".  It is an error for there to be more than one
 * top-level public declaration.
 * <li> If the SourceFile has no public declarations, the file name
 * is the input file name (e.g., "X.jl") with the suffix replaced with ".java"
 * (thus, "X.java").
 * </ul>
 *
 * To use:
 * <pre>
 *     new Translator(job, ts, nf, tf).translate(ast);
 * </pre>
 * The <code>ast</code> must be either a SourceFile or a SourceCollection.
 */
public class Translator extends PrettyPrinter implements Copy
{
    protected Job job;
    protected NodeFactory nf;
    protected TargetFactory tf;
    protected TypeSystem ts;
    
   /**
     * Create a Translator.  The output of the visitor is a collection of files
     * whose names are added to the collection <code>outputFiles</code>.
     */
    public Translator(Job job, TypeSystem ts, NodeFactory nf, TargetFactory tf) {
        super();

        this.job = job;
        this.nf = nf;
        this.tf = tf;
        this.ts = ts;
       
    }

    /**
     * Return the job associated with this Translator.
     */
    public Job job() { 
        return job;
    }
    
    /** Copy the translator. */
    public Object copy() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new InternalCompilerError("Java clone() weirdness.");
        }
    }
    
    /** Get the extension's type system. */
    public TypeSystem typeSystem() {
        return ts;
    }

    /** Get the extension's node factory. */
    public NodeFactory nodeFactory() {
        return nf;
    }

    public TargetFactory targetFactory() { 
      return tf;
    }
    
    /** Print an ast node using the given code writer.  This method should not
     * be called directly to translate a source file AST; use
     * <code>translate(Node)</code> instead.  This method should only be called
     * by nodes to print their children.
     */
    public void print(Node parent, Node child, CodeWriter w) {
        Translator tr = this;
        child.del().translate(w, tr);
    }

    /** Translate the entire AST. */
    public boolean translate(Node ast) {
        if (ast instanceof SourceFile) {
            SourceFile sfn = (SourceFile) ast;
            return translateSource(sfn);
        }
        else if (ast instanceof SourceCollection) {
            SourceCollection sc = (SourceCollection) ast;

            boolean okay = true;

            for (Iterator i = sc.sources().iterator(); i.hasNext(); ) {
                SourceFile sfn = (SourceFile) i.next();
                okay &= translateSource(sfn);
            }

            return okay;
        }
        else {
            throw new InternalCompilerError("AST root must be a SourceFile; " +
                                            "found a " + ast.getClass().getName());
        }
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

            writeHeader(sfn, w);

            for (Iterator i = sfn.decls().iterator(); i.hasNext(); ) {
                TopLevelDecl decl = (TopLevelDecl) i.next();

                if (decl.flags().isPublic() && decl != first) {
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

                if (i.hasNext()) {
                    w.newline(0);
                }
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
    
    public void translateTopLevelDecl(CodeWriter w, SourceFile parent, TopLevelDecl decl) {
        decl.del().translate(w, this);
    }

    /**
     * "Escapes" an input string "s" so that it can be used as a macro.
     * Removes all '.' and ':' chars and substitutes in '_' instead.
     * @param s - the input string to escape.
     * @return an escaped string.
     */
    public static String macroEscape(String s)
    {
      String out = "_";
      
      for(int i = 0; i < s.length(); i++)
      {
        char c = s.charAt(i);
        if(c == '.' || c == ':')
          out = out + "_";
        else
          out = out + c;
      }
      
      return out;
    }
    
   
    /** Write the package and import declarations for a source file. */
    protected void writeHeader(SourceFile sfn, CodeWriter w) {
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

    /** Get the list of public top-level classes declared in the source file. */
    protected List exports(SourceFile sfn) {
	List exports = new LinkedList();

	for (Iterator i = sfn.decls().iterator(); i.hasNext(); ) {
	    TopLevelDecl decl = (TopLevelDecl) i.next();

	    if (decl.flags().isPublic()) {
		exports.add(decl);
	    }
	}

	return exports;
    }

    public String toString() {
	return "Translator";
    }
}
