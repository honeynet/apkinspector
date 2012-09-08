package polyglot.ext.coffer.types;

import polyglot.types.*;

public interface CofferContext extends Context {
    void addKey(Key key);
    Key findKey(String name) throws SemanticException;
}
