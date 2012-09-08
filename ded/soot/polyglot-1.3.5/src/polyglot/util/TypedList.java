/*
 * TypedList.java
 */

package polyglot.util;


import java.util.*;

/**
 * A TypedList is an List which will not allow members not belonging
 * to a given type to be added to a collection.  Optionally, it may
 * also present an immutable view.
 *
 * If an attempt is made to change an immutable list, or if an attempt
 * is made to insert an improperly-typed element, an
 * UnsupportedOperationException is thrown.
 *
 * This class is given so that we can present a List for a given class
 * without worrying about outsiders breaking the rep.
 *
 * This is a poor substitute for PolyJ.
 **/
public class TypedList implements List, java.io.Serializable, Cloneable
{
  static final long serialVersionUID = -1390984392613203018L;

  /**
   * Requires: <list> not null, and every element of <list> may be
   *    cast to class <c>.
   * Creates a new TypedList, containing all the elements of <list>,
   * which restricts all members to belong to class <c>.  If <c> is
   * null, no typing restriction is made.  If <immutable> is true, no
   * modifications are allowed.
   **/
  public static TypedList copy(List list, Class c, boolean immutable) {
    return new TypedList(new ArrayList(list), c, immutable);
  }

  /**
   * Creates a new TypedList, containing all the elements of <list>,
   * which restricts all members to belong to class <c>.  If <c> is
   * null, no typing restriction is made.  If <immutable> is true, no
   * modifications are allowed.
   *
   * Throws an UnsupportedOperationException if any member of <list>
   * may not be cast to class <c>.
   **/
  public static TypedList copyAndCheck(List list, Class c, boolean immutable) {
    if (c != null)
      check(list,c);
    return copy(list,c,immutable);
  }

  /**
   * Throws an UnsupportedOperationException if any member of <list>
   * may not be cast to class <c>. Otherwise does nothing.
   **/
  public static void check(List list, Class c) {
    for (Iterator i = list.iterator(); i.hasNext(); ) {
      Object o = i.next();
      if (o != null && !c.isAssignableFrom(o.getClass())) {
	throw new UnsupportedOperationException(
		     "Tried to add a " + o.getClass().getName() +
   	             " to a list of type " + c.getName());
      }
    }
  }

  /**
   * Requires: <list> not null, and every element of <list> may be
   *    cast to class <c>.
   * Effects:
   * Creates a new TypedList around <list> which restricts all
   * members to belong to class <c>.  If <c> is null, no typing
   * restriction is made.  If <immutable> is true, no modifications
   * are allowed.
   **/
  public TypedList(List list, Class c, boolean immutable) {
    this.immutable = immutable;
    this.allowed_type = c;
    this.backing_list = list;
  }

  /**
   * Gets the allowed type for this list.
   **/
  public Class getAllowedType(){
    return allowed_type;
  }

  /**
   * Copies this list.
   **/
  public TypedList copy() {
      return (TypedList) clone();
  }
  public Object clone() {
      try {
          TypedList l = (TypedList) super.clone();
          l.backing_list = new ArrayList(backing_list);
          return l;
      }
      catch (CloneNotSupportedException e) {
          throw new InternalCompilerError("Java clone weirdness.");
      }
  }

  public void add(int idx, Object o) {
    tryIns(o);
    backing_list.add(idx,o);
  }
  public boolean add(Object o) {
    tryIns(o);
    return backing_list.add(o);
  }
  public boolean addAll(int idx, Collection coll) {
    tryIns(coll);
    return backing_list.addAll(idx, coll);
  }
  public boolean addAll(Collection coll) {
    tryIns(coll);
    return backing_list.addAll(coll);
  }
  public ListIterator listIterator() {
    return new TypedListIterator(backing_list.listIterator(),
				 allowed_type,
				 immutable);
  }
  public ListIterator listIterator(int idx) {
    return new TypedListIterator(backing_list.listIterator(idx),
				 allowed_type,
				 immutable);
  }
  public Object set(int idx, Object o) {
    tryIns(o);
    return backing_list.set(idx, o);
  }
  public List subList(int from, int to) {
    return new TypedList(backing_list.subList(from, to),
			 allowed_type,
			 immutable);
  }
  public void clear() 
    { tryMod(); backing_list.clear(); }
  public boolean contains(Object o) 
    { return backing_list.contains(o); }
  public boolean containsAll(Collection coll) 
    { return backing_list.containsAll(coll); }
  public boolean equals(Object o)
    { return backing_list.equals(o); }
  public Object get(int idx)
    { return backing_list.get(idx); }
  public int hashCode()
    { return backing_list.hashCode(); }
  public int indexOf(Object o)
    { return backing_list.indexOf(o); }
  public boolean isEmpty()
    { return backing_list.isEmpty(); }
  public Iterator iterator()
    { return listIterator(); }
  public int lastIndexOf(Object o)
    { return backing_list.lastIndexOf(o); }
  public Object remove(int idx)
    { tryMod(); return backing_list.remove(idx); }
  public boolean remove(Object o)
    { tryMod(); return backing_list.remove(o); }
  public boolean removeAll(Collection coll)
    { tryMod(); return backing_list.removeAll(coll); }
  public boolean retainAll(Collection coll)
    { tryMod(); return backing_list.retainAll(coll); }
  public int size()
    { return backing_list.size(); }
  public Object[] toArray() 
    { return backing_list.toArray(); }
  public Object[] toArray(Object[] oa)
    { return backing_list.toArray(oa); }
  public String toString()
    { return backing_list.toString(); }

  private final void tryIns(Object o) {
    if (immutable) 
      throw new UnsupportedOperationException(
			         "Add to an immutable TypedListIterator");
    
    if (allowed_type != null && 
	!allowed_type.isAssignableFrom(o.getClass())) {
      String why = "Tried to add a " + o.getClass().getName() +
	" to a list of type " + allowed_type.getName();
      throw new UnsupportedOperationException(why);
    }    
  }

  private final void tryIns(Collection coll) {
    if (immutable) 
      throw new UnsupportedOperationException(
			         "Add to an immutable TypedListIterator");
    
    for (Iterator it = coll.iterator(); it.hasNext(); ) {
      Object o = it.next();
      if (allowed_type != null && 
	  !allowed_type.isAssignableFrom(o.getClass())) {
	String why = "Tried to add a " + o.getClass().getName() +
	  " to a list of type " + allowed_type.getName();
	throw new UnsupportedOperationException(why);
      }    
    }
  }

  private final void tryMod() {
    if (immutable) 
      throw new UnsupportedOperationException(
			         "Change to an immutable TypedListIterator");
  }

  // RI: allowed_type may be null.
  private Class allowed_type;
  private boolean immutable;
  private List backing_list;
}

