package polyglot.visit;

import polyglot.ast.NodeFactory;
import polyglot.frontend.*;
import polyglot.types.TypeSystem;
import polyglot.visit.*;

/**
 * A HeaderTranslator acts exactly like a Translator object,
 * but translate() functions which are recipients of this object
 * know that they're supposed to be generating a .h file instead
 * of a .cpp file (this is only used for the c++ backend) 
 * @author ak333
 */
public class HeaderTranslator extends polyglot.visit.TypedTranslator
{
  public HeaderTranslator(Job job, TypeSystem ts, NodeFactory nf, TargetFactory tf) {
    super(job, ts, nf, tf);
  }  
  
  public HeaderTranslator(CppTranslator t) {
    super(t.job(), t.typeSystem(), t.nodeFactory(), t.targetFactory());
    this.context = t.context;
    this.appendSemicolon = t.appendSemicolon();
  }
}


