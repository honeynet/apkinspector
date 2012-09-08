package polyglot.ext.param;

import polyglot.main.Report;

/**
 * Extension information for param extension.
 */
public class Topics {
    public static final String param = "param";
    public static final String subst = "subst";

    static {
        Report.topics.add(param);
        Report.topics.add(subst);
    }
}
