package polyglot.ext.coffer.types;

import polyglot.types.*;
import polyglot.ext.param.types.*;

public interface CofferParsedClassType extends CofferClassType, ParsedClassType {
    void setKey(Key key);
    void setInstantiatedFrom(PClass pc);
}
