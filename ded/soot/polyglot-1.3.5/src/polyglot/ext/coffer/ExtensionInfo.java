package polyglot.ext.coffer;

import polyglot.ext.coffer.parse.Lexer_c;
import polyglot.ext.coffer.parse.Grm;
import polyglot.ext.coffer.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.ext.coffer.visit.*;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.frontend.*;
import polyglot.main.*;
import polyglot.lex.Lexer;

import java.util.*;
import java.io.*;

/**
 * Extension information for coffer extension.
 */
public class ExtensionInfo extends polyglot.ext.param.ExtensionInfo {
    static {
        // force Topics to load
        Topics t = new Topics();
    }

    public String defaultFileExtension() {
        return "cof";
    }

    public String compilerName() {
        return "cofferc";
    }

    public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
        Lexer lexer = new Lexer_c(reader, source.name(), eq);
        Grm grm = new Grm(lexer, ts, nf, eq);
        return new CupParser(grm, source, eq);
    }

    protected NodeFactory createNodeFactory() {
        return new CofferNodeFactory_c();
    }
    protected TypeSystem createTypeSystem() {
        return new CofferTypeSystem_c();
    }

    public static final Pass.ID KEY_CHECK = new Pass.ID("key-check");

    public List passes(Job job) {
        List passes = super.passes(job);

        beforePass(passes, Pass.PRE_OUTPUT_ALL,
                   new VisitorPass(KEY_CHECK,
                                   job, new KeyChecker(job, ts, nf)));

        return passes;
    }

}
