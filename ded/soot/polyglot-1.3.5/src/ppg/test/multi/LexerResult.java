package ppg.test.multi;

import java.io.*;

/**
 * The main interface for the return of Lexer output
 */
interface LexerResult {
	/**
	 * Displays the parsed token in human-readable form.
	 * The token has the form &lt;token-type, attribute, line-number&gt;
	 * @param o The OutputStream onto which to print the token
	 */
    void unparse(OutputStream o) throws IOException;
        // Print a human-readable representation of this token on the
        // output stream o; one that contains all the relevant information
        // associated with the token. The representation has the form
        // <token-type, attribute, line-number>
	/**
	 * @return line number on which the token was found
	 */
    int lineNumber();
        // Return the number of the line that this token came from.
}

