package polyglot.ext.coffer.extension;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.ext.coffer.types.*;
import java.util.*;

/** An immutable representation of the Coffer class declaration.
 *  It extends the Java class declaration with the label/principal parameters
 *  and the authority constraint.
 */
public interface CofferExt extends Ext {
    KeySet keyFlow(KeySet held_keys, Type throwType);
    KeySet keyAlias(KeySet stored_keys, Type throwType);
    void checkHeldKeys(KeySet held, KeySet stored) throws SemanticException;
}
