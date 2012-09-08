package polyglot.ast;

import polyglot.types.ProcedureInstance;
import polyglot.types.CodeInstance;
import polyglot.types.Flags;
import java.util.List;

/**
 * A code declaration.  A "code" is the supertype of methods,
 * constructors, and initalizers.
 */
public interface CodeDecl extends ClassMember
{
    /** The body of the method, constructor, or initializer. */
    Block body();

    /** Set the body. */
    CodeDecl body(Block body);
    
    /** The CodeInstance of the method, constructor, or initializer. */
    CodeInstance codeInstance();
}
