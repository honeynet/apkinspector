package polyglot.ext.pao.types;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import polyglot.ext.jl.types.TypeSystem_c;
import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.util.InternalCompilerError;

/**
 * Implementation of the PAO type system interface. Also overrides some
 * methods of <code>TypeSystem_c</code>.
 */
public class PaoTypeSystem_c extends TypeSystem_c implements PaoTypeSystem {

    /**
     * Returns a new instance of <code>PaoPrimitiveType_c</code>
     * @see PaoPrimitiveType_c
     */
    public PrimitiveType createPrimitive(PrimitiveType.Kind kind) {
        return new PaoPrimitiveType_c(this, kind);
    }

    /**
     * Returns a new instance of <code>PaoParsedClassType_c</code>
     * @see PaoParsedClassType_c
     */
    public ParsedClassType createClassType(LazyClassInitializer init, 
                                           Source fromSource) {
        return new PaoParsedClassType_c(this, init, fromSource);
    }

    /**
     * The package that contains the runtime classes for boxing primitive
     * values as objects.
     */
    private static final String RUNTIME_PACKAGE = "polyglot.ext.pao.runtime";

    /**
     * @see polyglot.ext.pao.types.PaoTypeSystem#primitiveEquals()
     */
    public MethodInstance primitiveEquals() {
        // The method instance could be cached for greater efficiency,
        // but we are not too worried about this.
        String name = RUNTIME_PACKAGE + ".Primitive";

        try {
            // use the system resolver to find the type named by name.
            Type ct = (Type) systemResolver().find(name);

            // create an argument list: two arguments of type Object.
            List args = new LinkedList();
            args.add(Object());
            args.add(Object());

            // take the first method "equals(Object, Object)" in ct.
            List l = ct.toClass().methods("equals", args);
            if (!l.isEmpty()) {
                return (MethodInstance)l.get(0);
            }
        }
        catch (SemanticException e) {
            throw new InternalCompilerError(e.getMessage());
        }

        throw new InternalCompilerError("Could not find equals method.");
    }

    public MethodInstance getter(PrimitiveType t) {
        // The method instances could be cached for greater efficiency,
        // but we are not too worried about this.
        
        String methodName = t.toString() + "Value";
        
        // get the type used to represent boxed values of type t
        ReferenceType boxedType = boxedType(t);

        // take the first method with the appropriate name and an empty 
        // argument list, in the type boxedType
        List l = boxedType.methods(methodName, Collections.EMPTY_LIST);
        if (!l.isEmpty()) {
            return (MethodInstance)l.get(0);
        }

        throw new InternalCompilerError("Could not find getter for " + t);
    }

    public ClassType boxedType(PrimitiveType t) {
        // The class types could be cached for greater efficiency,
        // but we are not too worried about this.

        String name = RUNTIME_PACKAGE + "."
                + wrapperTypeString(t).substring("java.lang.".length());

        try {
            return ((Type)systemResolver().find(name)).toClass();

        }
        catch (SemanticException e) {
            throw new InternalCompilerError(e.getMessage());
        }
    }

    public ConstructorInstance wrapper(PrimitiveType t) {
        // The constructor instances could be cached for greater efficiency,
        // but we are not too worried about this.

        ClassType ct = boxedType(t);
        for (Iterator i = ct.constructors().iterator(); i.hasNext(); ) {
            ConstructorInstance ci = (ConstructorInstance) i.next();
            if (ci.formalTypes().size() == 1) {
                Type argType = (Type) ci.formalTypes().get(0);
                if (equals(argType, t)) {
                    // found the appropriate constructor
                    return ci;
                }
            }
        }

        throw new InternalCompilerError("Could not find constructor for " + t);
    }
}
