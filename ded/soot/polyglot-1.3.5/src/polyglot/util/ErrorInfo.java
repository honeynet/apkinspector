package polyglot.util;

/** Information about an error message. */
public class ErrorInfo
{
  public static final int WARNING            = 0;
  public static final int INTERNAL_ERROR     = 1;
  public static final int IO_ERROR           = 2;
  public static final int LEXICAL_ERROR      = 3;
  public static final int SYNTAX_ERROR       = 4;
  public static final int SEMANTIC_ERROR     = 5;
  public static final int POST_COMPILER_ERROR= 6;

  protected int kind;
  protected String message;
  protected Position position;
  
  public ErrorInfo(int kind, String message, Position position)
  {
    this.kind = kind;
    this.message = message;
    this.position = position;
  }

  public int getErrorKind()
  {
    return kind;
  }

  public String getMessage()
  {
    return message;
  }

  public Position getPosition()
  {
    return position;
  }

  public String getErrorString()
  {
    return getErrorString( kind);
  }

  public static String getErrorString( int kind)
  {
    switch( kind) {
    case WARNING:
      return "Warning";
    case INTERNAL_ERROR:
      return "Internal Error";
    case IO_ERROR:
      return "I/O Error";
    case LEXICAL_ERROR:
      return "Lexical Error";
    case SYNTAX_ERROR:
      return "Syntax Error";
    case SEMANTIC_ERROR:
      return "Semantic Error";
    case POST_COMPILER_ERROR:
      return "Post-compiler Error";
    default:
      return "(Unknown)";
    }
  }  
}

