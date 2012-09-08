package polyglot.ast;

import polyglot.types.Flags;
import polyglot.types.Type;
import polyglot.types.Type;
import polyglot.types.Package;
import polyglot.util.Position;
import java.util.List;

/**
 * A <code>DelFactory</code> constructs delegates. It is only used by
 * a <code>NodeFactory</code>, during the creation of AST nodes.
 */
public interface DelFactory
{

    //////////////////////////////////////////////////////////////////
    // Factory Methods
    //////////////////////////////////////////////////////////////////
    
    JL delAmbAssign();

    JL delAmbExpr();
    
    JL delAmbPrefix();
    
    JL delAmbQualifierNode();
    
    JL delAmbReceiver();
    
    JL delAmbTypeNode();
    
    JL delArrayAccess();
    
    JL delArrayInit();
    
    JL delArrayTypeNode();
    
    JL delAssert();
    
    JL delAssign();

    JL delLocalAssign();
    JL delFieldAssign();
    JL delArrayAccessAssign();
    
    JL delBinary();
    
    JL delBlock();
    
    JL delBooleanLit();
    
    JL delBranch();
    
    JL delCall();
    
    JL delCanonicalTypeNode();
    
    JL delCase();
    
    JL delCast();
    
    JL delCatch();
    
    JL delCharLit();
    
    JL delClassBody();
    
    JL delClassDecl();

    JL delClassLit();
    
    JL delClassMember();

    JL delCodeDecl();

    JL delConditional();
    
    JL delConstructorCall();
    
    JL delConstructorDecl();
    
    JL delDo();
    
    JL delEmpty();
    
    JL delEval();
    
    JL delExpr();
    
    JL delField();
    
    JL delFieldDecl();
    
    JL delFloatLit();
    
    JL delFor();
    
    JL delFormal();
    
    JL delIf();
    
    JL delImport();
    
    JL delInitializer();
    
    JL delInstanceof();
    
    JL delIntLit();
    
    JL delLabeled();
    
    JL delLit();
    
    JL delLocal();
    
    JL delLocalClassDecl();
    
    JL delLocalDecl();
    
    JL delLoop();
    
    JL delMethodDecl();
    
    JL delNewArray();
    
    JL delNode();
    
    JL delNew();
    
    JL delNullLit();
    
    JL delNumLit();
    
    JL delPackageNode();
    
    JL delProcedureDecl();

    JL delReturn();
    
    JL delSourceCollection();
    
    JL delSourceFile();
    
    JL delSpecial();
    
    JL delStmt();
    
    JL delStringLit();
    
    JL delSwitchBlock();
    
    JL delSwitchElement();
    
    JL delSwitch();
    
    JL delSynchronized();
    
    JL delTerm();
    
    JL delThrow();
    
    JL delTry();
    
    JL delTypeNode();
    
    JL delUnary();
    
    JL delWhile();
}
