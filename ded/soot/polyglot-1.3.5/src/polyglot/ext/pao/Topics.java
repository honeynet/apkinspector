package polyglot.ext.pao;

import polyglot.main.Report;

/**
 * Additional report topics for the PAO extension.
 */
public class Topics {
    public static final String pao = "pao";

    static {
        // add the additional report topics to the Report class.
        Report.topics.add(pao);
    }
}
