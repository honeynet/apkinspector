package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.ast.Assert;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;
import java.util.*;

/**
 * This is a node factory that creates no nodes.  It, rather than
 * NodeFactory_c, should be subclassed by any extension which should
 * override the creation of <a>all</a> nodes.
 */
public abstract class AbstractNodeFactory_c implements NodeFactory
{
    public Disamb disamb() {
        return new Disamb_c();
    }

    public final AmbPrefix AmbPrefix(Position pos, String name) {
	return AmbPrefix(pos, null, name);
    }

    public final AmbReceiver AmbReceiver(Position pos, String name) {
	return AmbReceiver(pos, null, name);
    }

    public final AmbQualifierNode AmbQualifierNode(Position pos, String name) {
	return AmbQualifierNode(pos, null, name);
    }

    public final AmbTypeNode AmbTypeNode(Position pos, String name) {
        return AmbTypeNode(pos, null, name);
    }

    public final ArrayInit ArrayInit(Position pos) {
	return ArrayInit(pos, Collections.EMPTY_LIST);
    }

    public final Assert Assert(Position pos, Expr cond) {
        return Assert(pos, cond, null);
    }

    public final Block Block(Position pos) {
	return Block(pos, Collections.EMPTY_LIST);
    }

    public final Block Block(Position pos, Stmt s1) {
        List l = new ArrayList(1);
	l.add(s1);
	return Block(pos, l);
    }

    public final Block Block(Position pos, Stmt s1, Stmt s2) {
        List l = new ArrayList(2);
	l.add(s1);
	l.add(s2);
	return Block(pos, l);
    }

    public final Block Block(Position pos, Stmt s1, Stmt s2, Stmt s3) {
        List l = new ArrayList(3);
	l.add(s1);
	l.add(s2);
	l.add(s3);
	return Block(pos, l);
    }

    public final Block Block(Position pos, Stmt s1, Stmt s2, Stmt s3, Stmt s4) {
        List l = new ArrayList(4);
	l.add(s1);
	l.add(s2);
	l.add(s3);
	l.add(s4);
	return Block(pos, l);
    }

    public final Branch Break(Position pos) {
	return Branch(pos, Branch.BREAK, null);
    }

    public final Branch Break(Position pos, String label) {
	return Branch(pos, Branch.BREAK, label);
    }

    public final Branch Continue(Position pos) {
	return Branch(pos, Branch.CONTINUE, null);
    }

    public final Branch Continue(Position pos, String label) {
	return Branch(pos, Branch.CONTINUE, label);
    }

    public final Branch Branch(Position pos, Branch.Kind kind) {
	return Branch(pos, kind, null);
    }

    public final Call Call(Position pos, String name) {
	return Call(pos, null, name, Collections.EMPTY_LIST);
    }

    public final Call Call(Position pos, String name, Expr a1) {
        List l = new ArrayList(1);
	l.add(a1);
	return Call(pos, null, name, l);
    }

    public final Call Call(Position pos, String name, Expr a1, Expr a2) {
        List l = new ArrayList(2);
	l.add(a1);
	l.add(a2);
	return Call(pos, null, name, l);
    }

    public final Call Call(Position pos, String name, Expr a1, Expr a2, Expr a3) {
        List l = new ArrayList(3);
	l.add(a1);
	l.add(a2);
	l.add(a3);
	return Call(pos, null, name, l);
    }

    public final Call Call(Position pos, String name, Expr a1, Expr a2, Expr a3, Expr a4) {
        List l = new ArrayList(4);
	l.add(a1);
	l.add(a2);
	l.add(a3);
	l.add(a4);
	return Call(pos, null, name, l);
    }

    public final Call Call(Position pos, String name, List args) {
        return Call(pos, null, name, args);
    }

    public final Call Call(Position pos, Receiver target, String name) {
	return Call(pos, target, name, Collections.EMPTY_LIST);
    }

    public final Call Call(Position pos, Receiver target, String name, Expr a1) {
        List l = new ArrayList(1);
	l.add(a1);
	return Call(pos, target, name, l);
    }

    public final Call Call(Position pos, Receiver target, String name, Expr a1, Expr a2) {
        List l = new ArrayList(2);
	l.add(a1);
	l.add(a2);
	return Call(pos, target, name, l);
    }

    public final Call Call(Position pos, Receiver target, String name, Expr a1, Expr a2, Expr a3) {
        List l = new ArrayList(3);
	l.add(a1);
	l.add(a2);
	l.add(a3);
	return Call(pos, target, name, l);
    }

    public final Call Call(Position pos, Receiver target, String name, Expr a1, Expr a2, Expr a3, Expr a4) {
	List l = new ArrayList(4);
	l.add(a1);
	l.add(a2);
	l.add(a3);
	l.add(a4);
	return Call(pos, target, name, l);
    }

    public final Case Default(Position pos) {
	return Case(pos, null);
    }

    public final ConstructorCall ThisCall(Position pos, List args) {
	return ConstructorCall(pos, ConstructorCall.THIS, null, args);
    }

    public final ConstructorCall ThisCall(Position pos, Expr outer, List args) {
	return ConstructorCall(pos, ConstructorCall.THIS, outer, args);
    }

    public final ConstructorCall SuperCall(Position pos, List args) {
	return ConstructorCall(pos, ConstructorCall.SUPER, null, args);
    }

    public final ConstructorCall SuperCall(Position pos, Expr outer, List args) {
	return ConstructorCall(pos, ConstructorCall.SUPER, outer, args);
    }

    public final ConstructorCall ConstructorCall(Position pos, ConstructorCall.Kind kind, List args) {
	return ConstructorCall(pos, kind, null, args);
    }

    public final FieldDecl FieldDecl(Position pos, Flags flags, TypeNode type, String name) {
	return FieldDecl(pos, flags, type, name, null);
    }

    public final Field Field(Position pos, String name) {
	return Field(pos, null, name);
    }

    public final If If(Position pos, Expr cond, Stmt consequent) {
	return If(pos, cond, consequent, null);
    }

    public final LocalDecl LocalDecl(Position pos, Flags flags, TypeNode type, String name) {
        return LocalDecl(pos, flags, type, name, null);
    }

    public final New New(Position pos, TypeNode type, List args) {
        return New(pos, null, type, args, null);
    }

    public final New New(Position pos, TypeNode type, List args, ClassBody body) {
	return New(pos, null, type, args, body);
    }

    public final New New(Position pos, Expr outer, TypeNode objectType, List args) {
        return New(pos, outer, objectType, args, null);
    }

    public final NewArray NewArray(Position pos, TypeNode base, List dims) {
	return NewArray(pos, base, dims, 0, null);
    }

    public final NewArray NewArray(Position pos, TypeNode base, List dims, int addDims) {
	return NewArray(pos, base, dims, addDims, null);
    }

    public final NewArray NewArray(Position pos, TypeNode base, int addDims, ArrayInit init) {
	return NewArray(pos, base, Collections.EMPTY_LIST, addDims, init);
    }

    public final Return Return(Position pos) {
	return Return(pos, null);
    }

    public final SourceFile SourceFile(Position pos, List decls) {
        return SourceFile(pos, null, Collections.EMPTY_LIST, decls);
    }

    public final SourceFile SourceFile(Position pos, List imports, List decls) {
        return SourceFile(pos, null, imports, decls);
    }

    public final Special This(Position pos) {
        return Special(pos, Special.THIS, null);
    }

    public final Special This(Position pos, TypeNode outer) {
        return Special(pos, Special.THIS, outer);
    }

    public final Special Super(Position pos) {
        return Special(pos, Special.SUPER, null);
    }

    public final Special Super(Position pos, TypeNode outer) {
        return Special(pos, Special.SUPER, outer);
    }

    public final Special Special(Position pos, Special.Kind kind) {
        return Special(pos, kind, null);
    }

    public final Try Try(Position pos, Block tryBlock, List catchBlocks) {
        return Try(pos, tryBlock, catchBlocks, null);
    }

    public final Unary Unary(Position pos, Expr expr, Unary.Operator op) {
        return Unary(pos, op, expr);
    }
}
