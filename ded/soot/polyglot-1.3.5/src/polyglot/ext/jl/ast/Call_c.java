package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;

import java.util.*;
import polyglot.main.*;

/**
 * A <code>Call</code> is an immutable representation of a Java
 * method call.  It consists of a method name and a list of arguments.
 * It may also have either a Type upon which the method is being
 * called or an expression upon which the method is being called.
 */
public class Call_c extends Expr_c implements Call
{
  protected Receiver target;
  protected String name;
  protected List arguments;
  protected MethodInstance mi;
  protected boolean targetImplicit;

  public Call_c(Position pos, Receiver target, String name,
                List arguments) {
    super(pos);
    this.target = target;
    this.name = name;
    this.arguments = TypedList.copyAndCheck(arguments, Expr.class, true);
    this.targetImplicit = (target == null);
  }

  /** Get the precedence of the call. */
  public Precedence precedence() {
    return Precedence.LITERAL;
  }

  /** Get the target object or type of the call. */
  public Receiver target() {
    return this.target;
  }

  /** Set the target object or type of the call. */
  public Call target(Receiver target) {
    Call_c n = (Call_c) copy();
    n.target = target;
    return n;
  }

  /** Get the name of the call. */
  public String name() {
    return this.name;
  }

  /** Set the name of the call. */
  public Call name(String name) {
    Call_c n = (Call_c) copy();
    n.name = name;
    return n;
  }

  public ProcedureInstance procedureInstance() {
      return methodInstance();
  }

  /** Get the method instance of the call. */
  public MethodInstance methodInstance() {
    return this.mi;
  }

  /** Set the method instance of the call. */
  public Call methodInstance(MethodInstance mi) {
    Call_c n = (Call_c) copy();
    n.mi = mi;
    return n;
  }

  public boolean isTargetImplicit() {
      return this.targetImplicit;
  }

  public Call targetImplicit(boolean targetImplicit) {
      if (targetImplicit == this.targetImplicit) {
          return this;
      }
      
      Call_c n = (Call_c) copy();
      n.targetImplicit = targetImplicit;
      return n;
  }

  /** Get the actual arguments of the call. */
  public List arguments() {
    return this.arguments;
  }

  /** Set the actual arguments of the call. */
  public ProcedureCall arguments(List arguments) {
    Call_c n = (Call_c) copy();
    n.arguments = TypedList.copyAndCheck(arguments, Expr.class, true);
    return n;
  }

  /** Reconstruct the call. */
  protected Call_c reconstruct(Receiver target, List arguments) {
    if (target != this.target || ! CollectionUtil.equals(arguments,
                                                         this.arguments)) {
      Call_c n = (Call_c) copy();
      n.target = target;
      n.arguments = TypedList.copyAndCheck(arguments, Expr.class, true);
      return n;
    }

    return this;
  }

  /** Visit the children of the call. */
  public Node visitChildren(NodeVisitor v) {
    Receiver target = (Receiver) visitChild(this.target, v);
    List arguments = visitList(this.arguments, v);
    return reconstruct(target, arguments);
  }

  public Node buildTypes(TypeBuilder tb) throws SemanticException {
    Call_c n = (Call_c) super.buildTypes(tb);

    TypeSystem ts = tb.typeSystem();

    List l = new ArrayList(arguments.size());
    for (int i = 0; i < arguments.size(); i++) {
      l.add(ts.unknownType(position()));
    }

    MethodInstance mi = ts.methodInstance(position(), ts.Object(),
                                          Flags.NONE,
                                          ts.unknownType(position()),
                                          name, l,
                                          Collections.EMPTY_LIST);
    return n.methodInstance(mi);
  }

    /**
     * Typecheck the Call when the target is null. This method finds
     * an appropriate target, and then type checks accordingly.
     * 
     * @param argTypes list of <code>Type</code>s of the arguments
     */
    protected Node typeCheckNullTarget(TypeChecker tc, List argTypes) throws SemanticException {
        TypeSystem ts = tc.typeSystem();
        NodeFactory nf = tc.nodeFactory();
        Context c = tc.context();

        // the target is null, and thus implicit
        // let's find the target, using the context, and
        // set the target appropriately, and then type check
        // the result
        MethodInstance mi =  c.findMethod(this.name, argTypes);
        
        Receiver r;
        if (mi.flags().isStatic()) {
            r = nf.CanonicalTypeNode(position(), mi.container()).type(mi.container());
        } else {
            // The method is non-static, so we must prepend with "this", but we
            // need to determine if the "this" should be qualified.  Get the
            // enclosing class which brought the method into scope.  This is
            // different from mi.container().  mi.container() returns a super type
            // of the class we want.
            ClassType scope = c.findMethodScope(name);

            if (! ts.equals(scope, c.currentClass())) {
                r = nf.This(position(),
                            nf.CanonicalTypeNode(position(), scope)).type(scope);
            }
            else {
                r = nf.This(position()).type(scope);
            }
        }

        // we call typeCheck on the reciever too.
        r = (Receiver)r.typeCheck(tc);
        return this.targetImplicit(true).target(r).del().typeCheck(tc);
    }

    /** Type check the call. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        TypeSystem ts = tc.typeSystem();
        Context c = tc.context();

        List argTypes = new ArrayList(this.arguments.size());

        for (Iterator i = this.arguments.iterator(); i.hasNext(); ) {
            Expr e = (Expr) i.next();
            argTypes.add(e.type());
        }

        if (this.target == null) {
            return this.typeCheckNullTarget(tc, argTypes);
        }

        ReferenceType targetType = this.findTargetType();
        MethodInstance mi = ts.findMethod(targetType, 
                                          this.name, 
                                          argTypes, 
                                          c.currentClass());
        
        
        /* This call is in a static context if and only if
         * the target (possibly implicit) is a type node.
         */
        boolean staticContext = (this.target instanceof TypeNode);


        if (staticContext && !mi.flags().isStatic()) {
            throw new SemanticException("Cannot call non-static method " + this.name
                                  + " of " + targetType + " in static "
                                  + "context.", this.position());
        }

        // If the target is super, but the method is abstract, then complain.
        if (this.target instanceof Special && 
            ((Special)this.target).kind() == Special.SUPER &&
            mi.flags().isAbstract()) {
                throw new SemanticException("Cannot call an abstract method " +
                               "of the super class", this.position());            
        }
        // If we found a method, the call must type check, so no need to check
        // the arguments here.
        
        Call_c call = (Call_c)this.methodInstance(mi).type(mi.returnType());
        call.checkConsistency(c);
        return call;
    }
    
    //ak333: made public so it could be accessed by CppCallDel
    public ReferenceType findTargetType() throws SemanticException { 
        Type t = target.type();
        if (t.isReference()) {
            return t.toReference();
        } else {
            // trying to invoke a method on a non-reference type.
            // let's pull out an appropriate error message.
            if (target instanceof Expr) {
                throw new SemanticException("Cannot invoke method \"" + name + "\" on "
                                    + "an expression of non-reference type "
                                    + t + ".", target.position());
            }
            else if (target instanceof TypeNode) {
                throw new SemanticException("Cannot invoke static method \"" + name
                                    + "\" on non-reference type " + t + ".",
                                    target.position());
            }            
            throw new SemanticException("Receiver of method invocation must be a "
                                    + "reference type.",
                                        target.position());
        }
    }

  public Type childExpectedType(Expr child, AscriptionVisitor av)
  {
      if (child == target) {
          return mi.container();
      }

      Iterator i = this.arguments.iterator();
      Iterator j = mi.formalTypes().iterator();

      while (i.hasNext() && j.hasNext()) {
          Expr e = (Expr) i.next();
          Type t = (Type) j.next();

          if (e == child) {
              return t;
          }
      }

      return child.type();
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    sb.append(targetImplicit ? "" : target.toString() + ".");
    sb.append(name);
    sb.append("(");

    int count = 0;

    for (Iterator i = arguments.iterator(); i.hasNext(); ) {
        if (count++ > 2) {
            sb.append("...");
            break;
        }

        Expr n = (Expr) i.next();
        sb.append(n.toString());

        if (i.hasNext()) {
            sb.append(", ");
        }
    }

    sb.append(")");
    return sb.toString();
  }

  /** Write the expression to an output file. */
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	if (!targetImplicit) {
	  if (target instanceof Expr) {
	    printSubExpr((Expr) target, w, tr);
	    w.write(".");
	  }
	  else if (target != null) {
	    print(target, w, tr);
	    w.write(".");
	  }
	}
	
	w.write(name + "(");
	w.begin(0);
	
	for(Iterator i = arguments.iterator(); i.hasNext();) {
	  Expr e = (Expr) i.next();
	  print(e, w, tr);
	
	  if (i.hasNext()) {
	    w.write(",");
	    w.allowBreak(0, " ");
	  }
	}
	w.end();
	w.write(")");
  }

  /** Dumps the AST. */
  public void dump(CodeWriter w) {
    super.dump(w);

    if ( mi != null ) {
      w.allowBreak(4, " ");
      w.begin(0);
      w.write("(instance " + mi + ")");
      w.end();
    }

    w.allowBreak(4, " ");
    w.begin(0);
    w.write("(name " + name + ")");
    w.end();

    w.allowBreak(4, " ");
    w.begin(0);
    w.write("(arguments " + arguments + ")");
    w.end();
  }

  public Term entry() {
      if (target instanceof Expr) {
          return ((Expr) target).entry();
      }
      return listEntry(arguments, this);
  }

  public List acceptCFG(CFGBuilder v, List succs) {
      if (target instanceof Expr) {
          Expr t = (Expr) target;
          v.visitCFG(t, listEntry(arguments, this));
      }

      v.visitCFGList(arguments, this);

      return succs;
  }

  /** Check exceptions thrown by the call. */
  public Node exceptionCheck(ExceptionChecker ec) throws SemanticException {
    if (mi == null) {
      throw new InternalCompilerError(position(),
                                      "Null method instance after type "
                                      + "check.");
    }

    return super.exceptionCheck(ec);
  }


  public List throwTypes(TypeSystem ts) {
    List l = new LinkedList();

    l.addAll(mi.throwTypes());
    l.addAll(ts.uncheckedExceptions());

    if (target instanceof Expr && ! (target instanceof Special)) {
      l.add(ts.NullPointerException());
    }

    return l;
  }
  
  // check that the implicit target setting is correct.
  protected void checkConsistency(Context c) throws SemanticException {
      if (targetImplicit) {
          // the target is implicit. Check that the
          // method found in the target type is the
          // same as the method found in the context.
          
          // as exception will be thrown if no appropriate method
          // exists. 
          MethodInstance ctxtMI = c.findMethod(name, mi.formalTypes());
          
          // cannot perform this check due to the context's findMethod returning a 
          // different method instance than the typeSystem in some situations
//          if (!c.typeSystem().equals(ctxtMI, mi)) {
//              throw new InternalCompilerError("Method call " + this + " has an " +
//                   "implicit target, but the name " + name + " resolves to " +
//                   ctxtMI + " in " + ctxtMI.container() + " instead of " + mi+ " in " + mi.container(), position());
//          }
      }      
  }
  
}
