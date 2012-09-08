package polyglot.util;

import java.util.*;
import polyglot.types.*;

/**
 * Class to implement sets containing <code>polyglot.types.Type </code>.  
 * Set membership is based on the subtype relationships.  Thus, if 
 * <code>S</code> is a supertype of <code>A</code> and <code>B</code>, then
 * { <code>S</code> } union { <code>A</code>,<code>B</code> } = 
 * { <code>S</code> }.  Similarily, we remove elements from the set such 
 * that if <code>s</code> is an element of a set <code>S</code>, then a
 * call to remove <code>r</code> removes all <code>s</code> s.t. r is a 
 * a supertype of s.
 */
public class SubtypeSet implements java.util.Set
{
    Vector v; 
    TypeSystem ts;
    Type topType;  // Everything in the set must be a subtype of topType.

    /**
     * Creates an empty SubtypeSet
     */
    public SubtypeSet(TypeSystem ts) {
	this(ts.Object());
    }

    public SubtypeSet(Type top) {
	v = new Vector();
        this.ts = top.typeSystem();
	this.topType = top;
    }

    /**
     * Creates a copy of the given SubtypeSet
     */
    public SubtypeSet(SubtypeSet s) {
      v = new Vector(s.v);
      ts = s.ts;
      topType = s.topType;
    }

    public SubtypeSet(TypeSystem ts, Collection c) {
      this(ts);
      addAll(c);
    }

    public SubtypeSet(Type top, Collection c) {
      this(top);
      addAll(c);
    }

    /**
     * Add an element of type <code>polyglot.types.Type</code> to the set
     * only if it has no supertypes already in the set. If we do add it, 
     * remove any subtypes of <code>o</code>
     * 
     * @param o The element to add.
     */
    public boolean add(Object o) {
        if (o == null) {
	    return false;
	}

	if (o instanceof Type) {
	    Type type = (Type) o;

	    if (ts.isSubtype(type, topType)) {
		boolean haveToAdd = true;

		for (Iterator i = v.iterator(); i.hasNext(); ) {
		    Type t = (Type) i.next();

		    if (ts.descendsFrom(t, type)) {
			i.remove();
		    }

		    if (ts.isSubtype(type, t)) {
			haveToAdd = false;
			break;
		    }
		}

		if (haveToAdd) {
		    v.add(type);
		}

		return haveToAdd;
	    }
	}

	throw new InternalCompilerError(
	      "Can only add " + topType + "s to the set. Got a " + o);
    }

    /**
     * Adds all elements from c into this set.
     */
    public boolean addAll(Collection c) {
	if (c == null) {
	    return false;
	}

	boolean changed = false;

	for (Iterator i = c.iterator(); i.hasNext(); ) {
	    changed |= add(i.next()); 
	}

	return changed;
    }

    /**
     * Removes all elements from the set
     */
    public void clear() {
	v.clear();
    }

    /**
     * Check whether object <code>o</code> is in the set. Because of the 
     * semantics of the subtype set, <code>o</code> is in the set iff
     * it descends from (or is equal to) one of the elements in the set.
     */
    public boolean contains(Object o) {
	if (o instanceof Type) {
	    Type type = (Type) o;
	    
	    for (Iterator i = v.iterator(); i.hasNext(); ) {
		Type t = (Type) i.next();
		if (ts.isSubtype(type, t)) {
		    return true;
		}
	    }
	}

	return false;
    }

    /**
     * Check whether the type <code>t</code> or a subtype is in the set.
     * Returns true iff it descends from, is equal to, or is a super type of
     * one of the elements in the set.
     */
    public boolean containsSubtype(Type type) {
	for (Iterator i = v.iterator(); i.hasNext(); ) {
	    Type t = (Type)i.next();
	    if (ts.isSubtype(type, t) || ts.isSubtype(t, type)) return true;
	}

	return false;
    }

    /**
     * Checks whether all elements of the collection are in the set
     */
    public boolean containsAll(Collection c) {
	for (Iterator i = c.iterator(); i.hasNext(); ) {
	    if (! contains (i.next())) {
		return false;
	    }
	}

	return true;
    }

    public boolean isEmpty() {
	return v.isEmpty();
    }
    
    public Iterator iterator() {
	return v.iterator();
    }

    /**
     * Removes all elements <code>s</code> in the set such that 
     * <code>s</code> decends from <code>o</code>
     *
     * @return whether or not an element was removed.
     */
    public boolean remove(Object o) {
	Type type = (Type) o;

	boolean removed = false;

	for (Iterator i = v.iterator(); i.hasNext(); ) {
	    Type t = (Type) i.next();

	    if (ts.isSubtype(t, type)) {
		removed = true;
		i.remove(); 
	    }
	}

	return removed;
    }
    
    public boolean removeAll(Collection c) {
        boolean changed = false;

	for (Iterator i = c.iterator(); i.hasNext(); ) {
	    Object o = i.next();
	    changed |= remove(o);
	}

	return changed;
    }

    public boolean retainAll(Collection c) {
	throw new UnsupportedOperationException("Not supported");
    }

    public int size() {
	return v.size();
    }

    public Object[] toArray() {
	return v.toArray();
    }

    public Object[] toArray(Object[] a) {
	return v.toArray(a);
    }

    public String toString() {
	return v.toString();
    }
}
