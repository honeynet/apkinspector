package polyglot.pth;

import java_cup.runtime.Symbol;

%%
%cup
%public
%class Lexer_c

%{
   StringBuffer string = new StringBuffer();
%}

%eofval{
return (new Symbol(sym.EOF));
%eofval}

LINE_TERMINATOR = [\r]|[\n]|[\r\n]
WHITE_SPACE     = {LINE_TERMINATOR} | [ \t\f]

/* comments */
COMMENT = {C_COMMENT} | {LINE_COMMENT} | {END_OF_LINE_COMMENT}

C_COMMENT            = "/*" ~"*/"
END_OF_LINE_COMMENT  = "//" ~{LINE_TERMINATOR}
LINE_COMMENT         = "#" ~{LINE_TERMINATOR}

IDENT                = [a-zA-Z0-9\:\.\$\/\\]*

%state STRING_LIT

%%
<YYINITIAL> {
   /* identifiers */
   {IDENT}                     { return new Symbol(sym.IDENT, yytext()); }

   /* literals */
   \"                             { string.setLength(0); yybegin(STRING_LIT); }

   /* comments */
   {COMMENT}                      { /* ignore */ }

   /* whitespace */
   {WHITE_SPACE}                  { /* ignore white space. */ }

   /* symbols */
   ";"                            { return new Symbol(sym.SEMICOLON); }
   ","                            { return new Symbol(sym.COMMA); }
   "("                            { return new Symbol(sym.LPAREN); }
   ")"                            { return new Symbol(sym.RPAREN); }
   "["                            { return new Symbol(sym.LBRACK); }
   "]"                            { return new Symbol(sym.RBRACK); }
   "{"                            { return new Symbol(sym.LBRACE); }
   "}"                            { return new Symbol(sym.RBRACE); }
}

<STRING_LIT> {
   \"                             { yybegin(YYINITIAL);
                                    return new Symbol(sym.STRING_LITERAL,
                                    string.toString()); }
   [^\n\r\"\\]+                   { string.append( yytext() ); }
   \\t                            { string.append('\t'); }
   \\n                            { string.append('\n'); }

   \\r                            { string.append('\r'); }
   \\\"                           { string.append('\"'); }
   \\                             { string.append('\\'); }
}

\^Cd   { return new Symbol(sym.EOF); }

/* error fallback */
.|\n                             { throw new Error("Illegal character <"+
                                                     yytext()+">"); }


