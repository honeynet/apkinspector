package polyglot.ext.param.types;

import polyglot.ext.jl.types.*;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.*;
import java.util.*;
import java.io.*;

/**
 * Utility class that performs substitutions on type objects.
 */
public interface Subst extends Serializable
{
    /** Entries of the underlying substitution map.
     * @return An <code>Iterator</code> of <code>Map.Entry</code>.
     */
    public Iterator entries();

    /** Type system */
    public ParamTypeSystem typeSystem();

    /** Get the map of formals to actuals. */
    public Map substitutions();

    /** Perform substitutions on a type. */
    public Type substType(Type t);

    /** Perform substitutions on a PClass. */
    public PClass substPClass(PClass pc);

    /** Perform substititions on a field. */
    public FieldInstance substField(FieldInstance fi);

    /** Perform substititions on a method. */
    public MethodInstance substMethod(MethodInstance mi);

    /** Perform substititions on a constructor. */
    public ConstructorInstance substConstructor(ConstructorInstance ci);

    /** Perform substitutions on a list of types. */
    public List substTypeList(List list);

    /** Perform substitutions on a list of methods. */
    public List substMethodList(List list);

    /** Perform substitutions on a list of constructors. */
    public List substConstructorList(List list);

    /** Perform substitutions on a list of fields. */
    public List substFieldList(List list);

}
