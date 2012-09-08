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
public class OutputPass extends AbstractPass
{
    protected Job job;
    protected Translator translator;

    /**
     * Create a Translator.  The output of the visitor is a collection of files
     * whose names are added to the collection <code>outputFiles</code>.
     */
    public OutputPass(Pass.ID id, Job job, Translator translator) {
	super(id);
        this.job = job;
        this.translator = translator;
    }

    public boolean run() {
        Node ast = job.ast();

        if (ast == null) {
            throw new InternalCompilerError("AST is null");
        }

        return translator.translate(ast);
    }
}
