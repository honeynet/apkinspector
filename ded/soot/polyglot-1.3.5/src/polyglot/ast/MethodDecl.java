package polyglot.ast;

import polyglot.types.MethodInstance;
import polyglot.types.Flags;
import java.util.List;

/**
 * A method declaration.
 */
public interface MethodDecl extends ProcedureDecl 
{
    /** The method's flags. */
    Flags flags();

    /** Set the method's flags. */
    MethodDecl flags(Flags flags);

    /** The method's return type.  */
    TypeNode returnType();

    /** Set the method's return type.  */
    MethodDecl returnType(TypeNode returnType);

    /** The method's name. */
    String name();

    /** Set the method's name. */
    MethodDecl name(String name);

    /** The method's formal parameters.
     * @return A list of {@link polyglot.ast.Formal Formal}.
     */
    List formals();

    /** Set the method's formal parameters.
     * @param formals A list of {@link polyglot.ast.Formal Formal}.
     */
    MethodDecl formals(List formals);

    /** The method's exception throw types.
     * @return A list of {@link polyglot.ast.TypeNode TypeNode}.
     */
    List throwTypes();

    /** Set the method's exception throw types.
     * @param throwTypes A list of {@link polyglot.ast.TypeNode TypeNode}.
     */
    MethodDecl throwTypes(List throwTypes);

    /**
     * The method type object.  This field may not be valid until
     * after signature disambiguation.
     */
    MethodInstance methodInstance();

    /** Set the method's type object. */
    MethodDecl methodInstance(MethodInstance mi);
}
