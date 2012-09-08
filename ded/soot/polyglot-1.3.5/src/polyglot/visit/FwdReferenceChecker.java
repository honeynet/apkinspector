package polyglot.visit;

import java.util.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import polyglot.ast.*;
import polyglot.ast.ConstructorCall;
import polyglot.ast.Node;
import polyglot.ast.NodeFactory;
import polyglot.frontend.Job;
import polyglot.types.ConstructorInstance;
import polyglot.types.Context;
import polyglot.types.SemanticException;
import polyglot.types.TypeSystem;
import polyglot.util.InternalCompilerError;

/** Visitor which ensures that field intializers and initializers do not
 * make illegal forward references to fields.
 *  This is an implementation of the rules of the Java Language Spec, 2nd
 * Edition, Section 8.3.2.3 
 */
public class FwdReferenceChecker extends ContextVisitor
{
    public FwdReferenceChecker(Job job, TypeSystem ts, NodeFactory nf) {
	super(job, ts, nf);
    }

    private boolean inInitialization = false;
    private boolean inStaticInit = false;
    private Field fieldAssignLHS = null;
    private Set declaredFields = new HashSet();
    
    protected NodeVisitor enterCall(Node n) throws SemanticException {
        if (n instanceof FieldDecl) {
            FieldDecl fd = (FieldDecl)n;
            declaredFields.add(fd.fieldInstance());
            
            FwdReferenceChecker frc = (FwdReferenceChecker)this.copy();
            frc.inInitialization = true;
            frc.inStaticInit = fd.flags().isStatic();
            return frc;
        }
        else if (n instanceof Initializer) {
            FwdReferenceChecker frc = (FwdReferenceChecker)this.copy();
            frc.inInitialization = true;
            frc.inStaticInit = ((Initializer)n).flags().isStatic();
            return frc;
        }
        else if (n instanceof FieldAssign) {
            FwdReferenceChecker frc = (FwdReferenceChecker)this.copy();
            frc.fieldAssignLHS = (Field)((FieldAssign)n).left();
            return frc;
        }
        else if (n instanceof Field) {
            if (fieldAssignLHS == n) {
                // the field is on  the left hand side of an assignment.
                // we can ignore it
                fieldAssignLHS = null;
            }
            else if (inInitialization) {
                // we need to check if this is an illegal fwd reference.
                Field f = (Field)n;
                
                // an illegal fwd reference if a usage of an instance 
                // (resp. static) field occurs in an instance (resp. static)
                // initialization, and the innermost enclosing class or 
                // interface of the usage is the same as the container of
                // the field, and we have not yet seen the field declaration.
                //
                // In addition, if a field is not accessed as a simple name, 
                // then all is ok

                if (inStaticInit == f.fieldInstance().flags().isStatic() &&
                    context().currentClass().equals(f.fieldInstance().container()) &&
                   !declaredFields.contains(f.fieldInstance()) &&
                   f.isTargetImplicit()) {
                    throw new SemanticException("Illegal forward reference", 
                                                f.position());
                }
            }
        }
        return this;        
    }
}

