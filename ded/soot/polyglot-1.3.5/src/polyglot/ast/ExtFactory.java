package polyglot.ast;

import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.types.Type;
import polyglot.types.Package;
import polyglot.util.Position;
import java.util.List;

/**
 * An <code>ExtFactory</code> constructs extensions. It is only used by
 * a <code>NodeFactory</code>, during the creation of AST nodes. ExtFactories
 * may be chained together (see AbstractExtFactory_c) to allow extensions to be
 * composed.
 */
public interface ExtFactory
{

    /**
     * The next extFactory in the chain. 
     */
    ExtFactory nextExtFactory();

    //////////////////////////////////////////////////////////////////
    // Factory Methods
    //////////////////////////////////////////////////////////////////
    
    Ext extAmbAssign();

    Ext extAmbExpr();
    
    Ext extAmbPrefix();
    
    Ext extAmbQualifierNode();
    
    Ext extAmbReceiver();
    
    Ext extAmbTypeNode();
    
    Ext extArrayAccess();
    
    Ext extArrayInit();
    
    Ext extArrayTypeNode();
    
    Ext extAssert();
    
    Ext extAssign();

    Ext extLocalAssign();
    Ext extFieldAssign();
    Ext extArrayAccessAssign();
    
    Ext extBinary();
    
    Ext extBlock();
    
    Ext extBooleanLit();
    
    Ext extBranch();
    
    Ext extCall();
    
    Ext extCanonicalTypeNode();
    
    Ext extCase();
    
    Ext extCast();
    
    Ext extCatch();
    
    Ext extCharLit();
    
    Ext extClassBody();
    
    Ext extClassDecl();

    Ext extClassLit();

    Ext extClassMember();

    Ext extCodeDecl();
    
    Ext extConditional();
    
    Ext extConstructorCall();
    
    Ext extConstructorDecl();
    
    Ext extDo();
    
    Ext extEmpty();
    
    Ext extEval();
    
    Ext extExpr();
    
    Ext extField();
    
    Ext extFieldDecl();
    
    Ext extFloatLit();
    
    Ext extFor();
    
    Ext extFormal();
    
    Ext extIf();
    
    Ext extImport();
    
    Ext extInitializer();
    
    Ext extInstanceof();
    
    Ext extIntLit();
    
    Ext extLabeled();
    
    Ext extLit();
    
    Ext extLocal();
    
    Ext extLocalClassDecl();
    
    Ext extLocalDecl();
    
    Ext extLoop();
    
    Ext extMethodDecl();
    
    Ext extNewArray();
    
    Ext extNode();
    
    Ext extNew();
    
    Ext extNullLit();
    
    Ext extNumLit();
    
    Ext extPackageNode();
    
    Ext extProcedureDecl();

    Ext extReturn();
    
    Ext extSourceCollection();
    
    Ext extSourceFile();
    
    Ext extSpecial();
    
    Ext extStmt();
    
    Ext extStringLit();
    
    Ext extSwitchBlock();
    
    Ext extSwitchElement();
    
    Ext extSwitch();
    
    Ext extSynchronized();
    
    Ext extTerm();
    
    Ext extThrow();
    
    Ext extTry();
    
    Ext extTypeNode();
    
    Ext extUnary();
    
    Ext extWhile();
}
