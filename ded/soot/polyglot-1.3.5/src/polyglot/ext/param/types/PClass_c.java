package polyglot.ext.param.types;

import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.*;

import polyglot.ext.jl.types.*;

import java.util.*;

/**
 * A base implementation for parametric classes.
 * This class is a wrapper around
 * a ClassType that associates formal parameters with the class.
 * formals can be any type object.
 */
public abstract class PClass_c extends TypeObject_c implements PClass {
    protected PClass_c() { }

    public PClass_c(TypeSystem ts) {
        this(ts, null);
    }

    public PClass_c(TypeSystem ts, Position pos) {
	super(ts, pos);
    }

    /////////////////////////////////////////////////////////////////////////
    // Implement PClass

    public ClassType instantiate(Position pos, List actuals) 
        throws SemanticException
    {
	ParamTypeSystem pts = (ParamTypeSystem) typeSystem();
        return pts.instantiate(pos, this, actuals);
    }
    
    
    /////////////////////////////////////////////////////////////////////////
    // Implement TypeObject
    
    public boolean isCanonical() {
        if (!clazz().isCanonical()) {
            return false;
        } 
     
        for (Iterator i = formals().iterator(); i.hasNext(); ) {
            Param p = (Param) i.next();
            if (!p.isCanonical()) {
                return false;
            }
        }
        
        return true;
    }

    
    /////////////////////////////////////////////////////////////////////////
    // Implement Named
   
    public String name() {
        return clazz().name();
    }

    public String fullName() {
        return clazz().fullName();
    }

    
    /////////////////////////////////////////////////////////////////////////
    // Implement Importable

    public Package package_() {
        return clazz().package_();
    }
    
}
