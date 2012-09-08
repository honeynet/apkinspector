package polyglot.ext.jl;

/**
 * Version information for the base compiler.
 */
public class Version extends polyglot.main.Version {
    public String name()
        { return "jl"; }
    public int major()
	{ return 1; }
    public int minor()
	{ return 3; }
    public int patch_level()
	{ return 5; }
}
