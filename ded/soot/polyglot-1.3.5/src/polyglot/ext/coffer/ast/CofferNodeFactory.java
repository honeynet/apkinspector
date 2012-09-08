package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;
import java.util.*;

/**
 * NodeFactory for Coffer extension.
 */
public interface CofferNodeFactory extends NodeFactory {
    Free Free(Position pos, Expr expr);
    TrackedTypeNode TrackedTypeNode(Position pos, KeyNode key, TypeNode base);
    AmbKeySetNode AmbKeySetNode(Position pos, List keys);
    CanonicalKeySetNode CanonicalKeySetNode(Position pos, KeySet keys);
    KeyNode KeyNode(Position pos, Key key);

    New TrackedNew(Position pos, Expr outer, KeyNode key, TypeNode objectType, List args, ClassBody body);

    ThrowConstraintNode ThrowConstraintNode(Position pos, TypeNode tn, KeySetNode keys);

    CofferMethodDecl CofferMethodDecl(Position pos, Flags flags, TypeNode
                                    returnType, String name, List argTypes,
                                    KeySetNode entryKeys, KeySetNode returnKeys,
                                    List throwConstraints, Block body);

    CofferConstructorDecl CofferConstructorDecl(Position pos, Flags flags, String name, List
                                              argTypes, KeySetNode entryKeys,
                                              KeySetNode returnKeys, List
                                              throwConstraints, Block body);


    CofferClassDecl CofferClassDecl(Position pos, Flags flags, String name,
                                  KeyNode key, TypeNode superClass, List
                                  interfaces, ClassBody body);
}
