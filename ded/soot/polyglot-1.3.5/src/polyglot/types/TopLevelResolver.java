package polyglot.types;

public interface TopLevelResolver extends Resolver {
    /**
     * Check if a package exists.
     */
    public boolean packageExists(String name);
}
