package polyglot.visit;

import polyglot.main.*;
import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;

import java.io.*;
import java.util.*;

/**
 * Visitor which serializes class objects and adds a field to the class
 * containing the serialization.
 */
public class ClassSerializer extends NodeVisitor
{
    protected TypeEncoder te;
    protected ErrorQueue eq;
    protected Date date;
    protected TypeSystem ts;
    protected NodeFactory nf;
    protected Version ver;

    public ClassSerializer(TypeSystem ts, NodeFactory nf, Date date, ErrorQueue eq, Version ver) {
	this.ts = ts;
	this.nf = nf;
	this.te = new TypeEncoder( ts);
	this.eq = eq;
	this.date = date;
        this.ver = ver;
    }

    public Node override(Node n) {
        // Stop at class members.  We only want to encode top-level classes.
	if (n instanceof ClassMember && ! (n instanceof ClassDecl)) {
	    return n;
	}

	return null;
    }

    public Node leave(Node old, Node n, NodeVisitor v) {
	if (! (n instanceof ClassDecl)) {
	    return n;
	}

	try {
	    ClassDecl cn = (ClassDecl) n;
	    ClassBody body = cn.body();
	    ParsedClassType ct = cn.type();
	    byte[] b;

            // HACK: force class members to get created from lazy class
            // initializer.
            ct.superType();
            ct.interfaces();
            ct.memberClasses();
            ct.constructors();
            ct.methods();
            ct.fields();

	    if (! (ct.isTopLevel() || ct.isMember())) {
	        return n;
	    }

	    /* Add the compiler version number. */
            String suffix = ver.name();

	    // Check if we've already serialized.
	    if (ct.fieldNamed("jlc$CompilerVersion$" + suffix) != null ||
		ct.fieldNamed("jlc$SourceLastModified$" + suffix) != null ||
		ct.fieldNamed("jlc$ClassType$" + suffix) != null) {

		eq.enqueue(ErrorInfo.SEMANTIC_ERROR,
			   "Cannot encode Polyglot type information " +
			   "more than once.");

		return n;
	    }

	    Flags flags = Flags.PUBLIC.set(Flags.STATIC).set(Flags.FINAL);

	    FieldDecl f;
            FieldInstance fi;
            InitializerInstance ii;

	    /* Add the compiler version number. */
	    String version = ver.major() + "." +
			     ver.minor() + "." +
			     ver.patch_level();

            Position pos = Position.COMPILER_GENERATED;

	    fi = ts.fieldInstance(pos, ct,
                                  flags, ts.String(),
                                  "jlc$CompilerVersion$" + suffix);
            ii = ts.initializerInstance(pos, ct, Flags.STATIC);
	    f = nf.FieldDecl(fi.position(), fi.flags(),
		             nf.CanonicalTypeNode(fi.position(), fi.type()),
			     fi.name(),
			     nf.StringLit(pos, version).type(ts.String()));

	    f = f.fieldInstance(fi);
            f = f.initializerInstance(ii);
	    body = body.addMember(f);

	    /* Add the date of the last source file modification. */
	    long time = date.getTime();

	    fi = ts.fieldInstance(pos, ct,
                                  flags, ts.Long(),
                                  "jlc$SourceLastModified$" + suffix);
            ii = ts.initializerInstance(pos, ct, Flags.STATIC);
	    f = nf.FieldDecl(fi.position(), fi.flags(),
		             nf.CanonicalTypeNode(fi.position(), fi.type()),
			     fi.name(),
			     nf.IntLit(pos, IntLit.LONG, time).type(ts.Long()));

	    f = f.fieldInstance(fi);
            f = f.initializerInstance(ii);
	    body = body.addMember(f);

	    /* Add the class type info. */
	    fi = ts.fieldInstance(pos, ct,
                                  flags, ts.String(),
                                  "jlc$ClassType$" + suffix);
            ii = ts.initializerInstance(pos, ct, Flags.STATIC);
	    f = nf.FieldDecl(fi.position(), fi.flags(),
		             nf.CanonicalTypeNode(fi.position(), fi.type()),
			     fi.name(),
			     nf.StringLit(pos, te.encode(ct)).type(ts.String()));

	    f = f.fieldInstance(fi);
            f = f.initializerInstance(ii);
	    body = body.addMember(f);

	    return cn.body(body);
	}
	catch (IOException e) {
            if (Report.should_report(Report.serialize, 1))
                e.printStackTrace();
	    eq.enqueue(ErrorInfo.IO_ERROR,
		       "Unable to encode Polyglot type information.");
	    return n;
	}
    }
}
