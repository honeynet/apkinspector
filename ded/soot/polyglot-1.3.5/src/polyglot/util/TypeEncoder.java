package polyglot.util;

import polyglot.main.Report;
import polyglot.types.*;

import java.io.*;
import java.util.zip.*;

/**
 * The <code>TypeEncoder</code> gives the ability to encode a polyglot 
 * <code>Type</code> as a Java string.
 * <p>
 * It uses a form of serialization to encode the <code>Type</code> into
 * a byte stream and then converts the byte stream to a standard Java string.
 * <p>
 * The difference between the encoder and a normal serialization process is
 * that in order to encode this type, we need to sever any links to other types
 * in the current environment. So any <code>ClassType</code> other than the 
 * the type being encoded is replaced in the stream with a 
 * <code>PlaceHolder</code> that contains the name of the class. To aid
 * in the decoding process, placeholders for member classes user their 
 * "mangled" name; non-member classes use their fully qualified name.
 */
public class TypeEncoder
{
  protected TypeSystem ts;
  protected final boolean zip = false;
  protected final boolean base64 = true;
  protected final boolean test = false;

  public TypeEncoder( TypeSystem ts)
  {
    this.ts = ts;
  }

  public String encode( Type t) throws IOException
  {
    ByteArrayOutputStream baos;
    ObjectOutputStream oos;

    if (Report.should_report(Report.serialize, 1)) {
      Report.report(1, "Encoding type " + t);
    }
    
    baos = new ByteArrayOutputStream();

    if (zip) {
	oos = new TypeOutputStream( new GZIPOutputStream( baos), ts, t);
    }
    else {
	oos = new TypeOutputStream( baos, ts, t);
    }

    oos.writeObject( t);
    oos.flush();
    oos.close();
    
    byte[] b = baos.toByteArray();

    if (Report.should_report(Report.serialize, 2)) {
      Report.report(2, "Size of serialization (with" 
            + (zip?"":"out") + " zipping) is " + b.length + " bytes");
    }

    String s;
    if (base64) {
        s = new String(Base64.encode(b));
    }
    else {    
        StringBuffer sb = new StringBuffer(b.length);
        for (int i = 0; i < b.length; i++)
    	sb.append((char) b[i]);
        s = sb.toString();
    }
    
    if (Report.should_report(Report.serialize, 2)) {
      Report.report(2, "Size of serialization after conversion to string is " 
            + s.length() + " characters");
    }

    if (test) {
      // Test it.
      try {
	decode(s);
      }
      catch (Exception e) {
        e.printStackTrace();
	throw new InternalCompilerError(
	    "Could not decode back to " + t + ": " + e.getMessage(), e);
      }
    }

    return s;
  }

  public Type decode(String s) throws InvalidClassException
  {
    ObjectInputStream ois;
    byte[] b;

    if (base64) {
        b = Base64.decode(s.toCharArray());    
    }
    else {
        char[] source;        
        source = s.toCharArray();
        b = new byte[ source.length];
        for (int i = 0; i < source.length; i++)
    	b[i] = (byte) source[i];
    }
    
    try {
        if (zip) {
            ois = new TypeInputStream( new GZIPInputStream( 
    				     new ByteArrayInputStream( b)), ts);
        }
        else {
            ois = new TypeInputStream( new ByteArrayInputStream( b), ts);
        }
        return (Type)ois.readObject();
    }
    catch (InvalidClassException e) {
        throw e;
    }
    catch (IOException e) {
        throw new InternalCompilerError("IOException thrown while " +
            "decoding serialized type info: " + e.getMessage(), e);
    }
    catch (ClassNotFoundException e) {
        throw new InternalCompilerError("Unable to find one of the classes " +
            "for the serialized type info: " + e.getMessage(), e);
    }
  }
}
