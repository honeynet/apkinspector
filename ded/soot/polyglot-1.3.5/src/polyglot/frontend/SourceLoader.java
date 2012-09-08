package polyglot.frontend;

import java.io.*;
import java.util.*;
import polyglot.main.Report;
import polyglot.util.InternalCompilerError;

/** A <code>SourceLoader</code> is responsible for loading source files. */
public class SourceLoader
{
    protected ExtensionInfo sourceExt;
    protected Collection sourcePath;

    /** 0 if unknown, 1 if case insensitive, -1 if not. */
    protected int caseInsensitive;

    /** Set of sources already loaded.  An attempt to load a source
      * already loaded will cause an IOException. */
    protected Set loadedSources;

    /**
     * This is a map from Files (of directories) to Set[String]s, which
     * records the first level of contents of the directory. This cache
     * is used to avoid a number of File.exists() calls.
     */
    protected Map directoryContentsCache;

    public SourceLoader(ExtensionInfo sourceExt, Collection sourcePath) {
	this.sourcePath = sourcePath;
	this.sourceExt = sourceExt;
        this.directoryContentsCache = new HashMap();
        this.caseInsensitive = 0;
        this.loadedSources = new HashSet();
    }

    /** Load a source from a specific file. */
    public FileSource fileSource(String fileName) throws IOException {
        // If we haven't done so already,
        // determine if the file system is case insensitive
        setCaseInsensitive(fileName);

	File sourceFile = new File(fileName);

	if (! sourceFile.exists()) {
	    throw new FileNotFoundException(fileName);
	}

        if (loadedSources.contains(fileKey(sourceFile))) {
	    throw new FileNotFoundException(fileName);
        }

        loadedSources.add(fileKey(sourceFile));

        String[] exts = sourceExt.fileExtensions();
        boolean ok = false;

        for (int i = 0; i < exts.length; i++) {
            String ext = exts[i];

            if (fileName.endsWith("." + ext)) {
                ok = true;
                break;
            }
        }

        if (! ok) {
            String extString = "";

            for (int i = 0; i < exts.length; i++) {
                if (exts.length == 2 && i == exts.length-1) {
                    extString += " or ";
                }
                else if (exts.length != 1 && i == exts.length-1) {
                    extString += ", or ";
                }
                else if (i != 0) {
                    extString += ", ";
                }
                extString = extString + "\"." + exts[i] + "\"";
            }

            if (exts.length == 1) {
                throw new IOException("Source \"" + fileName +
                                      "\" does not have the extension "
                                      + extString + ".");
            }
            else {
                throw new IOException("Source \"" + fileName +
                                      "\" does not have any of the extensions "
                                      + extString + ".");
            }
        }

	if (Report.should_report(Report.frontend, 2))
	    Report.report(2, "Loading class from " + sourceFile);

	return new FileSource(sourceFile);
    }

    /**
     * The current user directory. We make it static so we don't need to
     * keep on making copies of it. 
     */
    static File current_dir = null;

    /**
     * The current user directory.
     */
    protected static File current_dir() {
        if (current_dir == null) {
            current_dir = new File(System.getProperty("user.dir"));
        }
        return current_dir;
    }

    /** Check if a directory for a package exists. */
    public boolean packageExists(String name) {
        String fileName = name.replace('.', File.separatorChar);

	/* Search the source path. */
        for (Iterator i = sourcePath.iterator(); i.hasNext(); ) {
            File directory = (File) i.next();

            File f = new File(directory, fileName);

            if (f.exists() && f.isDirectory()) {
                return true;
            }
        }

        return false;
    }
    
    /** Load the source file for the given class name using the source path. */
    public FileSource classSource(String className) {
	/* Search the source path. */
        String[] exts = sourceExt.fileExtensions();

        for (int k = 0; k < exts.length; k++) {
            String fileName = className.replace('.', File.separatorChar) +
                                      "." + exts[k];

            for (Iterator i = sourcePath.iterator(); i.hasNext(); ) {
                File directory = (File) i.next();
                Set dirContents = (Set)directoryContentsCache.get(directory);
                if (dirContents == null) {
                    dirContents = new HashSet();
                    directoryContentsCache.put(directory, dirContents);
                    if (directory.exists()) {
                        String[] contents = directory.list();
                        for (int j = 0; j < contents.length; j++) {
                            dirContents.add(contents[j]);
                        }
                    }                
                }

                // check if the source file exists in the directory
                int index = fileName.indexOf(File.separatorChar);
                if (index < 0) index = fileName.length(); 
                String firstPart = fileName.substring(0, index);

                if (dirContents.contains(firstPart)) {
                    // the directory contains at least the first part of the
                    // file path. We will check if this file exists.
                    File sourceFile;
                    
                    if (directory != null && directory.equals(current_dir())) {
                        sourceFile = new File(fileName);
                    }
                    else {
                        sourceFile = new File(directory, fileName);
                    }
                    
                    // Skip it if already loaded
                    if (loadedSources.contains(fileKey(sourceFile))) {
                        continue;
                    }

                    try {
                        if (Report.should_report(Report.frontend, 2))
                            Report.report(2, "Loading " + className + " from " + sourceFile);
                        FileSource s = new FileSource(sourceFile);
                        loadedSources.add(fileKey(sourceFile));
                        return s;
                    }
                    catch (IOException e) {
                    }
                }
            }
        }

        return null;
    }

    public Object fileKey(File file) {
        setCaseInsensitive(file.getAbsolutePath());
        if (caseInsensitive()) {
            return file.getAbsolutePath().toLowerCase();
        }
        return file.getAbsolutePath();
    }

    /** Is the file system case insensitive. */
    public boolean caseInsensitive() {
        if (caseInsensitive == 0) {
            throw new InternalCompilerError("unknown case sensitivity");
        }
        return caseInsensitive == 1;
    }

    protected void setCaseInsensitive(String fileName) {
        if (caseInsensitive != 0) {
            return;
        }

        // File.equals doesn't work correctly on the Mac.
        // So, get the list of files in the same directory
        // as sourceFile.  Check if the sourceFile with two
        // different cases exists but only appears in the list once.
        File f1 = new File(fileName.toUpperCase());
        File f2 = new File(fileName.toLowerCase());

        if (f1.equals(f2)) {
            caseInsensitive = 1;
        }
        else if (f1.exists() && f2.exists()) {
            boolean f1Exists = false;
            boolean f2Exists = false;

            File dir;

            if (f1.getParent() != null) {
                dir = new File(f1.getParent());
            }
            else {
                dir = current_dir();
            }

            File[] ls = dir.listFiles();
            for (int i = 0; i < ls.length; i++) {
                if (f1.equals(ls[i])) {
                    f1Exists = true;
                }
                if (f2.equals(ls[i])) {
                    f2Exists = true;
                }
            }

            if (! f1Exists || ! f2Exists) {
                caseInsensitive = 1;
            }
            else {
                // There are two files.
                caseInsensitive = -1;
            }
        }
        else {
            caseInsensitive = -1;
        }
    }

    protected String canonicalize(String fileName) {
        return fileName;
    }
}
