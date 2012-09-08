package polyglot.frontend;

/**
 * A <code>GlobalBarrierPass</code> is a special pass that ensures that
 * before a job <code>J</code> can run a <code>GlobalBarrierPass</code>,
 * <i>every</i> currently active job must have completed all passes up to the
 * same <code>GlobalBarrierPass</code>.
 */
public class GlobalBarrierPass extends BarrierPass {
    public GlobalBarrierPass(ID id, Job job) {
        super(id, job);
    }
}
