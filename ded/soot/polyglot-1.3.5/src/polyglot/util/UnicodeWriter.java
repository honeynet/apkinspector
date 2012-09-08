package polyglot.util;

import java.io.*;

/**
 * Output stream for writing unicode.  Non-ASCII Unicode characters
 * are escaped.
 */
public class UnicodeWriter extends FilterWriter
{
  public UnicodeWriter(Writer out)
  {
    super(out);
  }

  public void write(int c) throws IOException
  {
    if( c <= 0xFF) {
      super.write(c);
    }
    else {
      String s = String.valueOf(Integer.toHexString(c));
      super.write('\\');
      super.write('u');
      for(int i = s.length(); i < 4; i++) {
        super.write('0');
      }
      write(s);
    }
  }
  
  public void write(char[] cbuf, int off, int len) throws IOException
  {
    for( int i = 0; i < len; i++)
    {
      write((int)cbuf[i+off]);
    }
  }

  public void write(String str, int off, int len) throws IOException
  {
    write(str.toCharArray(), off, len);
  }
}
