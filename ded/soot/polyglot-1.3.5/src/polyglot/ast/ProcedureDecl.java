package polyglot.ast;

import polyglot.types.ProcedureInstance;
import polyglot.types.Flags;
import java.util.List;

/**
 * A procedure declaration.  A procedure is the supertype of methods and
 * constructors.
 */
public interface ProcedureDecl extends CodeDecl 
{
    /** The procedure's flags. */
    Flags flags();

    /** The procedure's name. */
    String name();

    /** The procedure's formal parameters.
     * @return A list of {@link polyglot.ast.Formal Formal}.
     */
    List formals();

    /** The procedure's exception throw types.
     * @return A list of {@link polyglot.ast.TypeNode TypeNode}.
     */
    List throwTypes();

    /**
     * The procedure type object.  This field may not be valid until
     * after signature disambiguation.
     */
    ProcedureInstance procedureInstance();
}
