/*
 * ConcatenatedIterator.java
 */

package polyglot.util;

import java.util.Iterator;

/**
 * ConcatenatedIterator
 *
 * Overview:
 *     This iterator wraps other iterators, and returns all their elements
 *     in order.
 *
 *     Does not support Remove.
 **/
public final class ConcatenatedIterator implements Iterator {
  /**
   * Constructs a new ConcatenatedIterator which yields all of the
   * elements of <iter1>, followed by all the elements of <iter2>.
   **/
  public ConcatenatedIterator(Iterator iter1, Iterator iter2) {
    this(new Iterator[]{iter1, iter2});
  }

  /**
   * Constructs a new ConcatenatedIterator which yields every element, in
   *  order, of every element of the array iters, in order.
   **/
  public ConcatenatedIterator(Iterator[] iters) {
    this.backing_iterators = (Iterator[]) iters.clone();
    findNextItem();
  }

  /**
   * Constructs a new ConcatenatedIterator which yields every element, in
   * order, of every element of the collection iters, in order.
   **/
  public ConcatenatedIterator(java.util.Collection iters) {
    this.backing_iterators = (Iterator[])iters.toArray(new Iterator[0]);
    findNextItem();
  }

  public Object next() {
    Object res = next_item;
    if (res == null)
      throw new java.util.NoSuchElementException();
    findNextItem();
    return res;
  }

  public boolean hasNext() {
    return next_item != null;
  }
  
  public void remove() {
    throw new UnsupportedOperationException("ConcatenatedIterator.remove");
  }

  // Advances the internal iterator.
  private void findNextItem() {
    while(index < backing_iterators.length) {
      Iterator it = backing_iterators[index];
      if (it.hasNext()) {
	next_item = it.next();
	return;
      } else {
	index++;
      }
    }
    next_item = null;
  }
  
  // AF:  if next_item==null, this iterator has no more elts to yield.
  //      otherwise, this iterator will yield next_item, followed by the 
  //      remaining elements of backing_iterators[index], followed by the
  //      elements of backing_iterators[index+1]...
  Object next_item;
  Iterator[] backing_iterators;
  int index;
}


