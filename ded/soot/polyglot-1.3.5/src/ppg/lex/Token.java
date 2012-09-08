package ppg.lex;

import java.io.*;
import java_cup.runtime.Symbol;
import ppg.parse.*;

public class Token /* extends Symbol */ implements LexerResult {

    private Symbol symbol;
    private String filename;
    private int lineno;
    private Object value;	// String, Boolean, Integer, Vector, null
	//private Position position;
	
	static int lastID;
	
    public Token (String filename, int lineno, Object value/*, Position pos*/) {
		this (-1, filename, lineno, -1, -1, value/*, pos*/);
    }

    public Token (int id, String filename, int lineno, int left, int right,
		  Object value/*, Position pos*/) {
		// super(id, left, right, value);
		
		symbol = new Symbol (id, left, right, this);
		lastID=id;
		this.filename = filename;
		this.lineno = lineno;
		this.value = value;
		//position = pos;
    }

    public int getCode () {
		return symbol.sym; 
	}
	
    public Symbol getSymbol () {
		return symbol;
	}
	
    public Object getValue () {
		return value;
	}

    public String getID () {
		return toString(symbol.sym);
		/*
		switch (symbol.sym) {

			// tokens
			default: break;
		}
		throw new IllegalStateException ("Unknown symbol code: " + symbol.sym);
		*/
	}

	public static String toString (int type) {
		switch (type) {
			case Constant.INCLUDE:				return "INCLUDE";
			case Constant.EXTEND:				return "EXTEND";
			case Constant.DROP:					return "DROP";
			case Constant.OVERRIDE:				return "OVERRIDE";
			case Constant.TRANSFER:				return "TRANSFER";
			case Constant.IMPORT:				return "IMPORT";

			case Constant.COLON_COLON_EQUALS:	return "CCEQ";
			case Constant.SEMI:					return "SEMI";
			case Constant.COMMA:				return "COMMA";
			case Constant.DOT:					return "DOT";
			case Constant.COLON:				return "COLON";

			case Constant.LBRACE:				return "LBRACE";
			case Constant.RBRACE:				return "RBRACE";
			case Constant.LBRACK:				return "LBRACK";
			case Constant.RBRACK:				return "RBRACK";
					
			case Constant.ID:					return "ID";
			case Constant.CODE_STR:				return "CODE_STR";
			case Constant.STRING_CONST:			return "STRING_CONST";
				
			case Constant.WITH:					return "WITH";
			case Constant.PARSER:				return "PARSER";
			case Constant.INIT:					return "INIT";
			case Constant.STAR:					return "STAR";
			case Constant.BAR:					return "BAR";
			case Constant.SCAN:					return "SCAN";
			case Constant.NON:					return "NON";
			case Constant.CODE:					return "CODE";
			case Constant.LEFT:					return "LEFT";
			case Constant.START:				return "START";
			case Constant.NONTERMINAL:			return "NONTERMINAL";
			case Constant.ACTION:				return "ACTION";
			case Constant.TO:					return "TO";
			case Constant.PACKAGE:				return "PACKAGE";
			case Constant.NONASSOC:				return "NONASSOC";
			case Constant.PRECEDENCE:			return "PRECEDENCE";
			case Constant.PERCENT_PREC:			return "PRECEDENCE";
			case Constant.TERMINAL:				return "TERMINAL";
			case Constant.RIGHT:				return "RIGHT";
											
			case Constant.EOF:					return "EOF";
			case Constant.error:				return "ERROR";

			default: {
				System.out.println("Invalid token conversion: "+type);
				System.exit(2);
			}
		}
		return null;
	}

	public String toString () {
		return (String) value;
		//return filename + ":" + lineno + ": \"" + value + "\"";
    }

    public void unparse (java.io.OutputStream o) {
		
		if (value != null) {
			try {
				o.write ((filename + ":" + lineno + ": " + this.getID()+ ": \"" +value + "\"").getBytes());
				//o.write (value.toString ().getBytes ());
			}
			catch (IOException e) {
			}
		}
    }

    public String getFilename () {
		return filename;
	}
	
    public int lineNumber () {
		return lineno;
	}
	
	public int getLineno() {
		return lineno;
	}
	
    public void setLineno (int i) {
		lineno = i;
	}
}
