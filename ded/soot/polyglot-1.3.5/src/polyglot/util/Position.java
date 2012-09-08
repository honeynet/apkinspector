package polyglot.util;

import java.io.Serializable;

/**
 * This class represents a posiiton within a file.
 **/
public class Position implements Serializable
{
    static final long serialVersionUID = -4588386982624074261L;

    private String file;
    private int line;
    private int column;
    
    private int endLine;
    private int endColumn;

    public static final int UNKNOWN = -1;
    public static final int END_UNUSED = -2;
    public static final Position COMPILER_GENERATED = new Position("Compiler Generated");
    
    /**
     * Get a compiler generated position using the caller at the given stack
     * depth.  Depth 1 is the caller.  Depth 2 is the caller's caller, etc.
     */ 
    public static Position compilerGenerated(int depth) {
        Position p = new Position();
        StackTraceElement[] stack = new Exception().getStackTrace();
        if (depth < stack.length) {
            return new Position(stack[depth].getFileName() + " (compiler generated)", stack[depth].getLineNumber());
        }
        else {
            return COMPILER_GENERATED;
        }
    }

    /** Get a compiler generated position. */ 
    public static Position compilerGenerated() {
        return compilerGenerated(2);
    }

    /** For deserialization. */
    protected Position() { }

    public Position(String file) {
    this(file, UNKNOWN, UNKNOWN);
    }

    public Position(String file, int line) {
    this(file, line, UNKNOWN);
    }

    public Position(String file, int line, int column) {
        this(file, line, column, END_UNUSED, END_UNUSED);
    }
    
    public Position(String file, int line, int column, int endLine, int endColumn) {
        this.file = file;
        this.line = line;
        this.column = column;
        this.endLine = endLine;
        this.endColumn = endColumn;
    }

    public Position(Position start, Position end) {
        this(start.file(), start.line, start.column, end.endLine, end.endColumn);
    }
    
    public int line() {
    return line;
    }

    public int column() {
    return column;
    }

    public int endLine() {
        if (endLine == UNKNOWN || (line != UNKNOWN && endLine < line)) {
            return line;
        }
        return endLine;
    }

    public int endColumn() {
        if (endColumn == UNKNOWN || (column != UNKNOWN && endLine()==line() && endColumn < column)) {
            return column;
        }
        return endColumn;
    }

    public String file() {
    return file;
    }

    public String nameAndLineString() {
    String s = file;

    if (line != UNKNOWN) {
        s += ":" + line;
            if (endLine != line &&
                      endLine != UNKNOWN && endLine != END_UNUSED) {
                s += "-" + endLine;
            }
    }

    return s;
    }

    public String toString() {
        String s = file;

        if (line != UNKNOWN) {
            s += ":" + line;
        
            if (column != UNKNOWN) {
                s += "," + column;
                if (line == endLine && 
                  endColumn != UNKNOWN && endColumn != END_UNUSED) {
                    s += "-" + endColumn;
                }
                if (line != endLine && 
                      endColumn != UNKNOWN && endColumn != END_UNUSED) {
                    s += "-" + endLine + ":" + endColumn;
                }
            }
        }
        
        return s;
    }
}
