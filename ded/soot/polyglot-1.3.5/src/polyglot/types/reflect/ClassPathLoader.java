package polyglot.types.reflect;

import polyglot.main.Report;

import java.io.*;
import java.util.*;

/**
 * We implement our own class loader.  All this pain is so
 * we can define the classpath on the command line.
 */
public class ClassPathLoader
{
    List classpath;
    ClassFileLoader loader;

    public ClassPathLoader(List classpath, ClassFileLoader loader) {
        this.classpath = new ArrayList(classpath);
        this.loader = loader;
    }

    public ClassPathLoader(String classpath, ClassFileLoader loader) {
        this.classpath = new ArrayList();

        StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);

        while (st.hasMoreTokens()) {
            String s = st.nextToken();
            this.classpath.add(new File(s));
        }

        this.loader = loader;
    }

    public String classpath() {
        return classpath.toString();
    }

    public boolean packageExists(String name) {
	for (Iterator i = classpath.iterator(); i.hasNext(); ) {
	    File dir = (File) i.next();
            if (loader.packageExists(dir, name)) {
                return true;
            }
        }

        return false;
    }

    /**
     * Load a class from the classpath. If the class is not found, then
     * <code>null</code> is returned.
     */
    public ClassFile loadClass(String name) {
        if (Report.should_report(verbose, 2)) {
	    Report.report(2, "attempting to load class " + name);
	    Report.report(2, "classpath = " + classpath);
	}

	for (Iterator i = classpath.iterator(); i.hasNext(); ) {
	    File dir = (File) i.next();
            ClassFile cf = loader.loadClass(dir, name); 
            if (cf != null) {
                return cf;
            }
        }

        return null;
    }

    static Collection verbose;

    static {
        verbose = new HashSet();
        verbose.add("loader");
    }
}
