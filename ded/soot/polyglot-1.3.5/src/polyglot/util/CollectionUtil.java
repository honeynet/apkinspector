package polyglot.util;

import java.util.*;

/** Collection utilities. */
public class CollectionUtil
{
	/** Return a new list with <code>o</code> appended to <code>l</code>. */
	public static List add(List l, Object o) {
		l.add(o);
		return l;
	}

	/**
	 * Return true if <code>a</code> and <code>b</code> are
	 * pointer equal, or if iterators over both return the same
	 * sequence of pointer equal elements.
	 */
	public static boolean equals(Collection a, Collection b) {
		if (a == b) {
			return true;
		}
		
		// the case where both are null is handled in the previous if.
		if (a == null ^ b == null) {
			return false;
		}
		
		Iterator i = a.iterator();
		Iterator j = b.iterator();

		while (i.hasNext() && j.hasNext()) {
			Object o = i.next();
			Object p = j.next();

			if (o != p) {
				return false;
			}
		}

		if (i.hasNext() || j.hasNext()) {
			return false;
		}

		return true;
	}

	/** Return a singleton list containing <code>o</code>. */
	public static List list(Object o) {
		return Collections.singletonList(o);
	}

	/** Return a list containing <code>o1</code> and <code>o2</code>. */
	public static List list(Object o1, Object o2) {
		List l = new ArrayList(2);
		l.add(o1);
		l.add(o2);
		return l;
	}

	/** Return a list containing <code>o1</code>, ..., <code>o3</code>. */
	public static List list(Object o1, Object o2, Object o3) {
		List l = new ArrayList(3);
		l.add(o1);
		l.add(o2);
		l.add(o3);
		return l;
	}

	/** Return a list containing <code>o1</code>, ..., <code>o4</code>. */
	public static List list(Object o1, Object o2, Object o3, Object o4) {
		List l = new ArrayList(3);
		l.add(o1);
		l.add(o2);
		l.add(o3);
		l.add(o4);
		return l;
	}

        public static Object firstOrElse(Collection l, Object alt) {
                Iterator i = l.iterator();
                if (i.hasNext()) return i.next();
                return alt;
        }


        public static Iterator pairs(Collection l) {
                List x = new LinkedList();
                Object prev = null;
                for (Iterator i = l.iterator(); i.hasNext(); ) {
                    Object curr = i.next();
                    if (prev != null) x.add(new Object[] { prev, curr });
                    prev = curr;
                }
                return x.iterator();
        }

	/**
	 * Apply <code>t</code> to each element of <code>l</code>.
	 * <code>l</code> is not modified.  
	 * @return A list containing the result of each transformation,
	 * in the same order as the original elements.
	 */
	public static List map(List l, Transformation t) {
		List m = new ArrayList(l.size());
		for (Iterator i = new TransformingIterator(l.iterator(), t); 
			i.hasNext(); )
		{
			m.add(i.next());
		}
		return m;
	}

}
