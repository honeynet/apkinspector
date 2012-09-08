/*
 * CachingTransformingList.java
 */
package polyglot.util;

import java.util.*;

/**
 * This subclass of TransformingList applies the transformation to each
 * element of the underlying list at most once.
 */
public class CachingTransformingList extends TransformingList {
    ArrayList cache;

    public CachingTransformingList(Collection underlying,
				   Transformation trans)
	{
	    this(new ArrayList(underlying), trans);
	}

    public CachingTransformingList(List underlying, Transformation trans) {
	super(underlying, trans);
	cache = new ArrayList(Collections.nCopies(underlying.size(), null));
    }

    public Object get(int index) {
	Object o = cache.get(index);
	if (o == null) {
	    o = trans.transform(underlying.get(index));
	    cache.set(index, o);
	}
	return o;
    }
}
