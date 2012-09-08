package polyglot.util;

import java.io.PrintWriter;
import java.io.Writer;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CodeWriter {
    
    public CodeWriter(OutputStream o, int width_) {
        this(new PrintWriter(new OutputStreamWriter(o)), width_);
    }

    public CodeWriter(PrintWriter o, int width_) {
        super();
        output = o;
        width = width_;
        current = (input = new BlockItem(null, 0));
        if (CodeWriter.showInput) {
            trace("new CodeWriter: width = " + width);
        }
    }

    public CodeWriter(Writer o, int width_) {
        this(new PrintWriter(o), width_);
    }
        
    public void write(String s) { if (s.length() > 0) write(s, s.length()); }
        
    public void write(String s, int length) {
        if (CodeWriter.showInput) {
            trace("write \'" + s + "\' (" + length + ")");
        }
        current.add(new TextItem(s, length));
    }

    public void begin(int n) {
        if (CodeWriter.showInput) {
            trace("begin " + n);
            incIndent();
        }
        BlockItem b = new BlockItem(current, n);
        current.add(b);
        current = b;
    }
        
    public void end() {
        if (CodeWriter.showInput) {
            decIndent();
            trace("end");
        }
        current = current.parent;
    }

    public void allowBreak(int n) {
	allowBreak(n, 1, "", 1);
    }

    public void allowBreak(int n, String alt) {
	allowBreak(n, 1, alt, 1);
    }

    public void allowBreak(int n, int level, String alt, int altlen) {
        if (CodeWriter.showInput) {
            trace("allowBreak " + n + " level=" + level);
        }
        current.add(new AllowBreak(n, level, alt, altlen, false));
    }
    
    public void unifiedBreak(int n, int level, String alt, int altlen) {
        if (CodeWriter.showInput) {
            trace("unifiedBreak " + n + " level=" + level);
    }
        current.add(new AllowBreak(n, level, alt, altlen, true));
    }

    public void newline() { newline(0, 1); }

    public void newline(int n, int level) {
        if (CodeWriter.showInput) { trace("newline " + n); }
        current.add(new Newline(n, level));
    }
    public void newline(int n) {
	newline(n, 1);
    }

    public boolean flush() throws IOException { return flush(true); }

    public boolean flush(boolean format) throws IOException {
        if (CodeWriter.showInput) { trace("flush"); }
	boolean success = true;
	format_calls = 0;
	if (format) {
	    try {
	        top = input;
	        Item.format(input, 0, 0, width, width,
                            new MaxLevels(Integer.MAX_VALUE, Integer.MAX_VALUE),
                            0, 0);
            }
            catch (Overrun o) { success = false; }
	} else success = false;
        input.sendOutput(output, 0, 0, success, null);
        output.flush();
        if (CodeWriter.debug) {
            System.err.println("Total calls to format = " + format_calls);
            System.err.flush();
        }
        current = (input = new BlockItem(null, 0));
	return success;
    }
    
    public void close() throws IOException {
        flush();
        output.close();
    }

    public String toString() { return input.toString(); }
    
    protected BlockItem input;
    protected BlockItem current;
    protected static Item top;
    protected PrintWriter output;
    protected int width;
    protected static int format_calls = 0;
    final public static boolean debug = false;
    final public static boolean showInput = false;
    final public static boolean visualize = false;
    final public static boolean precompute = true;
    protected int trace_indent = 0;
    
    void incIndent() { trace_indent++; }
    
    void decIndent() {
        trace_indent--;
        if (trace_indent < 0) throw new RuntimeException("unmatched end");
    }

    void trace(String s) {
        for (int i = 0; i < trace_indent; i++) System.out.print(" ");
        System.out.println(s);
    }
}

class Overrun extends Exception {
    int amount;
    int type;
    final static int POS = 0;
    final static int WIDTH = 1;
    final static int FIN = 2;
    final private static Overrun overrun = new Overrun();

    private Overrun() { super(); }

    static Overrun overrun(Item it, MaxLevels m, int amount, int type) {
        if (CodeWriter.debug)
            System.err.println("-- Overrun: " + amount);
        if (CodeWriter.visualize) {
            System.err.print("\033[H\033[2J");
            PrintWriter w = new PrintWriter(new OutputStreamWriter(System.err));
            try {
                CodeWriter.top.sendOutput(w, 0, 0, true, it);
            }
            catch (IOException e) {  }
            w.flush();
            System.err.println();
            String type_name;
            switch (type) {
                default:
                case POS:
                    type_name = "pos";
                    break;
                case WIDTH:
                    type_name = "width";
                    break;
                case FIN:
                    type_name = "fin";
                    break;
            }
            System.err.println("  overrun: type " + type_name + " amount: " +
                               amount);
            System.err.println("  next item is " + it);
            System.err.println("  minPosWidth" + m + " of next item = " +
                               Item.getMinPosWidth(it, m));
            System.err.println("  minWidth" + m + " of next item = " +
                               Item.getMinWidth(it, m));
            System.err.println("  minIndent" + m + " of next item = " +
                               Item.getMinIndent(it, m));
            System.err.println("  containsBreaks" + m + " of next item = " +
                               Item.containsBreaks(it, m));
            try {
                System.in.read();
            }
            catch (IOException e) {  }
        }
        overrun.amount = amount;
        overrun.type = type;
        return overrun;
    }
}

abstract class Item {
    Item next;

    protected Item() {
        super();
        next = null;
    }

    abstract FormatResult formatN(int lmargin, int pos, int rmargin, int fin,
                                  MaxLevels m, int minLevel,
                                  int minLevelUnified)
    	throws Overrun;
    
    abstract int sendOutput(PrintWriter o, int lmargin, int pos,
                            boolean success, Item last)
      throws IOException;
    
    static FormatResult format(Item it, int lmargin, int pos, int rmargin,
                               int fin, MaxLevels m, int minLevel,
                               int minLevelUnified)
          throws Overrun {
        CodeWriter.format_calls++;
        if (CodeWriter.debug) {
            if (it != null && it != CodeWriter.top) {
	        System.err.println("SNAPSHOT:");
                PrintWriter w =
                  new PrintWriter(new OutputStreamWriter(System.err));
	        try {	            	            
                    CodeWriter.top.sendOutput(w, 0, 0, true, it);
	        }
	        catch (IOException e) {  }
	        w.write("<END>\n");
	        w.flush();
	    }
            System.err.println("Format: " + it + "\n  lmargin = " + lmargin +
                               " pos = " + pos + " fin = " + fin +
		" max break levels: " + m +
                               " min break levels: " + minLevel + "/" +
                               minLevelUnified);
            if (CodeWriter.debug) {
	        System.err.println("  MinWidth = " + getMinWidth(it, m));
	        System.err.println("  MinPosWidth = " + getMinPosWidth(it, m));
	        System.err.println("  MinIndent = " + getMinIndent(it, m));
	    }
	    System.err.flush();
	}
        if (it == null) {
	    if (pos > fin) {
                if (CodeWriter.debug)
                    System.err.println("Final position overrun: " +
                                       (pos - fin));
                throw Overrun.overrun(it, m, pos - fin, Overrun.FIN);
            } else return new FormatResult(pos, minLevelUnified);
	    }
        int amount2 = lmargin + getMinWidth(it, m) - rmargin;
	if (amount2 > 0) {
            if (CodeWriter.debug)
	        System.err.println("Width overrun: " + amount2);
            throw Overrun.overrun(it, m, amount2, Overrun.WIDTH);
	}
        int amount = pos + getMinPosWidth(it, m) - rmargin;
	if (amount > 0) {
            if (CodeWriter.debug)
	        System.err.println("Position (first line) overrun: " + amount);
            throw Overrun.overrun(it, m, amount, Overrun.POS);
	}
        int amount3 = lmargin + getMinIndent(it, m) - fin;
	if (amount3 > 0) {
            if (CodeWriter.debug)
                System.err.println("Final position (predicted) overrun: " +
                                   amount3);
            throw Overrun.overrun(it, m, amount3, Overrun.FIN);
	}	
        return it.formatN(lmargin, pos, rmargin, fin, m, minLevel,
                          minLevelUnified);
    }

    final public static int NO_WIDTH = -9999;
    final public static int NEWLINE_VIOLATION = 9999;
    Map min_widths = new HashMap();
    Map min_indents = new HashMap();
    Map min_pos_width = new HashMap();
    
    static int getMinWidth(Item it, MaxLevels m) {
	if (it == null) return NO_WIDTH;
	if (it.min_widths.containsKey(m))
	    return ((Integer)it.min_widths.get(m)).intValue();
	int p1 = it.selfMinWidth(m);	  
	int p2 = it.selfMinIndent(m);	
        int p3 = p2 != NO_WIDTH ? getMinPosWidth(it.next, m) + p2 : NO_WIDTH;
	int p4 = getMinWidth(it.next, m);
        if (CodeWriter.debug)
            System.err.println("minwidth" + m + ": item = " + it + ":  p1 = " +
                               p1 + ", p2 = " + p2 + ", p3 = " + p3 +
                               ", p4 = " + p4);
	int result = Math.max(Math.max(p1, p3), p4);
	it.min_widths.put(m, new Integer(result));
	return result;
    }

    static int getMinPosWidth(Item it, MaxLevels m) {
	if (it == null) return 0;
	if (it.min_pos_width.containsKey(m)) {
	    return ((Integer)it.min_pos_width.get(m)).intValue();
	}
	int p1 = it.selfMinPosWidth(m);
	int result;
	if (it.next == null || it.selfContainsBreaks(m)) {
	    result = p1;
            if (CodeWriter.debug)
                System.err.println("minpos " + m + ": item = " + it +
                                   ":  p1 = " + p1);
	} else {
	    result = p1 + getMinPosWidth(it.next, m);
            if (CodeWriter.debug)
                System.err.println("minpos " + m + ": item = " + it +
                                   ":  p1 = " + p1 + " + " +
                                   getMinPosWidth(it.next, m) + " = " + result);
	}
	it.min_pos_width.put(m, new Integer(result));
	return result;
    }

    static int getMinIndent(Item it, MaxLevels m) {
	if (it == null) return NO_WIDTH;
	if (it.min_indents.containsKey(m)) {
	    return ((Integer)it.min_indents.get(m)).intValue();
	}
	int p1 = it.selfMinIndent(m);
	if (it.next == null) return p1;
	int result;
        if (containsBreaks(it.next, m)) result = getMinIndent(it.next, m);
        else result = getMinPosWidth(it.next, m);
	it.min_indents.put(m, new Integer(result));
	return result;
    }

    static boolean containsBreaks(Item it, MaxLevels m) {
	if (it == null) return false;
	if (it.selfContainsBreaks(m)) {
            if (CodeWriter.debug)
                System.err.println("containsBreaks " + m + " of " + it +
                                   ": true");
	    return true;
	}
        if (it.next == null) {
            if (CodeWriter.debug)
                System.err.println("containsBreaks " + m + " of " + it +
                                   ": false");
            return false;
        }
        return containsBreaks(it.next, m);
    }

    public String summarize(String s) {
	if (s.length() <= 79) return s;
	return s.substring(0, 76) + "...";
    }

    public String toString() {
	if (next == null) return summarize(selfToString());
	return summarize(selfToString() + next.toString());
    }

    abstract String selfToString();
    
    abstract int selfMinIndent(MaxLevels m);
    
    abstract int selfMinWidth(MaxLevels m);
    
    abstract int selfMinPosWidth(MaxLevels m);
    
    abstract boolean selfContainsBreaks(MaxLevels m);
}

class TextItem extends Item {
    String s;
    int length;    
    
    TextItem(String s_, int length_) {
        super();
        s = s_;
        length = length_;
    }
        
    FormatResult formatN(int lmargin, int pos, int rmargin, int fin,
            MaxLevels m, int minLevel, int minLevelUnified)
      throws Overrun {
        return format(next, lmargin, pos + length, rmargin, fin, m, minLevel,
                      minLevelUnified);
    }
    
    int sendOutput(PrintWriter o, int lm, int pos, boolean success, Item last)
          throws IOException {
        o.write(s);
        return pos + length;
    }
    
    boolean selfContainsBreaks(MaxLevels m) { return false; }
    
    int selfMinIndent(MaxLevels m) { return NO_WIDTH; }
    
    int selfMinWidth(MaxLevels m) { return NO_WIDTH; }
    
    int selfMinPosWidth(MaxLevels m) { return length; }
    
    String selfToString() {
	java.io.StringWriter sw = new java.io.StringWriter();
	for (int i = 0; i < s.length(); i++) {
	    char c = s.charAt(i);
            if (c == ' ') sw.write("\\ "); else sw.write(c);
	}
	return sw.toString();
    }

    public void appendTextItem(TextItem item) {
        s += item.s;
        length += item.length;       
    }
}

class AllowBreak extends Item {
    final int indent;
    final int level;
    final boolean unified;
    final String alt;
    final int altlen;
    boolean broken = false;
        
    AllowBreak(int n_, int level_, String alt_, int altlen_, boolean u) {
        super();
        indent = n_;
        alt = alt_;
        altlen = altlen_;
        level = level_;
        unified = u;
    }
        
    FormatResult formatN(int lmargin, int pos, int rmargin, int fin,
                         MaxLevels m, int minLevel, int minLevelUnified)
          throws Overrun {
        if (canLeaveUnbroken(minLevel, minLevelUnified)) {
            try {
                if (CodeWriter.debug)
                    System.err.println("trying not breaking it.");
                broken = false;
                return format(next,
                              lmargin,
                              pos +
                                altlen,
                              rmargin,
                              fin,
                              new MaxLevels(Math.min(unified
                                                       ? level - 1
                                                       : level, m.maxLevel),
                                            Math.min(level - 1,
                                                     m.maxLevelInner)),
                              minLevel,
                              minLevelUnified);
            }
            catch (Overrun o) {
                if (CodeWriter.debug) {
                    System.err.println("not breaking caused overrun of " +
                                       o.amount);
                }
                if (level > m.maxLevel) {
                    if (CodeWriter.debug) {
                        System.err.println("not breaking failed, " +
                                           "but can\'t break either.");
                    }
                    throw o;
                }
            }
        }
        if (canBreak(m)) {
            if (CodeWriter.debug)
                System.err.println("trying breaking at " + this);
            broken = true;
            try {
                return format(next, lmargin, lmargin + indent, rmargin, fin, m,
                        Math.max(level-1, minLevel),
                        Math.max(level, minLevelUnified));
            }
            catch (Overrun o) {
                o.type = Overrun.WIDTH;
                throw o;
            }
        }
        throw new IllegalArgumentException(
          "internal error: could not either break or not break");
    }
        
    int sendOutput(PrintWriter o, int lmargin, int pos, boolean success,
                   Item last)
        throws IOException {
        if (broken || !success) {
            o.println();
            for (int i = 0; i < lmargin + indent; i++) o.print(" ");
            return lmargin + indent;
        } else {
	    o.print(alt);
            return pos + altlen;
        }
    }
    
    boolean canBreak(MaxLevels m) { return level <= m.maxLevel; }
    
    boolean canLeaveUnbroken(int minLevel, int minLevelUnified) {
        return level > minLevelUnified || !unified && level > minLevel;
    }
    
    int selfMinIndent(MaxLevels m) {
        if (canBreak(m)) return indent; else return NO_WIDTH;
    }
    
    int selfMinPosWidth(MaxLevels m) {
        if (canBreak(m)) return 0; else return altlen;
    }
    
    int selfMinWidth(MaxLevels m) {
        if (canBreak(m)) return indent; else return NO_WIDTH;
    }
    
    boolean selfContainsBreaks(MaxLevels m) { return canBreak(m); }
    
    String selfToString() {
        if (indent == 0) return " "; else return "^" + indent;
    }
}

class Newline extends AllowBreak {
    
    Newline(int n) { this(n, 1); }
    
    Newline(int n, int level) {
        super(n, level, "\n", 0, true);
        broken = true;
    }

    boolean canLeaveUnbroken() { return false; }
    
    String selfToString() {
        if (indent == 0) return "\\n"; else return "\\n[" + indent + "]";
    }
    
    int sendOutput(PrintWriter o, int lmargin, int pos, boolean success,
                   Item last)
          throws IOException {
        broken = true;
        return super.sendOutput(o, lmargin, pos, success, last);
    }
    
    int selfMinIndent(MaxLevels m) {
        if (canBreak(m)) return indent; else return NEWLINE_VIOLATION;
    }
    
    int selfMinPosWidth(MaxLevels m) {
        if (canBreak(m)) return 0; else return NEWLINE_VIOLATION;
    }
    
    int selfMinWidth(MaxLevels m) {
        if (canBreak(m)) return indent; else return NEWLINE_VIOLATION;
    }
}

class BlockItem extends Item {
    BlockItem parent;
    Item first;
    Item last;
    int indent;
        
    BlockItem(BlockItem parent_, int indent_) {
        super();
        parent = parent_;
        first = (last = null);
        indent = indent_;
    }

    void add(Item it) {
        if (first == null) {
	    first = it;
	} else {
	    if (it instanceof TextItem && last instanceof TextItem) {
		TextItem lasts = (TextItem)last;
                lasts.appendTextItem((TextItem) it);
		return;
	    } else {
		last.next = it;
	    }
	}
        last = it;
    }

    FormatResult formatN(int lmargin, int pos, int rmargin, int fin,
                         MaxLevels m, int minLevel, int minLevelUnified)
          throws Overrun {
        int childfin = fin;
        if (childfin + getMinPosWidth(next, m) > rmargin) {
            childfin = rmargin - getMinPosWidth(next, m);
        }
        while (true) {
            FormatResult fr =
              format(first, pos + indent, pos, rmargin, childfin,
                    new MaxLevels(m.maxLevelInner, m.maxLevelInner), 0, 0);
            int minLevel2 = Math.max(minLevel, fr.minLevel);
            int minLevelU2 = Math.max(minLevelUnified, fr.minLevel);
            try {
                return format(next, lmargin, fr.pos, rmargin, fin, m, minLevel2,
                              minLevelU2);
            }
            catch (Overrun o) {
                if (o.type == Overrun.WIDTH) {
                    o.type = Overrun.FIN;
                    throw o;
                }
                childfin -= o.amount;
            }          
        }
    }

    int sendOutput(PrintWriter o, int lmargin, int pos, boolean success,
                   Item last)
          throws IOException {
        Item it = first;
        lmargin = pos + indent;
        if (last != this) {
        while (it != null) {
            pos = it.sendOutput(o, lmargin, pos, success, last);
                if (last != null && it == last) { throw new IOException(); }
            it = it.next;
        }
        } else {
            o.print("...");
        }
        return pos;
    }

    int selfMinWidth(MaxLevels m) {
        return getMinWidth(first,
                new MaxLevels(m.maxLevelInner, m.maxLevelInner));
    }
    
    int selfMinPosWidth(MaxLevels m) {
        return getMinPosWidth(first,
                new MaxLevels(m.maxLevelInner, m.maxLevelInner));
    }
    
    int selfMinIndent(MaxLevels m) {
        return getMinIndent(first,
                new MaxLevels(m.maxLevelInner, m.maxLevelInner));
    }
    
    Map containsBreaks = new HashMap();
    
    boolean selfContainsBreaks(MaxLevels m) {
	if (containsBreaks.containsKey(m)) {
            return containsBreaks.get(m) != null;
	}
        boolean result =
          containsBreaks(first,
                         new MaxLevels(m.maxLevelInner, m.maxLevelInner));
	containsBreaks.put(m, result ? m : null);
	return result;
    }
    
    String selfToString() {
	if (indent == 0) return "[" + first + "]";
	else return "[" + indent + first + "]";
    }
}

class FormatResult {
    int pos;
    int minLevel;
    
    FormatResult(int pos_, int minLevel_) {
        super();
        pos = pos_;
        minLevel = minLevel_;
    }
}

class MaxLevels {
    int maxLevel;
    int maxLevelInner;
    
    MaxLevels(int ml, int mli) {
        super();
        maxLevel = ml;
        maxLevelInner = mli;
    }
    
    public int hashCode() { return maxLevel * 17 + maxLevelInner; }
    
    public boolean equals(Object o) {
        if (o instanceof MaxLevels) {
            MaxLevels m2 = (MaxLevels)o;
            return maxLevel == m2.maxLevel && maxLevelInner == m2.maxLevelInner;
        } else return false;
    }
    
    public String toString() {
        return "[" + maxLevel + "/" + maxLevelInner + "]";
    }
}
