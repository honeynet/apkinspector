/*
 * TransformingList.java
 */
package polyglot.util;

import java.util.*;

/**
 * This unmodifiable List supports performing an arbitrary transformation on
 * the underlying list's elements.  The transformation is applied on every
 * access to the underlying members.
 */
public class TransformingList extends java.util.AbstractList {
    protected final Transformation trans;
    protected final List underlying;
    

    public TransformingList(Collection underlying, Transformation trans) {
	this(new ArrayList(underlying), trans);
    }

    public TransformingList(List underlying, Transformation trans) {
      this.underlying = underlying;
      this.trans = trans;
    }

    public int size() { return underlying.size(); }

    public Object get(int index) {
	return trans.transform(underlying.get(index));
    }

}

