/*
 * NestedMap.java
 */

package polyglot.util;

import java.util.Map;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.AbstractSet;

/**
 * A NestedMap is a map which, when it cannot find an element in itself,
 * defers to another map.  Modifications, however, are not passed on to
 * the supermap.
 *
 * A NestedMap and its backing collections and iterators support all
 * operations except 'remove' and 'clear', since operations to a
 * NestedMap must not affect the backing map.  Instead, use the 'release'
 * method.
 *
 * It is used to implement nested namespaces, such as those which store
 * local-variable bindings.
 **/
public class NestedMap extends AbstractMap implements Map {
  /**
   * Creates a new nested map, which defers to <containing>.  If containing
   * is null, it defaults to a NilMap.
   **/
  public NestedMap(Map containing) {
    this.superMap = containing == null ? NilMap.EMPTY_MAP : containing;
    this.myMap = new HashMap();
    setView = new EntrySet();
    nShadowed = 0;
  }

  /////
  // For NestedMap.
  /////
  /**
   * Returns the map to which this map defers, or null for none.
   **/
  public Map getContainingMap() {
    return superMap instanceof NilMap ? null : superMap;
  }

  /**
   * Removes any binding in this for <key>, returning to the binding (if any)
   * from the supermap.
   **/
  public void release(Object key) {
    myMap.remove(key);
  }  

  /**
   * Returns the map containing the elements for this level of nesting.
   **/
  public Map getInnerMap() {
    return myMap;
  }

  /////
  // Methods required for AbstractMap.
  /////

  public Set entrySet() {
    return setView;
  }

  public int size() {
    return superMap.size() + myMap.size() - nShadowed;
  }

  public boolean containsKey(Object key) {
    return myMap.containsKey(key) || superMap.containsKey(key);
  }

  public Object get(Object key) {
    if (myMap.containsKey(key))
      return myMap.get(key);
    else 
      return superMap.get(key);
  }
  
  public Object put(Object key, Object value) {
    if (myMap.containsKey(key)) {
      return myMap.put(key,value);
    } else {
      Object oldV = superMap.get(key);
      myMap.put(key,value);
      nShadowed++;
      return oldV;
    }
  }  

  public Object remove(Object key) {
    throw new UnsupportedOperationException("Remove from NestedMap");
  }

  public void clear() {
    throw new UnsupportedOperationException("Clear in NestedMap");
  }

  public final class KeySet extends AbstractSet {
    public Iterator iterator() {
      return new ConcatenatedIterator(
	   myMap.keySet().iterator(),
	   new FilteringIterator(superMap.keySet(), keyNotInMyMap));
    }
    public int size() {
      return NestedMap.this.size();
    }
    // No add; it's not meaningful.
    public boolean contains(Object o) {
      return NestedMap.this.containsKey(o);
    }
    public boolean remove(Object o) {
      throw new UnsupportedOperationException(
               "Remove from NestedMap.keySet");
    }
  }

  private final class EntrySet extends AbstractSet {    
    public Iterator iterator() {
      return new ConcatenatedIterator(
	  myMap.entrySet().iterator(),
	  new FilteringIterator(superMap.entrySet(), entryKeyNotInMyMap));
    }
    public int size() {
      return NestedMap.this.size();
    }
    // No add; it's not meaningful.
    public boolean contains(Object o) {
      if (! (o instanceof Map.Entry)) return false;
      Map.Entry ent = (Map.Entry) o;
      Object entKey = ent.getKey();
      Object entVal = ent.getValue();
      if (entVal != null) {
	Object val = NestedMap.this.get(entKey);
	return (val != null) && val.equals(entVal);
      } else {
	return NestedMap.this.containsKey(entKey) &&
	  (NestedMap.this.get(entKey) == null);
      }
    }
    public boolean remove(Object o) {
      throw new UnsupportedOperationException(
               "Remove from NestedMap.entrySet");
    }
  }
 
  private HashMap myMap;
  private int nShadowed;
  private Set setView; // the set view of this.
  private Map superMap;
  private Predicate entryKeyNotInMyMap = new Predicate() {
    public boolean isTrue(Object o) {
      Map.Entry ent = (Map.Entry) o;
      return ! myMap.containsKey(ent.getKey());
    }
  };
  private Predicate keyNotInMyMap = new Predicate() {
    public boolean isTrue(Object o) {
      return ! myMap.containsKey(o);
    }
  };

}

