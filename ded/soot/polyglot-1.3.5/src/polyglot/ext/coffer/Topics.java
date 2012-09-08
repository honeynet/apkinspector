package polyglot.ext.coffer;

import polyglot.main.Report;

/**
 * Extension information for coffer extension.
 */
public class Topics {
    public static final String coffer = "coffer";
    public static final String keycheck = "keycheck";

    static {
        Report.topics.add(coffer);
        Report.topics.add(keycheck);
    }
}
