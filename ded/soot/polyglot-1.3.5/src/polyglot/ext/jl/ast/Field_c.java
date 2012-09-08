package polyglot.ext.jl.ast;

import java.util.Collections;
import java.util.List;

import polyglot.ast.AmbReceiver;
import polyglot.ast.Expr;
import polyglot.ast.Field;
import polyglot.ast.Node;
import polyglot.ast.Precedence;
import polyglot.ast.Receiver;
import polyglot.ast.Special;
import polyglot.ast.Term;
import polyglot.ast.TypeNode;
import polyglot.types.Context;
import polyglot.types.FieldInstance;
import polyglot.types.Flags;
import polyglot.types.SemanticException;
import polyglot.types.Type;
import polyglot.types.TypeSystem;
import polyglot.types.VarInstance;
import polyglot.util.CodeWriter;
import polyglot.util.InternalCompilerError;
import polyglot.util.Position;
import polyglot.visit.AscriptionVisitor;
import polyglot.visit.CFGBuilder;
import polyglot.visit.NodeVisitor;
import polyglot.visit.PrettyPrinter;
import polyglot.visit.Translator;
import polyglot.visit.TypeBuilder;
import polyglot.visit.TypeChecker;
import polyglot.main.Options;

/**
 * A <code>Field</code> is an immutable representation of a Java field
 * access.  It consists of field name and may also have either a 
 * <code>Type</code> or an <code>Expr</code> containing the field being 
 * accessed.
 */
public class Field_c extends Expr_c implements Field
{
  protected Receiver target;
  protected String name;
  protected FieldInstance fi;
  protected boolean targetImplicit;

  public Field_c(Position pos, Receiver target, String name) {
    super(pos);
    this.target = target;
    this.name = name;
    this.targetImplicit = false;

    if (target == null) {
      throw new InternalCompilerError("Cannot create a field with a null "
                                      + "target.  Use AmbExpr or prefix "
                                      + "with the appropriate type node or "
                                      + "this.");
    }
  }

  /** Get the precedence of the field. */
  public Precedence precedence() { 
    return Precedence.LITERAL;
  }

  /** Get the target of the field. */
  public Receiver target() {
    return this.target;
  }

  /** Set the target of the field. */
  public Field target(Receiver target) {
    Field_c n = (Field_c) copy();
    n.target = target;
    return n;
  }

  /** Get the name of the field. */
  public String name() {
    return this.name;
  }

  /** Set the name of the field. */
  public Field name(String name) {
    Field_c n = (Field_c) copy();
    n.name = name;
    return n;
  }

  /** Return the access flags of the variable. */
  public Flags flags() {
    return fi.flags();
  }

  /** Get the field instance of the field. */
  public FieldInstance fieldInstance() {
    return fi;
  }

  /** Set the field instance of the field. */
  public Field fieldInstance(FieldInstance fi) {
    /*
    if (! fi.type().isCanonical()) {
      throw new InternalCompilerError("Type of " + fi + " in " +
                                      fi.container() + " is not canonical.");
    }
    */

    Field_c n = (Field_c) copy();
    n.fi = fi;
    return n;
  }

  public boolean isTargetImplicit() {
      return this.targetImplicit;
  }

  public Field targetImplicit(boolean implicit) {
      Field_c n = (Field_c) copy();
      n.targetImplicit = implicit;
      return n;
  }

  /** Reconstruct the field. */
  protected Field_c reconstruct(Receiver target) {
    if (target != this.target) {
      Field_c n = (Field_c) copy();
      n.target = target;
      return n;
    }

    return this;
  }

  /** Visit the children of the field. */
  public Node visitChildren(NodeVisitor v) {
    Receiver target = (Receiver) visitChild(this.target, v);
    return reconstruct(target);
  }

  public Node buildTypes(TypeBuilder tb) throws SemanticException {
      Field_c n = (Field_c) super.buildTypes(tb);

      TypeSystem ts = tb.typeSystem();

      FieldInstance fi = ts.fieldInstance(position(), ts.Object(), Flags.NONE,
                                          ts.unknownType(position()), name);
      return n.fieldInstance(fi);
  }

  /** Type check the field. */
  public Node typeCheck(TypeChecker tc) throws SemanticException {
      Context c = tc.context();
      TypeSystem ts = tc.typeSystem();
      
      if (! target.type().isReference()) {
	  throw new SemanticException("Cannot access field \"" + name +
				      "\" " + (target instanceof Expr
					       ? "on an expression "
					       : "") +
				      "of non-reference type \"" +
				      target.type() + "\".", target.position());
      }
      
      FieldInstance fi = ts.findField(target.type().toReference(), name, c.currentClass());
      
      if (fi == null) {
	  throw new InternalCompilerError("Cannot access field on node of type " +
					  target.getClass().getName() + ".");
      }
      
      Field_c f = (Field_c)fieldInstance(fi).type(fi.type());
      f.checkConsistency(c);
      
      return f;
  }
  
  public Type childExpectedType(Expr child, AscriptionVisitor av)
  {
      if (child == target) {
          return fi.container();
      }

      return child.type();
  }

  /** Write the field to an output file. */
  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
	if (!targetImplicit) {
	  // explicit target.
	  if (target instanceof Expr) {
	    printSubExpr((Expr) target, w, tr);
	  }
	  else if (target instanceof TypeNode || target instanceof AmbReceiver) {
	    print(target, w, tr);
	  }
	
	  w.write(".");
	}
	w.write(name);
  }

  public void dump(CodeWriter w) {
    super.dump(w);

    w.allowBreak(4, " ");
    w.begin(0);
    w.write("(name \"" + name + "\")");
    w.end();
  }

  public Term entry() {
      if (target instanceof Expr) {
          return ((Expr) target).entry();
      }
      return this;
  }

  public List acceptCFG(CFGBuilder v, List succs) {
      if (target instanceof Expr) {
          v.visitCFG((Expr) target, this);
      }
      return succs;
  }


  public String toString() {
    return ((target != null && !targetImplicit)? target + "." : "") + name;
  }


  public List throwTypes(TypeSystem ts) {
      if (target instanceof Expr && ! (target instanceof Special)) {
          return Collections.singletonList(ts.NullPointerException());
      }

      return Collections.EMPTY_LIST;
  }

  public boolean isConstant() {
    if (fi != null &&
        (target instanceof TypeNode ||
         (target instanceof Special && targetImplicit))) {
      return fi.isConstant();
    }

    return false;
  }

  public Object constantValue() {
    if (isConstant()) {
      return fi.constantValue();
    }

    return null;
  }
  
  // check that the implicit target setting is correct.
  protected void checkConsistency(Context c) {
      if (targetImplicit) {
          VarInstance vi = c.findVariableSilent(name);
          if (vi instanceof FieldInstance) {
              FieldInstance rfi = (FieldInstance) vi;
              if (c.typeSystem().equals(rfi, fi)) {
                  // all is OK.
                  return;
              }
          }
          throw new InternalCompilerError("Field " + this + " has an " +
               "implicit target, but the name " + name + " resolves to " +
               vi + " instead of " + target, position());
      }      
  }

}
