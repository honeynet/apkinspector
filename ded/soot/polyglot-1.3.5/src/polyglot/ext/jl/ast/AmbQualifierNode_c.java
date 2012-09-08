package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;

/**
 * An <code>AmbQualifierNode</code> is an ambiguous AST node composed of
 * dot-separated list of identifiers that must resolve to a type qualifier.
 */
public class AmbQualifierNode_c extends Node_c implements AmbQualifierNode
{
    protected Qualifier qualifier;
    protected QualifierNode qual;
    protected String name;

    public AmbQualifierNode_c(Position pos, QualifierNode qual, String name) {
	super(pos);

	this.qual = qual;
	this.name = name;
    }

    public Qualifier qualifier() {
	return this.qualifier;
    }

    public String name() {
	return this.name;
    }

    public AmbQualifierNode name(String name) {
	AmbQualifierNode_c n = (AmbQualifierNode_c) copy();
	n.name = name;
	return n;
    }

    public QualifierNode qual() {
	return this.qual;
    }

    public AmbQualifierNode qual(QualifierNode qual) {
	AmbQualifierNode_c n = (AmbQualifierNode_c) copy();
	n.qual = qual;
	return n;
    }

    public AmbQualifierNode qualifier(Qualifier qualifier) {
	AmbQualifierNode_c n = (AmbQualifierNode_c) copy();
	n.qualifier = qualifier;
	return n;
    }

    protected AmbQualifierNode_c reconstruct(QualifierNode qual) {
	if (qual != this.qual) {
	    AmbQualifierNode_c n = (AmbQualifierNode_c) copy();
	    n.qual = qual;
	    return n;
	}

	return this;
    }

    public Node visitChildren(NodeVisitor v) {
	QualifierNode qual = (QualifierNode) visitChild(this.qual, v);
	return reconstruct(qual);
    }

    public Node buildTypes(TypeBuilder tb) throws SemanticException {
        return qualifier(tb.typeSystem().unknownQualifier(position()));
    }

    public Node disambiguate(AmbiguityRemover sc) throws SemanticException {
	Node n = sc.nodeFactory().disamb().disambiguate(this, sc, position(), qual, name);

	if (n instanceof QualifierNode) {
	    return n;
	}

	throw new SemanticException("Could not find type or package \"" +
            (qual == null ? name : qual.toString() + "." + name) +
	    "\".", position());
    }

    public Node typeCheck(TypeChecker tc) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot type check ambiguous node " + this + ".");
    } 

    public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
	throw new InternalCompilerError(position(),
	    "Cannot exception check ambiguous node " + this + ".");
    } 

    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	if (qual != null) {
            print(qual, w, tr);
            w.write(".");
        }
                
        w.write(name);
    }

    public void translate(CodeWriter w, Translator tr) {
      throw new InternalCompilerError(position(),
                                      "Cannot translate ambiguous node "
                                      + this + ".");
    }

    public String toString() {
	return (qual == null
		? name
		: qual.toString() + "." + name) + "{amb}";
    }

  public void dump(CodeWriter w) {
    super.dump(w);

    w.allowBreak(4, " ");
    w.begin(0);
    w.write("(name \"" + name + "\")");
    w.end();
  }
}
