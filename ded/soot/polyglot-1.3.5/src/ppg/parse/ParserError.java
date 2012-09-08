package ppg.parse;

import java.io.*;
import ppg.lex.*;

/** 
 * This error is thrown when the parser has an internal error -- the user should not see these
 * in the ideal case -- ex: we have a null somewhere.
 * If there is a problem with the source, either a syntaxError or SemanticError will be thrown, 
 * depending on nature of the error
 */
public class ParserError extends Exception
{
	/**
	 * This contains the errorMessage for that caused the exception 
	 */
	protected String errorMessage;
	
	/**
	 * @param message The massage that contains a description of the error	 
	 */
	public ParserError(String message){
		errorMessage = message;	
	}

	/**
	 * @param file The file where the error came from.
	 * @param msg The message that contains a description of the error.
	 * @param tok Token from which to get the line number and the text
         *            of the error token.
	 */
	public ParserError(String file, String msg, Token tok) {
		//errorMessage = file+ ":" +tok.lineNumber() + ": at " +tok.tokenText+ " :" +msg;	
	}

	/**
	 * In rare cases when no error message is know return a generic message	 
	 */
	public ParserError() {
		this("There is a parse error in your code...");	
	}
	
	/**
	 * @return String that is the message of the error  
	 */
	public String getErrorMessage(){
		return errorMessage;
	}
}
