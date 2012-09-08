package polyglot.ast;

import polyglot.types.MethodInstance;
import java.util.List;

/**
 * A <code>Call</code> is an immutable representation of a Java
 * method call.  It consists of a method name and a list of arguments.
 * It may also have either a Type upon which the method is being
 * called or an expression upon which the method is being called.
 */
public interface Call extends Expr, ProcedureCall
{
    /**
     * The call's target object.
     */
    Receiver target();

    /**
     * Set the call's target.
     */
    Call target(Receiver target);

    /**
     * The name of the method to call.
     */
    String name();

    /**
     * Set the name of the method to call.
     */
    Call name(String name);

    /**
     * Indicates if the target of this call is implicit, that 
     * is, was not specified explicitly in the syntax.  
     * @return boolean indicating if the target of this call is implicit
     */
    boolean isTargetImplicit();
    
    /**
     * Set whether the target of this call is implicit.
     */
    Call targetImplicit(boolean targetImplicit);
    
    /**
     * The call's actual arguments.
     * @return A list of {@link polyglot.ast.Expr Expr}.
     */
    List arguments();

    /**
     * Set the call's actual arguments.
     * @param arguments A list of {@link polyglot.ast.Expr Expr}.
     */
    ProcedureCall arguments(List arguments);

    /**
     * The type object of the method we are calling.  This is, generally, only
     * valid after the type-checking pass.
     */
    MethodInstance methodInstance();

    /**
     * Set the type object of the method we are calling.
     */
    Call methodInstance(MethodInstance mi);
}
