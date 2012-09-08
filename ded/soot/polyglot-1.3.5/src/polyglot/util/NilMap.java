/*
 * NilMap.java
 */

package polyglot.util;

import java.util.Map;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * This class represents a constant map which never contains any elements.
 **/
public final class NilMap implements Map {
  public static final NilMap EMPTY_MAP = new NilMap();

  private NilMap() {}

  public boolean containsKey(Object key)   { return false; }
  public boolean containsValue(Object val) { return false; }
  public Set entrySet()                    { return Collections.EMPTY_SET; } 
  public int hashCode()                    { return 0; }
  public boolean isEmpty()                 { return true; }
  public Set keySet()                      { return Collections.EMPTY_SET; }
  public int size()                        { return 0; }
  public Collection values()               { return Collections.EMPTY_SET; }
  public Object get(Object k)              { return null; }
  public boolean equals(Object o) 
    { return (o instanceof Map) && ((Map) o).size() == 0 ; }


  public void clear()            { throw new UnsupportedOperationException(); }
  public void putAll(Map t)      { throw new UnsupportedOperationException(); }
  public Object remove(Object o) { throw new UnsupportedOperationException(); }
  public Object put(Object o1, Object o2)
    { throw new UnsupportedOperationException(); }
}

