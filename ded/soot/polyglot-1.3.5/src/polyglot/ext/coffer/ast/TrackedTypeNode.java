package polyglot.ext.coffer.ast;

import polyglot.ext.coffer.types.*;
import polyglot.ast.*;
import java.util.*;

/** Type node for a class instantiated with a key.
 */
public interface TrackedTypeNode extends TypeNode
{
    TypeNode base();
    TrackedTypeNode base(TypeNode base);

    KeyNode key();
    TrackedTypeNode key(KeyNode key);
}
