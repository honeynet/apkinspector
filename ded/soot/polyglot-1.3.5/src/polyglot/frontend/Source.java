package polyglot.frontend;

import java.util.Date;

/** A <code>Source</code> represents a source file. */
public class Source
{
    protected String name;
    protected String path;
    protected Date lastModified;
    
    /**
     * Indicates if this source was explicitly specified by the user,
     * or if it a source that has been drawn in to the compilation process
     * due to a dependency.
     */
    protected boolean userSpecified;

    protected Source(String name) {
        this(name, null, null, false);
    }

    protected Source(String name, boolean userSpecified) {
        this(name, null, null, userSpecified);
    }

    public Source(String name, String path, Date lastModified) {
        this(name, path, lastModified, false);
    }
    
    public Source(String name, String path, Date lastModified, boolean userSpecified) {
	this.name = name;
        this.path = path;
	this.lastModified = lastModified;
        this.userSpecified = userSpecified;   
    }

    public boolean equals(Object o) {
	if (o instanceof Source) {
	    Source s = (Source) o;
	    return name.equals(s.name) && path.equals(s.path);
	}

	return false;
    }

    public int hashCode() {
	return path.hashCode() + name.hashCode();
    }

    /** The name of the source file. */
    public String name() {
	return name;
    }

    /** The name of the source file. */
    public String path() {
	return path;
    }

    /** Return the date the source file was last modified. */
    public Date lastModified() {
	return lastModified;
    }

    public String toString() {
	return path;
    }
    
    public void setUserSpecified(boolean userSpecified) {
        this.userSpecified = userSpecified;
    }
    
    public boolean userSpecified() {
        return userSpecified;
    }
}
