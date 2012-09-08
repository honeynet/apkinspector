package ppg.test.multi;

import java.io.*;

public class LexicalError extends Exception implements LexerResult {
	private String filename;
	private int lineNumber;
	private String message;
	
	public LexicalError(String filename, int lineNumber, String message) {
		this.message = message;
		//super(message);
		this.filename = filename;
		this.lineNumber = lineNumber;
	}

	public void unparse(OutputStream o) throws IOException { 
		o.write(this.toString().getBytes());
	}
	
	public String toString() {
		return filename + "(" + lineNumber + ") : Lexical error : " + message;
	}

	public String filename() {
		return filename;
	}

	public String getMessage(){
		return toString();
	}
	
	public int lineNumber() { 
		return lineNumber; 
	}
}
