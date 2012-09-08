package polyglot.ext.param.types;

import polyglot.types.*;
import java.util.List;

/**
 * A parameterized type instantiated on actual arguments.
 */
public interface InstType extends Type
{
    PClass instantiatedFrom();
    List actuals();
}
