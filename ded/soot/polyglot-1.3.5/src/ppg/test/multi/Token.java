package ppg.test.multi;

import java.io.*;
import java_cup.runtime.Symbol;

public class Token /*extends Symbol */implements LexerResult {

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
	
	public Token (int i) {
		this(i, "sdf", -1, -1, -1, null);	
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
	}

	public static String toString (int type) {
		switch (type) {
			case Constant.PLUS:					return "+";
			case Constant.MINUS:				return "-";
			case Constant.EXPR:					return "expr";
			case Constant.NUM:					return "num";
			case Constant.LPAREN:				return "lparen";
			case Constant.RPAREN:				return "rparen";
						
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
