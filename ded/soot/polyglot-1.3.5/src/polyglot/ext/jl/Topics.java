package polyglot.ext.jl;

import polyglot.main.Report;

/**
 * Extension information for jl extension.
 */
public class Topics {
    public static final String jl = "jl";
    public static final String qq = "qq";

    static {
        Report.topics.add(jl);
        Report.topics.add(qq);
    }
}
