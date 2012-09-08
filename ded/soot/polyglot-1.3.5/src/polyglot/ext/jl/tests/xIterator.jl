/**
 * Simple LinkedListIterator class
 *
 * Where label L refers to the label of the contents
 * of the list
 */
class xIterator implements java.util.Iterator {

    public boolean hasNext() {
        return false;
    }

    public Object next() throws java.util.NoSuchElementException {
        return null;
    }

    public void remove() throws java.lang.UnsupportedOperationException,
                                  java.lang.IllegalStateException
    {
        throw new UnsupportedOperationException();
    }

}
