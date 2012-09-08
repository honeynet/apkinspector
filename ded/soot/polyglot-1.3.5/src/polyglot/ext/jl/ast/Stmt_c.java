package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.visit.*;
import polyglot.util.*;

/**
 * A <code>Stmt</code> represents any Java statement.  All statements must
 * be subtypes of Stmt.
 */
public abstract class Stmt_c extends Term_c implements Stmt
{
    public Stmt_c(Position pos) {
	super(pos);
    }
}
