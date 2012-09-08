package ppg.test.multi;

import java.io.InputStream;

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

%class Lexer_c
%type Token
%function getToken
%public
%char
%line
%state COMMENT
%state STRING
%state CODE

digits = 0|[1-9][0-9]*
white_space_char = [\ \t\n\r\f]
whitespace = {white_space_char}+
slashcomment = "//".*

%%
<YYINITIAL> "+"				{ return t(Constant.PLUS); }
<YYINITIAL> "-"				{ return t(Constant.MINUS); }
<YYINITIAL> "("				{ return t(Constant.LPAREN); }
<YYINITIAL> ")"				{ return t(Constant.RPAREN); }
<YYINITIAL> {digits}		{ return t(Constant.NUM, Integer.valueOf(yytext())); }
					    
<YYINITIAL> {slashcomment}	{}
<YYINITIAL> {whitespace}	{}

<YYINITIAL> .		{ 
	error("Invalid character: " + yytext());
}
