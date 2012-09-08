package polyglot.ext.coffer.ast;

import polyglot.ast.*;
import polyglot.ext.jl.ast.*;
import polyglot.ext.coffer.types.*;
import polyglot.ext.coffer.extension.*;
import polyglot.types.Flags;
import polyglot.types.Package;
import polyglot.types.Type;
import polyglot.types.Qualifier;
import polyglot.util.*;
import java.util.*;

/** An implementation of the <code>CofferNodeFactory</code> interface. 
 */
public class CofferNodeFactory_c extends NodeFactory_c implements CofferNodeFactory
{
    public CofferNodeFactory_c() {
        super(new CofferExtFactory_c());
    }
    protected CofferNodeFactory_c(ExtFactory extFact) {
        super(extFact);
    }

    public New TrackedNew(Position pos, Expr outer, KeyNode key, TypeNode objectType, List args, ClassBody body) {
        return New(pos, outer, TrackedTypeNode(key.position(), key, objectType), args, body);
        
    }

    public Free Free(Position pos, Expr expr) {
        Free n = new Free_c(pos, expr);
        n = (Free)n.ext(((CofferExtFactory_c)extFactory()).extFree());
        return n;
    }

    public TrackedTypeNode TrackedTypeNode(Position pos, KeyNode key, TypeNode base) {
        TrackedTypeNode n = new TrackedTypeNode_c(pos, key, base);
        n = (TrackedTypeNode)n.ext(((CofferExtFactory_c)extFactory()).extTrackedTypeNode());
        return n;
    }

    public AmbKeySetNode AmbKeySetNode(Position pos, List keys) {
        AmbKeySetNode n = new AmbKeySetNode_c(pos, keys);
        n = (AmbKeySetNode)n.ext(((CofferExtFactory_c)extFactory()).extAmbKeySetNode());
        return n;
    }

    public CanonicalKeySetNode CanonicalKeySetNode(Position pos, KeySet keys) {
        CanonicalKeySetNode n = new CanonicalKeySetNode_c(pos, keys);
        n = (CanonicalKeySetNode)n.ext(((CofferExtFactory_c)extFactory()).extCanonicalKeySetNode());
        return n;
    }

    public KeyNode KeyNode(Position pos, Key key) {
        KeyNode n = new KeyNode_c(pos, key);
        n = (KeyNode)n.ext(((CofferExtFactory_c)extFactory()).extKeyNode());
        return n;
    }

    public ClassDecl ClassDecl(Position pos, Flags flags, String name,
                               TypeNode superClass, List interfaces,
                               ClassBody body)
    {
        return CofferClassDecl(pos, flags, name, null,
                              superClass, interfaces, body);
    }

    public CofferClassDecl CofferClassDecl(Position pos, Flags flags,
                                         String name, KeyNode key,
                                         TypeNode superClass, List interfaces,
                                         ClassBody body)
    {
        CofferClassDecl n = new CofferClassDecl_c(pos, flags, name, key,
                superClass, interfaces, body);
        n = (CofferClassDecl)n.ext(extFactory().extClassDecl());
        return n;
    }

    public ThrowConstraintNode ThrowConstraintNode(Position pos, TypeNode tn, KeySetNode keys) {
        ThrowConstraintNode n = new ThrowConstraintNode_c(pos, tn, keys);
        n = (ThrowConstraintNode)n.ext(((CofferExtFactory_c)extFactory()).extThrowConstraintNode());
        return n;
    }

    public MethodDecl MethodDecl(Position pos, Flags flags,
                                 TypeNode returnType, String name,
                                 List argTypes, List excTypes, Block body)
    {
        List l = new LinkedList();

        for (Iterator i = excTypes.iterator(); i.hasNext(); ) {
            TypeNode tn = (TypeNode) i.next();
            l.add(ThrowConstraintNode(tn.position(), tn, null));
        }

        return CofferMethodDecl(pos, flags, returnType, name, argTypes,
                               null, null, l, body);

    }

    public ConstructorDecl ConstructorDecl(Position pos, Flags flags,
                                           String name, List argTypes,
                                           List excTypes, Block body)
    {
        List l = new LinkedList();

        for (Iterator i = excTypes.iterator(); i.hasNext(); ) {
            TypeNode tn = (TypeNode) i.next();
            l.add(ThrowConstraintNode(tn.position(), tn, null));
        }

        return CofferConstructorDecl(pos, flags, name, argTypes,
                                     null, null, l, body);
    }

    public CofferMethodDecl CofferMethodDecl(Position pos, Flags flags,
                                              TypeNode returnType, String name,
                                              List argTypes,
                                              KeySetNode entryKeys,
                                              KeySetNode returnKeys,
                                              List throwConstraints,
                                              Block body)
    {
        CofferMethodDecl n = new CofferMethodDecl_c(pos, flags, returnType, name, argTypes,
                entryKeys, returnKeys, throwConstraints, body);
        n = (CofferMethodDecl)n.ext(extFactory().extMethodDecl());
        return n;
    }

    public CofferConstructorDecl CofferConstructorDecl(Position pos,
                                                        Flags flags,
                                                        String name,
                                                        List argTypes,
                                                        KeySetNode entryKeys,
                                                        KeySetNode returnKeys,
                                                        List throwConstraints,
                                                        Block body)
    {
        CofferConstructorDecl n = new CofferConstructorDecl_c(pos, flags, name, argTypes,
                entryKeys, returnKeys, throwConstraints, body);
        n = (CofferConstructorDecl)n.ext(extFactory().extConstructorDecl());
        return n;
    }
    
    public Assign Assign(Position pos, Expr left, Assign.Operator op, Expr right) {
        return (Assign) super.Assign(pos, left, op, right).del(new AssignDel_c());
    }
    
}
