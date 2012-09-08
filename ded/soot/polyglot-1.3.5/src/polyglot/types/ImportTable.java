package polyglot.types;

import polyglot.util.*;
import polyglot.main.Report;

import java.util.*;


/**
 * An <code>ImportTable</code> is a type of <code>ClassResolver</code> that
 * corresponds to a particular source file.
 * <p>
 * It has a set of package and class imports, which caches the results of
 * lookups for future reference.
 */
public class ImportTable extends ClassResolver
{
    protected TypeSystem ts;

    /** The underlying resolver. */
    protected Resolver resolver;
    /** A list of all package imports. */
    protected List packageImports;
    /** Map from names to classes found, or to the NOT_FOUND object. */
    protected Map map;
    /** List of class imports which will be lazily added to the table at the
     * next lookup. */
    protected List lazyImports;
    /** List of explicitly imported classes added to the table or pending in
     * the lazyImports list. */
    protected List classImports;
    /** Source name to use for debugging and error reporting */
    protected String sourceName;
    /** Position to use for error reporting */
    protected Position sourcePos;
    /** Our package */
    protected Package pkg;

    private static final Object NOT_FOUND = "NOT FOUND";
    
    /**
     * Create an import table.
     * @param ts The type system
     * @param base The outermost resolver to use for looking up types.
     * @param pkg The package of the source we are importing types into.
     */
    public ImportTable(TypeSystem ts, Resolver base, Package pkg) {
        this(ts, base, pkg, null);
    }

    /**
     * Create an import table.
     * @param ts The type system
     * @param base The outermost resolver to use for looking up types.
     * @param pkg The package of the source we are importing types into.
     * @param src The name of the source file we are importing into.
     */
    public ImportTable(TypeSystem ts, Resolver base, Package pkg, String src) {
        this.resolver = base;
        this.ts = ts;
        this.sourceName = src;
        this.sourcePos = src != null ? new Position(src) : null;
        this.pkg = pkg;

	this.map = new HashMap();
	this.packageImports = new ArrayList();
	this.lazyImports = new ArrayList();
	this.classImports = new ArrayList();
    }

    /**
     * The package of the source we are importing types into.
     */
    public Package package_() {
        return pkg;
    }

    /**
     * Add a class import.
     */
    public void addClassImport(String className) {
        if (Report.should_report(TOPICS, 2))
            Report.report(2, this + ": lazy import " + className);

	lazyImports.add(className);
        classImports.add(className);
    }

    /**
     * Add a package import.
     */
    public void addPackageImport(String pkgName) {
        // don't add the import if it is the same as the current package,
        // the same as a default import, or has already been imported
        if ((pkg != null && pkg.fullName().equals(pkgName)) ||
                ts.defaultPackageImports().contains(pkgName) ||
                packageImports.contains(pkgName)) {
            return;
        }
        
        packageImports.add(pkgName);
    }

    /**
     * List the packages we import from.
     */
    public List packageImports() {
        return packageImports;
    }

    /**
     * List the classes explicitly imported.
     */
    public List classImports() {
        return classImports;
    }

    /**
     * The name of the source file we are importing into.
     */
    public String sourceName() {
        return sourceName;
    }

    /**
     * Find a type by name, using the cache and the outer resolver,
     * but not the import table.
     */
    protected Named cachedFind(String name) throws SemanticException {
        Object res = map.get(name);

        if (res != null) {
            return (Named) res;
        }

        Named t = resolver.find(name);
        map.put(name, t);
        return t;
    }

    /**
     * Find a type by name, searching the import table.
     */
    public Named find(String name) throws SemanticException {
        if (Report.should_report(TOPICS, 2))
           Report.report(2, this + ".find(" + name + ")");

        /* First add any lazy imports. */
        lazyImport();

        if (!StringUtil.isNameShort(name)) {
            // The name was long.
            return resolver.find(name);
        }
        
        // The class name is short.
        // First see if we have a mapping already.
        Object res = map.get(name);

        if (res != null) {
            if (res == NOT_FOUND) {
                throw new NoClassException(name, sourcePos);
            }
            return (Named) res;
        }

        try {
            if (pkg != null) {
                // check if the current package defines it.
                // If so, this takes priority over the package imports (or 
                // "type-import-on-demand" declarations as they are called in
                // the JLS), so even if another package defines the same name,
                // there is no conflict. See Section 6.5.2 of JLS, 2nd Ed.
                Named n = findInPkg(name, pkg.fullName());
                if (n != null) {
                    if (Report.should_report(TOPICS, 3))
                       Report.report(3, this + ".find(" + name + "): found in current package");

                    // Memoize the result.
                    map.put(name, n);
                    return n;
                }
            }
            
            List imports = new ArrayList(packageImports.size() + 5);
            
            imports.addAll(ts.defaultPackageImports());
            imports.addAll(packageImports);
            
            // It wasn't a ClassImport.  Maybe it was a PackageImport?
            Named resolved = null;
            for (Iterator iter = imports.iterator(); iter.hasNext(); ) {
                String pkgName = (String) iter.next();
                Named n = findInPkg(name, pkgName);
                if (n != null) {
                    if (resolved == null) {
                        // this is the first occurance of name we've found
                        // in a package import.
                        // Record it, and keep going, to see if there
                        // are any conflicts.
                        resolved = n;
                    }
                    else {
                        // this is the 2nd occurance of name we've found
                        // in an imported package.
                        // That's bad.
                        throw new SemanticException("Reference to \"" + 
                                name + "\" is ambiguous; both " + 
                                resolved.fullName() + " and " + n.fullName() + 
                                " match.");
                    }
                }
            }
            
            if (resolved == null) {
                // The name was short, but not in any imported class or package.
                // Check the null package.
                resolved = resolver.find(name); // may throw exception
            
                if (!isVisibleFrom(resolved, "")) {
                    // Not visible.
                    throw new NoClassException(name, sourcePos);
                }
            }
            
            // Memoize the result.
            if (Report.should_report(TOPICS, 3))
               Report.report(3, this + ".find(" + name + "): found as " + resolved.fullName());
            map.put(name, resolved);
            return resolved;
        }
        catch (NoClassException e) {
            // memoize the no class exception
            if (Report.should_report(TOPICS, 3))
               Report.report(3, this + ".find(" + name + "): didn't find it");
            map.put(name, NOT_FOUND);
            throw e;
        }
    }
    
    protected Named findInPkg(String name, String pkgName) throws SemanticException {
        String fullName = pkgName + "." + name;

        try {
            Named n = resolver.find(pkgName);

            if (n instanceof ClassType) {
                n = ts.classContextResolver((ClassType) n).find(name); 
                return n;
            }
        }
        catch (NoClassException ex) {
            // Do nothing.
        }

        try {
            Named n = resolver.find(fullName);

            // Check if the type is visible in this package.
            if (isVisibleFrom(n, pkgName)) {
                return n;
            }
        }
        catch (NoClassException ex) {
            // Do nothing.
        }

        return null;
    }

    /**
     * Return whether <code>n</code> in package <code>pkgName</code> is visible from within
     * package <code>pkg</code>.  The empty string may
     * be passed in to represent the default package.
     */
    protected boolean isVisibleFrom(Named n, String pkgName) {
        boolean isVisible = false;
        boolean inSamePackage = this.pkg != null 
                && this.pkg.fullName().equals(pkgName)
            || this.pkg == null 
                && pkgName.equals("");
        if (n instanceof Type) {
            Type t = (Type) n;
            //FIXME: Assume non-class types are always visible.
            isVisible = !t.isClass() 
                || t.toClass().flags().isPublic() 
                || inSamePackage; 
        } else {
            //FIXME: Assume non-types are always visible.
            isVisible = true;
        }
        return isVisible;
    }
    
    /**
     * Load the class imports, lazily.
     */
    protected void lazyImport() throws SemanticException {
	if (lazyImports.isEmpty()) {
            return;
	}

	for (int i = 0; i < lazyImports.size(); i++) {
	    String longName = (String) lazyImports.get(i);

            if (Report.should_report(TOPICS, 2))
		Report.report(2, this + ": import " + longName);

	    try {
                // Try to find a class named longName.
                // The class maybe a static member class of another, so we'll
                // make several attempts.
                StringTokenizer st = new StringTokenizer(longName, ".");
                StringBuffer name = new StringBuffer();
                Named t = null;

                while (st.hasMoreTokens()) {
                    String s = st.nextToken();
                    name.append(s);

                    try {
                        t = cachedFind(name.toString());

                        if (! st.hasMoreTokens()) {
                            // found it
                            break;
                        }

                        if (t instanceof ClassType) {
                            // If we find a class that is further qualfied,
                            // search for member classes of that class.
                            ClassType ct = (ClassType) t;

                            while (st.hasMoreTokens()) {
                                String n = st.nextToken();
                                t = ct = ts.findMemberClass(ct, n);

                                // cache the result
                                map.put(n, ct);
                            }
                        }
                        else {
                            // t, whatever it is, is further qualified, but 
                            // should be, at least in Java, a ClassType.
                            throw new InternalCompilerError("Qualified type \"" + t + "\" is not a class type.", sourcePos);
                        }
                    }
                    catch (SemanticException e) {
                        if (! st.hasMoreTokens()) {
                            throw e;
                        }

                        // try again with the next level of type qualification
                        name.append(".");
                    }
                }

                String shortName = StringUtil.getShortNameComponent(longName);

                if (Report.should_report(TOPICS, 2))
		    Report.report(2, this + ": import " + shortName + " as " + t);

		if (map.containsKey(shortName)) {
		    Named s = (Named) map.get(shortName);

		    if (! ts.equals(s, t)) {
			throw new SemanticException("Class " + shortName +
			    " already defined as " + map.get(shortName),
                            sourcePos);
		    }
		}

                // map.put(longName, t); // should already be in the cache
		map.put(shortName, t);
	    }
	    catch (SemanticException e) {
                if (e.position == null) {
                    e.position = sourcePos;
                }

                throw e;
	    }
	}

	lazyImports = new ArrayList();
    }

    public String toString() {
        if (sourceName != null) {
            return "(import " + sourceName + ")";
        }
        else {
            return "(import)";
        }
    }

    private static final Collection TOPICS = 
        CollectionUtil.list(Report.types, Report.resolver, Report.imports);

}
