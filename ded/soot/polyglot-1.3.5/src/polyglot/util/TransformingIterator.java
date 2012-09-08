/*
 * TransformingIterator.java
 */

package polyglot.util;

import java.util.Iterator;
import java.util.Collection;

/**
 * TransformingIterator
 *
 * Overview:
 *     This is a swiss-army-knife of iterators.  It concatenates, maps, and
 *     filters.  
 *
 *     Does not support Remove.
 **/
public final class TransformingIterator implements Iterator {
  public TransformingIterator(Iterator iter, Transformation trans) {
    this(new Iterator[]{iter}, trans);
  }

  public TransformingIterator(Collection iters, Transformation trans) {
    index = 0;
    backing_iterators = (Iterator[]) iters.toArray(new Iterator[0]);
    transformation = trans;
    if (backing_iterators.length > 0)
      current_iter = backing_iterators[0];
    findNextItem();
  }

  public TransformingIterator(Iterator[] iters, Transformation trans) {
    index = 0;
    backing_iterators = (Iterator[]) iters.clone();
    transformation = trans;
    if (iters.length > 0) 
      current_iter = iters[0];
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
    throw new UnsupportedOperationException("TransformingIterator.remove");
  }

  // Advances the internal iterator.
  private void findNextItem() {
    while (current_iter != null) {
    inner_loop:
      while (current_iter.hasNext()) {		
	Object o = current_iter.next();	
	Object res = transformation.transform(o);
	if (res == Transformation.NOTHING)
	  continue inner_loop;
	next_item = res;
	return;
      }
      index++;
      if (index < backing_iterators.length) {
	current_iter = backing_iterators[index];
      } else {
	current_iter = null;
      }
    }
    next_item = null;
  }
  
  // AF:  if next_item==null, this iterator has no more elts to yield.
  //      otherwise, this iterator will yield next_item, followed by
  //      those elements e of backing_iterator[index] transformed by TRANS.
  // RI: current_iter = backing_iterators[index], or null if no 
  //     backing_iterator hasNext.
  Object next_item;
  Iterator current_iter;
  int index;
  Iterator[] backing_iterators;
  Transformation transformation;
}


