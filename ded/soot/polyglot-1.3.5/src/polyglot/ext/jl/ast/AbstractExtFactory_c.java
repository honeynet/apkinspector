package polyglot.ext.jl.ast;

import polyglot.ast.Ext;
import polyglot.ast.ExtFactory;

/**
 * This abstract implementation of <code>ExtFactory</code> provides
 * a way of chaining together ExtFactories, and default implementations
 * of factory methods for each node.
 * 
 * <p>
 * For a given type of AST node <code>N</code>, there are three methods:
 * <code>extN()</code>,  <code>extNImpl()</code> and <code>postExtN(Ext)</code>. 
 * The method <code>extN()</code> calls <code>extNImpl()</code> to create
 * an appropriate extension object, and if other <code>ExtFactory</code>s are 
 * chained onto this one, it will also call <code>extN()</code> on the next 
 * <code>ExtFactory</code>. The method <code>extN()</code> will then 
 * call <code>postExtN</code>, passing in the newly created extension object.
 * 
 * <p>
 * The default implementation of <code>extNImpl()</code> is to simply call
 * <code>extMImpl()</code>, where <code>M</code> is the immediate 
 * superclass of <code>N</code>. Similarly, the default implementation of
 * <code>postExtN(Ext)</code> is to call <code>postExtM(Ext)</code>.
 * 
 * @see polyglot.ext.jl.ast.AbstractDelFactory_c has a very similar structure. 
 */
public abstract class AbstractExtFactory_c implements ExtFactory
{
    protected AbstractExtFactory_c() {
        this(null);
    }
    
    protected AbstractExtFactory_c(ExtFactory nextExtFactory) {
        this.nextExtFactory = nextExtFactory;
    }
    
    /**
     * The next extFactory in the chain. Whenever an extension is instantiated,
     * the next extFactory should be called to see if it also has an extension,
     * and if so, the extensions should be joined together using the method
     * <code>composeExts</code>
     */
    private ExtFactory nextExtFactory;

    public ExtFactory nextExtFactory() {
        return nextExtFactory;
    }
    /**
     * Compose two extensions together. Order is important: e1 gets added
     * at the end of e2's chain of extensions.
     * @param e1 the <code>Ext</code> object to add to the end of e2's 
     *             chain of extensions. 
     * @param e2 the second <code>Ext</code> object that will have e1 added to 
     *             its chain of extensions.
     * @return the result of adding e1 to the end of e2's chain of extensions.
     */
    protected Ext composeExts(Ext e1, Ext e2) {
        if (e1 == null) return e2;        
        if (e2 == null) return e1;        
        // add e1 as e2's last extension, by recursing...
        return e2.ext(composeExts(e1, e2.ext()));
    }
    
    // ******************************************
    // Final methods that call the Impl methods to construct 
    // extensions, and then check with nextExtFactory to see if it
    // also has an extension. Finally, call an appropriate post method,
    // to allow subclasses to perform operations on the construction Exts
    // ******************************************
    public final Ext extAmbAssign() {
        Ext e = extAmbAssignImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAmbAssign();
            e = composeExts(e, e2);
        }
        return postExtAmbAssign(e);
    }

    public final Ext extAmbExpr() {
        Ext e = extAmbExprImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAmbExpr();
            e = composeExts(e, e2);
        }
        return postExtAmbExpr(e);
    }

    public final Ext extAmbPrefix() {
        Ext e = extAmbPrefixImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAmbPrefix();
            e = composeExts(e, e2);
        }
        return postExtAmbPrefix(e);
    }

    public final Ext extAmbQualifierNode() {
        Ext e = extAmbQualifierNodeImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAmbQualifierNode();
            e = composeExts(e, e2);
        }
        return postExtAmbQualifierNode(e);
    }

    public final Ext extAmbReceiver() {
        Ext e = extAmbReceiverImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAmbReceiver();
            e = composeExts(e, e2);
        }
        return postExtAmbReceiver(e);
    }

    public final Ext extAmbTypeNode() {
        Ext e = extAmbTypeNodeImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAmbTypeNode();
            e = composeExts(e, e2);
        }
        return postExtAmbTypeNode(e);
    }

    public final Ext extArrayAccess() {
        Ext e = extArrayAccessImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extArrayAccess();
            e = composeExts(e, e2);
        }
        return postExtArrayAccess(e);
    }

    public final Ext extArrayInit() {
        Ext e = extArrayInitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extArrayInit();
            e = composeExts(e, e2);
        }
        return postExtArrayInit(e);
    }

    public final Ext extArrayTypeNode() {
        Ext e = extArrayTypeNodeImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extArrayTypeNode();
            e = composeExts(e, e2);
        }
        return postExtArrayTypeNode(e);
    }

    public final Ext extAssert() {
        Ext e = extAssertImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAssert();
            e = composeExts(e, e2);
        }
        return postExtAssert(e);
    }

    public final Ext extAssign() {
        Ext e = extAssignImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extAssign();
            e = composeExts(e, e2);
        }
        return postExtAssign(e);
    }

    public final Ext extLocalAssign() {
        Ext e = extLocalAssignImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extLocalAssign();
            e = composeExts(e, e2);
        }
        return postExtLocalAssign(e);
    }

    public final Ext extFieldAssign() {
        Ext e = extFieldAssignImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extFieldAssign();
            e = composeExts(e, e2);
        }
        return postExtFieldAssign(e);
    }

    public final Ext extArrayAccessAssign() {
        Ext e = extArrayAccessAssignImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extArrayAccessAssign();
            e = composeExts(e, e2);
        }
        return postExtArrayAccessAssign(e);
    }

    public final Ext extBinary() {
        Ext e = extBinaryImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extBinary();
            e = composeExts(e, e2);
        }
        return postExtBinary(e);
    }

    public final Ext extBlock() {
        Ext e = extBlockImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extBlock();
            e = composeExts(e, e2);
        }
        return postExtBlock(e);
    }

    public final Ext extBooleanLit() {
        Ext e = extBooleanLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extBooleanLit();
            e = composeExts(e, e2);
        }
        return postExtBooleanLit(e);
    }

    public final Ext extBranch() {
        Ext e = extBranchImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extBranch();
            e = composeExts(e, e2);
        }
        return postExtBranch(e);
    }

    public final Ext extCall() {
        Ext e = extCallImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extCall();
            e = composeExts(e, e2);
        }
        return postExtCall(e);
    }

    public final Ext extCanonicalTypeNode() {
        Ext e = extCanonicalTypeNodeImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extCanonicalTypeNode();
            e = composeExts(e, e2);
        }
        return postExtCanonicalTypeNode(e);
    }

    public final Ext extCase() {
        Ext e = extCaseImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extCase();
            e = composeExts(e, e2);
        }
        return postExtCase(e);
    }

    public final Ext extCast() {
        Ext e = extCastImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extCast();
            e = composeExts(e, e2);
        }
        return postExtCast(e);
    }

    public final Ext extCatch() {
        Ext e = extCatchImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extCatch();
            e = composeExts(e, e2);
        }
        return postExtCatch(e);
    }

    public final Ext extCharLit() {
        Ext e = extCharLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extCharLit();
            e = composeExts(e, e2);
        }
        return postExtCharLit(e);
    }

    public final Ext extClassBody() {
        Ext e = extClassBodyImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extClassBody();
            e = composeExts(e, e2);
        }
        return postExtClassBody(e);
    }

    public final Ext extClassDecl() {
        Ext e = extClassDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extClassDecl();
            e = composeExts(e, e2);
        }
        return postExtClassDecl(e);
    }

    public final Ext extClassLit() {
        Ext e = extClassLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extClassLit();
            e = composeExts(e, e2);
        }
        return postExtClassLit(e);
    }

    public final Ext extClassMember() {
        Ext e = extClassMemberImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extClassMember();
            e = composeExts(e, e2);
        }
        return postExtClassMember(e);
    }

    public final Ext extCodeDecl() {
        Ext e = extCodeDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extCodeDecl();
            e = composeExts(e, e2);
        }
        return postExtCodeDecl(e);
    }

    public final Ext extConditional() {
        Ext e = extConditionalImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extConditional();
            e = composeExts(e, e2);
        }
        return postExtConditional(e);
    }

    public final Ext extConstructorCall() {
        Ext e = extConstructorCallImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extConstructorCall();
            e = composeExts(e, e2);
        }
        return postExtConstructorCall(e);
    }

    public final Ext extConstructorDecl() {
        Ext e = extConstructorDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extConstructorDecl();
            e = composeExts(e, e2);
        }
        return postExtConstructorDecl(e);
    }

    public final Ext extDo() {
        Ext e = extDoImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extDo();
            e = composeExts(e, e2);
        }
        return postExtDo(e);
    }

    public final Ext extEmpty() {
        Ext e = extEmptyImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extEmpty();
            e = composeExts(e, e2);
        }
        return postExtEmpty(e);
    }

    public final Ext extEval() {
        Ext e = extEvalImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extEval();
            e = composeExts(e, e2);
        }
        return postExtEval(e);
    }

    public final Ext extExpr() {
        Ext e = extExprImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extExpr();
            e = composeExts(e, e2);
        }
        return postExtExpr(e);
    }

    public final Ext extField() {
        Ext e = extFieldImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extField();
            e = composeExts(e, e2);
        }
        return postExtField(e);
    }

    public final Ext extFieldDecl() {
        Ext e = extFieldDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extFieldDecl();
            e = composeExts(e, e2);
        }
        return postExtFieldDecl(e);
    }

    public final Ext extFloatLit() {
        Ext e = extFloatLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extFloatLit();
            e = composeExts(e, e2);
        }
        return postExtFloatLit(e);
    }

    public final Ext extFor() {
        Ext e = extForImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extFor();
            e = composeExts(e, e2);
        }
        return postExtFor(e);
    }

    public final Ext extFormal() {
        Ext e = extFormalImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extFormal();
            e = composeExts(e, e2);
        }
        return postExtFormal(e);
    }

    public final Ext extIf() {
        Ext e = extIfImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extIf();
            e = composeExts(e, e2);
        }
        return postExtIf(e);
    }

    public final Ext extImport() {
        Ext e = extImportImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extImport();
            e = composeExts(e, e2);
        }
        return postExtImport(e);
    }

    public final Ext extInitializer() {
        Ext e = extInitializerImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extInitializer();
            e = composeExts(e, e2);
        }
        return postExtInitializer(e);
    }

    public final Ext extInstanceof() {
        Ext e = extInstanceofImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extInstanceof();
            e = composeExts(e, e2);
        }
        return postExtInstanceof(e);
    }

    public final Ext extIntLit() {
        Ext e = extIntLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extIntLit();
            e = composeExts(e, e2);
        }
        return postExtIntLit(e);
    }

    public final Ext extLabeled() {
        Ext e = extLabeledImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extLabeled();
            e = composeExts(e, e2);
        }
        return postExtLabeled(e);
    }

    public final Ext extLit() {
        Ext e = extLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extLit();
            e = composeExts(e, e2);
        }
        return postExtLit(e);
    }

    public final Ext extLocal() {
        Ext e = extLocalImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extLocal();
            e = composeExts(e, e2);
        }
        return postExtLocal(e);
    }

    public final Ext extLocalClassDecl() {
        Ext e = extLocalClassDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extLocalClassDecl();
            e = composeExts(e, e2);
        }
        return postExtLocalClassDecl(e);
    }

    public final Ext extLocalDecl() {
        Ext e = extLocalDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extLocalDecl();
            e = composeExts(e, e2);
        }
        return postExtLocalDecl(e);
    }

    public final Ext extLoop() {
        Ext e = extLoopImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extLoop();
            e = composeExts(e, e2);
        }
        return postExtLoop(e);
    }

    public final Ext extMethodDecl() {
        Ext e = extMethodDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extMethodDecl();
            e = composeExts(e, e2);
        }
        return postExtMethodDecl(e);
    }

    public final Ext extNewArray() {
        Ext e = extNewArrayImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extNewArray();
            e = composeExts(e, e2);
        }
        return postExtNewArray(e);
    }

    public final Ext extNode() {
        Ext e = extNodeImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extNode();
            e = composeExts(e, e2);
        }
        return postExtNode(e);
    }

    public final Ext extNew() {
        Ext e = extNewImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extNew();
            e = composeExts(e, e2);
        }
        return postExtNew(e);
    }

    public final Ext extNullLit() {
        Ext e = extNullLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extNullLit();
            e = composeExts(e, e2);
        }
        return postExtNullLit(e);
    }

    public final Ext extNumLit() {
        Ext e = extNumLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extNumLit();
            e = composeExts(e, e2);
        }
        return postExtNumLit(e);
    }

    public final Ext extPackageNode() {
        Ext e = extPackageNodeImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extPackageNode();
            e = composeExts(e, e2);
        }
        return postExtPackageNode(e);
    }

    public final Ext extProcedureDecl() {
        Ext e = extProcedureDeclImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extProcedureDecl();
            e = composeExts(e, e2);
        }
        return postExtProcedureDecl(e);
    }

    public final Ext extReturn() {
        Ext e = extReturnImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extReturn();
            e = composeExts(e, e2);
        }
        return postExtReturn(e);
    }

    public final Ext extSourceCollection() {
        Ext e = extSourceCollectionImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extSourceCollection();
            e = composeExts(e, e2);
        }
        return postExtSourceCollection(e);
    }

    public final Ext extSourceFile() {
        Ext e = extSourceFileImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extSourceFile();
            e = composeExts(e, e2);
        }
        return postExtSourceFile(e);
    }

    public final Ext extSpecial() {
        Ext e = extSpecialImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extSpecial();
            e = composeExts(e, e2);
        }
        return postExtSpecial(e);
    }

    public final Ext extStmt() {
        Ext e = extStmtImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extStmt();
            e = composeExts(e, e2);
        }
        return postExtStmt(e);
    }

    public final Ext extStringLit() {
        Ext e = extStringLitImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extStringLit();
            e = composeExts(e, e2);
        }
        return postExtStringLit(e);
    }

    public final Ext extSwitchBlock() {
        Ext e = extSwitchBlockImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extSwitchBlock();
            e = composeExts(e, e2);
        }
        return postExtSwitchBlock(e);
    }

    public final Ext extSwitchElement() {
        Ext e = extSwitchElementImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extSwitchElement();
            e = composeExts(e, e2);
        }
        return postExtSwitchElement(e);
    }

    public final Ext extSwitch() {
        Ext e = extSwitchImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extSwitch();
            e = composeExts(e, e2);
        }
        return postExtSwitch(e);
    }

    public final Ext extSynchronized() {
        Ext e = extSynchronizedImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extSynchronized();
            e = composeExts(e, e2);
        }
        return postExtSynchronized(e);
    }

    public final Ext extTerm() {
        Ext e = extTermImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extTerm();
            e = composeExts(e, e2);
        }
        return postExtTerm(e);
    }

    public final Ext extThrow() {
        Ext e = extThrowImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extThrow();
            e = composeExts(e, e2);
        }
        return postExtThrow(e);
    }

    public final Ext extTry() {
        Ext e = extTryImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extTry();
            e = composeExts(e, e2);
        }
        return postExtTry(e);
    }

    public final Ext extTypeNode() {
        Ext e = extTypeNodeImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extTypeNode();
            e = composeExts(e, e2);
        }
        return postExtTypeNode(e);
    }

    public final Ext extUnary() {
        Ext e = extUnaryImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extUnary();
            e = composeExts(e, e2);
        }
        return postExtUnary(e);
    }

    public final Ext extWhile() {
        Ext e = extWhileImpl();

        if (nextExtFactory != null) {
            Ext e2 = nextExtFactory.extWhile();
            e = composeExts(e, e2);
        }
        return postExtWhile(e);
    }

    // ********************************************
    // Impl methods
    // ********************************************
    
    /**
     * Create the <code>Ext</code> object for a <code>AmbAssign</code> AST node.
     * @return the <code>Ext</code> object for a <code>AmbAssign</code> AST node.
     */
    protected Ext extAmbAssignImpl() {
        return extAssignImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>AmbExpr</code> AST node.
     * @return the <code>Ext</code> object for a <code>AmbExpr</code> AST node.
     */
    protected Ext extAmbExprImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>AmbPrefix</code> AST node.
     * @return the <code>Ext</code> object for a <code>AmbPrefix</code> AST node.
     */
    protected Ext extAmbPrefixImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>AmbQualifierNode</code> AST node.
     * @return the <code>Ext</code> object for a <code>AmbQualifierNode</code> AST node.
     */
    protected Ext extAmbQualifierNodeImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>AmbReceiver</code> AST node.
     * @return the <code>Ext</code> object for a <code>AmbReceiver</code> AST node.
     */
    protected Ext extAmbReceiverImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>AmbTypeNode</code> AST node.
     * @return the <code>Ext</code> object for a <code>AmbTypeNode</code> AST node.
     */
    protected Ext extAmbTypeNodeImpl() {
        return extTypeNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ArrayAccess</code> AST node.
     * @return the <code>Ext</code> object for a <code>ArrayAccess</code> AST node.
     */
    protected Ext extArrayAccessImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ArrayInit</code> AST node.
     * @return the <code>Ext</code> object for a <code>ArrayInit</code> AST node.
     */
    protected Ext extArrayInitImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ArrayTypeNode</code> AST node.
     * @return the <code>Ext</code> object for a <code>ArrayTypeNode</code> AST node.
     */
    protected Ext extArrayTypeNodeImpl() {
        return extTypeNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Assert</code> AST node.
     * @return the <code>Ext</code> object for a <code>Assert</code> AST node.
     */
    protected Ext extAssertImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Assign</code> AST node.
     * @return the <code>Ext</code> object for a <code>Assign</code> AST node.
     */
    protected Ext extAssignImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>LocalAssign</code> AST node.
     * @return the <code>Ext</code> object for a <code>LocalAssign</code> AST node.
     */
    protected Ext extLocalAssignImpl() {
        return extAssignImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>FieldAssign</code> AST node.
     * @return the <code>Ext</code> object for a <code>FieldAssign</code> AST node.
     */
    protected Ext extFieldAssignImpl() {
        return extAssignImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ArrayAccessAssign</code> AST node.
     * @return the <code>Ext</code> object for a <code>ArrayAccessAssign</code> AST node.
     */
    protected Ext extArrayAccessAssignImpl() {
        return extAssignImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Binary</code> AST node.
     * @return the <code>Ext</code> object for a <code>Binary</code> AST node.
     */
    protected Ext extBinaryImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Block</code> AST node.
     * @return the <code>Ext</code> object for a <code>Block</code> AST node.
     */
    protected Ext extBlockImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>BooleanLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>BooleanLit</code> AST node.
     */
    protected Ext extBooleanLitImpl() {
        return extLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Branch</code> AST node.
     * @return the <code>Ext</code> object for a <code>Branch</code> AST node.
     */
    protected Ext extBranchImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Call</code> AST node.
     * @return the <code>Ext</code> object for a <code>Call</code> AST node.
     */
    protected Ext extCallImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>CanonicalTypeNode</code> AST node.
     * @return the <code>Ext</code> object for a <code>CanonicalTypeNode</code> AST node.
     */
    protected Ext extCanonicalTypeNodeImpl() {
        return extTypeNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Case</code> AST node.
     * @return the <code>Ext</code> object for a <code>Case</code> AST node.
     */
    protected Ext extCaseImpl() {
        return extSwitchElementImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Cast</code> AST node.
     * @return the <code>Ext</code> object for a <code>Cast</code> AST node.
     */
    protected Ext extCastImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Catch</code> AST node.
     * @return the <code>Ext</code> object for a <code>Catch</code> AST node.
     */
    protected Ext extCatchImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>CharLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>CharLit</code> AST node.
     */
    protected Ext extCharLitImpl() {
        return extNumLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ClassBody</code> AST node.
     * @return the <code>Ext</code> object for a <code>ClassBody</code> AST node.
     */
    protected Ext extClassBodyImpl() {
        return extTermImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ClassDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>ClassDecl</code> AST node.
     */
    protected Ext extClassDeclImpl() {
        return extTermImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ClassLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>ClassLit</code> AST node.
     */
    protected Ext extClassLitImpl() {
        return extLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ClassMember</code> AST node.
     * @return the <code>Ext</code> object for a <code>ClassMember</code> AST node.
     */
    protected Ext extClassMemberImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>CodeDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>CodeDecl</code> AST node.
     */
    protected Ext extCodeDeclImpl() {
        return extClassMemberImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Conditional</code> AST node.
     * @return the <code>Ext</code> object for a <code>Conditional</code> AST node.
     */
    protected Ext extConditionalImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ConstructorCall</code> AST node.
     * @return the <code>Ext</code> object for a <code>ConstructorCall</code> AST node.
     */
    protected Ext extConstructorCallImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ConstructorDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>ConstructorDecl</code> AST node.
     */
    protected Ext extConstructorDeclImpl() {
        return extProcedureDeclImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Do</code> AST node.
     * @return the <code>Ext</code> object for a <code>Do</code> AST node.
     */
    protected Ext extDoImpl() {
        return extLoopImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Empty</code> AST node.
     * @return the <code>Ext</code> object for a <code>Empty</code> AST node.
     */
    protected Ext extEmptyImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Eval</code> AST node.
     * @return the <code>Ext</code> object for a <code>Eval</code> AST node.
     */
    protected Ext extEvalImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Expr</code> AST node.
     * @return the <code>Ext</code> object for a <code>Expr</code> AST node.
     */
    protected Ext extExprImpl() {
        return extTermImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Field</code> AST node.
     * @return the <code>Ext</code> object for a <code>Field</code> AST node.
     */
    protected Ext extFieldImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>FieldDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>FieldDecl</code> AST node.
     */
    protected Ext extFieldDeclImpl() {
        return extClassMemberImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>FloatLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>FloatLit</code> AST node.
     */
    protected Ext extFloatLitImpl() {
        return extLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>For</code> AST node.
     * @return the <code>Ext</code> object for a <code>For</code> AST node.
     */
    protected Ext extForImpl() {
        return extLoopImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Formal</code> AST node.
     * @return the <code>Ext</code> object for a <code>Formal</code> AST node.
     */
    protected Ext extFormalImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>If</code> AST node.
     * @return the <code>Ext</code> object for a <code>If</code> AST node.
     */
    protected Ext extIfImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Import</code> AST node.
     * @return the <code>Ext</code> object for a <code>Import</code> AST node.
     */
    protected Ext extImportImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Initializer</code> AST node.
     * @return the <code>Ext</code> object for a <code>Initializer</code> AST node.
     */
    protected Ext extInitializerImpl() {
        return extCodeDeclImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Instanceof</code> AST node.
     * @return the <code>Ext</code> object for a <code>Instanceof</code> AST node.
     */
    protected Ext extInstanceofImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>IntLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>IntLit</code> AST node.
     */
    protected Ext extIntLitImpl() {
        return extNumLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Labeled</code> AST node.
     * @return the <code>Ext</code> object for a <code>Labeled</code> AST node.
     */
    protected Ext extLabeledImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Lit</code> AST node.
     * @return the <code>Ext</code> object for a <code>Lit</code> AST node.
     */
    protected Ext extLitImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Local</code> AST node.
     * @return the <code>Ext</code> object for a <code>Local</code> AST node.
     */
    protected Ext extLocalImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>LocalClassDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>LocalClassDecl</code> AST node.
     */
    protected Ext extLocalClassDeclImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>LocalDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>LocalDecl</code> AST node.
     */
    protected Ext extLocalDeclImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Loop</code> AST node.
     * @return the <code>Ext</code> object for a <code>Loop</code> AST node.
     */
    protected Ext extLoopImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>MethodDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>MethodDecl</code> AST node.
     */
    protected Ext extMethodDeclImpl() {
        return extProcedureDeclImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>NewArray</code> AST node.
     * @return the <code>Ext</code> object for a <code>NewArray</code> AST node.
     */
    protected Ext extNewArrayImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Node</code> AST node.
     * @return the <code>Ext</code> object for a <code>Node</code> AST node.
     */
    protected Ext extNodeImpl() {
        return null;
    }

    /**
     * Create the <code>Ext</code> object for a <code>New</code> AST node.
     * @return the <code>Ext</code> object for a <code>New</code> AST node.
     */
    protected Ext extNewImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>NullLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>NullLit</code> AST node.
     */
    protected Ext extNullLitImpl() {
        return extLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>NumLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>NumLit</code> AST node.
     */
    protected Ext extNumLitImpl() {
        return extLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>PackageNode</code> AST node.
     * @return the <code>Ext</code> object for a <code>PackageNode</code> AST node.
     */
    protected Ext extPackageNodeImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>ProcedureDecl</code> AST node.
     * @return the <code>Ext</code> object for a <code>ProcedureDecl</code> AST node.
     */
    protected Ext extProcedureDeclImpl() {
        return extCodeDeclImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Return</code> AST node.
     * @return the <code>Ext</code> object for a <code>Return</code> AST node.
     */
    protected Ext extReturnImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>SourceCollection</code> AST node.
     * @return the <code>Ext</code> object for a <code>SourceCollection</code> AST node.
     */
    protected Ext extSourceCollectionImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>SourceFile</code> AST node.
     * @return the <code>Ext</code> object for a <code>SourceFile</code> AST node.
     */
    protected Ext extSourceFileImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Special</code> AST node.
     * @return the <code>Ext</code> object for a <code>Special</code> AST node.
     */
    protected Ext extSpecialImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Stmt</code> AST node.
     * @return the <code>Ext</code> object for a <code>Stmt</code> AST node.
     */
    protected Ext extStmtImpl() {
        return extTermImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>StringLit</code> AST node.
     * @return the <code>Ext</code> object for a <code>StringLit</code> AST node.
     */
    protected Ext extStringLitImpl() {
        return extLitImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>SwitchBlock</code> AST node.
     * @return the <code>Ext</code> object for a <code>SwitchBlock</code> AST node.
     */
    protected Ext extSwitchBlockImpl() {
        return extSwitchElementImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>SwitchElement</code> AST node.
     * @return the <code>Ext</code> object for a <code>SwitchElement</code> AST node.
     */
    protected Ext extSwitchElementImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Switch</code> AST node.
     * @return the <code>Ext</code> object for a <code>Switch</code> AST node.
     */
    protected Ext extSwitchImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Synchronized</code> AST node.
     * @return the <code>Ext</code> object for a <code>Synchronized</code> AST node.
     */
    protected Ext extSynchronizedImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Term</code> AST node.
     * @return the <code>Ext</code> object for a <code>Term</code> AST node.
     */
    protected Ext extTermImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Throw</code> AST node.
     * @return the <code>Ext</code> object for a <code>Throw</code> AST node.
     */
    protected Ext extThrowImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Try</code> AST node.
     * @return the <code>Ext</code> object for a <code>Try</code> AST node.
     */
    protected Ext extTryImpl() {
        return extStmtImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>TypeNode</code> AST node.
     * @return the <code>Ext</code> object for a <code>TypeNode</code> AST node.
     */
    protected Ext extTypeNodeImpl() {
        return extNodeImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>Unary</code> AST node.
     * @return the <code>Ext</code> object for a <code>Unary</code> AST node.
     */
    protected Ext extUnaryImpl() {
        return extExprImpl();
    }

    /**
     * Create the <code>Ext</code> object for a <code>While</code> AST node.
     * @return the <code>Ext</code> object for a <code>While</code> AST node.
     */
    protected Ext extWhileImpl() {
        return extLoopImpl();
    }



    // ********************************************
    // Post methods
    // ********************************************

    protected Ext postExtAmbAssign(Ext ext) {
        return postExtAssign(ext);
    }

    protected Ext postExtAmbExpr(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtAmbPrefix(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtAmbQualifierNode(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtAmbReceiver(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtAmbTypeNode(Ext ext) {
        return postExtTypeNode(ext);
    }

    protected Ext postExtArrayAccess(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtArrayInit(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtArrayTypeNode(Ext ext) {
        return postExtTypeNode(ext);
    }

    protected Ext postExtAssert(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtAssign(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtLocalAssign(Ext ext) {
        return postExtAssign(ext);
    }

    protected Ext postExtFieldAssign(Ext ext) {
        return postExtAssign(ext);
    }

    protected Ext postExtArrayAccessAssign(Ext ext) {
        return postExtAssign(ext);
    }

    protected Ext postExtBinary(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtBlock(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtBooleanLit(Ext ext) {
        return postExtLit(ext);
    }

    protected Ext postExtBranch(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtCall(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtCanonicalTypeNode(Ext ext) {
        return postExtTypeNode(ext);
    }

    protected Ext postExtCase(Ext ext) {
        return postExtSwitchElement(ext);
    }

    protected Ext postExtCast(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtCatch(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtCharLit(Ext ext) {
        return postExtNumLit(ext);
    }

    protected Ext postExtClassBody(Ext ext) {
        return postExtTerm(ext);
    }

    protected Ext postExtClassDecl(Ext ext) {
        return postExtTerm(ext);
    }

    protected Ext postExtClassLit(Ext ext) {
        return postExtLit(ext);
    }

    protected Ext postExtClassMember(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtCodeDecl(Ext ext) {
        return postExtClassMember(ext);
    }

    protected Ext postExtConditional(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtConstructorCall(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtConstructorDecl(Ext ext) {
        return postExtProcedureDecl(ext);
    }

    protected Ext postExtDo(Ext ext) {
        return postExtLoop(ext);
    }

    protected Ext postExtEmpty(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtEval(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtExpr(Ext ext) {
        return postExtTerm(ext);
    }

    protected Ext postExtField(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtFieldDecl(Ext ext) {
        return postExtClassMember(ext);
    }

    protected Ext postExtFloatLit(Ext ext) {
        return postExtLit(ext);
    }

    protected Ext postExtFor(Ext ext) {
        return postExtLoop(ext);
    }

    protected Ext postExtFormal(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtIf(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtImport(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtInitializer(Ext ext) {
        return postExtCodeDecl(ext);
    }

    protected Ext postExtInstanceof(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtIntLit(Ext ext) {
        return postExtNumLit(ext);
    }

    protected Ext postExtLabeled(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtLit(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtLocal(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtLocalClassDecl(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtLocalDecl(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtLoop(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtMethodDecl(Ext ext) {
        return postExtProcedureDecl(ext);
    }

    protected Ext postExtNewArray(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtNode(Ext ext) {
        return ext;
    }

    protected Ext postExtNew(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtNullLit(Ext ext) {
        return postExtLit(ext);
    }

    protected Ext postExtNumLit(Ext ext) {
        return postExtLit(ext);
    }

    protected Ext postExtPackageNode(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtProcedureDecl(Ext ext) {
        return postExtCodeDecl(ext);
    }

    protected Ext postExtReturn(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtSourceCollection(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtSourceFile(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtSpecial(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtStmt(Ext ext) {
        return postExtTerm(ext);
    }

    protected Ext postExtStringLit(Ext ext) {
        return postExtLit(ext);
    }

    protected Ext postExtSwitchBlock(Ext ext) {
        return postExtSwitchElement(ext);
    }

    protected Ext postExtSwitchElement(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtSwitch(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtSynchronized(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtTerm(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtThrow(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtTry(Ext ext) {
        return postExtStmt(ext);
    }

    protected Ext postExtTypeNode(Ext ext) {
        return postExtNode(ext);
    }

    protected Ext postExtUnary(Ext ext) {
        return postExtExpr(ext);
    }

    protected Ext postExtWhile(Ext ext) {
        return postExtLoop(ext);
    }

}
