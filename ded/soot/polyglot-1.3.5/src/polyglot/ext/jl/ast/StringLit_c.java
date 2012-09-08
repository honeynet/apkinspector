package polyglot.ext.jl.ast;

import polyglot.ast.*;
import polyglot.types.*;
import polyglot.visit.*;
import polyglot.util.*;
import java.util.*;
import polyglot.main.Options;

/** 
 * A <code>StringLit</code> represents an immutable instance of a 
 * <code>String</code> which corresponds to a literal string in Java code.
 */
public class StringLit_c extends Lit_c implements StringLit
{
    protected String value;

    public StringLit_c(Position pos, String value) {
	super(pos);
	this.value = value;
    }

    /** Get the value of the expression. */
    public String value() {
	return this.value;
    }

    /** Set the value of the expression. */
    public StringLit value(String value) {
	StringLit_c n = (StringLit_c) copy();
	n.value = value;
	return n;
    }

    /** Type check the expression. */
    public Node typeCheck(TypeChecker tc) throws SemanticException {
        return type(tc.typeSystem().String());
    }

    public String toString() {
        if (StringUtil.unicodeEscape(value).length() > 11) {
            return "\"" + StringUtil.unicodeEscape(value).substring(0,8) + "...\"";
        }
                
	return "\"" + StringUtil.unicodeEscape(value) + "\"";
    }

    protected int MAX_LENGTH = 60;
 
    /** Write the expression to an output file. */
    public void prettyPrint(CodeWriter w, PrettyPrinter tr) {
        List l = breakupString();
        
        // If we break up the string, parenthesize it to avoid precedence bugs.
        if (l.size() > 1) {
            w.write("(");
        }

        for (Iterator i = l.iterator(); i.hasNext(); ) {
            String s = (String) i.next();
            w.begin(0);
        }

        for (Iterator i = l.iterator(); i.hasNext(); ) {
            String s = (String) i.next();

            w.write("\"");
            w.write(StringUtil.escape(s));
            w.write("\"");
            w.end();

            if (i.hasNext()) {
                w.write(" +");
                w.allowBreak(0, " ");
            }
        }

        if (l.size() > 1) {
            w.write(")");
        }
    }

    /**
     * Break a long string literal into a concatenation of small string
     * literals.  This avoids messing up the pretty printer and editors.
     * 
     * ak333: made public to allow CppStringLitDel to get to it. 
     */
    public List breakupString() {
        List result = new LinkedList();
        int n = value.length();
        int i = 0;

        while (i < n) {
            int j;

            // Compensate for the unicode transformation by computing
            // the length of the encoded string.
            int len = 0;

            for (j = i; j < n; j++) {
                char c = value.charAt(j);
                int k = StringUtil.unicodeEscape(c).length();
                if (len + k > MAX_LENGTH) break;
                len += k;
            }

            result.add(value.substring(i, j));

            i = j;
        }

        if (result.isEmpty()) {
            // This should only happen when value == "".
            if (! value.equals("")) {
                throw new InternalCompilerError("breakupString failed");
            }
            result.add(value);
        }

        return result;
    }
    
    public Object constantValue() {
	return value;
    }
}
