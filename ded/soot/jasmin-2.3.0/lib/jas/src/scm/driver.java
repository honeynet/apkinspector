package scm;

import java.io.*;
import java.util.*;
import jas.RuntimeConstants;

class driver implements RuntimeConstants
{
  static Hashtable syms;
  public static void main(String argv[])
    throws Exception
  {
    String infileName, outfileName;
    StreamTokenizer inp;

    if (argv.length == 0)
        inp = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
      //inp = new StreamTokenizer(System.in);
    else
      inp =
        new StreamTokenizer(new BufferedReader(new InputStreamReader
        (new BufferedInputStream
         (new FileInputStream(argv[0])))));
      //inp =
      //  new StreamTokenizer
      //  (new BufferedInputStream
      //   (new FileInputStream(argv[0])));

    inp.resetSyntax();
    inp.wordChars('a', 'z');
    inp.wordChars('A', 'Z');
    inp.wordChars('!', '!');
    inp.wordChars('?', '?');
    inp.wordChars('_', '_');
    inp.wordChars(128 + 32, 255);
    inp.whitespaceChars(0, ' ');
    inp.quoteChar('"');
    inp.commentChar(';');
    inp.parseNumbers();
    inp.eolIsSignificant(false);

    Env global = new Env();

    global.definevar(Symbol.intern("define"), new Define());
    global.definevar(Symbol.intern("set!"), new Setvar());
    global.definevar(Symbol.intern("lambda"), new Lambda());
    global.definevar(Symbol.intern("quote"), new Quote());
    global.definevar(Symbol.intern("car"), new Car());
    global.definevar(Symbol.intern("cdr"), new Cdr());
    global.definevar(Symbol.intern("cons"), new Cons());
    global.definevar(Symbol.intern("cond"), new Cond());
    global.definevar(Symbol.intern("num?"), new NumP());
    global.definevar(Symbol.intern("string?"), new StringP());
    global.definevar(Symbol.intern("progn"), new Progn());
    global.definevar(Symbol.intern("mapcar"), new Mapcar());
    global.definevar(Symbol.intern("+"), new Plus());
    global.definevar(Symbol.intern("-"), new Minus());
    global.definevar(Symbol.intern("*"), new Mult());
    global.definevar(Symbol.intern("/"), new Div());
    global.definevar(Symbol.intern("|"), new Or());
    global.definevar(Symbol.intern("<"), new LessP());
    global.definevar(Symbol.intern(">"), new MoreP());
    global.definevar(Symbol.intern("eq?"), new EqP());


                                // Include jas stuff now.

                                // load in constants

    global.definevar(Symbol.intern("acc-public"),
                     new Selfrep(ACC_PUBLIC));
    global.definevar(Symbol.intern("acc-private"),
                     new Selfrep(ACC_PRIVATE));
    global.definevar(Symbol.intern("acc-protected"),
                     new Selfrep(ACC_PROTECTED));
    global.definevar(Symbol.intern("acc-static"),
                     new Selfrep(ACC_STATIC));
    global.definevar(Symbol.intern("acc-final"),
                     new Selfrep(ACC_FINAL));
    global.definevar(Symbol.intern("acc-synchronized"),
                     new Selfrep(ACC_SYNCHRONIZED));
    global.definevar(Symbol.intern("acc-volatile"),
                     new Selfrep(ACC_VOLATILE));
    global.definevar(Symbol.intern("acc-transient"),
                     new Selfrep(ACC_TRANSIENT));
    global.definevar(Symbol.intern("acc-native"),
                     new Selfrep(ACC_NATIVE));
    global.definevar(Symbol.intern("acc-interface"),
                     new Selfrep(ACC_INTERFACE));
    global.definevar(Symbol.intern("acc-abstract"),
                     new Selfrep(ACC_ABSTRACT));
    global.definevar(Symbol.intern("acc-strictfp"),
                     new Selfrep(ACC_STRICTFP));

                                // Pull in auto generated stuff
    AutoInit.fillit(global);

                                // now add in procedures for opcodes
    int opc_cnt = opcNames.length;
    for (int i=0; i<opc_cnt; i++)
      {
        switch(i)
          {
          default:
            global.definevar(Symbol.intern(opcNames[i]),
                             new InsnProcedure(i));
          case opc_iinc:
          case opc_multianewarray:
          case opc_tableswitch:
          case opc_invokeinterface:
                                // special instructions
            break;
          }
      }

                                // enter repl
    do
      {
        inp.nextToken();
        Obj c = readinp(inp);
        if (c != null)  { c = c.eval(global); }
        // uncomment this if you want to
        // see the results of the eval
        //        if (c == null) System.out.println("nil");
        //        else System.out.println(c.toString());
      }
    while (inp.ttype != inp.TT_EOF);
  }

  static Obj readinp(StreamTokenizer inp)
    throws IOException
  {
    switch (inp.ttype)
      {
      case '(':
        return readparen(inp);
      case ')':
        throw new SchemeError("Unexpected close paren");
      default:
        return readtok(inp);
      }
  }

  static Cell readparen(StreamTokenizer inp)
    throws IOException
  {
    inp.nextToken();
    if (inp.ttype == ')') return (null);

    return(new Cell(readinp(inp), readparen(inp)));
  }

  static Obj readtok(StreamTokenizer inp)
  {
    Obj ret;
    if (inp.ttype == inp.TT_NUMBER)
        return new Selfrep(inp.nval); 
    else if (inp.ttype == inp.TT_WORD)
        return Symbol.intern(inp.sval);
    else if (inp.ttype == inp.TT_EOF)
	return null;

    switch (inp.ttype)
      {
      case '"':
        ret = new Selfrep(inp.sval); break;
      case '+':
      case '-':
      case '*':
      case '/':
      case '|':
      case '<':
      case '>':
        ret =  Symbol.intern(String.valueOf((char)(inp.ttype)));
        break;
      default:
        throw new SchemeError("Unexpected parse error");
      }
    return ret;
  }
}
