package polyglot.util;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;

/**
 * This class represents a set of calculations to be performed, some
 * of which have already been completed.  It optionally stores the results of
 * those calculations.
 *
 * Requires: every 'calculation' object has a working hashcode and
 * toString method.
 **/
public class WorkList {

  /**
   * Creates a new, empty worklist.
   **/
  public WorkList() {
    pending = new LinkedList();
    results = new HashMap();
    size = 0;
  }

  /**
   * Adds the calculation represented by <o> to the worklist, if it has not
   * already been calculated.
   **/
  public void addWork(Object o) {
    if (! results.containsKey(o)) {
      results.put(o, NOT_CALCULATED);
      pending.addLast(o);
      size++;
    }
  }

  /**
   * Adds every member of the collection <c> to the worklist, if it
   * has not already been calculted.
   **/
  public void addWork(Collection c) {
    for (Iterator i = c.iterator(); i.hasNext(); )
      addWork(i.next());
  }

  /**
   * Returns true iff there is no more work to do.
   **/
  public boolean finished() {
    return size == 0;
  }

  /**
   * Returns the first element with no known result.  Throws
   * NoSuchElementException if no such element exists.
   **/
  public Object getWork() {
    if (size>0) 
      return pending.getFirst();
    else
      throw new java.util.NoSuchElementException("WorkList.getWork");
  }

  /**
   * Announces that we have finished the calculation represented by <work>,
   * getting the result <result>.  Removes <work> from the pending list,
   * and sets its result to <result>
   **/
  public void finishWork(Object work, Object result) {
    if (results.get(work) == NOT_CALCULATED) {
      for (ListIterator i = pending.listIterator(); i.hasNext(); ) {
	if (i.next().equals(work))
	  i.remove();
      }
    }
    results.put(work, result);
  }

  /**
   * Announces that we have finished the calculation represented by <work>.
   **/
  public void finishWork(Object work) {
    finishWork(work, null);
  }

  /**
   * Returns true iff <work> has been completed.
   **/
  public boolean isFinished(Object work) {
    return results.containsKey(work) && results.get(work) != NOT_CALCULATED;
  }

  /**
   * Returns an immutable view of a map from calculation objects to
   * their results.  Non-computed values map to NOT_CALCULATED
   **/
  public Map getMap() {
    return Collections.unmodifiableMap(results);
  }

  // The list of objects to be calculated on.  The oldest element is first.
  // RI: Every member of <pending> is a key in <results>.
  LinkedList pending;
  // A map from all objects to their results.  Any object with no result
  // maps to NOT_CALCULATED.
  HashMap results;
  // The number of elements in pending.
  int size;

  public static final Object NOT_CALCULATED = new Object();
}
