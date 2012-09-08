package polyglot.ext.coffer.types;

import polyglot.types.*;

public interface ThrowConstraint {
    public KeySet keys();
    public ThrowConstraint keys(KeySet keys);
    public Type throwType();
    public ThrowConstraint throwType(Type throwType);
}
