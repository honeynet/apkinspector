package polyglot.ext.param.types;

import polyglot.types.*;
import polyglot.util.*;

import polyglot.ext.jl.types.*;

import java.util.*;

/**
 * A base implementation for mutable parametric classes.
 */
public class MuPClass_c extends PClass_c implements MuPClass {
    protected List/*[Param]*/ formals;
    protected ClassType clazz;
	
    protected MuPClass_c() { }

    public MuPClass_c(TypeSystem ts) {
        this(ts, null);
    }

    public MuPClass_c(TypeSystem ts, Position pos) {
	super(ts, pos);
	formals = new TypedList(new LinkedList(), Param.class, false);
    }

    /////////////////////////////////////////////////////////////////////////
    // Implement PClass

    public List formals() {
	return formals;
    }

    public ClassType clazz() {
        return clazz;
    }
    
    /////////////////////////////////////////////////////////////////////////
    // Implement MuPClass

    public void formals(List formals) {
	this.formals = formals;
    }

    public void addFormal(Param param) {
	formals().add(param);
    }
    
    public void clazz(ClassType clazz) {
	this.clazz = clazz;
    }
}
