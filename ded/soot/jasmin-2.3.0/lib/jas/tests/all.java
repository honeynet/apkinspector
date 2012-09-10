                                // Simply try
                                // to access as much of
                                // the API as possible.

import jas.*;

import java.io.*;
import sun.tools.java.RuntimeConstants;

public class all implements RuntimeConstants
{
  public static void main(String argv[])
    throws Exception
  {
    ClassEnv c = new ClassEnv();

                                // Adding CP Items directly

    c.addCPItem(new AsciiCP("fubar"));
    c.addCPItem(new ClassCP("java/lang/Number"));
    c.addCPItem(new DoubleCP(2.0));
    c.addCPItem(new FieldCP("java/lang/System",
                            "out",
                            "Ljava/io/PrintStream;"));
    c.addCPItem(new FloatCP((float)(2.0)));
    c.addCPItem(new IntegerCP(2));
    c.addCPItem(new InterfaceCP("java/lang/Runnable",
                                "run",
                                "()V"));
    c.addCPItem(new LongCP(2));
    c.addCPItem(new MethodCP("java/lang/Thread",
                             "run",
                             "()V"));

    c.addCPItem(new NameTypeCP("sdfsdf", "Ljava/lang/Object;"));
    c.addCPItem(new StringCP("sdf"));


                                // Add fields, creating variables
    c.addField(new Var((short) ACC_PUBLIC,
                       new AsciiCP("someIntvar"),
                       new AsciiCP("I"),
                       null));
    c.addField(new Var((short)(ACC_PUBLIC|ACC_STATIC|ACC_FINAL),
                       new AsciiCP("finalInt"),
                       new AsciiCP("I"),
                       new ConstAttr(new IntegerCP(10))));

                                // Check if I can add interfaces
    c.addInterface(new ClassCP("java/lang/Runnable"));

    c.setClass(new ClassCP("regress"));
    c.setSuperClass(new ClassCP("java/lang/Object"));
    c.setClassAccess((short) ACC_PUBLIC);

                                // Creating code.
    
    CodeAttr code = new CodeAttr();
                                // add instructions of various
                                // operand types.

                                // No operands
    code.addInsn(new Insn(opc_return));
                                // one arg operands
    code.addInsn(new Insn(opc_astore, 5));
                                // one arg arguments with wide operand
    code.addInsn(new Insn(opc_dstore, 256));
    code.addInsn(new Insn(opc_istore, 2576));

                                // Add a label
    code.addInsn(new Label("First label"));
                                // refer back to it
    code.addInsn(new Insn(opc_jsr,
                          new Label("First label")));
                                // add another label
    code.addInsn(new Label("second_label"));
                                // insn with CP argument
    code.addInsn(new Insn(opc_ldc_w, new StringCP("sdfsdf")));

                                // the "special" instructions
    code.addInsn(new IincInsn(2, -2));
                                // wider version check
    code.addInsn(new IincInsn(1234, 2));
    code.addInsn(new IincInsn(3, -200));
    code.addInsn(new InvokeinterfaceInsn(new ClassCP("java/lang/Number"), 1));
    code.addInsn(new MultiarrayInsn(new ClassCP("java/lang/Double"), 3));
    Label woo[] = new Label[3];
    woo[0] = new Label("First label");
    woo[1] = new Label("second_label");
    woo[2] = new Label("second_label");
    code.addInsn(new TableswitchInsn(0, 2, woo[0], woo));

    int m[] = new int[3];
    m[0] = 11;
    m[1] = 15;
    m[2] = -1;
    code.addInsn(new LookupswitchInsn(woo[0], m, woo));


                                // make a catchtable
    Catchtable ctb = new Catchtable();
                                // add a couple of entries
    ctb.addEntry(new Label("First label"),
                 new Label("second_label"),
                 new Label("second_label"),
                 new ClassCP("java/lang/Exception"));
    ctb.addEntry(new Label("First label"),
                 new Label("second_label"),
                 new Label("second_label"),
                 new ClassCP("java/lang/Error"));
    code.setCatchtable(ctb);
    code.setStackSize((short)100);
    code.setVarSize((short)500);
				// Add some line table info
    LineTableAttr ln = new LineTableAttr();
    ln.addEntry(woo[0], 234);
    ln.addEntry(woo[1], 245);
    ln.addEntry(woo[2], 22);
    code.setLineTable(ln);
				// Add a generic attr to a method
    String foo = "sldkfj sdlfkj";
    byte dat[] = new byte[foo.length()];
    foo.getBytes(0, dat.length, dat, 0);
    code.addGenericAttr(new GenericAttr("strangeAttr", dat));

				// Also adding local varinfo
    LocalVarTableAttr lv = new LocalVarTableAttr();
    lv.addEntry(new LocalVarEntry(woo[0], woo[2], "fakevar", "I", 22));
    lv.addEntry(new LocalVarEntry(woo[1], woo[1], "morefa", "()V", 10));
    code.setLocalVarTable(lv);

                                // check out add method, also
                                // adding a throws exception for
                                // good measure
    ExceptAttr ex = new ExceptAttr();
    ex.addException(new ClassCP("java/io/IOException"));
    ex.addException(new ClassCP("java/lang/Error"));
    c.addMethod((short) ACC_PUBLIC,
                "fubarmethod",
                "()V",
                code,
                ex);




				// Add a source file attribute
    c.setSource(new SourceAttr("all.java"));
				// Add some more generic attribute
    c.addGenericAttr(new GenericAttr("blahAttr", dat));
    c.write(new DataOutputStream(new FileOutputStream("regress.class")));
  }
}

    
