package polyglot.frontend;

/** The base class for most passes. */
public abstract class AbstractPass implements Pass
{
    /** The id of the pass. */
    protected Pass.ID id;

    /**
     * If the pass is running, the time that the pass started.
     * If the pass has completed, the time in ms the pass took to run,
     * excluding spawned passes.
     */
    protected long exclusive_time = 0;

    /**
     * If the pass is running, the time that the pass started.
     * If the pass has completed, the time in ms the pass took to run,
     * including spawned passes.
     */
    protected long inclusive_time = 0;

    public AbstractPass(Pass.ID id) {
        this.id = id;
    }

    /** The id of the pass. */
    public Pass.ID id() {
        return id;
    }

    /** The human-readable name of the pass. */
    public String name() {
        return id.toString();
    }

    /** Run the pass, returning true on success. */
    public abstract boolean run();

    /** Start or stop the pass timer. */
    public void toggleTimers(boolean exclusive_only) {
        // How this works:
        // reset: time = 0
        // start: time = T - 0 = T
        //  stop: time = T' - T = delta1
        // start: time = T'' - delta1 = T'' - T' + T
        //  stop: time = T''' - (T'' - T' + T) = delta2 + delta1
        if (! exclusive_only) {
            inclusive_time = System.currentTimeMillis() - inclusive_time;
        }
        exclusive_time = System.currentTimeMillis() - exclusive_time;
    }

    /** Reset the pass timer. */
    public void resetTimers() {
        inclusive_time = 0;
        exclusive_time = 0;
    }

    /** Return the time in ms taken to run the pass, excluding the time in
     * spawned passes */
    public long exclusiveTime() {
        return exclusive_time;
    }

    /** Return the time in ms taken to run the pass, including the time in
     * spawned passes */
    public long inclusiveTime() {
        return inclusive_time;
    }

    public String toString() {
	return getClass().getName() + ":" + id;
    }
}
