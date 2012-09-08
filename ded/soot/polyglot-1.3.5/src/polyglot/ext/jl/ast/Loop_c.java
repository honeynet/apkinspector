package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;

/**
 * An immutable representation of a Java language <code>while</code>
 * statement.  It contains a statement to be executed and an expression
 * to be tested indicating whether to reexecute the statement.
 */ 
public abstract class Loop_c extends Stmt_c implements Loop
{
    public Loop_c(Position pos) {
	super(pos);
    }

    public boolean condIsConstant() {
        return cond().isConstant();
    }

    public boolean condIsConstantTrue() {
        return Boolean.TRUE.equals(cond().constantValue());
    }
}
