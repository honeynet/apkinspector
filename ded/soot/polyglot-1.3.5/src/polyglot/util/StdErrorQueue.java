package polyglot.util;

import java.io.*;
import java.util.StringTokenizer;

/**
 * A <code>StdErrorQueue</code> handles outputing error messages.
 */
public class StdErrorQueue extends AbstractErrorQueue
{
    private PrintStream err;

    public StdErrorQueue(PrintStream err, int limit, String name) {
        super(limit, name);
	this.err = err;
    }

    public void displayError(ErrorInfo e) {
	String message = e.getErrorKind() != ErrorInfo.WARNING
		       ? e.getMessage()
		       : e.getErrorString() + " -- " + e.getMessage();

	Position position = e.getPosition();

	String prefix = position != null
	  		? position.nameAndLineString()
			: name;

        /*

        // This doesn't work: it breaks the error message into one word
        // per line.

        CodeWriter w = new CodeWriter(err, 78);

        w.begin(0);
        w.write(prefix + ":");
        w.write(" ");

	StringTokenizer st = new StringTokenizer(message, " ");

	while (st.hasMoreTokens()) {
	    String s = st.nextToken();
            w.write(s);
            if (st.hasMoreTokens()) {
                w.allowBreak(0, " ");
            }
        }

        w.end();
        w.newline(0);

        try {
            w.flush();
        }
        catch (IOException ex) {
        }
        */

	// I (Nate) tried without success to get CodeWriter to do this.
	// It would be nice if we could specify where breaks are allowed 
	// when generating the error.  We don't want to break Jif labels,
	// for instance.

	int width = 0;
	err.print(prefix + ":");
	width += prefix.length() + 1;

	int lmargin = 4;
	int rmargin = 78;

	StringTokenizer lines = new StringTokenizer(message, "\n", true);
        boolean needNewline = false;

	while (lines.hasMoreTokens()) {
	    String line = lines.nextToken();

            if (line.indexOf("\n") < 0) {
                StringTokenizer st = new StringTokenizer(line, " ");

                while (st.hasMoreTokens()) {
                    String s = st.nextToken();

                    if (width + s.length() + 1 > rmargin) {
                        err.println();
                        for (int i = 0; i < lmargin; i++) err.print(" ");
                        width = lmargin;
                    }
                    else {
                        err.print(" ");
                        width++;
                    }

                    err.print(s);

                    width += s.length();
                }

                needNewline = true;
            }
            else {
                err.println();
                needNewline = false;
            }

            width = lmargin;

            if (lines.hasMoreTokens()) {
                for (int i = 0; i < lmargin; i++) err.print(" ");
            }
            else if (needNewline) {
                err.println();
            }
	}

	if (position != null) {
	    showError(position);
	}
    }
    
    protected void tooManyErrors(ErrorInfo lastError) {
        Position position = lastError.getPosition();
        String prefix = position != null ? (position.file() + ": ") : "";
        err.println(prefix + "Too many errors.  Aborting compilation.");        
    }

    protected Reader reader(Position pos) throws IOException {
      if (pos.file() != null && pos.line() != Position.UNKNOWN) {
	  return new FileReader(pos.file());
      }
      return null;
    }

    private void showError(Position pos) {
      try {
        Reader r = reader(pos);

        if (r == null) {
          return;
        }

        LineNumberReader reader = new LineNumberReader(r);

        String s = null;
        while (reader.getLineNumber() < pos.line()) {
          s = reader.readLine();
        }

        if (s != null) {
          err.println(s);
          showErrorIndicator(pos, reader.getLineNumber(), s);
          
          if (pos.endLine() != pos.line() &&
              pos.endLine() != Position.UNKNOWN &&
              pos.endLine() != Position.END_UNUSED) {

              // if there is more than two lines,
              // print some ellipsis.
              if (pos.endLine() - pos.line() > 1) {
                  // add some whitespace first
                  for (int j = 0; j < s.length() && Character.isWhitespace(s.charAt(j)); j++) {
                      err.print(s.charAt(j));
                  }
                  err.println("...");
              }
              
              while (reader.getLineNumber() < pos.endLine()) {
                s = reader.readLine();
              }

              // s is now the last line of the error.
              if (s != null) {
                err.println(s);
                showErrorIndicator(pos, reader.getLineNumber(), s);
              }
            }
        }
        
        reader.close();

        err.println();
      }
      catch (IOException e) {
      }
    }
    
    protected void showErrorIndicator(Position pos, int lineNum, String s) {
        if (pos.column() == Position.UNKNOWN) {
            return;
        }
        
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            err.print(s.charAt(i++));
        }
        
        int startIndAt = i; // column position to start showing the indicator marks
        int stopIndAt = s.length() - 1; // column position to stop showing the indicator marks
        if (pos.line() == lineNum) {
            startIndAt = pos.column();
        }        
        if (pos.endLine() == lineNum) {
            stopIndAt = pos.endColumn() - 1;
        }
        if (stopIndAt < startIndAt) {
            stopIndAt = startIndAt;
        }
        if (pos.endColumn() == Position.UNKNOWN ||
            pos.endColumn() == Position.END_UNUSED) {
            stopIndAt = startIndAt;
        }
                
        for (;i <= stopIndAt; i++) {
            char c = '-';            
            if (i < startIndAt) {
              c = ' ';
            }
            if (i < s.length() && s.charAt(i) == '\t') {
              c = '\t';
            }            
            if (i == startIndAt && pos.line() == lineNum) {
                c = '^';
            }
            if (i == stopIndAt && pos.endLine() == lineNum) {
                c = '^';
            }
            
            err.print(c);
        }

        err.println();
    }
    
    public void flush() {
	if (! flushed) {
            if (errorCount() > 0) {
                err.println(errorCount() + " error" +
                            (errorCount() > 1 ? "s." : "."));
            }
	}
        super.flush();
    }
}
