package polyglot.main;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/** Class used for reporting debug messages. */
public class Report {
  /** A collection of string names of topics which can be used with the
      -report command-line switch */
  public final static Collection topics = new HashSet();

  /** A collection of string names of topics which we should always check
      if we should report. */
  public final static Stack should_report = new Stack();

  /** 
   * The topics that the user has selected to report, mapped to the level
   * they want to report them to.
   */
  protected final static Map reportTopics = new HashMap(); // Map[String, Integer]
  

  /**
   * Indicates if there is no reporting at all.
   * The normal case is that we do not report anything, so for efficiency 
   * reasons, since <code>should_report</code> is called so often, we'll use
   * this flag to bypass a lot of the checking. When the options are processed,
   * this flag should be changed.
   */
  static boolean noReporting = true;
  
  /** Report topics understood by the base compiler. */
  public final static String cfg = "cfg";
  public final static String context = "context";
  public final static String dataflow = "dataflow";
  public final static String errors = "errors";
  public final static String frontend = "frontend";
  public final static String imports = "imports";
  public final static String loader = "loader";
  public final static String resolver = "resolver";
  public final static String serialize = "serialize";
  public final static String time = "time";
  public final static String types = "types";
  public final static String visit = "visit";
  public final static String verbose = "verbose";
  
  // This topic is the level of detail that should be in messages.
  public final static String debug = "debug";  

  static {
    topics.add(cfg);
    topics.add(context);
    topics.add(dataflow);
    topics.add(errors);
    topics.add(frontend);
    topics.add(imports);
    topics.add(loader);
    topics.add(resolver);
    topics.add(serialize);
    topics.add(time);
    topics.add(types);
    topics.add(visit);
    topics.add(verbose);
    topics.add(debug);

    should_report.push(verbose);
  }

  /**
   * Return whether a message on <code>topic</code> of obscurity
   * <code>level</code> should be reported, based on use of the
   * -report command-line switches given by the user.
   */
  public static boolean should_report(String topic, int level) {
    if (noReporting)
        return false;
    return should_report(Collections.singletonList(topic), level); 
  }

  /**
   * Return whether a message on <code>topics</code> of obscurity
   * <code>level</code> should be reported, based on use of the
   * -report command-line switches given by the user.
   */
  public static boolean should_report(String[] topics, int level) {
      if (noReporting)
          return false;
    return should_report(Arrays.asList(topics), level);
  }

  /**
   * Return whether a message on <code>topics</code> of obscurity
   * <code>level</code> should be reported, based on use of the
   * -report command-line switches given by the user.
   */
  public static boolean should_report(Collection topics, int level) {
      if (noReporting)
          return false;
    for (Iterator i = should_report.iterator(); i.hasNext();) {
        String topic = (String) i.next();
        if (level(topic) >= level) return true;
    }
    if (topics != null) {
	for (Iterator i = topics.iterator(); i.hasNext();) {
	    String topic = (String) i.next();
	    if (level(topic) >= level) return true;
	}
    }
    return false;
  }
  
  public static void addTopic(String topic, int level) {
      Integer i = (Integer)reportTopics.get(topic);
      if (i == null || i.intValue() < level) {
          reportTopics.put(topic, new Integer(level));
      }
      noReporting = false;
  }

  protected static int level(String name) {
      Object i = reportTopics.get(name);
      if (i == null) return 0;
      else return ((Integer)i).intValue();
  }

  /** This is the standard way to report debugging information in the
   *  compiler.  It reports a message of the specified level (which
   *  controls the presentation of the message. To test whether such
   *  message should be reported, use "should_report"
   *
   *  NOTE: This is a change of spec from earlier versions of Report.
   */
  public static void report(int level, String message) {
    for (int j = 1; j < level; j++) System.err.print("  ");
    System.err.println(message);
  }
}
