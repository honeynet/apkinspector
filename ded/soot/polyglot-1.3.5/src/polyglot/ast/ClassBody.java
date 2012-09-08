package polyglot.ast;

import java.util.List;

/**
 * A <code>ClassBody</code> represents the body of a class or interface
 * declaration or the body of an anonymous class.
 */
public interface ClassBody extends Term
{
    /**
     * List of the class's members.
     * @return A list of {@link polyglot.ast.ClassMember ClassMember}.
     */
    List members();

    /**
     * Set the class's members.
     * @param members A list of {@link polyglot.ast.ClassMember ClassMember}.
     */
    ClassBody members(List members);

    /**
     * Add a member to the class, returning a new node.
     */
    ClassBody addMember(ClassMember member);
}
