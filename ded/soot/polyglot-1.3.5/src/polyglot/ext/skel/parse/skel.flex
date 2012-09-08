/* Java 1.4 scanner for JFlex.
 * Based on JLS, 2ed, Chapter 3.
 */

package polyglot.ext.skel.parse;

import java_cup.runtime.Symbol;
import polyglot.lex.Lexer;
import polyglot.lex.*;
import polyglot.util.Position;
import polyglot.util.ErrorQueue;
import polyglot.util.ErrorInfo;
import java.util.HashMap;

%%

%public
%class Lexer_c
%implements Lexer
%type Token
%function nextToken

%unicode
%pack

%line
%column

%state STRING, CHARACTER, TRADITIONAL_COMMENT, END_OF_LINE_COMMENT

%{
    StringBuffer sb = new StringBuffer();
    String file;
    ErrorQueue eq;
    HashMap keywords;

    public Lexer_c(java.io.InputStream in, String file, ErrorQueue eq) {
        this(new java.io.BufferedReader(new java.io.InputStreamReader(in)),
             file, eq);
    }

    public Lexer_c(java.io.Reader reader, String file, ErrorQueue eq) {
        this(new EscapedUnicodeReader(reader));
        this.file = file;
        this.eq = eq;
        this.keywords = new HashMap();
        init_keywords();
    }

    protected void init_keywords() {
        keywords.put("abstract",      new Integer(sym.ABSTRACT));
        keywords.put("assert",        new Integer(sym.ASSERT));
        keywords.put("boolean",       new Integer(sym.BOOLEAN));
        keywords.put("break",         new Integer(sym.BREAK));
        keywords.put("byte",          new Integer(sym.BYTE));
        keywords.put("case",          new Integer(sym.CASE));
        keywords.put("catch",         new Integer(sym.CATCH));
        keywords.put("char",          new Integer(sym.CHAR));
        keywords.put("class",         new Integer(sym.CLASS));
        keywords.put("const",         new Integer(sym.CONST));
        keywords.put("continue",      new Integer(sym.CONTINUE));
        keywords.put("default",       new Integer(sym.DEFAULT));
        keywords.put("do",            new Integer(sym.DO));
        keywords.put("double",        new Integer(sym.DOUBLE));
        keywords.put("else",          new Integer(sym.ELSE));
        keywords.put("extends",       new Integer(sym.EXTENDS));
        keywords.put("final",         new Integer(sym.FINAL));
        keywords.put("finally",       new Integer(sym.FINALLY));
        keywords.put("float",         new Integer(sym.FLOAT));
        keywords.put("for",           new Integer(sym.FOR));
        keywords.put("goto",          new Integer(sym.GOTO));
        keywords.put("if",            new Integer(sym.IF));
        keywords.put("implements",    new Integer(sym.IMPLEMENTS));
        keywords.put("import",        new Integer(sym.IMPORT));
        keywords.put("instanceof",    new Integer(sym.INSTANCEOF));
        keywords.put("int",           new Integer(sym.INT));
        keywords.put("interface",     new Integer(sym.INTERFACE));
        keywords.put("long",          new Integer(sym.LONG));
        keywords.put("native",        new Integer(sym.NATIVE));
        keywords.put("new",           new Integer(sym.NEW));
        keywords.put("package",       new Integer(sym.PACKAGE));
        keywords.put("private",       new Integer(sym.PRIVATE));
        keywords.put("protected",     new Integer(sym.PROTECTED));
        keywords.put("public",        new Integer(sym.PUBLIC));
        keywords.put("return",        new Integer(sym.RETURN));
        keywords.put("short",         new Integer(sym.SHORT));
        keywords.put("static",        new Integer(sym.STATIC));
        keywords.put("strictfp",      new Integer(sym.STRICTFP));
        keywords.put("super",         new Integer(sym.SUPER));
        keywords.put("switch",        new Integer(sym.SWITCH));
        keywords.put("synchronized",  new Integer(sym.SYNCHRONIZED));
        keywords.put("this",          new Integer(sym.THIS));
        keywords.put("throw",         new Integer(sym.THROW));
        keywords.put("throws",        new Integer(sym.THROWS));
        keywords.put("transient",     new Integer(sym.TRANSIENT));
        keywords.put("try",           new Integer(sym.TRY));
        keywords.put("void",          new Integer(sym.VOID));
        keywords.put("volatile",      new Integer(sym.VOLATILE));
        keywords.put("while",         new Integer(sym.WHILE));
    }

    public String file() {
        return file;
    }

    private Position pos() {
        return new Position(file, yyline+1, yycolumn, yyline+1,
                            yycolumn + yytext().length());
    }

    private Position pos(int len) {
        return new Position(file, yyline+1, yycolumn-len-1, yyline+1,
                            yycolumn+1);
    }

    private Token key(int symbol) {
        return new Keyword(pos(), yytext(), symbol);
    }

    private Token op(int symbol) {
        return new Operator(pos(), yytext(), symbol);
    }

    private Token id() {
        return new Identifier(pos(), yytext(), sym.IDENTIFIER);
    }

    /* Roll our own integer parser.  We can't use Long.parseLong because
     * it doesn't handle numbers greater than 0x7fffffffffffffff correctly.
     */
    private long parseLong(String s, int radix) {
        long x = 0L;

        s = s.toLowerCase();

        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i);

            if (c < '0' || c > '9') {
                c = c - 'a' + 10;
            }
            else {
                c = c - '0';
            }

            x *= radix;
            x += c;
        }

        return x;
    }

    private Token int_lit(String s, int radix) {
        long x = parseLong(s, radix);
        return new IntegerLiteral(pos(), (int) x, sym.INTEGER_LITERAL);
    }

    private Token long_lit(String s, int radix) {
        long x = parseLong(s, radix);
        return new LongLiteral(pos(), x, sym.LONG_LITERAL);
    }

    private Token float_lit(String s) {
        try {
            float x = Float.parseFloat(s);
            return new FloatLiteral(pos(), x, sym.FLOAT_LITERAL);
        }
        catch (NumberFormatException e) {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                       "Illegal float literal \"" + yytext() + "\"", pos());
            return null;
        }
    }

    private Token double_lit(String s) {
        try {
            double x = Double.parseDouble(s);
            return new DoubleLiteral(pos(), x, sym.DOUBLE_LITERAL);
        }
        catch (NumberFormatException e) {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                       "Illegal float literal \"" + yytext() + "\"", pos());
            return null;
        }
    }

    private Token char_lit(String s) {
        if (s.length() == 1) {
            char x = s.charAt(0);
            return new CharacterLiteral(pos(), x, sym.CHARACTER_LITERAL);
        }
        else {
            eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                       "Illegal character literal \'" + s + "\'", pos(s.length()));
            return null;
        }
    }

    private Token boolean_lit(boolean x) {
        return new BooleanLiteral(pos(), x, sym.BOOLEAN_LITERAL);
    }

    private Token null_lit() {
        return new NullLiteral(pos(), sym.NULL_LITERAL);
    }

    private Token string_lit() {
        return new StringLiteral(pos(sb.length()), sb.toString(),
                                 sym.STRING_LITERAL);
    }

    private String chop(int i, int j) {
        return yytext().substring(i,yylength()-j);
    }

    private String chop(int j) {
        return chop(0, j);
    }

    private String chop() {
        return chop(0, 1);
    }
%}

%eofval{
    return new EOF(pos(), sym.EOF);
%eofval}

/* From Chapter 3 of the JLS: */

/* 3.4 Line Terminators */
/* LineTerminator:
      the ASCII LF character, also known as "newline"
      the ASCII CR character, also known as "return"
      the ASCII CR character followed by the ASCII LF character
*/
LineTerminator = \n|\r|\r\n

/* 3.6 White Space */
/*
WhiteSpace:
    the ASCII SP character, also known as "space"
    the ASCII HT character, also known as "horizontal tab"
    the ASCII FF character, also known as "form feed"
    LineTerminator
*/
WhiteSpace = [ \t\f] | {LineTerminator}

/* 3.8 Identifiers */
Identifier = [:jletter:] [:jletterdigit:]*

/* 3.10.1 Integer Literals */
DecimalNumeral = 0 | [1-9][0-9]*
HexNumeral = 0 [xX] [0-9a-fA-F]+
OctalNumeral = 0 [0-7]+

/* 3.10.2 Floating-Point Literals */
FloatingPointLiteral = [0-9]+ "." [0-9]* {ExponentPart}?
                     | "." [0-9]+ {ExponentPart}?
                     | [0-9]+ {ExponentPart}

ExponentPart = [eE] {SignedInteger}
SignedInteger = [-+]? [0-9]

/* 3.10.4 Character Literals */
OctalEscape = \\ [0-7]
            | \\ [0-7][0-7]
            | \\ [0-3][0-7][0-7]

%%

<YYINITIAL> {
    /* 3.7 Comments */
    "/*"    { yybegin(TRADITIONAL_COMMENT); }
    "//"    { yybegin(END_OF_LINE_COMMENT); }

    /* 3.10.4 Character Literals */
    \'      { yybegin(CHARACTER); sb.setLength(0); }

    /* 3.10.5 String Literals */
    \"      { yybegin(STRING); sb.setLength(0); }

    /* 3.10.3 Boolean Literals */
    "true"  { return boolean_lit(true);  }
    "false" { return boolean_lit(false); }

    /* 3.10.6 Null Literal */
    "null"  { return null_lit(); }

    /* 3.9 Keywords */
    /* 3.8 Identifiers */
    {Identifier}   { Integer i = (Integer) keywords.get(yytext());
                    if (i == null) return id();
                    else return key(i.intValue()); }

    /* 3.11 Separators */
    "("    { return op(sym.LPAREN);    }
    ")"    { return op(sym.RPAREN);    }
    "{"    { return op(sym.LBRACE);    }
    "}"    { return op(sym.RBRACE);    }
    "["    { return op(sym.LBRACK);    }
    "]"    { return op(sym.RBRACK);    }
    ";"    { return op(sym.SEMICOLON); }
    ","    { return op(sym.COMMA);     }
    "."    { return op(sym.DOT);       }

    /* 3.12 Operators */
    "="    { return op(sym.EQ);         }
    ">"    { return op(sym.GT);         }
    "<"    { return op(sym.LT);         }
    "!"    { return op(sym.NOT);        }
    "~"    { return op(sym.COMP);       }
    "?"    { return op(sym.QUESTION);   }
    ":"    { return op(sym.COLON);      }
    "=="   { return op(sym.EQEQ);       }
    "<="   { return op(sym.LTEQ);       }
    ">="   { return op(sym.GTEQ);       }
    "!="   { return op(sym.NOTEQ);      }
    "&&"   { return op(sym.ANDAND);     }
    "||"   { return op(sym.OROR);       }
    "++"   { return op(sym.PLUSPLUS);   }
    "--"   { return op(sym.MINUSMINUS); }
    "+"    { return op(sym.PLUS);       }
    "-"    { return op(sym.MINUS);      }
    "*"    { return op(sym.MULT);       }
    "/"    { return op(sym.DIV);        }
    "&"    { return op(sym.AND);        }
    "|"    { return op(sym.OR);         }
    "^"    { return op(sym.XOR);        }
    "%"    { return op(sym.MOD);        }
    "<<"   { return op(sym.LSHIFT);     }
    ">>"   { return op(sym.RSHIFT);     }
    ">>>"  { return op(sym.URSHIFT);    }
    "+="   { return op(sym.PLUSEQ);     }
    "-="   { return op(sym.MINUSEQ);    }
    "*="   { return op(sym.MULTEQ);     }
    "/="   { return op(sym.DIVEQ);      }
    "&="   { return op(sym.ANDEQ);      }
    "|="   { return op(sym.OREQ);       }
    "^="   { return op(sym.XOREQ);      }
    "%="   { return op(sym.MODEQ);      }
    "<<="  { return op(sym.LSHIFTEQ);   }
    ">>="  { return op(sym.RSHIFTEQ);   }
    ">>>=" { return op(sym.URSHIFTEQ);  }

    /* 3.10.1 Integer Literals */
    {OctalNumeral} [lL]          { Token t = long_lit(chop(), 8);
                                   if (t != null) return t; }
    {HexNumeral} [lL]            { Token t = long_lit(chop(2,1), 16);
                                   if (t != null) return t; }
    {DecimalNumeral} [lL]        { Token t = long_lit(chop(), 10);
                                   if (t != null) return t; }
    {OctalNumeral}               { Token t = int_lit(yytext(), 8);
                                   if (t != null) return t; }
    {HexNumeral}                 { Token t = int_lit(chop(2,0), 16);
                                   if (t != null) return t; }
    {DecimalNumeral}             { Token t = int_lit(yytext(), 10);
                                   if (t != null) return t; }

    /* 3.10.2 Floating-Point Literals */
    {FloatingPointLiteral} [fF]  { Token t = float_lit(chop());
                                   if (t != null) return t; }
    {DecimalNumeral} [fF]        { Token t = float_lit(chop());
                                   if (t != null) return t; }
    {FloatingPointLiteral} [dD]  { Token t = double_lit(chop());
                                   if (t != null) return t; }
    {DecimalNumeral} [dD]        { Token t = double_lit(chop());
                                   if (t != null) return t; }
    {FloatingPointLiteral}       { Token t = double_lit(yytext());
                                   if (t != null) return t; }

    /* 3.6 White Space */
    {WhiteSpace}                 { /* ignore */ }
}

<TRADITIONAL_COMMENT> {
    "*/"                         { yybegin(YYINITIAL); }
    .|\n                         { /* ignore */ }
}

<END_OF_LINE_COMMENT> {
    {LineTerminator}             { yybegin(YYINITIAL); }
    .                            { /* ignore */ }
}

<CHARACTER> {
    /* End of the character literal */
    \'                           { yybegin(YYINITIAL);
                                   Token t = char_lit(sb.toString());
                                   if (t != null) return t; }

    /* 3.10.6 Escape Sequences for Character and String Literals */
    "\\b"                        { sb.append('\b'); }
    "\\t"                        { sb.append('\t'); }
    "\\n"                        { sb.append('\n'); }
    "\\f"                        { sb.append('\f'); }
    "\\r"                        { sb.append('\r'); }
    "\\\""                       { sb.append('\"'); }
    "\\'"                        { sb.append('\''); }
    "\\\\"                       { sb.append('\\'); }
    {OctalEscape}                { try {
                                       int x = Integer.parseInt(chop(1,0), 8);
                                       sb.append((char) x);
                                   }
                                   catch (NumberFormatException e) {
                                       eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                                  "Illegal octal escape \""
                                                  + yytext() + "\"", pos());
                                   }
                                 }

    /* Illegal escape character */
    \\.                          { eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Illegal escape character \"" +
                                              yytext() + "\"", pos()); }

    /* Unclosed character literal */
    {LineTerminator}             { yybegin(YYINITIAL);
                                  eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                             "Unclosed character literal",
                                             pos()); }

    /* Anything else is okay */
    [^\r\n\'\\]+                 { sb.append( yytext() ); }
}

<STRING> {
    /* End of string */
    \"                           { yybegin(YYINITIAL);
                                   return string_lit(); }

    /* 3.10.6 Escape Sequences for Character and String Literals */
    "\\b"                        { sb.append( '\b' ); }
    "\\t"                        { sb.append( '\t' ); }
    "\\n"                        { sb.append( '\n' ); }
    "\\f"                        { sb.append( '\f' ); }
    "\\r"                        { sb.append( '\r' ); }
    "\\\""                       { sb.append( '\"' ); }
    "\\'"                        { sb.append( '\'' ); }
    "\\\\"                       { sb.append( '\\' ); }
    {OctalEscape}                { try {
                                       int x = Integer.parseInt(chop(1,0), 8);
                                       sb.append((char) x);
                                   }
                                   catch (NumberFormatException e) {
                                       eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                                  "Illegal octal escape \""
                                                  + yytext() + "\"", pos());
                                   }
                                 }

    /* Illegal escape character */
    \\.                          { eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Illegal escape character \"" +
                                              yytext() + "\"", pos()); }

    /* Unclosed string literal */
    {LineTerminator}             { eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Unclosed string literal",
                                              pos()); }

    /* Anything else is okay */
    [^\r\n\"\\]+                 { sb.append( yytext() ); }
}

/* Fallthrough case: anything not matched above is an error */
.|\n                             { eq.enqueue(ErrorInfo.LEXICAL_ERROR,
                                              "Illegal character \"" +
                                              yytext() + "\"", pos()); }
