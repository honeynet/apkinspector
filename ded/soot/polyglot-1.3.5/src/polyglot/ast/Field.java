package polyglot.ast;

import polyglot.types.FieldInstance;

/**
 * A <code>Field</code> is an immutable representation of a Java field
 * access.  It consists of field name and may also have either a 
 * <code>Type</code> or an <code>Expr</code> containing the field being 
 * accessed.
 */
public interface Field extends Variable
{
    /**
     * Get the type object for the field.  This field may not be valid until
     * after type checking.
     */
    FieldInstance fieldInstance();

    /** Set the type object for the field. */
    Field fieldInstance(FieldInstance fi);

    /**
     * Get the field's container object or type.  May be null before
     * disambiguation.
     */
    Receiver target();

    /** Set the field's container object or type. */
    Field target(Receiver target);

    /**
     * Returns whether the target of this field is implicit, that is if the
     * target is either "this" or a classname, and the source code did not
     * explicitly provide a target. 
     */
    boolean isTargetImplicit();
    
    /** 
     * Set whether the target of the field is implicit.
     */
    Field targetImplicit(boolean implicit);
    
    /** Get the field's name. */
    String name();
    /** Set the field's name. */
    Field name(String name);
}
