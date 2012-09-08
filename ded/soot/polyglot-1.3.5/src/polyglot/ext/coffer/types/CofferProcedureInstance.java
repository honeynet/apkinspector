package polyglot.ext.coffer.types;

import polyglot.types.*;
import java.util.*;

/** Coffer procedure instance. A wrapper of all the type information 
 *  related to a procedure. 
 */
public interface CofferProcedureInstance extends ProcedureInstance
{
    KeySet entryKeys();
    KeySet returnKeys();
    List throwConstraints();

    CofferProcedureInstance entryKeys(KeySet entryKeys);
    CofferProcedureInstance returnKeys(KeySet returnKeys);
    CofferProcedureInstance throwConstraints(List throwConstraints);
}
