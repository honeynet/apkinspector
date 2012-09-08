package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.util.*;
import polyglot.visit.*;
import polyglot.main.Options;

/**
 * A <code>CanonicalTypeNode</code> is a type node for a canonical type.
 */
public class CanonicalTypeNode_c extends TypeNode_c implements CanonicalTypeNode
{
  public CanonicalTypeNode_c(Position pos, Type type) {
    super(pos);
    this.type = type;
  }


  /** Type check the type node.  Check accessibility of class types. */
  public Node typeCheck(TypeChecker tc) throws SemanticException {
      TypeSystem ts = tc.typeSystem();

      if (type.isClass()) {
          ClassType ct = type.toClass();
          if (ct.isTopLevel() || ct.isMember()) {
              if (! ts.classAccessible(ct, tc.context())) {
                  throw new SemanticException("Cannot access class \"" +
                      ct + "\" from the body of \"" +
                      tc.context().currentClass() + "\".", position());
              }
          }
      }

      return this;
  }

  public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
    w.write(type.translate(null));
  }

  /**
   * Translate the type.
   * If the "use-fully-qualified-class-names" options is used, then the
   * fully qualified names is written out (<code>java.lang.Object</code>).
   * Otherwise, the string that originally represented the type in the
   * source file is used.
   */
  public void translate(CodeWriter w, Translator tr) {
    if (Options.global.fully_qualified_names || ! (tr instanceof TypedTranslator)) {
      // if a field or local variable in this context shadows the package
      // name of the type, then the output code may not compile, e.g.
      //   ...
      //   java.lang.Object foo;
      //   foo.package.Bar = new foo.package.Bar();
      //   ...
      // 
      // But, the user asked for fully qualified names, so that's what they
      // get.
      w.write(type.translate(null));
    }
    else { 
      w.write(type.translate(((TypedTranslator) tr).context()));
    }
  }

  public String toString() {
    if (type == null) return "<unknown-type>";
    return type.toString();
  }

  public void dump(CodeWriter w) {
    super.dump(w);
    w.allowBreak(4, " ");
    w.begin(0);
    w.write("(type " + type + ")");
    w.end();
  }
}
