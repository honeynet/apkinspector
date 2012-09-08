package polyglot.ext.jl.ast;

import polyglot.ast.*;

import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.main.Options;

/**
 * An <code>Import</code> is an immutable representation of a Java
 * <code>import</code> statement.  It consists of the string representing the
 * item being imported and the kind which is either indicating that a class
 * is being imported, or that an entire package is being imported.
 */
public class Import_c extends Node_c implements Import
{
    protected Kind kind;
    protected String name;

    public Import_c(Position pos, Kind kind, String name) {
	super(pos);
	this.name = name;
	this.kind = kind;
    }

    /** Get the name of the import. */
    public String name() {
	return this.name;
    }

    /** Set the name of the import. */
    public Import name(String name) {
	Import_c n = (Import_c) copy();
	n.name = name;
	return n;
    }

    /** Get the kind of the import. */
    public Kind kind() {
	return this.kind;
    }

    /** Set the kind of the import. */
    public Import kind(Kind kind) {
	Import_c n = (Import_c) copy();
	n.kind = kind;
	return n;
    }

    /** Build type objects for the import. */
    public Node buildTypes(TypeBuilder tb) throws SemanticException {
	ImportTable it = tb.importTable();

	if (kind == CLASS) {
	    it.addClassImport(name);
	}
	else if (kind == PACKAGE) {
	    it.addPackageImport(name);
	}

	return this;
    }

    /** Check that imported classes and packages exist. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        if (kind == PACKAGE && tc.typeSystem().packageExists(name)) {
            return this;
        }

        // Must be importing a class, either as p.C, or as p.C.*

        // The first component of the type name must be a package.
        String pkgName = StringUtil.getFirstComponent(name);

        if (! tc.typeSystem().packageExists(pkgName)) {
            throw new SemanticException("Package \"" + pkgName +
                "\" not found.", position());
        }

        // The type must exist.
        Named nt = tc.typeSystem().forName(name);

        // And the type must be accessible.
        if (nt instanceof Type) {
            Type t = (Type) nt;
            if (t.isClass()) {
                tc.typeSystem().classAccessibleFromPackage(t.toClass(),
                    tc.context().package_());
            }
        }

	return this;
    }

    public String toString() {
	return "import " + name + (kind == PACKAGE ? ".*" : "");
    }

    /** Write the import to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
		if (! Options.global.fully_qualified_names) {
		    w.write("import ");
		    w.write(name);
		
		    if (kind == PACKAGE) {
		        w.write(".*");
		    }
		
		    w.write(";");
		    w.newline(0);
		}
    }
}
