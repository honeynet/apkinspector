package polyglot.ext.skel;

import polyglot.lex.Lexer;
import polyglot.ext.skel.parse.Lexer_c;
import polyglot.ext.skel.parse.Grm;
import polyglot.ext.skel.ast.*;
import polyglot.ext.skel.types.*;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.frontend.*;
import polyglot.main.*;

import java.util.*;
import java.io.*;

/**
 * Extension information for skel extension.
 */
public class ExtensionInfo extends polyglot.ext.jl.ExtensionInfo {
    static {
        // force Topics to load
        Topics t = new Topics();
    }

    public String defaultFileExtension() {
        return "sx";
    }

    public String compilerName() {
        return "skelc";
    }

    public Parser parser(Reader reader, FileSource source, ErrorQueue eq) {
        Lexer lexer = new Lexer_c(reader, source.name(), eq);
        Grm grm = new Grm(lexer, ts, nf, eq);
        return new CupParser(grm, source, eq);
    }

    protected NodeFactory createNodeFactory() {
        return new SkelNodeFactory_c();
    }

    protected TypeSystem createTypeSystem() {
        return new SkelTypeSystem_c();
    }

    public List passes(Job job) {
        List passes = super.passes(job);
        // TODO: add passes as needed by your compiler
        return passes;
    }

}
