package polyglot.types;

/**
 * A <code>MemberInstance</code> is an entity that can be a member of
 * a class.
 */
public interface MemberInstance extends TypeObject
{
    /**
     * Return the member's flags.
     */
    Flags flags();

    /**
     * Return the member's containing type.
     */
    ReferenceType container();
}
