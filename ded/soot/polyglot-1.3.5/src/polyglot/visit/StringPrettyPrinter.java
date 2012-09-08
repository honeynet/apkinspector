package polyglot.visit;

import polyglot.ast.*;
import polyglot.frontend.*;
import polyglot.util.*;

import java.io.*;
import java.util.*;

/**
 * A PrettyPrinter generates output code from the processed AST.
 *
 * To use:
 *     new PrettyPrinter().printAst(node, new CodeWriter(out));
 */
public class StringPrettyPrinter extends PrettyPrinter
{
    int maxdepth;
    int depth;

    public StringPrettyPrinter(int maxdepth) {
        this.maxdepth = maxdepth;
        this.depth = 0;
    }

    public void print(Node parent, Node child, CodeWriter w) {
        depth++;

        if (depth < maxdepth) {
            super.print(parent, child, w);
        }
        else {
            w.write("...");
        }

        depth--;
    }

    public String toString(Node ast) {
        StringCodeWriter w = new StringCodeWriter(new CharArrayWriter());

        print(null, ast, w);

        try {
            w.flush();
        }
        catch (IOException e) {
        }

        return w.toString();
    }

    public static class StringCodeWriter extends CodeWriter {
        CharArrayWriter w;

        public StringCodeWriter(CharArrayWriter w) {
            super(w, 1000);
            this.w = w;
        }

        public void write(String s) {
            StringBuffer sb = new StringBuffer();
            char last = 0;

            // remove duplicate spaces
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (Character.isSpaceChar(c) && Character.isSpaceChar(last))
                    continue;
                sb.append(c);
                last = c;
            }

            super.write(sb.toString());
        }

        public void newline(int n) { super.write(" "); }
        public void allowBreak(int n) { super.write(" "); }
        public void allowBreak(int n, String alt) { super.write(alt); }
        public void begin(int n) { super.begin(0); }

        public String toString() {
            return w.toString();
        }
    }
}
