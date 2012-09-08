import java.util.*;

public class Hash
{
  public Hash( int i) 
  {
    System.out.println( "Creating!");
  }

  public static void main( String[] args) throws Exception
  {
    new Hash( 432);

    synchronized( 3)
    {
      System.out.println("synced");
    }

    Hashtable ht = new Hashtable();
    ht.put ( 32, "Hello");
    System.out.println ( ht.get( 32));    

    ht.put( "World", 39);

    Object o = ht.get( "World");
    if( o instanceof int) {
      System.out.println( (int)o + 3);
    }

    ht.put( "!!!", true);
    if( (boolean)ht.get( "!!!")) {
      System.out.println( "World!");
    }
  }
}
