package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import java.util.*;

/**
 * A Coffer class declaration.
 * <code>ClassDecl</code> is extended with a possibly-null key name.
 */
public interface CofferClassDecl extends ClassDecl {
    KeyNode key();
    CofferClassDecl key(KeyNode key);
}
