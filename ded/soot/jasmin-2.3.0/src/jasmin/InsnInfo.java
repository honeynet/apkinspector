/* --- Copyright Jonathan Meyer 1997. All rights reserved. -----------------
 > File:        jasmin/src/jasmin/InsnInfo.java
 > Purpose:     Information about instructions (opcode, type of args, etc)
 > Author:      Jonathan Meyer, 8 Feb 1996
 */

//
// InsnInfo is used to hold info about the opcode and parameters needed
// by an instruction. Instances of InsnInfo are created by a static
// initializer and stored in a table.
//

package jasmin;

import jas.RuntimeConstants;
import java.util.Hashtable;

class InsnInfo {
    // maps instruction name -> InsnInfo object
    static Hashtable infoTable;

    // information maintained about each instruction:
    public String name;     // instruction name
    public int opcode;      // its opcode
    public String args;     // the argument code

    public static InsnInfo get(String name) {
	return (InsnInfo)infoTable.get(name);	
    }

    public static boolean contains(String name) {
	return infoTable.get(name) != null;	
    }

    //
    // used to initialize the infoTable table (see below)
    //
    static private void addInfo(String name, int opcode, String args) {
	InsnInfo info = new InsnInfo();
	info.name = name;
	info.opcode = opcode;
	info.args = args;
        infoTable.put(name, info);
    }

    //
    // initializes the infoTable table
    //
    static {
        infoTable = new Hashtable();

        addInfo("aaload", RuntimeConstants.opc_aaload, "");
        addInfo("aastore", RuntimeConstants.opc_aastore, "");
        addInfo("aconst_null", RuntimeConstants.opc_aconst_null, "");
        addInfo("aload", RuntimeConstants.opc_aload, "i");
        addInfo("aload_0", RuntimeConstants.opc_aload_0, "");
        addInfo("aload_1", RuntimeConstants.opc_aload_1, "");
        addInfo("aload_2", RuntimeConstants.opc_aload_2, "");
        addInfo("aload_3", RuntimeConstants.opc_aload_3, "");
        addInfo("anewarray", RuntimeConstants.opc_anewarray, "class");
        addInfo("areturn", RuntimeConstants.opc_areturn, "");
        addInfo("arraylength", RuntimeConstants.opc_arraylength, "");
        addInfo("astore", RuntimeConstants.opc_astore, "i");
        addInfo("astore_0", RuntimeConstants.opc_astore_0, "");
        addInfo("astore_1", RuntimeConstants.opc_astore_1, "");
        addInfo("astore_2", RuntimeConstants.opc_astore_2, "");
        addInfo("astore_3", RuntimeConstants.opc_astore_3, "");
        addInfo("athrow", RuntimeConstants.opc_athrow, "");
        addInfo("baload", RuntimeConstants.opc_baload, "");
        addInfo("bastore", RuntimeConstants.opc_bastore, "");
        addInfo("bipush", RuntimeConstants.opc_bipush, "i");
        addInfo("breakpoint", RuntimeConstants.opc_breakpoint, "");
        addInfo("caload", RuntimeConstants.opc_caload, "");
        addInfo("castore", RuntimeConstants.opc_castore, "");
        addInfo("checkcast", RuntimeConstants.opc_checkcast, "class");
        addInfo("d2f", RuntimeConstants.opc_d2f, "");
        addInfo("d2i", RuntimeConstants.opc_d2i, "");
        addInfo("d2l", RuntimeConstants.opc_d2l, "");
        addInfo("dadd", RuntimeConstants.opc_dadd, "");
        addInfo("daload", RuntimeConstants.opc_daload, "");
        addInfo("dastore", RuntimeConstants.opc_dastore, "");
        addInfo("dcmpg", RuntimeConstants.opc_dcmpg, "");
        addInfo("dcmpl", RuntimeConstants.opc_dcmpl, "");
        addInfo("dconst_0", RuntimeConstants.opc_dconst_0, "");
        addInfo("dconst_1", RuntimeConstants.opc_dconst_1, "");
        addInfo("ddiv", RuntimeConstants.opc_ddiv, "");
        addInfo("dload", RuntimeConstants.opc_dload, "i");
        addInfo("dload_0", RuntimeConstants.opc_dload_0, "");
        addInfo("dload_1", RuntimeConstants.opc_dload_1, "");
        addInfo("dload_2", RuntimeConstants.opc_dload_2, "");
        addInfo("dload_3", RuntimeConstants.opc_dload_3, "");
        addInfo("dmul", RuntimeConstants.opc_dmul, "");
        addInfo("dneg", RuntimeConstants.opc_dneg, "");
        addInfo("drem", RuntimeConstants.opc_drem, "");
        addInfo("dreturn", RuntimeConstants.opc_dreturn, "");
        addInfo("dstore", RuntimeConstants.opc_dstore, "i");
        addInfo("dstore_0", RuntimeConstants.opc_dstore_0, "");
        addInfo("dstore_1", RuntimeConstants.opc_dstore_1, "");
        addInfo("dstore_2", RuntimeConstants.opc_dstore_2, "");
        addInfo("dstore_3", RuntimeConstants.opc_dstore_3, "");
        addInfo("dsub", RuntimeConstants.opc_dsub, "");
        addInfo("dup", RuntimeConstants.opc_dup, "");
        addInfo("dup2", RuntimeConstants.opc_dup2, "");
        addInfo("dup2_x1", RuntimeConstants.opc_dup2_x1, "");
        addInfo("dup2_x2", RuntimeConstants.opc_dup2_x2, "");
        addInfo("dup_x1", RuntimeConstants.opc_dup_x1, "");
        addInfo("dup_x2", RuntimeConstants.opc_dup_x2, "");
        addInfo("f2d", RuntimeConstants.opc_f2d, "");
        addInfo("f2i", RuntimeConstants.opc_f2i, "");
        addInfo("f2l", RuntimeConstants.opc_f2l, "");
        addInfo("fadd", RuntimeConstants.opc_fadd, "");
        addInfo("faload", RuntimeConstants.opc_faload, "");
        addInfo("fastore", RuntimeConstants.opc_fastore, "");
        addInfo("fcmpg", RuntimeConstants.opc_fcmpg, "");
        addInfo("fcmpl", RuntimeConstants.opc_fcmpl, "");
        addInfo("fconst_0", RuntimeConstants.opc_fconst_0, "");
        addInfo("fconst_1", RuntimeConstants.opc_fconst_1, "");
        addInfo("fconst_2", RuntimeConstants.opc_fconst_2, "");
        addInfo("fdiv", RuntimeConstants.opc_fdiv, "");
        addInfo("fload", RuntimeConstants.opc_fload, "i");
        addInfo("fload_0", RuntimeConstants.opc_fload_0, "");
        addInfo("fload_1", RuntimeConstants.opc_fload_1, "");
        addInfo("fload_2", RuntimeConstants.opc_fload_2, "");
        addInfo("fload_3", RuntimeConstants.opc_fload_3, "");
        addInfo("fmul", RuntimeConstants.opc_fmul, "");
        addInfo("fneg", RuntimeConstants.opc_fneg, "");
        addInfo("frem", RuntimeConstants.opc_frem, "");
        addInfo("freturn", RuntimeConstants.opc_freturn, "");
        addInfo("fstore", RuntimeConstants.opc_fstore, "i");
        addInfo("fstore_0", RuntimeConstants.opc_fstore_0, "");
        addInfo("fstore_1", RuntimeConstants.opc_fstore_1, "");
        addInfo("fstore_2", RuntimeConstants.opc_fstore_2, "");
        addInfo("fstore_3", RuntimeConstants.opc_fstore_3, "");
        addInfo("fsub", RuntimeConstants.opc_fsub, "");
        addInfo("getfield", RuntimeConstants.opc_getfield, "field");
        addInfo("getstatic", RuntimeConstants.opc_getstatic, "field");
        addInfo("goto", RuntimeConstants.opc_goto, "label");
        addInfo("goto_w", RuntimeConstants.opc_goto_w, "label");
        addInfo("i2d", RuntimeConstants.opc_i2d, "");
        addInfo("i2f", RuntimeConstants.opc_i2f, "");
        addInfo("i2l", RuntimeConstants.opc_i2l, "");
        addInfo("iadd", RuntimeConstants.opc_iadd, "");
        addInfo("iaload", RuntimeConstants.opc_iaload, "");
        addInfo("iand", RuntimeConstants.opc_iand, "");
        addInfo("iastore", RuntimeConstants.opc_iastore, "");
        addInfo("iconst_0", RuntimeConstants.opc_iconst_0, "");
        addInfo("iconst_1", RuntimeConstants.opc_iconst_1, "");
        addInfo("iconst_2", RuntimeConstants.opc_iconst_2, "");
        addInfo("iconst_3", RuntimeConstants.opc_iconst_3, "");
        addInfo("iconst_4", RuntimeConstants.opc_iconst_4, "");
        addInfo("iconst_5", RuntimeConstants.opc_iconst_5, "");
        addInfo("iconst_m1", RuntimeConstants.opc_iconst_m1, "");
        addInfo("idiv", RuntimeConstants.opc_idiv, "");
        addInfo("if_acmpeq", RuntimeConstants.opc_if_acmpeq, "label");
        addInfo("if_acmpne", RuntimeConstants.opc_if_acmpne, "label");
        addInfo("if_icmpeq", RuntimeConstants.opc_if_icmpeq, "label");
        addInfo("if_icmpge", RuntimeConstants.opc_if_icmpge, "label");
        addInfo("if_icmpgt", RuntimeConstants.opc_if_icmpgt, "label");
        addInfo("if_icmple", RuntimeConstants.opc_if_icmple, "label");
        addInfo("if_icmplt", RuntimeConstants.opc_if_icmplt, "label");
        addInfo("if_icmpne", RuntimeConstants.opc_if_icmpne, "label");
        addInfo("ifeq", RuntimeConstants.opc_ifeq, "label");
        addInfo("ifge", RuntimeConstants.opc_ifge, "label");
        addInfo("ifgt", RuntimeConstants.opc_ifgt, "label");
        addInfo("ifle", RuntimeConstants.opc_ifle, "label");
        addInfo("iflt", RuntimeConstants.opc_iflt, "label");
        addInfo("ifne", RuntimeConstants.opc_ifne, "label");
        addInfo("ifnonnull", RuntimeConstants.opc_ifnonnull, "label");
        addInfo("ifnull", RuntimeConstants.opc_ifnull, "label");
        addInfo("iinc", RuntimeConstants.opc_iinc, "ii");
        addInfo("iload", RuntimeConstants.opc_iload, "i");
        addInfo("iload_0", RuntimeConstants.opc_iload_0, "");
        addInfo("iload_1", RuntimeConstants.opc_iload_1, "");
        addInfo("iload_2", RuntimeConstants.opc_iload_2, "");
        addInfo("iload_3", RuntimeConstants.opc_iload_3, "");
        addInfo("imul", RuntimeConstants.opc_imul, "");
        addInfo("ineg", RuntimeConstants.opc_ineg, "");
        addInfo("instanceof", RuntimeConstants.opc_instanceof, "class");
        addInfo("int2byte", RuntimeConstants.opc_int2byte, "");
        addInfo("int2char", RuntimeConstants.opc_int2char, "");
        addInfo("int2short", RuntimeConstants.opc_int2short, "");
        // added this synonym
        addInfo("i2b", RuntimeConstants.opc_int2byte, "");
        // added this synonym
        addInfo("i2c", RuntimeConstants.opc_int2char, "");
        // added this synonym
        addInfo("i2s", RuntimeConstants.opc_int2short, "");
        addInfo("invokeinterface", RuntimeConstants.opc_invokeinterface, "interface");
        addInfo("invokenonvirtual", RuntimeConstants.opc_invokenonvirtual, "method");
        // added this synonym
        addInfo("invokespecial", RuntimeConstants.opc_invokenonvirtual, "method");
        addInfo("invokestatic", RuntimeConstants.opc_invokestatic, "method");
        addInfo("invokevirtual", RuntimeConstants.opc_invokevirtual, "method");
        addInfo("ior", RuntimeConstants.opc_ior, "");
        addInfo("irem", RuntimeConstants.opc_irem, "");
        addInfo("ireturn", RuntimeConstants.opc_ireturn, "");
        addInfo("ishl", RuntimeConstants.opc_ishl, "");
        addInfo("ishr", RuntimeConstants.opc_ishr, "");
        addInfo("istore", RuntimeConstants.opc_istore, "i");
        addInfo("istore_0", RuntimeConstants.opc_istore_0, "");
        addInfo("istore_1", RuntimeConstants.opc_istore_1, "");
        addInfo("istore_2", RuntimeConstants.opc_istore_2, "");
        addInfo("istore_3", RuntimeConstants.opc_istore_3, "");
        addInfo("isub", RuntimeConstants.opc_isub, "");
        addInfo("iushr", RuntimeConstants.opc_iushr, "");
        addInfo("ixor", RuntimeConstants.opc_ixor, "");
        addInfo("jsr", RuntimeConstants.opc_jsr, "label");
        addInfo("jsr_w", RuntimeConstants.opc_jsr, "label");  // synonym for "jsr"
        addInfo("l2d", RuntimeConstants.opc_l2d, "");
        addInfo("l2f", RuntimeConstants.opc_l2f, "");
        addInfo("l2i", RuntimeConstants.opc_l2i, "");
        addInfo("ladd", RuntimeConstants.opc_ladd, "");
        addInfo("laload", RuntimeConstants.opc_laload, "");
        addInfo("land", RuntimeConstants.opc_land, "");
        addInfo("lastore", RuntimeConstants.opc_lastore, "");
        addInfo("lcmp", RuntimeConstants.opc_lcmp, "");
        addInfo("lconst_0", RuntimeConstants.opc_lconst_0, "");
        addInfo("lconst_1", RuntimeConstants.opc_lconst_1, "");
        addInfo("ldc", RuntimeConstants.opc_ldc, "constant");
        addInfo("ldc_w", RuntimeConstants.opc_ldc_w, "constant");
        addInfo("ldc2_w", RuntimeConstants.opc_ldc2_w, "bigconstant");
        addInfo("ldiv", RuntimeConstants.opc_ldiv, "");
        addInfo("lload", RuntimeConstants.opc_lload, "i");
        addInfo("lload_0", RuntimeConstants.opc_lload_0, "");
        addInfo("lload_1", RuntimeConstants.opc_lload_1, "");
        addInfo("lload_2", RuntimeConstants.opc_lload_2, "");
        addInfo("lload_3", RuntimeConstants.opc_lload_3, "");
        addInfo("lmul", RuntimeConstants.opc_lmul, "");
        addInfo("lneg", RuntimeConstants.opc_lneg, "");
        addInfo("lookupswitch", RuntimeConstants.opc_lookupswitch, "switch");
        addInfo("lor", RuntimeConstants.opc_lor, "");
        addInfo("lrem", RuntimeConstants.opc_lrem, "");
        addInfo("lreturn", RuntimeConstants.opc_lreturn, "");
        addInfo("lshl", RuntimeConstants.opc_lshl, "");
        addInfo("lshr", RuntimeConstants.opc_lshr, "");
        addInfo("lstore", RuntimeConstants.opc_lstore, "i");
        addInfo("lstore_0", RuntimeConstants.opc_lstore_0, "");
        addInfo("lstore_1", RuntimeConstants.opc_lstore_1, "");
        addInfo("lstore_2", RuntimeConstants.opc_lstore_2, "");
        addInfo("lstore_3", RuntimeConstants.opc_lstore_3, "");
        addInfo("lsub", RuntimeConstants.opc_lsub, "");
        addInfo("lushr", RuntimeConstants.opc_lushr, "");
        addInfo("lxor", RuntimeConstants.opc_lxor, "");
        addInfo("monitorenter", RuntimeConstants.opc_monitorenter, "");
        addInfo("monitorexit", RuntimeConstants.opc_monitorexit, "");
        addInfo("multianewarray", RuntimeConstants.opc_multianewarray, "marray");
        addInfo("new", RuntimeConstants.opc_new, "class");
        addInfo("newarray", RuntimeConstants.opc_newarray, "atype");
        addInfo("nop", RuntimeConstants.opc_nop, "");
        addInfo("pop", RuntimeConstants.opc_pop, "");
        addInfo("pop2", RuntimeConstants.opc_pop2, "");
        addInfo("putfield", RuntimeConstants.opc_putfield, "field");
        addInfo("putstatic", RuntimeConstants.opc_putstatic, "field");
        addInfo("ret", RuntimeConstants.opc_ret, "i");
        addInfo("ret_w", RuntimeConstants.opc_ret, "i"); // synonym for ret
        addInfo("return", RuntimeConstants.opc_return, "");
        addInfo("saload", RuntimeConstants.opc_saload, "");
        addInfo("sastore", RuntimeConstants.opc_sastore, "");
        addInfo("sipush", RuntimeConstants.opc_sipush, "i");
        addInfo("swap", RuntimeConstants.opc_swap, "");
        addInfo("tableswitch", RuntimeConstants.opc_tableswitch, "switch");

        // special case:
        addInfo("wide", RuntimeConstants.opc_wide, "ignore");
    }

};

/* --- Revision History ---------------------------------------------------
--- Jonathan Meyer, Feb 8 1997
    Added invokespecial as a synonym for invokenonvirtual
*/
