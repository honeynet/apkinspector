package polyglot.ext.jl.qq;

import java_cup.runtime.Symbol;
import polyglot.ast.*;

/** Quasiquoting parser.  Interface to the parser with new start rules
  to enable quasiquoting. */
public interface QQParser {
    /** Parse the input as an <code>Expr</code>. */
    public Symbol qq_expr() throws Exception;

    /** Parse the input as an <code>Stmt</code>. */
    public Symbol qq_stmt() throws Exception;

    /** Parse the input as an <code>TypeNode</code>. */
    public Symbol qq_type() throws Exception;

    /** Parse the input as an <code>ClassDecl</code>. */
    public Symbol qq_decl() throws Exception;

    /** Parse the input as an <code>SourceFile</code>. */
    public Symbol qq_file() throws Exception;

    /** Parse the input as an <code>ClassMember</code>. */
    public Symbol qq_member() throws Exception;
}
