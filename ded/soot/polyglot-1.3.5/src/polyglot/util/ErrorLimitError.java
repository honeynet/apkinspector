package polyglot.util;

/** Exception thrown when the number of errors exceeds a limit. */
public class ErrorLimitError extends RuntimeException
{
  public ErrorLimitError( String msg)
  {
    super( msg);
  }

  public ErrorLimitError()
  {
    super();
  }
}
