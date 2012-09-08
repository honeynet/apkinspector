package polyglot.util;

/** A bit vector class implemented more naively than java.util.BitSet. */
public class BitVector
{
  private int size;
  private boolean[] bits;

  public BitVector()
  {
    this( 32);
  }
  
  public BitVector( int initialSize)
  {
    size = initialSize;
    bits = new boolean[ size];
  }

  public final void setBit( int which, boolean value)
  {
    if( which >= size) {
      size += 32;
      boolean[] newBits = new boolean[ size ];
      for( int i = 0; i < bits.length; i++)
        newBits[i] = bits[i];
      bits = newBits;
    }
    
    bits[ which] = value;
  }

  public final boolean getBit( int which)
  {
    if( which >= size) {
      return false;
    }
    else {
      return bits[ which];
    }
  }
}

