package polyglot.frontend;

import java.io.*;
import polyglot.ast.*;
import polyglot.util.*;
import polyglot.frontend.Compiler;
import polyglot.main.Report;

/**
 * A pass which runs a parser.  After parsing it stores the AST in the Job.
 * so it can be accessed by later passes.
 */
public class ParserPass extends AbstractPass
{
    Job job;
    Compiler compiler;

    public ParserPass(Pass.ID id, Compiler compiler, Job job) {
        super(id);
	this.compiler = compiler;
	this.job = job;
    }

    public boolean run() {
	ErrorQueue eq = compiler.errorQueue();
	FileSource source = (FileSource) job.source();

	try {
	    Reader reader = source.open();

	    Parser p = compiler.sourceExtension().parser(reader, source, eq);

	    if (Report.should_report(Report.frontend, 2))
		Report.report(2, "Using parser " + p);

	    Node ast = p.parse();

	    source.close();

	    if (ast != null) {
		job.ast(ast);
		return true;
	    }

	    return false;
	}
	catch (IOException e) {
	    eq.enqueue(ErrorInfo.IO_ERROR, e.getMessage());
	    return false;
	}
    }

    public String toString() {
	return id + "(" + job.source() + ")";
    }
}
