package polyglot.ext.coffer.types;

import polyglot.types.*;
import polyglot.ext.param.types.*;
import polyglot.util.*;
import java.util.*;

public interface CofferSubst extends Subst
{
    public Key substKey(Key key);
    public KeySet substKeySet(KeySet key);
    public ThrowConstraint substThrowConstraint(ThrowConstraint c);
    public List substThrowConstraintList(List l);

    public Key get(Key formal);
    public void put(Key formal, Key actual);
}
