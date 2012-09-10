/** 
    Modifications Copyright (C) 1999 Raja Vallee-Rai (rvalleerai@sable.mcgill.ca)
    All rights reserved.                                              
   
    Changes:
        - Added \\ to the list of possible escape characters for Strings.
        - March 15, 1999: $ does no longer significant substitution
*/

/* --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
 > File:        jasmin/src/jasmin/Scanner.java
 > Purpose:     Tokenizer for Jasmin
 > Author:      Jonathan Meyer, 10 July 1996
 */

/* Scanner.java - class for tokenizing Jasmin files. This is rather
 * cheap and cheerful.
*/

package jasmin;

import jas.*;
import java_cup.runtime.*;
import java.util.*;
import java.io.InputStream;

class Scanner implements java_cup.runtime.Scanner {
    InputStream inp;

    // single lookahead character
    int next_char;

    // temporary buffer
    char chars[];
    char secondChars[];
    char[] unicodeBuffer;
        
    // true if we have not yet emitted a SEP ('\n') token. This is a bit
    // of a hack so to strip out multiple newlines at the start of the file
    // and replace them with a single SEP token. (for some reason I can't
    // write the CUP grammar to accept multiple newlines at the start of the
    // file)
    boolean is_first_sep;

    // Whitespace characters
    static final String WHITESPACE = " \n\t\r";

    // Separator characters
    static final String SEPARATORS = WHITESPACE + ":=";


    // used for error reporting to print out where an error is on the line
    public int line_num, char_num, token_line_num;
    public StringBuffer line;

    // used by the .set directive to define new variables.
    public Hashtable dict = new Hashtable();

    //
    // returns true if a character code is a whitespace character
    //
    protected static boolean whitespace(int c) {
        return (WHITESPACE.indexOf(c) != -1);
    }

    //
    // returns true if a character code is a separator character
    //
    protected static boolean separator(int c) {
        return (SEPARATORS.indexOf(c) != -1);
    }


    //
    // Advanced the input by one character
    //
    protected void advance() throws java.io.IOException
    {
        next_char = inp.read();
        if (next_char == '\n') {
            // a new line
            line_num++;
            char_num = 0;
            line.setLength(0);
        } else {
            line.append((char)next_char);
            char_num++;
        }
    }

    //
    // initialize the scanner
    //
    final static int BIGNUM=65000;
    public Scanner(InputStream i) throws java.io.IOException
    {
	inp = i;
        line_num = 1;
        char_num = 0;
        line = new StringBuffer();
        chars = new char[BIGNUM];
        secondChars = new char[BIGNUM];
        unicodeBuffer = new char[4];
        is_first_sep = true;
        advance();
    }

    int readOctal(int firstChar) throws java.io.IOException {
        int d1, d2, d3;
        d1 = firstChar;
        advance();
        d2 = next_char;
        advance();
        d3 = next_char;
        return ((d1-'0')&7) * 64 + ((d2-'0')&7) * 8 + ((d3-'0')&7);
    }

    //
    // recognize and return the next complete symbol
    //
    public Symbol next_token()
                throws java.io.IOException, jasError
    {

        token_line_num = line_num;

        for (;;) {
            switch (next_char) {

            case ';':
                // a comment
                do { advance(); } while (next_char != '\n');

            case '\n':
                // return single SEP token (skip multiple newlines
                // interspersed with whitespace or comments)
                for (;;) {
                    do { advance(); } while (whitespace(next_char));
                    if (next_char == ';') {
                        do { advance(); } while (next_char != '\n');
                    } else {
                        break;
                    }
                }
                if (is_first_sep) {
                    return next_token();
                }
                token_line_num = line_num;
                return new Symbol(sym.SEP);

            case '0': case '1': case '2': case '3': case '4':
            case '5': case '6': case '7': case '8': case '9':
            case '-': case '+':
            case '.':                       // a number
                {
                    int pos = 0;

                    // record that we have found first item
                    is_first_sep = false;

                    chars[0] = (char)next_char;
                    pos++;
                    for (;;) {
                        advance();
                        if (separator(next_char)) {
                            break;
                        }
			try {
			  chars[pos] = (char)next_char;
			} catch (ArrayIndexOutOfBoundsException abe) {
			  char[] tmparray = new char[chars.length*2];
			  System.arraycopy(chars, 0,
					   tmparray, 0,
					   chars.length);
			  chars = tmparray;
			  chars[pos] = (char)next_char;			 
			}
                        pos++;
                    }
                    String str = new String(chars, 0, pos);
                    Symbol tok;

                    if(str.equals("+DoubleInfinity"))
                        return new Symbol(sym.Num, new Double(1.0/0.0));
                    
                    if(str.equals("+DoubleNaN"))
                        return new Symbol(sym.Num, new Double(0.0d/0.0));
                    
                    if(str.equals("+FloatNaN"))
                        return new Symbol(sym.Num, new Float(0.0f/0.0));
                        
                    if(str.equals("-DoubleInfinity"))
                        return new Symbol(sym.Num, new Double(-1.0/0.0));
                    
                    if(str.equals("+FloatInfinity"))
                        return new Symbol(sym.Num, new Float(1.0f/0.0f));
                        
                    if(str.equals("-FloatInfinity"))
                        return new Symbol(sym.Num, new Float(-1.0f/0.0f));
                    
                     
                            
                    // This catches directives like ".method"
                    if ((tok = ReservedWords.get(str)) != null) {
                        return tok;
                    }

                    Number num;
                    try {
                        num = ScannerUtils.convertNumber(str);
                    } catch (NumberFormatException e) {
                        if (chars[0] == '.') {
                            throw new jasError("Unknown directive or badly formed number.");
                        } else {
                            throw new jasError("Badly formatted number");
                        }
                    }

                    if (num instanceof Integer) {
                        return new Symbol(sym.Int, new Integer(num.intValue()));
                    } else {
                        return new Symbol(sym.Num, num);
                    }
                }

            case '"':           // quoted strings
                {
                    int pos = 0;

                    is_first_sep = false;

                    for (;;) {
                        advance();
                        if (next_char == '\\') {
                            advance();
                            switch (next_char) {
                            case 'n': next_char = '\n'; break;
                            case 'r': next_char = '\r'; break;
                            case 't': next_char = '\t'; break;
                            case 'f': next_char = '\f'; break;
                            case 'b': next_char = '\b'; break;
                            case 'u': 
                            {                          
                                advance();
                                unicodeBuffer[0] = (char) next_char;
                                advance();
                                unicodeBuffer[1] = (char) next_char;
                                advance();
                                unicodeBuffer[2] = (char) next_char;
                                advance();
                                unicodeBuffer[3] = (char) next_char;
                                
                                // System.out.println(unicodeBuffer[0] + ":" + unicodeBuffer[1] + ":" + unicodeBuffer[2] + ":" + unicodeBuffer[3] + ":");
                                
                                next_char = (char) Integer.parseInt(new String(unicodeBuffer, 0, 4), 16);
                                // System.out.println("value: " + next_char);
                                break;
                            }
                            case '"': next_char = '"'; break;
                            case '\'': next_char = '\''; break;
                            case '\\': next_char = '\\'; break;
                            
                            case '0': case '1': case '2': case '3': case '4':
                            case '5': case '6': case '7':
				next_char = readOctal(next_char);
				break;
                            default:
				throw new jasError("Bad backslash escape sequence");
                            }
                        } else if (next_char == '"') {
                            break;
                        }
			
			try {
			  chars[pos] = (char)next_char;
			} catch (ArrayIndexOutOfBoundsException abe) {
			  char[] tmparray = new char[chars.length*2];
			  System.arraycopy(chars, 0,
					   tmparray, 0,
					   chars.length);
			  chars = tmparray;
			  chars[pos] = (char)next_char;
			}
                        pos++;
                    }
                    advance(); // skip close quote
                    return new Symbol(sym.Str, new String(chars, 0, pos));
                }

            case ' ':
            case '\t':
            case '\r':              // whitespace
                advance();
                break;

            case '=':               // EQUALS token
                advance();
                is_first_sep = false;
                return new Symbol(sym.EQ);

            case ':':               // COLON token
                advance();
                is_first_sep = false;
                return new Symbol(sym.COLON);

            case -1:                // EOF token
                is_first_sep = false;
                char_num = -1;
                line.setLength(0);
                return new Symbol(sym.EOF);

            default:
                {
                    // read up until a separatorcharacter

                    int pos = 0;
                    int secondPos = 0;
                    chars[0] = (char)next_char;
                    is_first_sep = false;

                    pos++;
                    for (;;) {
                        advance();
                        if (separator(next_char)) {
                            break;
                        }
			try {
			  chars[pos] = (char)next_char;
			} catch (ArrayIndexOutOfBoundsException abe) {
			  char[] tmparray = new char[chars.length*2];
			  System.arraycopy(chars, 0,
					   tmparray, 0,
					   chars.length);
			  chars = tmparray;
			  chars[pos] = (char)next_char;
			}
                        pos++;
                    }

                    secondPos = 0;
                    
                    // Parse all the unicode escape sequences
                        for(int i = 0; i < pos; i++)
                        {
			  if(chars[i] == '\\' && (i + 5) < pos &&
			     chars[i+1] == 'u') {
			    int intValue = 
			      Integer.parseInt(new String(chars, i+2, 4), 16);
                          
			    try {
			      secondChars[secondPos] = (char) intValue;
			    } catch (ArrayIndexOutOfBoundsException abe) {
			      char[] tmparray = 
				new char[secondChars.length*2];
			      System.arraycopy(secondChars, 0,
					       tmparray, 0,
					       secondChars.length);
			      secondChars = tmparray;
			      secondChars[secondPos] = (char)intValue;
			    }
			    secondPos++;

			    i += 5;
			  } else {
			    try {
			      secondChars[secondPos] = chars[i];
			    } catch (ArrayIndexOutOfBoundsException abe) {
			      char[] tmparray = 
				new char[secondChars.length*2];
			      System.arraycopy(secondChars, 0,
					       tmparray, 0,
					       secondChars.length);
			      secondChars = tmparray;
			      secondChars[secondPos] = chars[i];
			    }
			    secondPos++;
			  }
                        }
                        
                    // convert the byte array into a String
                    String str = new String(secondChars, 0, secondPos);

                    Symbol tok;
                    if ((tok = ReservedWords.get(str)) != null) {
                        // Jasmin keyword or directive
                        return tok;
                    } else if (InsnInfo.contains(str)) {
                        // its a JVM instruction
                        return new Symbol(sym.Insn, str);
                    } /*else if (str.charAt(0) == '$') {
                        // Perform variable substitution
                        Object v;
                        if ((v = dict.get(str.substring(1))) != null) {
                            return ((Symbol)v);
                        }
                    } */ else {
                        // Unrecognized string token (e.g. a classname)
                        return new Symbol(sym.Word, str);
                    }

                } /* default */
            } /* switch */
        } /* for */
    }

};

/* --- Revision History ---------------------------------------------------
--- Jonathan Meyer, Feb 8 1997
    Converted to be non-static
--- Jonathan Meyer, Oct 30 1996
    Added support for more \ escapes in quoted strings (including octals).
--- Jonathan Meyer, Oct 1 1996
    Added .interface and .implements
--- Jonathan Meyer, July 25 1996
    changed IN to IS. Added token_line_num, which is the line number of the
    last token returned by next_token().
--- Jonathan Meyer, July 24 1996 added mods to recognize '\r' as whitespace.
*/
