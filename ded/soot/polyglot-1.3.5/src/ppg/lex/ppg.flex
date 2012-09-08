package ppg.lex;

import java.io.InputStream;
import ppg.parse.*;

%%

%init{
    lineSeparator = System.getProperty("line.separator", "\n");
%init}

%{

    private int lastId = -1;
    private String filename = "";
    private String lineSeparator;
/*
    private Position pos() {
        return new Position(filename, yyline+1, yycolumn);
    }
*/
    public Lexer(InputStream in, String filename) {
        this(in);
        this.filename = filename;
    }

    private void error(String message) throws LexicalError {
        throw new LexicalError(filename, yyline+1, message);
    }

    private Token t(int id, Object value) {
        lastId = id;
        return new Token(id, filename, yyline + 1, yychar, yychar + yylength(), value);
    }

    private Token t(int id) {
        return t(id, yytext());
    }

%}

%eofval{
    return t(Constant.EOF, "EOF");
    //return Constant.EOF;
%eofval}

%yylexthrow{
    LexicalError
%yylexthrow}

%class Lexer
%type Token
%function getToken
%public
%char
%line
%state COMMENT
%state STRING
%state CODE

letter = [A-Za-z]
identifier = {letter}({letter}|[0-9_])*
white_space_char = [\ \t\n\r\f]
whitespace = {white_space_char}+
slashcomment = "//".*
blockcomment=[^*]*"*"("*"|[^/*][^*]*"*")*"/"
code_block=[^:]*:(:|[^"}":][^:]*:)*"}"
PC=[\040-\041\043-\133\135-\176]
ES=\\(N|n|t|"^"[\100-\176]|[0-9][0-9][0-9]|\"|\\|{whitespace}\\)
string_lit=({PC}|{ES})*
string_lit_quote={string_lit}\"
string_lit_slash={string_lit}\\

%%
<YYINITIAL> "include"       { return t(Constant.INCLUDE); }
<YYINITIAL> "extend"        { return t(Constant.EXTEND); }
<YYINITIAL> "drop"          { return t(Constant.DROP); }
<YYINITIAL> "override"      { return t(Constant.OVERRIDE); }
<YYINITIAL> "transfer"      { return t(Constant.TRANSFER); }
<YYINITIAL> "to"            { return t(Constant.TO); }

<YYINITIAL> "package"       { return t(Constant.PACKAGE); }
<YYINITIAL> "import"        { return t(Constant.IMPORT); }
<YYINITIAL> "code"          { return t(Constant.CODE); }
<YYINITIAL> "action"        { return t(Constant.ACTION); }
<YYINITIAL> "parser"        { return t(Constant.PARSER); }
<YYINITIAL> "init"          { return t(Constant.INIT); }
<YYINITIAL> "scan"          { return t(Constant.SCAN); }
<YYINITIAL> "with"          { return t(Constant.WITH); }
<YYINITIAL> "start"         { return t(Constant.START); }
<YYINITIAL> "precedence"    { return t(Constant.PRECEDENCE); }
<YYINITIAL> "left"          { return t(Constant.LEFT); }
<YYINITIAL> "right"         { return t(Constant.RIGHT); }
<YYINITIAL> "nonassoc"      { return t(Constant.NONASSOC); }
<YYINITIAL> "terminal"      { return t(Constant.TERMINAL); }
<YYINITIAL> "non"           { return t(Constant.NON); }
<YYINITIAL> "nonterminal"   { return t(Constant.NONTERMINAL); }
<YYINITIAL> "extends"       { return t(Constant.EXTENDS); }
<YYINITIAL> "implements"    { return t(Constant.IMPLEMENTS); }

<YYINITIAL> "::="           { return t(Constant.COLON_COLON_EQUALS); }
<YYINITIAL> ";"             { return t(Constant.SEMI); }
<YYINITIAL> "."             { return t(Constant.DOT); }
<YYINITIAL> ","             { return t(Constant.COMMA); }
<YYINITIAL> "{"             { return t(Constant.LBRACE); }
<YYINITIAL> "}"             { return t(Constant.RBRACE); }
<YYINITIAL> "["             { return t(Constant.LBRACK); }
<YYINITIAL> "]"             { return t(Constant.RBRACK); }
<YYINITIAL> "|"             { return t(Constant.BAR); }
<YYINITIAL> ":"             { return t(Constant.COLON); }
<YYINITIAL> "*"             { return t(Constant.STAR); }
<YYINITIAL> {identifier}    { return t(Constant.ID, yytext().intern()); }
<YYINITIAL> "%prec"         { return t(Constant.PERCENT_PREC); }
                        
<YYINITIAL> \"      {yybegin(STRING);}

<STRING> {string_lit_quote} { 
    yybegin(YYINITIAL);
    String literal = yytext();
    return t(Constant.STRING_CONST, literal.substring(0, literal.length()-1));
}

<STRING> {string_lit} {
    error("Unclosed string literal");
}

<STRING> {string_lit_slash} {
    error("Illegal escape character");
}

<STRING> . {
    error("Illegal character in string literal: " + yytext());
}

<YYINITIAL> {slashcomment}  {}
<YYINITIAL> {whitespace}    {}
<YYINITIAL> "/*"    { yybegin (COMMENT); }

<COMMENT> {blockcomment} { yybegin (YYINITIAL); }
<COMMENT> . {
    error("Illegal comment");
}

<YYINITIAL> "{:"    { yybegin (CODE); }

<CODE> {code_block} {
    yybegin(YYINITIAL);
    String codeStr = yytext();
    // cut off ":}" from the end of the code string
    return t(Constant.CODE_STR, codeStr.substring(0, codeStr.length()-2));
}
<CODE> . {
    error("Invalid character in code block: '" + yytext() + "'");
}

<YYINITIAL> .       { 
    error("Invalid character: " + yytext());
}
