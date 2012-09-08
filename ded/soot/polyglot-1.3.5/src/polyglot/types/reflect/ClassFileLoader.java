package polyglot.types.reflect;

import polyglot.main.Report;
import polyglot.util.InternalCompilerError;
import polyglot.frontend.ExtensionInfo;

import java.io.*;
import java.util.*;
import java.util.zip.*;
import java.util.jar.*;

/**
 * We implement our own class loader.  All this pain is so
 * we can define the classpath on the command line.
 */
public class ClassFileLoader
{

    /** The extension info */
    private ExtensionInfo extensionInfo;

    /**
     * Keep a cache of the zips and jars so we don't have to keep
     * opening them from the file system.
     */
    Map zipCache;

    /**
     * A cache of directories found in zip files.
     */
    Set packageCache;

    /**
     * Directory contents cache. Cache the first level of the directory
     * so that we get less FileNotFoundExceptions
     */
    Map dirContentsCache;

    final static Object not_found = new Object();

    public ClassFileLoader(ExtensionInfo ext) {
        this.zipCache = new HashMap();
        this.dirContentsCache = new HashMap();
	this.packageCache = new HashSet();
        this.extensionInfo = ext;
    }

    /**
     * Return true if the package name exists under the directory or file
     * <code>dir</code>.
     */
    public boolean packageExists(File dir, String name) {
        if (Report.should_report(verbose, 3)) {
	    Report.report(3, "looking in " + dir + " for " +
                             name.replace('.', File.separatorChar));
        }

        try {
            if (dir.getName().endsWith(".jar") ||
                dir.getName().endsWith(".zip")) {

                String entryName = name.replace('.', '/');
		
		if (packageCache.contains(entryName)) {
		    return true;
		}

		// load the zip file, forcing the package cache to be initialized
		// with its contents.
                loadZip(dir);
    		
		return packageCache.contains(entryName);
	    }
            else {
                String entryName = name.replace('.', File.separatorChar);
                File f = new File(dir, entryName);
                return f.exists() && f.isDirectory();
            }
        }
        catch (FileNotFoundException e) {
            // ignore the exception.
        }
        catch (IOException e) {
            throw new InternalCompilerError(e);
        }

        return false;
    }

    /**
     * Try to find the class <code>name</code> in the directory or jar or zip
     * file <code>dir</code>.
     * If the class does not exist in the specified file/directory, then
     * <code>null</code> is returned.
     */
    public ClassFile loadClass(File dir, String name)
    {
        if (Report.should_report(verbose, 3)) {
	    Report.report(3, "looking in " + dir + " for " +
                             name.replace('.', File.separatorChar) + ".class");
        }
	
        try {
            if (dir.getName().endsWith(".jar") ||
                dir.getName().endsWith(".zip")) {

                ZipFile zip = loadZip(dir);
                String entryName = name.replace('.', '/') + ".class";
                return loadFromZip(dir, zip, entryName);
            }
            else {
                return loadFromFile(name, dir);
            }
        }
        catch (FileNotFoundException e) {
            // ignore the exception.
        }
        catch (IOException e) {
            throw new InternalCompilerError(e);
        }

        return null;
    }

    ZipFile loadZip(File dir) throws IOException {
        Object o = zipCache.get(dir);
        if (o != not_found) {
            ZipFile zip = (ZipFile) o;
            if (zip != null) {
                return zip;
            }
            else {
                // the zip is not in the cache.
                // try to get it.
                if (!dir.exists()) {
                    // record that the file does not exist,
                    zipCache.put(dir, not_found);
                }
                else {
                    // get the zip and put it in the cache.
                    if (Report.should_report(verbose, 2))
                        Report.report(2, "Opening zip " + dir);
		    if (dir.getName().endsWith(".jar")) {
			zip = new JarFile(dir);
		    }
		    else {
			zip = new ZipFile(dir);
		    }
		    zipCache.put(dir, zip);
		    
		    // Load the package cache.
		    for (Enumeration i = zip.entries(); i.hasMoreElements(); ) {
			ZipEntry ei = (ZipEntry) i.nextElement();
			String n = ei.getName();
			
			int index = n.indexOf('/');
			while (index >= 0) {
			    packageCache.add(n.substring(0, index));
			    index = n.indexOf('/', index+1);
			}
		    }
		    
                    return zip;
                }
            }
        }
        throw new FileNotFoundException(dir.getAbsolutePath());
    }

    ClassFile loadFromZip(File source, ZipFile zip, String entryName) throws IOException {
        if (Report.should_report(verbose, 2))
            Report.report(2, "Looking for " + entryName + " in " + zip.getName());
        if (zip != null) {
            ZipEntry entry = zip.getEntry(entryName);
            if (entry != null) {
                if (Report.should_report(verbose, 3))
                    Report.report(3, "found zip entry " + entry);
                InputStream in = zip.getInputStream(entry);
                ClassFile c = loadFromStream(source, in, entryName);
                in.close();
                return c;
            }
        }
        return null;
    }

    ClassFile loadFromFile(String name, File dir) throws IOException {
        Set dirContents = (Set)dirContentsCache.get(dir);
        if (dirContents == null) {
            dirContents = new HashSet();
            dirContentsCache.put(dir, dirContents);
            if (dir.exists() && dir.isDirectory()) {
                String[] contents = dir.list();
                for (int j = 0; j < contents.length; j++) {
                    dirContents.add(contents[j]);
                }
            }
        }


        StringBuffer filenameSB = new StringBuffer(name.length() + 8);
        int firstSeparator = -1;
        filenameSB.append(name);
        // our own replace, to save a suprising amount of memory
        for (int i = 0; i < filenameSB.length(); i++) {
            if (filenameSB.charAt(i) == '.') {
                filenameSB.setCharAt(i, File.separatorChar);
                if (firstSeparator == -1)
                    firstSeparator = i;
            }
        }
        filenameSB.append(".class");

        String filename = filenameSB.toString();
        String firstPart = (firstSeparator==-1) ? filename
                                                : filename.substring(0, firstSeparator);

        // check to see if the directory has the first part of the filename,
        // to avoid trying to open the file if it doesn't
        if (!dirContents.contains(firstPart)) {
            return null;
        }

        // otherwise, try and open the thing.
        File file = new File(dir, filename);

        FileInputStream in = new FileInputStream(file);
        if (Report.should_report(verbose, 3))
            Report.report(3, "found " + file);
        ClassFile c = loadFromStream(file, in, name);
        in.close();
        return c;
    }

    /**
     * Load a class from an input stream.
     */
    ClassFile loadFromStream(File source, InputStream in, String name) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        byte[] buf = new byte[4096];
        int n = 0;

        do {
            n = in.read(buf);
            if (n >= 0) out.write(buf, 0, n);
        } while (n >= 0);

        byte[] bytecode = out.toByteArray();

        try {
            if (Report.should_report(verbose, 3))
		Report.report(3, "defining class " + name);
            return extensionInfo.createClassFile(source, bytecode);
        }
        catch (ClassFormatError e) {
            throw new IOException(e.getMessage());
        }
    }

    static Collection verbose;

    static {
        verbose = new HashSet();
        verbose.add("loader");
    }
}
