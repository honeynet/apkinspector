package polyglot.ast;

import java.util.List;
import polyglot.types.Flags;
import polyglot.types.ParsedClassType;

/**
 * A <code>ClassDecl</code> represents a top-level, member, or local class
 * declaration.
 */
public interface ClassDecl extends Term, TopLevelDecl, ClassMember
{
    /**
     * The type of the class declaration.
     */
    ParsedClassType type();

    /**
     * Set the type of the class declaration.
     */
    ClassDecl type(ParsedClassType type);

    /**
     * The class declaration's flags.
     */
    Flags flags();

    /**
     * Set the class declaration's flags.
     */
    ClassDecl flags(Flags flags);

    /**
     * The class declaration's name.
     */
    String name();

    /**
     * Set the class declaration's name.
     */
    ClassDecl name(String name);

    /**
     * The class's super class.
     */
    TypeNode superClass();

    /**
     * Set the class's super class.
     */
    ClassDecl superClass(TypeNode superClass);

    /**
     * The class's interface list.
     * @return A list of {@link polyglot.ast.TypeNode TypeNode}.
     */
    List interfaces();

    /**
     * Set the class's interface list.
     * @param interfaces A list of {@link polyglot.ast.TypeNode TypeNode}.
     */
    ClassDecl interfaces(List interfaces);

    /**
     * The class's body.
     */
    ClassBody body();

    /**
     * Set the class's body.
     */
    ClassDecl body(ClassBody body);
}
