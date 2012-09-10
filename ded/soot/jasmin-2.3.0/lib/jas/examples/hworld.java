import jas.*;
import java.io.*;

//
// This is program that makes calls into the jas package
// to generate a class that prints a string afew times.
//

class hworld implements RuntimeConstants
{
  public static void main(String argv[])
    throws jasError, IOException
  {

                                // class hierarchy
    ClassEnv nclass = new ClassEnv();
    nclass.setClass(new ClassCP("out"));
    nclass.setSuperClass(new ClassCP("java/lang/Object"));
    nclass.setClassAccess((short)ACC_PUBLIC);

                                // Initialization code

    CodeAttr init = new CodeAttr();
    init.addInsn(new Insn(opc_aload_0));
    init.addInsn(new Insn(opc_invokenonvirtual,
                          new MethodCP("java/lang/Object", "<init>", "()V")));
    init.addInsn(new Insn(opc_return));


                                // Actual code to print string
    CodeAttr doit = new CodeAttr();

                                // store refs in local variables
    doit.addInsn(new Insn(opc_getstatic,
                          new FieldCP("java/lang/System",
                                      "out",
                                      "Ljava/io/PrintStream;")));
    doit.addInsn(new Insn(opc_astore_1));
    doit.addInsn(new Insn(opc_ldc,
                          new StringCP("Hello World")));
    doit.addInsn(new Insn(opc_astore_2));

                                // Loop index in var reg 3
    doit.addInsn(new Insn(opc_bipush, 5));
    doit.addInsn(new Insn(opc_istore_3));

                                // Start the loop
    Label loop = new Label("loop");
    doit.addInsn(loop);
    doit.addInsn(new Insn(opc_aload_1));
    doit.addInsn(new Insn(opc_aload_2));
    doit.addInsn(new Insn(opc_invokevirtual,
                          new MethodCP("java/io/PrintStream",
                                       "println",
                                       "(Ljava/lang/String;)V")));
    doit.addInsn(new IincInsn(3, -1));
    doit.addInsn(new Insn(opc_iload_3));
    doit.addInsn(new Insn(opc_ifne, loop));
    doit.addInsn(new Insn(opc_return));

                                // set the right sizes for code
    doit.setStackSize((short)3); doit.setVarSize((short)4);

                                // Add the init code to the class.
    nclass.addMethod((short)ACC_PUBLIC, "<init>", "()V", init, null);

                                // Add the printing code
    nclass.addMethod((short)(ACC_PUBLIC|ACC_STATIC), "main",
                     "([Ljava/lang/String;)V", doit, null);

                                // write it all out
    nclass.write(new DataOutputStream
                 (new FileOutputStream("out.class")));
  }
}

    
