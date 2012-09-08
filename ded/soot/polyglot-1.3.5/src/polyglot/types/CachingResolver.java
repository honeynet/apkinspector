package polyglot.types;

import polyglot.util.*;
import polyglot.frontend.ExtensionInfo;
import polyglot.main.Report;
import polyglot.types.Package;
import java.util.*;

/**
 * An <code>CachingResolver</code> memoizes another Resolver
 */
public class CachingResolver implements TopLevelResolver {
    protected TopLevelResolver inner;
    protected Map cache;
    protected Map packageCache;
    protected ExtensionInfo extInfo;

    protected static final Object NOT_FOUND = new Object();

    /**
     * Create a caching resolver.
     * @param inner The resolver whose results this resolver caches.
     */
    public CachingResolver(TopLevelResolver inner, ExtensionInfo extInfo) {
	this.inner = inner;
	this.cache = new HashMap();
	this.packageCache = new HashMap();
        this.extInfo = extInfo;
    }

    /**
     * The resolver whose results this resolver caches.
     */
    public TopLevelResolver inner() {
        return this.inner;
    }

    public String toString() {
        return "(cache " + inner.toString() + ")";
    }

    /**
     * Check if a package exists.
     */
    public boolean packageExists(String name) {
	Boolean b = (Boolean) packageCache.get(name);
	if (b != null) {
	    return b.booleanValue();
	}
	else {
            String prefix = StringUtil.getPackageComponent(name);

            if (packageCache.get(prefix) == Boolean.FALSE) {
                packageCache.put(name, Boolean.FALSE);
                return false;
            }

            boolean exists = inner.packageExists(name);

            if (exists) {
                packageCache.put(name, Boolean.TRUE);

                do {
                    packageCache.put(prefix, Boolean.TRUE);
                    prefix = StringUtil.getPackageComponent(prefix);
                } while (! prefix.equals(""));
            }
            else {
                packageCache.put(name, Boolean.FALSE);
            }

            return exists;
	}
    }

    protected void cachePackage(Package p) {
        if (p != null) {
            packageCache.put(p.fullName(), Boolean.TRUE);
            cachePackage(p.prefix());
        }
    }

    /**
     * Find a type object by name.
     * @param name The name to search for.
     */
    public Named find(String name) throws SemanticException {
        if (Report.should_report(TOPICS, 2))
            Report.report(2, "CachingResolver: find: " + name);

        Object o = cache.get(name);

        if (o == NOT_FOUND) {
            throw new NoClassException(name);
        }

        Named q = (Named) o;

	if (q == null) {
            if (Report.should_report(TOPICS, 3))
                Report.report(3, "CachingResolver: not cached: " + name);

            try {
                q = inner.find(name);
            }
            catch (NoClassException e) {
                cache.put(name, NOT_FOUND);
                throw e;
            }

            if (q instanceof ClassType) {
                Package p = ((ClassType) q).package_();
                cachePackage(p);
            }

            addNamed(name, q);

            if (Report.should_report(TOPICS, 3))
                Report.report(3, "CachingResolver: loaded: " + name);
	}
        else {
            if (Report.should_report(TOPICS, 3))
                Report.report(3, "CachingResolver: cached: " + name);
        }

        if (q instanceof ParsedClassType) {
            extInfo.addDependencyToCurrentJob(((ParsedClassType)q).fromSource());
        }

	return q;
    }

    /**
     * Check if a type is in the cache, returning null if not.
     * @param name The name to search for.
     */
    public Type checkType(String name) {
        return (Type) check(name);
    }

    /**
     * Check if a type object is in the cache, returning null if not.
     * @param name The name to search for.
     */
    public Named check(String name) {
        Object o = cache.get(name);
        if (o == NOT_FOUND) return null;
        return (Named) cache.get(name);
    }

    /**
     * Install a qualifier in the cache.
     * @param name The name of the qualifier to insert.
     * @param q The qualifier to insert.
     */
    public void install(String name, Named q) {
	cache.put(name, q);
    }

    /**
     * Install a qualifier in the cache.
     * @param name The name of the qualifier to insert.
     * @param q The qualifier to insert.
     */
    public void addNamed(String name, Named q) throws SemanticException {
	install(name, q);

	if (q instanceof Type && packageExists(name)) {
	    throw new SemanticException("Type \"" + name +
					"\" clashes with package of the same name.", q.position());
	}
    }

    protected static final Collection TOPICS =
                    CollectionUtil.list(Report.types,
                                        Report.resolver);
}
