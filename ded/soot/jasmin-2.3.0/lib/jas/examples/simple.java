import jas.*;
import java.io.*;

//
// This is program that makes calls into the jas package
// to generate a class that does nothing at all.
//

class simple implements RuntimeConstants
{
  public static void main(String argv[])
    throws jasError, IOException
  {

                                // CodeAttr's contain the body of
                                // a method.

    CodeAttr init = new CodeAttr();
    init.addInsn(new Insn(opc_aload_0));
    init.addInsn(new Insn(opc_invokenonvirtual,
                          new MethodCP("java/lang/Object", "<init>", "()V")));
    init.addInsn(new Insn(opc_return));


                                // ClassEnv's are used as a container
                                // to hold all information about a class.

    ClassEnv nclass = new ClassEnv();
    nclass.setClass(new ClassCP("out"));
    nclass.setSuperClass(new ClassCP("java/lang/Object"));



                                // Add the init code to the class.
    nclass.addMethod((short)ACC_PUBLIC, "<init>", "()V", init, null);


                                // write it all out
    nclass.write(new DataOutputStream
                 (new FileOutputStream("out.class")));
  }
}

    
