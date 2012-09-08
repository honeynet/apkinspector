package polyglot.ext.jl.types;

import java.util.LinkedList;
import java.util.List;

import polyglot.frontend.Source;
import polyglot.types.*;
import polyglot.types.Package;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.util.TypedList;

/**
 * ParsedClassType
 *
 * Overview: 
 * A ParsedClassType represents a information that has been parsed (but not
 * necessarily type checked) from a .java file.
 **/
public class ParsedClassType_c extends ClassType_c implements ParsedClassType
{
    protected transient LazyClassInitializer init;
    protected transient Source fromSource;
    protected Type superType;
    protected List interfaces;
    protected List methods;
    protected List fields;
    protected List constructors;
    protected List memberClasses;
    protected Package package_;
    protected Flags flags;
    protected Kind kind;
    protected String name;
    protected ClassType outer;
    /** Was the class declared in a static context? */
    protected boolean inStaticContext = false;

    protected ParsedClassType_c() {
	super();
    }

    public ParsedClassType_c(TypeSystem ts, LazyClassInitializer init, 
                             Source fromSource) {
	super(ts);
        this.init = init;
        this.fromSource = fromSource;

        if (init == null) {
          throw new InternalCompilerError("Null lazy class initializer");
        }
    }

    public Source fromSource() {
        return fromSource;
    }
    
    public Kind kind() {
        return kind;
    }

    public void inStaticContext(boolean inStaticContext) {
        this.inStaticContext = inStaticContext;
    }

    public boolean inStaticContext() {
        return inStaticContext;
    }
    
    public ClassType outer() {
        if (isTopLevel())
            return null;
        if (outer == null)
            throw new InternalCompilerError("Nested classes must have outer classes.");
            
        return outer;
    }

    public String name() {
        if (isAnonymous())
            throw new InternalCompilerError("Anonymous classes cannot have names.");

        if (name == null)
            throw new InternalCompilerError("Non-anonymous classes must have names.");
        return name;
    }

    /** Get the class's super type. */
    public Type superType() {
        return this.superType;
    }

    /** Get the class's package. */
    public Package package_() {
        return package_;
    }

    /** Get the class's flags. */
    public Flags flags() {
        if (isAnonymous())
            return Flags.NONE;
        return flags;
    }

    /** Return true if we no longer need the initializer object. */
    protected boolean initialized() {
        return this.methods != null &&
               this.constructors != null &&
               this.fields != null &&
               this.memberClasses != null &&
               this.interfaces != null;
    }

    /** Free the initializer object if we no longer need it. */
    protected void freeInit() {
        if (initialized()) {
            init = null;
        }
        else if (init == null) {
          throw new InternalCompilerError("Null lazy class initializer");
        }
    }

    public void flags(Flags flags) {
	this.flags = flags;
    }

    public void kind(Kind kind) {
        this.kind = kind;
    }

    public void outer(ClassType outer) {
        if (isTopLevel())
            throw new InternalCompilerError("Top-level classes cannot have outer classes.");
        this.outer = outer;
    }

    public void name(String name) {
        if (isAnonymous())
            throw new InternalCompilerError("Anonymous classes cannot have names.");
        this.name = name;
    }

    public void position(Position pos) {
	this.position = pos;
    }

    public void package_(Package p) {
	this.package_ = p;
    }

    public void superType(Type t) {
	this.superType = t;
    }

    public void addInterface(Type t) {
	interfaces().add(t);
    }

    public void addMethod(MethodInstance mi) {
	methods().add(mi);
    }

    public void addConstructor(ConstructorInstance ci) {
	constructors().add(ci);
    }

    public void addField(FieldInstance fi) {
	fields().add(fi);
    }

    public void addMemberClass(ClassType t) {
	memberClasses().add(t);
    }

    /** Return a mutable list of constructors */
    public List constructors() {
        if (constructors == null) {
            constructors = new TypedList(new LinkedList(), ConstructorInstance.class, false);
            init.initConstructors(this);
            freeInit();
        }
        return constructors;
    }

    /** Return a mutable list of member classes */
    public List memberClasses() {
        if (memberClasses == null) {
            memberClasses = new TypedList(new LinkedList(), Type.class, false);
            init.initMemberClasses(this);
            freeInit();
        }
        return memberClasses;
    }

    /** Return a mutable list of methods */
    public List methods() {
        if (methods == null) {
            methods = new TypedList(new LinkedList(), MethodInstance.class, false);
            init.initMethods(this);
            freeInit();
        }
        return methods;
    }

    /** Return a mutable list of fields */
    public List fields() {
        if (fields == null) {
            fields = new TypedList(new LinkedList(), FieldInstance.class, false);
            init.initFields(this);
            freeInit();
        }
        return fields;
    }

    /** Return a mutable list of interfaces */
    public List interfaces() {
        if (interfaces == null) {
            interfaces = new TypedList(new LinkedList(), Type.class, false);
            init.initInterfaces(this);
            freeInit();
        }
        return interfaces;
    }
}
