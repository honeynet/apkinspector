package polyglot.frontend;

import polyglot.ast.*;
import polyglot.frontend.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.types.Package;

import java.io.*;
import java.util.*;

/** An output pass generates output code from the processed AST. */
public class PrettyPrintPass extends AbstractPass
{
    protected Job job;
    protected PrettyPrinter pp;
    protected CodeWriter w;

    /**
     * Create a PrettyPrinter.  The output of the visitor is a collection of files
     * whose names are added to the collection <code>outputFiles</code>.
     */
    public PrettyPrintPass(Pass.ID id, Job job, CodeWriter w, PrettyPrinter pp) {
	super(id);
        this.job = job;
        this.pp = pp;
        this.w = w;
    }
    
    public boolean run() {
        Node ast = job.ast();

        if (ast == null) {
            w.write("<<<< null AST >>>>");
        }
        else {
            pp.printAst(ast, w);
        }

        return true;
    }
}
