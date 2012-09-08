package polyglot.ext.coffer.extension;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.ext.coffer.ast.*;

import java.util.*;

/** The Coffer extension of the <code>ProcedureDecl</code> node. 
 * 
 *  @see polyglot.ast.ProcedureDecl
 */
public class ProcedureDeclExt_c extends CofferExt_c {
    public void checkHeldKeys(KeySet held, KeySet stored) throws SemanticException {
        ProcedureDecl n = (ProcedureDecl) node();

        CofferProcedureInstance pi =
            (CofferProcedureInstance) n.procedureInstance();
            
        checkHeldKeys(held, pi.returnKeys(), n.position());

    }
    public void checkHeldKeysThrowConstraint(ThrowConstraint tc, KeySet held, KeySet stored) 
    throws SemanticException {
        checkHeldKeys(held, tc.keys(), tc.throwType().position());
        
    }
    
    private void checkHeldKeys(KeySet held, KeySet returnKeys, Position pos) throws SemanticException {
        if (! held.equals(returnKeys)) {
            KeySet too_much = held.removeAll(returnKeys);
            KeySet not_enough = returnKeys.removeAll(held);

            if (too_much.size() == 1) {
                Key k = (Key) too_much.iterator().next();
                throw new SemanticException(KeysToString(too_much) +
                                            " not freed at return.",
                                            pos);
            }
            else if (! too_much.isEmpty()) {
                throw new SemanticException(KeysToString(too_much) +
                                            " not freed at return.",
                                            pos);
            }

            /*
            if (! not_enough.isEmpty())
                throw new SemanticException(KeysToString(not_enough) +
                                            " not held at return.",
                                            n.position());
                                            */
        }        
    }
}
