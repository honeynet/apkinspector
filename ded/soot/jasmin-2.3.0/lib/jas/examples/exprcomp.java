// Primitive runtime code generation of expressions. This is a jas
// implementation of the example in Aho Sethi Ullman.
// 
// You pass to it statements of the form
// a = 1*2 + 3*5;
// Only integer operations are allowed, and variables
// are magically created by using them in the LHS of a statement.
//
// You can print out the value of expressions with the println keyword:
// println(a + 10);
//
// It compiles this into a bytearray, and then loads it as 
// a new class.
//
// Unfortunately, this trick cannot be used in an applet --- security
// restrictions prevent ClassLoaders from being placed on the
// stack. So much for writing regexp compilers that can do codegen instead of
// state tables.
//
// The grammar is simple, so code generation is part of the parsing
// step. Operator precedence is directly handled by the grammar.
//
// Grammar + production rules:
//
// start -> list EOF
// list -> id = expr ;  { emit(istore, id.index); } list
//        | println expr ;  { emit(<println sequence>); } list
//        | lambda
// expr -> term moreterms
// moreterms -> + term { emit(iadd) } moreterms
//           |  - term { emit(isub) } moreterms
//           |  lambda
// term -> factor morefactors
// morefactors -> * factor { emit(imul) } morefactors
//              | / factor { emit(idiv) } morefactors
//              | lambda
// factor -> ( expr )
//          | number { emit(iconst, number.value) }
//          | id { emit(iload, id.index); }

import java.util.*;
import java.io.*;
import jas.*;

public class exprcomp implements RuntimeConstants
{
  StreamTokenizer inp;
  CodeAttr myCode;
  short cur_stack_height;
  short max_stack_height;
  short max_vars;
  Hashtable vars;

  public exprcomp(StreamTokenizer inp)
    throws jasError
  {
    inp.eolIsSignificant(false);
    this.inp = inp;
    myCode = new CodeAttr();
                                // Add initializations
    myCode.addInsn(new Insn(opc_aload_0));
    myCode.addInsn(new Insn(opc_invokenonvirtual,
                            new MethodCP("java/lang/Object",
                                         "<init>", "()V")));
    cur_stack_height = max_stack_height = 1;
    vars = new Hashtable();
    max_vars = 1;
  }
  public void write(DataOutputStream out)
    throws IOException, jasError
  {
    ClassEnv clazz = new ClassEnv();
    myCode.setStackSize(max_stack_height);
    myCode.setVarSize(max_vars);
                                // add initializer to class
    clazz.addMethod
      ((short) ACC_PUBLIC, "<init>", "()V", myCode, null);
    clazz.setClassAccess((short) ACC_PUBLIC);
    clazz.setClass(new ClassCP("results"));
    clazz.setSuperClass(new ClassCP("java/lang/Object"));
    clazz.write(out);
  }

  public void parse()
    throws IOException, parseError, jasError
  {
    inp.nextToken();
    while(inp.ttype != inp.TT_EOF)
      {
        if (inp.ttype != inp.TT_WORD)
          {
            throw new parseError("Expected an id at line " + inp.lineno());
          }
        if (inp.sval.equals("println"))
          {
            match(inp.TT_WORD);
            expr();
            match(';');
            emit(new Insn(opc_getstatic,
                          new FieldCP("java/lang/System",
                                      "out", "Ljava/io/PrintStream;")));
            emit(new Insn(opc_swap));
            emit(new Insn(opc_invokevirtual,
                          new MethodCP("java/io/PrintStream",
                                       "println", "(I)V")));
          }
        else
          {                     // search, maybe add into var list
            Integer idx;
            if ((idx = (Integer) vars.get(inp.sval)) == null)
              {
                idx = new Integer(max_vars++);
                vars.put(inp.sval.intern(), idx);
              }
            match(inp.TT_WORD); match('='); expr(); match(';');
            emit(new Insn(opc_istore, idx.intValue()));
          }
      }
    emit(new Insn(opc_return));
  }

  void expr()
    throws IOException, parseError, jasError
  {
    term();
    while (true)
      {
        switch(inp.ttype)
          {
          case '+':
            match('+'); term(); emit(new Insn(opc_iadd)); break;
          case '-':
            match('-'); term(); emit(new Insn(opc_isub)); break;
          default: return;
          }
        cur_stack_height--;
      }
  }

  void term()
    throws IOException, parseError, jasError
  {
    factor();
    while (true)
      {
        switch(inp.ttype)
          {
          case '*': match('*'); factor(); emit(new Insn(opc_imul)); break;
          case '/': match('/'); factor(); emit(new Insn(opc_idiv)); break;
          default: return;
          }
        cur_stack_height --;
      }
  }
  void factor()
    throws IOException, parseError, jasError
  {
    switch(inp.ttype)
      {
      case '(': match('('); expr(); match(')'); break;
      case inp.TT_NUMBER:
        int val = (int)(inp.nval);
        emit(new Insn(opc_bipush, (short)val));
        match(inp.TT_NUMBER);
        break;
      case inp.TT_WORD:
        Integer idx;
        if ((idx = (Integer) vars.get(inp.sval)) == null)
          {
            throw new parseError
              ("Unknown variable " + inp.sval + " at line " + inp.lineno());
          }
        emit(new Insn(opc_iload, idx.intValue()));
        match(inp.TT_WORD);
        break;
      default:
        throw new parseError("Syntax error at line " + inp.lineno());
      }
    cur_stack_height++;
    if (max_stack_height < cur_stack_height)
      max_stack_height = cur_stack_height;
  }
  void match(int val)
    throws IOException, parseError
  {
    if (inp.ttype != val)
      { throw new parseError
          ("line " + inp.lineno() + ": expected " + val + " but got " + inp); }
    inp.nextToken();
  }

  void emit(Insn insn)
  {
    myCode.addInsn(insn);
  }

  public static void main(String argv[])
    throws Exception
  {
    exprcomp compiler =
      new exprcomp(new StreamTokenizer(new FileInputStream(argv[0])));
    compiler.parse();
    ByteArrayOutputStream data = new ByteArrayOutputStream();
    compiler.write(new DataOutputStream(data));
    dynaloader dl = new dynaloader(data.toByteArray());
    dl.exec();
  }
}

class dynaloader extends ClassLoader
{
  Hashtable cache;
  Class ex;

  dynaloader(byte[] data)
    throws ClassFormatError
  {
    cache = new Hashtable();
    ex = defineClass(data, 0, data.length);
    cache.put("results", ex);
    resolveClass(ex);
  }

  void exec() throws Exception { ex.newInstance(); }
  public synchronized Class loadClass(String name, boolean resolve)
    throws ClassNotFoundException
  {
    Class c = (Class) cache.get(name);
    if (c == null)
      {
        c = findSystemClass(name);
        cache.put(name, c);
      }
    if (resolve) resolveClass(c);
    return c;
  }
}


class parseError extends Exception
{ parseError(String s) { super(s); } }
