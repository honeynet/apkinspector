package polyglot.util;

import java.util.StringTokenizer;
import java.util.NoSuchElementException;

/**
 * A string tokenizer that understands quotes and escape characters.
 * @author Igor Peshansky, IBM Corporation
 */
public class QuotedStringTokenizer extends StringTokenizer {
    /* Have to keep copies because StringTokenizer makes everything private */
    protected final String str;
    protected String delim;
    protected final String quotes;
    protected final char escape;
    protected final boolean returnDelims;
    protected int pos = 0;
    protected final int len;

    /**
     * Constructs a string tokenizer for the specified string.
     * The default delimiters for StringTokenizer are used.
     * "\"\'" are used as quotes, and '\\' is used as the escape character.
     */
    public QuotedStringTokenizer(String str) {
        this(str, " \t\n\r\f", "\"\'", '\\', false);
    }

    /**
     * Constructs a string tokenizer for the specified string.
     * "\"\'" are used as quotes, and '\\' is used as the escape character.
     */
    public QuotedStringTokenizer(String str, String delim) {
        this(str, delim, "\"\'", '\\', false);
    }

    /**
     * Constructs a string tokenizer for the specified string.
     * Quotes cannot be delimiters, and the escape character can be neither a
     * quote nor a delimiter.
     */
    public QuotedStringTokenizer(String str, String delim, String quotes, char escape, boolean returnDelims) {
        super(str, delim, returnDelims);
        this.str = str;
        this.len = str.length();
        this.delim = delim;
        this.quotes = quotes;
        for (int i = 0; i < quotes.length(); i++)
            if (delim.indexOf(quotes.charAt(i)) >= 0)
                throw new IllegalArgumentException("Invalid quote character '"+quotes.charAt(i)+"'");
        this.escape = escape;
        if (delim.indexOf(escape) >= 0)
            throw new IllegalArgumentException("Invalid escape character '"+escape+"'");
        if (quotes.indexOf(escape) >= 0)
            throw new IllegalArgumentException("Invalid escape character '"+escape+"'");
        this.returnDelims = returnDelims;
    }

    /**
     * Returns the position of the next non-delimiter character.
     * Pre-condition: not inside a quoted string (token).
     */
    private int skipDelim(int pos) {
        while (pos < len && delim.indexOf(str.charAt(pos)) >= 0)
            pos++;
        return pos;
    }
 
    private StringBuffer token;
    /**
     * Returns the position of the next delimiter character after the token.
     * If collect is true, collects the token into the StringBuffer.
     * Pre-condition: not on a delimiter.
     */
    private int skipToken(int pos, boolean collect) {
        if (collect)
            token = new StringBuffer();
        boolean quoted = false;
        char quote = '\000';
        boolean escaped = false;
        for (; pos < len; pos++) {
            char curr = str.charAt(pos);
            if (escaped) {
                escaped = false;
                if (collect) token.append(curr);
                continue;
            }
            if (curr == escape) { // escape character
                escaped = true;
                continue;
            }
            if (quoted) {
                if (curr == quote) { // closing quote
                    quoted = false;
                    quote = '\000';
                } else
                    if (collect) token.append(curr);
                continue;
            }
            if (quotes.indexOf(curr) >= 0) { // opening quote
                quoted = true;
                quote = curr;
                continue;
            }
            if (delim.indexOf(str.charAt(pos)) >= 0) // unquoted delimiter
                break;
            if (collect) token.append(curr);
        }
        if (escaped || quoted)
            throw new IllegalArgumentException("Unterminated quoted string");
        return pos;
    }
 
    /**
     * Tests if there are more tokens available from this tokenizer's string.
     * Pre-condition: not inside a quoted string (token).
     */
    public boolean hasMoreTokens() {
        if (!returnDelims) {
            pos = skipDelim(pos);
        }
        return (pos < len);
    }

    /**
     * Returns the next token from this string tokenizer.
     */
    public String nextToken() {
        if (!returnDelims)
            pos = skipDelim(pos);
        if (pos >= len)
            throw new NoSuchElementException();
        if (returnDelims && delim.indexOf(str.charAt(pos)) >= 0)
            return str.substring(pos, ++pos);
        //int start = pos;
        pos = skipToken(pos, true);
        return token.toString();
        //return str.substring(start, pos);
    }

    /**
     * Returns the next token in this string tokenizer's string.
     */
    public String nextToken(String delim) {
        this.delim = delim;
        return nextToken();
    }

    /**
     * Calculates the number of times that this tokenizer's nextToken method
     * can be called before it generates an exception.
     */
    public int countTokens() {
        int count = 0;
        int dcount = 0;
        int curr = pos;
        while (curr < len) {
            if (delim.indexOf(str.charAt(curr)) >= 0) {
                curr++;
                dcount++;
            } else {
                curr = skipToken(curr, false);
                count++;
            }
        }
        if (returnDelims)
            return count + dcount;
        return count;
    }

    /**
     * Returns the same value as the hasMoreTokens method.
     */
    public boolean hasMoreElements() { return hasMoreTokens(); }

    /**
     * Returns the same value as the nextToken method, except that its declared
     * return value is Object rather than String.
     */
    public Object nextElement() { return nextToken(); }
}

