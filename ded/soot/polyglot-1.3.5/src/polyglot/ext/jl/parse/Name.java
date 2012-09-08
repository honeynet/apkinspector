package polyglot.ext.jl.parse;

import polyglot.ast.*;
import polyglot.parse.*;
import polyglot.types.*;
import polyglot.util.*;

/**
 * Represents an ambiguous, possibly qualified, identifier encountered while parsing.
 */
public class Name {
	public final Name prefix;
	public final String name;
	public final Position pos;
	NodeFactory nf;
	TypeSystem ts;

	public Name(BaseParser parser, Position pos, String name) {
		this(parser, pos, null, name);
	}

	public Name(BaseParser parser, Position pos, Name prefix, String name) {
		this.nf = parser.nf;
		this.ts = parser.ts;
		this.pos = pos;
		this.prefix = prefix;
		this.name = name;
	}

	// expr
	public Expr toExpr() {
		if (prefix == null) {
			return nf.AmbExpr(pos, name);
		}

		return nf.Field(pos, prefix.toReceiver(), name);
	}

	// expr or type
	public Receiver toReceiver() {
		if (prefix == null) {
			return nf.AmbReceiver(pos, name);
		}

		return nf.AmbReceiver(pos, prefix.toPrefix(), name);
	}

	// expr, type, or package
	public Prefix toPrefix() {
		if (prefix == null) {
			return nf.AmbPrefix(pos, name);
		}

		return nf.AmbPrefix(pos, prefix.toPrefix(), name);
	}

	// type or package
	public QualifierNode toQualifier() {
		if (prefix == null) {
			return nf.AmbQualifierNode(pos, name);
		}

		return nf.AmbQualifierNode(pos, prefix.toQualifier(), name);
	}

	// package
	public PackageNode toPackage() {
                if (prefix == null) {
                    return nf.PackageNode(pos, ts.createPackage(null, name));
                }
                else {
                    return nf.PackageNode(pos, ts.createPackage(prefix.toPackage().package_(), name));
                }
	}

	// type
	public TypeNode toType() {
		if (prefix == null) {
			return nf.AmbTypeNode(pos, name);
		}

		return nf.AmbTypeNode(pos, prefix.toQualifier(), name);
	}

	public String toString() {
		if (prefix == null) {
			return name;
		}

		return prefix.toString() + "." + name;
	}
}
