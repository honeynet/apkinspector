package scm;

import jas.*;
class AutoInit
{
  static void fillit(Env e)
  {
e.definevar(Symbol.intern("make-ascii-cpe"), new scmAsciiCP());
e.definevar(Symbol.intern("make-class-cpe"), new scmClassCP());
e.definevar(Symbol.intern("make-name-type-cpe"), new scmNameTypeCP());
e.definevar(Symbol.intern("make-field-cpe"), new scmFieldCP());
e.definevar(Symbol.intern("make-interface-cpe"), new scmInterfaceCP());
e.definevar(Symbol.intern("make-method-cpe"), new scmMethodCP());
e.definevar(Symbol.intern("make-integer-cpe"), new scmIntegerCP());
e.definevar(Symbol.intern("make-float-cpe"), new scmFloatCP());
e.definevar(Symbol.intern("make-long-cpe"), new scmLongCP());
e.definevar(Symbol.intern("make-double-cpe"), new scmDoubleCP());
e.definevar(Symbol.intern("make-string-cpe"), new scmStringCP());
e.definevar(Symbol.intern("make-field"), new scmVar());
e.definevar(Symbol.intern("make-const"), new scmConstAttr());
e.definevar(Symbol.intern("make-outputstream"), new scmscmOutputStream());
e.definevar(Symbol.intern("make-label"), new scmLabel());
e.definevar(Symbol.intern("make-class-env"), new scmClassEnv());
e.definevar(Symbol.intern("make-code"), new scmCodeAttr());
e.definevar(Symbol.intern("make-exception"), new scmExceptAttr());
e.definevar(Symbol.intern("make-catchtable"), new scmCatchtable());
e.definevar(Symbol.intern("make-catch-entry"), new scmCatchEntry());
e.definevar(Symbol.intern("iinc"), new scmIincInsn());
e.definevar(Symbol.intern("multianewarray"), new scmMultiarrayInsn());
e.definevar(Symbol.intern("invokeinterface"), new scmInvokeinterfaceInsn());
e.definevar(Symbol.intern("jas-class-addcpe"), new scmaddCPItem());
e.definevar(Symbol.intern("jas-class-addfield"), new scmaddField());
e.definevar(Symbol.intern("jas-class-addinterface"), new scmaddInterface());
e.definevar(Symbol.intern("jas-class-setclass"), new scmsetClass());
e.definevar(Symbol.intern("jas-class-setsuperclass"), new scmsetSuperClass());
e.definevar(Symbol.intern("jas-class-addmethod"), new scmaddMethod());
e.definevar(Symbol.intern("jas-class-setaccess"), new scmsetClassAccess());
e.definevar(Symbol.intern("jas-class-setsource"), new scmsetSource());
e.definevar(Symbol.intern("jas-class-write"), new scmwrite());
e.definevar(Symbol.intern("jas-exception-add"), new scmaddException());
e.definevar(Symbol.intern("jas-code-addinsn"), new scmaddInsn());
e.definevar(Symbol.intern("jas-code-stack-size"), new scmsetStackSize());
e.definevar(Symbol.intern("jas-code-var-size"), new scmsetVarSize());
e.definevar(Symbol.intern("jas-set-catchtable"), new scmsetCatchtable());
e.definevar(Symbol.intern("jas-add-catch-entry"), new scmaddEntry());
  }
}
