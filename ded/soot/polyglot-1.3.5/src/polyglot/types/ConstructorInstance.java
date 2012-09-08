package polyglot.types;

import java.util.List;

/**
 * A <code>ConstructorInstance</code> contains type information for a
 * constructor.
 */
public interface ConstructorInstance extends ProcedureInstance
{
    /**
     * Set the flags of the constructor.
     */
    ConstructorInstance flags(Flags flags);

    /**
     * Set the types of the formal parameters of the constructor.
     * @param l A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    ConstructorInstance formalTypes(List l);

    /**
     * Set the types of the exceptions thrown by the constructor.
     * @param l A list of <code>Type</code>.
     * @see polyglot.types.Type
     */
    ConstructorInstance throwTypes(List l);

    /**
     * Set the containing class of the constructor.
     */
    ConstructorInstance container(ClassType container);
}
