package polyglot.types;

import java.io.Serializable;

/**
 * A place holder type used to serialize types that cannot be serialized.  
 */
public interface PlaceHolder extends Serializable {
    TypeObject resolve(TypeSystem ts);
}
