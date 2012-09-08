package polyglot.ext.jl.ast;

import polyglot.ast.DelFactory;
import polyglot.ast.JL;
import polyglot.util.InternalCompilerError;

/**
 * This abstract implementation of <code>DelFactory</code> provides
 * a way of chaining together DelFactories, and default implementations
 * of factory methods for each node.
 * 
 * <p>
 * For a given type of AST node <code>N</code>, there are three methods:
 * <code>delN()</code>,  <code>delNImpl()</code> and <code>postDelN(JL)</code>. 
 * The method <code>delN()</code> calls <code>delNImpl()</code> to create
 * an appropriate delegate object, and if other <code>DelFactory</code>s are 
 * chained onto this one, it will also call <code>delN()</code> on the next 
 * <code>DelFactory</code>. The method <code>delN()</code> will then 
 * call <code>postDelN</code>, passing in the newly created extension object.
 * 
 * <p>
 * The default implementation of <code>delNImpl()</code> is to simply call
 * <code>delMImpl()</code>, where <code>M</code> is the immediate 
 * superclass of <code>N</code>. Similarly, the default implementation of
 * <code>postDelN(JL)</code> is to call <code>postDelM(JL)</code>.
 * 
 * @see polyglot.ext.jl.ast.AbstractExtFactory_c has a very similar structure. 
 */
public abstract class AbstractDelFactory_c implements DelFactory
{
    protected AbstractDelFactory_c() {
        this(null);
    }
    
    protected AbstractDelFactory_c(DelFactory nextDelFactory) {
        this.nextDelFactory = nextDelFactory;
    }
    
    /**
     * The next delFactory in the chain. Whenever an extension is instantiated,
     * the next delFactory should be called to see if it also has an extension,
     * and if so, the extensions should be joined together using the method
     * <code>composeDels</code>
     */
    private DelFactory nextDelFactory;

    public DelFactory nextDelFactory() {
        return nextDelFactory;
    }
    /**
     * Compose two delegates together. Order is important: e1 gets added
     * at the end of e2's chain.
     * @param e1 the <code>JL</code> object to add to the end of e2's 
     *             chain of delegates. 
     * @param e2 the second <code>JL</code> object that will have e1 added to 
     *             its chain of delegates.
     * @return the result of adding e1 to the end of e2's chain of delegates.
     */
    protected JL composeDels(JL e1, JL e2) {
        if (e1 == null) return e2;        
        if (e2 == null) return e1;        
        throw new InternalCompilerError("Composition of delegates unimplemented.");
        // add e1 as e2's last extension, by recursing...
        //return e2.ext(composeDels(e1, e2.ext()));
    }
    
    // ******************************************
    // Final methods that call the Impl methods to construct 
    // extensions, and then check with nextDelFactory to see if it
    // also has an extension. Finally, call an appropriate post method,
    // to allow subclasses to perform operations on the construction Exts
    // ******************************************
    public final JL delAmbAssign() {
        JL e = delAmbAssignImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAmbAssign();
            e = composeDels(e, e2);
        }
        return postDelAmbAssign(e);
    }

    public final JL delAmbExpr() {
        JL e = delAmbExprImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAmbExpr();
            e = composeDels(e, e2);
        }
        return postDelAmbExpr(e);
    }

    public final JL delAmbPrefix() {
        JL e = delAmbPrefixImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAmbPrefix();
            e = composeDels(e, e2);
        }
        return postDelAmbPrefix(e);
    }

    public final JL delAmbQualifierNode() {
        JL e = delAmbQualifierNodeImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAmbQualifierNode();
            e = composeDels(e, e2);
        }
        return postDelAmbQualifierNode(e);
    }

    public final JL delAmbReceiver() {
        JL e = delAmbReceiverImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAmbReceiver();
            e = composeDels(e, e2);
        }
        return postDelAmbReceiver(e);
    }

    public final JL delAmbTypeNode() {
        JL e = delAmbTypeNodeImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAmbTypeNode();
            e = composeDels(e, e2);
        }
        return postDelAmbTypeNode(e);
    }

    public final JL delArrayAccess() {
        JL e = delArrayAccessImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delArrayAccess();
            e = composeDels(e, e2);
        }
        return postDelArrayAccess(e);
    }

    public final JL delArrayInit() {
        JL e = delArrayInitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delArrayInit();
            e = composeDels(e, e2);
        }
        return postDelArrayInit(e);
    }

    public final JL delArrayTypeNode() {
        JL e = delArrayTypeNodeImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delArrayTypeNode();
            e = composeDels(e, e2);
        }
        return postDelArrayTypeNode(e);
    }

    public final JL delAssert() {
        JL e = delAssertImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAssert();
            e = composeDels(e, e2);
        }
        return postDelAssert(e);
    }

    public final JL delAssign() {
        JL e = delAssignImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delAssign();
            e = composeDels(e, e2);
        }
        return postDelAssign(e);
    }

    public final JL delLocalAssign() {
        JL e = delLocalAssignImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delLocalAssign();
            e = composeDels(e, e2);
        }
        return postDelLocalAssign(e);
    }

    public final JL delFieldAssign() {
        JL e = delFieldAssignImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delFieldAssign();
            e = composeDels(e, e2);
        }
        return postDelFieldAssign(e);
    }

    public final JL delArrayAccessAssign() {
        JL e = delArrayAccessAssignImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delArrayAccessAssign();
            e = composeDels(e, e2);
        }
        return postDelArrayAccessAssign(e);
    }

    public final JL delBinary() {
        JL e = delBinaryImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delBinary();
            e = composeDels(e, e2);
        }
        return postDelBinary(e);
    }

    public final JL delBlock() {
        JL e = delBlockImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delBlock();
            e = composeDels(e, e2);
        }
        return postDelBlock(e);
    }

    public final JL delBooleanLit() {
        JL e = delBooleanLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delBooleanLit();
            e = composeDels(e, e2);
        }
        return postDelBooleanLit(e);
    }

    public final JL delBranch() {
        JL e = delBranchImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delBranch();
            e = composeDels(e, e2);
        }
        return postDelBranch(e);
    }

    public final JL delCall() {
        JL e = delCallImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delCall();
            e = composeDels(e, e2);
        }
        return postDelCall(e);
    }

    public final JL delCanonicalTypeNode() {
        JL e = delCanonicalTypeNodeImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delCanonicalTypeNode();
            e = composeDels(e, e2);
        }
        return postDelCanonicalTypeNode(e);
    }

    public final JL delCase() {
        JL e = delCaseImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delCase();
            e = composeDels(e, e2);
        }
        return postDelCase(e);
    }

    public final JL delCast() {
        JL e = delCastImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delCast();
            e = composeDels(e, e2);
        }
        return postDelCast(e);
    }

    public final JL delCatch() {
        JL e = delCatchImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delCatch();
            e = composeDels(e, e2);
        }
        return postDelCatch(e);
    }

    public final JL delCharLit() {
        JL e = delCharLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delCharLit();
            e = composeDels(e, e2);
        }
        return postDelCharLit(e);
    }

    public final JL delClassBody() {
        JL e = delClassBodyImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delClassBody();
            e = composeDels(e, e2);
        }
        return postDelClassBody(e);
    }

    public final JL delClassDecl() {
        JL e = delClassDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delClassDecl();
            e = composeDels(e, e2);
        }
        return postDelClassDecl(e);
    }

    public final JL delClassLit() {
        JL e = delClassLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delClassLit();
            e = composeDels(e, e2);
        }
        return postDelClassLit(e);
    }

    public final JL delClassMember() {
        JL e = delClassMemberImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delClassMember();
            e = composeDels(e, e2);
        }
        return postDelClassMember(e);
    }

    public final JL delCodeDecl() {
        JL e = delCodeDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delCodeDecl();
            e = composeDels(e, e2);
        }
        return postDelCodeDecl(e);
    }

    public final JL delConditional() {
        JL e = delConditionalImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delConditional();
            e = composeDels(e, e2);
        }
        return postDelConditional(e);
    }

    public final JL delConstructorCall() {
        JL e = delConstructorCallImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delConstructorCall();
            e = composeDels(e, e2);
        }
        return postDelConstructorCall(e);
    }

    public final JL delConstructorDecl() {
        JL e = delConstructorDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delConstructorDecl();
            e = composeDels(e, e2);
        }
        return postDelConstructorDecl(e);
    }

    public final JL delDo() {
        JL e = delDoImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delDo();
            e = composeDels(e, e2);
        }
        return postDelDo(e);
    }

    public final JL delEmpty() {
        JL e = delEmptyImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delEmpty();
            e = composeDels(e, e2);
        }
        return postDelEmpty(e);
    }

    public final JL delEval() {
        JL e = delEvalImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delEval();
            e = composeDels(e, e2);
        }
        return postDelEval(e);
    }

    public final JL delExpr() {
        JL e = delExprImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delExpr();
            e = composeDels(e, e2);
        }
        return postDelExpr(e);
    }

    public final JL delField() {
        JL e = delFieldImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delField();
            e = composeDels(e, e2);
        }
        return postDelField(e);
    }

    public final JL delFieldDecl() {
        JL e = delFieldDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delFieldDecl();
            e = composeDels(e, e2);
        }
        return postDelFieldDecl(e);
    }

    public final JL delFloatLit() {
        JL e = delFloatLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delFloatLit();
            e = composeDels(e, e2);
        }
        return postDelFloatLit(e);
    }

    public final JL delFor() {
        JL e = delForImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delFor();
            e = composeDels(e, e2);
        }
        return postDelFor(e);
    }

    public final JL delFormal() {
        JL e = delFormalImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delFormal();
            e = composeDels(e, e2);
        }
        return postDelFormal(e);
    }

    public final JL delIf() {
        JL e = delIfImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delIf();
            e = composeDels(e, e2);
        }
        return postDelIf(e);
    }

    public final JL delImport() {
        JL e = delImportImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delImport();
            e = composeDels(e, e2);
        }
        return postDelImport(e);
    }

    public final JL delInitializer() {
        JL e = delInitializerImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delInitializer();
            e = composeDels(e, e2);
        }
        return postDelInitializer(e);
    }

    public final JL delInstanceof() {
        JL e = delInstanceofImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delInstanceof();
            e = composeDels(e, e2);
        }
        return postDelInstanceof(e);
    }

    public final JL delIntLit() {
        JL e = delIntLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delIntLit();
            e = composeDels(e, e2);
        }
        return postDelIntLit(e);
    }

    public final JL delLabeled() {
        JL e = delLabeledImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delLabeled();
            e = composeDels(e, e2);
        }
        return postDelLabeled(e);
    }

    public final JL delLit() {
        JL e = delLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delLit();
            e = composeDels(e, e2);
        }
        return postDelLit(e);
    }

    public final JL delLocal() {
        JL e = delLocalImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delLocal();
            e = composeDels(e, e2);
        }
        return postDelLocal(e);
    }

    public final JL delLocalClassDecl() {
        JL e = delLocalClassDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delLocalClassDecl();
            e = composeDels(e, e2);
        }
        return postDelLocalClassDecl(e);
    }

    public final JL delLocalDecl() {
        JL e = delLocalDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delLocalDecl();
            e = composeDels(e, e2);
        }
        return postDelLocalDecl(e);
    }

    public final JL delLoop() {
        JL e = delLoopImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delLoop();
            e = composeDels(e, e2);
        }
        return postDelLoop(e);
    }

    public final JL delMethodDecl() {
        JL e = delMethodDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delMethodDecl();
            e = composeDels(e, e2);
        }
        return postDelMethodDecl(e);
    }

    public final JL delNewArray() {
        JL e = delNewArrayImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delNewArray();
            e = composeDels(e, e2);
        }
        return postDelNewArray(e);
    }

    public final JL delNode() {
        JL e = delNodeImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delNode();
            e = composeDels(e, e2);
        }
        return postDelNode(e);
    }

    public final JL delNew() {
        JL e = delNewImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delNew();
            e = composeDels(e, e2);
        }
        return postDelNew(e);
    }

    public final JL delNullLit() {
        JL e = delNullLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delNullLit();
            e = composeDels(e, e2);
        }
        return postDelNullLit(e);
    }

    public final JL delNumLit() {
        JL e = delNumLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delNumLit();
            e = composeDels(e, e2);
        }
        return postDelNumLit(e);
    }

    public final JL delPackageNode() {
        JL e = delPackageNodeImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delPackageNode();
            e = composeDels(e, e2);
        }
        return postDelPackageNode(e);
    }

    public final JL delProcedureDecl() {
        JL e = delProcedureDeclImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delProcedureDecl();
            e = composeDels(e, e2);
        }
        return postDelProcedureDecl(e);
    }

    public final JL delReturn() {
        JL e = delReturnImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delReturn();
            e = composeDels(e, e2);
        }
        return postDelReturn(e);
    }

    public final JL delSourceCollection() {
        JL e = delSourceCollectionImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delSourceCollection();
            e = composeDels(e, e2);
        }
        return postDelSourceCollection(e);
    }

    public final JL delSourceFile() {
        JL e = delSourceFileImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delSourceFile();
            e = composeDels(e, e2);
        }
        return postDelSourceFile(e);
    }

    public final JL delSpecial() {
        JL e = delSpecialImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delSpecial();
            e = composeDels(e, e2);
        }
        return postDelSpecial(e);
    }

    public final JL delStmt() {
        JL e = delStmtImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delStmt();
            e = composeDels(e, e2);
        }
        return postDelStmt(e);
    }

    public final JL delStringLit() {
        JL e = delStringLitImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delStringLit();
            e = composeDels(e, e2);
        }
        return postDelStringLit(e);
    }

    public final JL delSwitchBlock() {
        JL e = delSwitchBlockImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delSwitchBlock();
            e = composeDels(e, e2);
        }
        return postDelSwitchBlock(e);
    }

    public final JL delSwitchElement() {
        JL e = delSwitchElementImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delSwitchElement();
            e = composeDels(e, e2);
        }
        return postDelSwitchElement(e);
    }

    public final JL delSwitch() {
        JL e = delSwitchImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delSwitch();
            e = composeDels(e, e2);
        }
        return postDelSwitch(e);
    }

    public final JL delSynchronized() {
        JL e = delSynchronizedImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delSynchronized();
            e = composeDels(e, e2);
        }
        return postDelSynchronized(e);
    }

    public final JL delTerm() {
        JL e = delTermImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delTerm();
            e = composeDels(e, e2);
        }
        return postDelTerm(e);
    }

    public final JL delThrow() {
        JL e = delThrowImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delThrow();
            e = composeDels(e, e2);
        }
        return postDelThrow(e);
    }

    public final JL delTry() {
        JL e = delTryImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delTry();
            e = composeDels(e, e2);
        }
        return postDelTry(e);
    }

    public final JL delTypeNode() {
        JL e = delTypeNodeImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delTypeNode();
            e = composeDels(e, e2);
        }
        return postDelTypeNode(e);
    }

    public final JL delUnary() {
        JL e = delUnaryImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delUnary();
            e = composeDels(e, e2);
        }
        return postDelUnary(e);
    }

    public final JL delWhile() {
        JL e = delWhileImpl();

        if (nextDelFactory != null) {
            JL e2 = nextDelFactory.delWhile();
            e = composeDels(e, e2);
        }
        return postDelWhile(e);
    }

    // ********************************************
    // Impl methods
    // ********************************************
    
    /**
     * Create the delegate for a <code>AmbAssign</code> AST node.
     * @return the delegate for a <code>AmbAssign</code> AST node.
     */
    protected JL delAmbAssignImpl() {
        return delAssignImpl();
    }

    /**
     * Create the delegate for a <code>AmbExpr</code> AST node.
     * @return the delegate for a <code>AmbExpr</code> AST node.
     */
    protected JL delAmbExprImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>AmbPrefix</code> AST node.
     * @return the delegate for a <code>AmbPrefix</code> AST node.
     */
    protected JL delAmbPrefixImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>AmbQualifierNode</code> AST node.
     * @return the delegate for a <code>AmbQualifierNode</code> AST node.
     */
    protected JL delAmbQualifierNodeImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>AmbReceiver</code> AST node.
     * @return the delegate for a <code>AmbReceiver</code> AST node.
     */
    protected JL delAmbReceiverImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>AmbTypeNode</code> AST node.
     * @return the delegate for a <code>AmbTypeNode</code> AST node.
     */
    protected JL delAmbTypeNodeImpl() {
        return delTypeNodeImpl();
    }

    /**
     * Create the delegate for a <code>ArrayAccess</code> AST node.
     * @return the delegate for a <code>ArrayAccess</code> AST node.
     */
    protected JL delArrayAccessImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>ArrayInit</code> AST node.
     * @return the delegate for a <code>ArrayInit</code> AST node.
     */
    protected JL delArrayInitImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>ArrayTypeNode</code> AST node.
     * @return the delegate for a <code>ArrayTypeNode</code> AST node.
     */
    protected JL delArrayTypeNodeImpl() {
        return delTypeNodeImpl();
    }

    /**
     * Create the delegate for a <code>Assert</code> AST node.
     * @return the delegate for a <code>Assert</code> AST node.
     */
    protected JL delAssertImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Assign</code> AST node.
     * @return the delegate for a <code>Assign</code> AST node.
     */
    protected JL delAssignImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>LocalAssign</code> AST node.
     * @return the delegate for a <code>LocalAssign</code> AST node.
     */
    protected JL delLocalAssignImpl() {
        return delAssignImpl();
    }

    /**
     * Create the delegate for a <code>FieldAssign</code> AST node.
     * @return the delegate for a <code>FieldAssign</code> AST node.
     */
    protected JL delFieldAssignImpl() {
        return delAssignImpl();
    }

    /**
     * Create the delegate for a <code>ArrayAccessAssign</code> AST node.
     * @return the delegate for a <code>ArrayAccessAssign</code> AST node.
     */
    protected JL delArrayAccessAssignImpl() {
        return delAssignImpl();
    }
    protected JL delBinaryImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>Block</code> AST node.
     * @return the delegate for a <code>Block</code> AST node.
     */
    protected JL delBlockImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>BooleanLit</code> AST node.
     * @return the delegate for a <code>BooleanLit</code> AST node.
     */
    protected JL delBooleanLitImpl() {
        return delLitImpl();
    }

    /**
     * Create the delegate for a <code>Branch</code> AST node.
     * @return the delegate for a <code>Branch</code> AST node.
     */
    protected JL delBranchImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Call</code> AST node.
     * @return the delegate for a <code>Call</code> AST node.
     */
    protected JL delCallImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>CanonicalTypeNode</code> AST node.
     * @return the delegate for a <code>CanonicalTypeNode</code> AST node.
     */
    protected JL delCanonicalTypeNodeImpl() {
        return delTypeNodeImpl();
    }

    /**
     * Create the delegate for a <code>Case</code> AST node.
     * @return the delegate for a <code>Case</code> AST node.
     */
    protected JL delCaseImpl() {
        return delSwitchElementImpl();
    }

    /**
     * Create the delegate for a <code>Cast</code> AST node.
     * @return the delegate for a <code>Cast</code> AST node.
     */
    protected JL delCastImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>Catch</code> AST node.
     * @return the delegate for a <code>Catch</code> AST node.
     */
    protected JL delCatchImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>CharLit</code> AST node.
     * @return the delegate for a <code>CharLit</code> AST node.
     */
    protected JL delCharLitImpl() {
        return delNumLitImpl();
    }

    /**
     * Create the delegate for a <code>ClassBody</code> AST node.
     * @return the delegate for a <code>ClassBody</code> AST node.
     */
    protected JL delClassBodyImpl() {
        return delTermImpl();
    }

    /**
     * Create the delegate for a <code>ClassDecl</code> AST node.
     * @return the delegate for a <code>ClassDecl</code> AST node.
     */
    protected JL delClassDeclImpl() {
        return delTermImpl();
    }

    /**
     * Create the delegate for a <code>ClassLit</code> AST node.
     * @return the delegate for a <code>ClassLit</code> AST node.
     */
    protected JL delClassLitImpl() {
        return delLitImpl();
    }

    /**
     * Create the delegate for a <code>ClassMember</code> AST node.
     * @return the delegate for a <code>ClassMember</code> AST node.
     */
    protected JL delClassMemberImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>CodeDecl</code> AST node.
     * @return the delegate for a <code>CodeDecl</code> AST node.
     */
    protected JL delCodeDeclImpl() {
        return delClassMemberImpl();
    }

    /**
     * Create the delegate for a <code>Conditional</code> AST node.
     * @return the delegate for a <code>Conditional</code> AST node.
     */
    protected JL delConditionalImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>ConstructorCall</code> AST node.
     * @return the delegate for a <code>ConstructorCall</code> AST node.
     */
    protected JL delConstructorCallImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>ConstructorDecl</code> AST node.
     * @return the delegate for a <code>ConstructorDecl</code> AST node.
     */
    protected JL delConstructorDeclImpl() {
        return delProcedureDeclImpl();
    }

    /**
     * Create the delegate for a <code>Do</code> AST node.
     * @return the delegate for a <code>Do</code> AST node.
     */
    protected JL delDoImpl() {
        return delLoopImpl();
    }

    /**
     * Create the delegate for a <code>Empty</code> AST node.
     * @return the delegate for a <code>Empty</code> AST node.
     */
    protected JL delEmptyImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Eval</code> AST node.
     * @return the delegate for a <code>Eval</code> AST node.
     */
    protected JL delEvalImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Expr</code> AST node.
     * @return the delegate for a <code>Expr</code> AST node.
     */
    protected JL delExprImpl() {
        return delTermImpl();
    }

    /**
     * Create the delegate for a <code>Field</code> AST node.
     * @return the delegate for a <code>Field</code> AST node.
     */
    protected JL delFieldImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>FieldDecl</code> AST node.
     * @return the delegate for a <code>FieldDecl</code> AST node.
     */
    protected JL delFieldDeclImpl() {
        return delClassMemberImpl();
    }

    /**
     * Create the delegate for a <code>FloatLit</code> AST node.
     * @return the delegate for a <code>FloatLit</code> AST node.
     */
    protected JL delFloatLitImpl() {
        return delLitImpl();
    }

    /**
     * Create the delegate for a <code>For</code> AST node.
     * @return the delegate for a <code>For</code> AST node.
     */
    protected JL delForImpl() {
        return delLoopImpl();
    }

    /**
     * Create the delegate for a <code>Formal</code> AST node.
     * @return the delegate for a <code>Formal</code> AST node.
     */
    protected JL delFormalImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>If</code> AST node.
     * @return the delegate for a <code>If</code> AST node.
     */
    protected JL delIfImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Import</code> AST node.
     * @return the delegate for a <code>Import</code> AST node.
     */
    protected JL delImportImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>Initializer</code> AST node.
     * @return the delegate for a <code>Initializer</code> AST node.
     */
    protected JL delInitializerImpl() {
        return delCodeDeclImpl();
    }

    /**
     * Create the delegate for a <code>Instanceof</code> AST node.
     * @return the delegate for a <code>Instanceof</code> AST node.
     */
    protected JL delInstanceofImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>IntLit</code> AST node.
     * @return the delegate for a <code>IntLit</code> AST node.
     */
    protected JL delIntLitImpl() {
        return delNumLitImpl();
    }

    /**
     * Create the delegate for a <code>Labeled</code> AST node.
     * @return the delegate for a <code>Labeled</code> AST node.
     */
    protected JL delLabeledImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Lit</code> AST node.
     * @return the delegate for a <code>Lit</code> AST node.
     */
    protected JL delLitImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>Local</code> AST node.
     * @return the delegate for a <code>Local</code> AST node.
     */
    protected JL delLocalImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>LocalClassDecl</code> AST node.
     * @return the delegate for a <code>LocalClassDecl</code> AST node.
     */
    protected JL delLocalClassDeclImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>LocalDecl</code> AST node.
     * @return the delegate for a <code>LocalDecl</code> AST node.
     */
    protected JL delLocalDeclImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>Loop</code> AST node.
     * @return the delegate for a <code>Loop</code> AST node.
     */
    protected JL delLoopImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>MethodDecl</code> AST node.
     * @return the delegate for a <code>MethodDecl</code> AST node.
     */
    protected JL delMethodDeclImpl() {
        return delProcedureDeclImpl();
    }

    /**
     * Create the delegate for a <code>NewArray</code> AST node.
     * @return the delegate for a <code>NewArray</code> AST node.
     */
    protected JL delNewArrayImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>Node</code> AST node.
     * @return the delegate for a <code>Node</code> AST node.
     */
    protected JL delNodeImpl() {
        return null;
    }

    /**
     * Create the delegate for a <code>New</code> AST node.
     * @return the delegate for a <code>New</code> AST node.
     */
    protected JL delNewImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>NullLit</code> AST node.
     * @return the delegate for a <code>NullLit</code> AST node.
     */
    protected JL delNullLitImpl() {
        return delLitImpl();
    }

    /**
     * Create the delegate for a <code>NumLit</code> AST node.
     * @return the delegate for a <code>NumLit</code> AST node.
     */
    protected JL delNumLitImpl() {
        return delLitImpl();
    }

    /**
     * Create the delegate for a <code>PackageNode</code> AST node.
     * @return the delegate for a <code>PackageNode</code> AST node.
     */
    protected JL delPackageNodeImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>ProcedureDecl</code> AST node.
     * @return the delegate for a <code>ProcedureDecl</code> AST node.
     */
    protected JL delProcedureDeclImpl() {
        return delCodeDeclImpl();
    }

    /**
     * Create the delegate for a <code>Return</code> AST node.
     * @return the delegate for a <code>Return</code> AST node.
     */
    protected JL delReturnImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>SourceCollection</code> AST node.
     * @return the delegate for a <code>SourceCollection</code> AST node.
     */
    protected JL delSourceCollectionImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>SourceFile</code> AST node.
     * @return the delegate for a <code>SourceFile</code> AST node.
     */
    protected JL delSourceFileImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>Special</code> AST node.
     * @return the delegate for a <code>Special</code> AST node.
     */
    protected JL delSpecialImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>Stmt</code> AST node.
     * @return the delegate for a <code>Stmt</code> AST node.
     */
    protected JL delStmtImpl() {
        return delTermImpl();
    }

    /**
     * Create the delegate for a <code>StringLit</code> AST node.
     * @return the delegate for a <code>StringLit</code> AST node.
     */
    protected JL delStringLitImpl() {
        return delLitImpl();
    }

    /**
     * Create the delegate for a <code>SwitchBlock</code> AST node.
     * @return the delegate for a <code>SwitchBlock</code> AST node.
     */
    protected JL delSwitchBlockImpl() {
        return delSwitchElementImpl();
    }

    /**
     * Create the delegate for a <code>SwitchElement</code> AST node.
     * @return the delegate for a <code>SwitchElement</code> AST node.
     */
    protected JL delSwitchElementImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Switch</code> AST node.
     * @return the delegate for a <code>Switch</code> AST node.
     */
    protected JL delSwitchImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Synchronized</code> AST node.
     * @return the delegate for a <code>Synchronized</code> AST node.
     */
    protected JL delSynchronizedImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Term</code> AST node.
     * @return the delegate for a <code>Term</code> AST node.
     */
    protected JL delTermImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>Throw</code> AST node.
     * @return the delegate for a <code>Throw</code> AST node.
     */
    protected JL delThrowImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>Try</code> AST node.
     * @return the delegate for a <code>Try</code> AST node.
     */
    protected JL delTryImpl() {
        return delStmtImpl();
    }

    /**
     * Create the delegate for a <code>TypeNode</code> AST node.
     * @return the delegate for a <code>TypeNode</code> AST node.
     */
    protected JL delTypeNodeImpl() {
        return delNodeImpl();
    }

    /**
     * Create the delegate for a <code>Unary</code> AST node.
     * @return the delegate for a <code>Unary</code> AST node.
     */
    protected JL delUnaryImpl() {
        return delExprImpl();
    }

    /**
     * Create the delegate for a <code>While</code> AST node.
     * @return the delegate for a <code>While</code> AST node.
     */
    protected JL delWhileImpl() {
        return delLoopImpl();
    }

    // ********************************************
    // Post methods
    // ********************************************

    protected JL postDelAmbAssign(JL del) {
        return postDelAssign(del);
    }

    protected JL postDelAmbExpr(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelAmbPrefix(JL del) {
        return postDelNode(del);
    }

    protected JL postDelAmbQualifierNode(JL del) {
        return postDelNode(del);
    }

    protected JL postDelAmbReceiver(JL del) {
        return postDelNode(del);
    }

    protected JL postDelAmbTypeNode(JL del) {
        return postDelTypeNode(del);
    }

    protected JL postDelArrayAccess(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelArrayInit(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelArrayTypeNode(JL del) {
        return postDelTypeNode(del);
    }

    protected JL postDelAssert(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelAssign(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelLocalAssign(JL del) {
        return postDelAssign(del);
    }

    protected JL postDelFieldAssign(JL del) {
        return postDelAssign(del);
    }

    protected JL postDelArrayAccessAssign(JL del) {
        return postDelAssign(del);
    }

    protected JL postDelBinary(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelBlock(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelBooleanLit(JL del) {
        return postDelLit(del);
    }

    protected JL postDelBranch(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelCall(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelCanonicalTypeNode(JL del) {
        return postDelTypeNode(del);
    }

    protected JL postDelCase(JL del) {
        return postDelSwitchElement(del);
    }

    protected JL postDelCast(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelCatch(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelCharLit(JL del) {
        return postDelNumLit(del);
    }

    protected JL postDelClassBody(JL del) {
        return postDelTerm(del);
    }

    protected JL postDelClassDecl(JL del) {
        return postDelTerm(del);
    }

    protected JL postDelClassLit(JL del) {
        return postDelLit(del);
    }

    protected JL postDelClassMember(JL del) {
        return postDelNode(del);
    }

    protected JL postDelCodeDecl(JL del) {
        return postDelClassMember(del);
    }

    protected JL postDelConditional(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelConstructorCall(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelConstructorDecl(JL del) {
        return postDelProcedureDecl(del);
    }

    protected JL postDelDo(JL del) {
        return postDelLoop(del);
    }

    protected JL postDelEmpty(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelEval(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelExpr(JL del) {
        return postDelTerm(del);
    }

    protected JL postDelField(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelFieldDecl(JL del) {
        return postDelClassMember(del);
    }

    protected JL postDelFloatLit(JL del) {
        return postDelLit(del);
    }

    protected JL postDelFor(JL del) {
        return postDelLoop(del);
    }

    protected JL postDelFormal(JL del) {
        return postDelNode(del);
    }

    protected JL postDelIf(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelImport(JL del) {
        return postDelNode(del);
    }

    protected JL postDelInitializer(JL del) {
        return postDelCodeDecl(del);
    }

    protected JL postDelInstanceof(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelIntLit(JL del) {
        return postDelNumLit(del);
    }

    protected JL postDelLabeled(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelLit(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelLocal(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelLocalClassDecl(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelLocalDecl(JL del) {
        return postDelNode(del);
    }

    protected JL postDelLoop(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelMethodDecl(JL del) {
        return postDelProcedureDecl(del);
    }

    protected JL postDelNewArray(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelNode(JL del) {
        return del;
    }

    protected JL postDelNew(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelNullLit(JL del) {
        return postDelLit(del);
    }

    protected JL postDelNumLit(JL del) {
        return postDelLit(del);
    }

    protected JL postDelPackageNode(JL del) {
        return postDelNode(del);
    }

    protected JL postDelProcedureDecl(JL del) {
        return postDelCodeDecl(del);
    }

    protected JL postDelReturn(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelSourceCollection(JL del) {
        return postDelNode(del);
    }

    protected JL postDelSourceFile(JL del) {
        return postDelNode(del);
    }

    protected JL postDelSpecial(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelStmt(JL del) {
        return postDelTerm(del);
    }

    protected JL postDelStringLit(JL del) {
        return postDelLit(del);
    }

    protected JL postDelSwitchBlock(JL del) {
        return postDelSwitchElement(del);
    }

    protected JL postDelSwitchElement(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelSwitch(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelSynchronized(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelTerm(JL del) {
        return postDelNode(del);
    }

    protected JL postDelThrow(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelTry(JL del) {
        return postDelStmt(del);
    }

    protected JL postDelTypeNode(JL del) {
        return postDelNode(del);
    }

    protected JL postDelUnary(JL del) {
        return postDelExpr(del);
    }

    protected JL postDelWhile(JL del) {
        return postDelLoop(del);
    }

}
