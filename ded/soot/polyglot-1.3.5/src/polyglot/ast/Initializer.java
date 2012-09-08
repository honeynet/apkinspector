package polyglot.ast;

import polyglot.types.InitializerInstance;
import polyglot.types.Flags;

/**
 * An <code>Initializer</code> is an immutable representation of an
 * initializer block in a Java class (which appears outside of any
 * method).  Such a block is executed before the code for any of the
 * constructors.  Such a block can optionally be static, in which case
 * it is executed when the class is loaded.  
 */
public interface Initializer extends CodeDecl 
{
    /** Get the initializer's flags. */
    Flags flags();
    /** Set the initializer's flags. */
    Initializer flags(Flags flags);

    /**
     * Get the initializer's type object.  This field may not be valid until
     * after signature disambiguation.
     */
    InitializerInstance initializerInstance();

    /** Set the initializer's type object. */
    Initializer initializerInstance(InitializerInstance ii);
}
