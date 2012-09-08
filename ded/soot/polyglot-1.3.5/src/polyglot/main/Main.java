package polyglot.main;

import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.util.ErrorInfo;
import polyglot.util.ErrorQueue;
import polyglot.util.StdErrorQueue;
import polyglot.util.QuotedStringTokenizer;

import java.io.*;
import java.util.*;

/** Main is the main program of the extensible compiler. It should not
 * need to be replaced.
 */
public class Main
{

  /** Source files specified on the command line */
  private Set source;

  //ak333: set to 'public' so cppout MakePass can see it.
  public final static String verbose = "verbose";

  /* modifies args */
  protected ExtensionInfo getExtensionInfo(List args) throws TerminationException {
      ExtensionInfo ext = null;

      for (Iterator i = args.iterator(); i.hasNext(); ) {
          String s = (String)i.next();
          if (s.equals("-ext") || s.equals("-extension"))
          {
              if (ext != null) {
                  throw new TerminationException("only one extension can be specified");
              }

              i.remove();
              if (!i.hasNext()) {
                  throw new TerminationException("missing argument");
              }
              String extName = (String)i.next();
              i.remove();
              ext = loadExtension("polyglot.ext." + extName + ".ExtensionInfo");
          }
          else if (s.equals("-extclass"))
          {
              if (ext != null) {
                  throw new TerminationException("only one extension can be specified");
              }

              i.remove();
              if (!i.hasNext()) {
                  throw new TerminationException("missing argument");
              }
              String extClass = (String)i.next();
              i.remove();
              ext = loadExtension(extClass);
          }
      }
      if (ext != null) {
          return ext;
      }
      return loadExtension("polyglot.ext.jl.ExtensionInfo");
  }

  public void start(String[] argv) throws TerminationException {
      start(argv, null);
  }

  public void start(String[] argv, ErrorQueue eq) throws TerminationException {
      source = new LinkedHashSet();
      List args = explodeOptions(argv);
      ExtensionInfo ext = getExtensionInfo(args);
      Options options = ext.getOptions();

      // Allow all objects to get access to the Options object. This hack should
      // be fixed somehow. XXX###@@@
      Options.global = options;
      try {
          argv = (String[]) args.toArray(new String[0]);
          options.parseCommandLine(argv, source);
      }
      catch (UsageError ue) {
          PrintStream out = (ue.exitCode==0 ? System.out : System.err);
          if (ue.getMessage() != null && ue.getMessage().length() > 0) {
              out.println(ext.compilerName() +": " + ue.getMessage());
          }
          options.usage(out);
          throw new TerminationException(ue.exitCode);
      }

      if (eq == null) {
          eq = new StdErrorQueue(System.err,
                                 options.error_count,
                                 ext.compilerName());
      }

      Compiler compiler = new Compiler(ext, eq);

      long time0 = System.currentTimeMillis();

      if (!compiler.compile(source)) {
          throw new TerminationException(1);
      }

      if (Report.should_report(verbose, 1))
          Report.report(1, "Output files: " + compiler.outputFiles());

      long start_time = System.currentTimeMillis();

      /* Now call javac or jikes, if necessary. */
      if (!invokePostCompiler(options, compiler, eq)) {
          throw new TerminationException(1);
      }

      if (Report.should_report(verbose, 1)) {
          reportTime("Finished compiling Java output files. time=" +
                  (System.currentTimeMillis() - start_time), 1);

          reportTime("Total time=" + (System.currentTimeMillis() - time0), 1);
      }
  }

  protected boolean invokePostCompiler(Options options,
                                    Compiler compiler,
                                    ErrorQueue eq) {
      if (options.post_compiler != null && !options.output_stdout) {
          Runtime runtime = Runtime.getRuntime();
          QuotedStringTokenizer st = new QuotedStringTokenizer(options.post_compiler);
          int pc_size = st.countTokens();
          String[] javacCmd = new String[pc_size+2+compiler.outputFiles().size()];
          int j = 0;
          for (int i = 0; i < pc_size; i++) {
              javacCmd[j++] = st.nextToken();
          }
          javacCmd[j++] = "-classpath";
          javacCmd[j++] = options.constructPostCompilerClasspath();

          Iterator iter = compiler.outputFiles().iterator();
          for (; iter.hasNext(); j++) {
              javacCmd[j] = (String) iter.next();
          }

          if (Report.should_report(verbose, 1)) {
              StringBuffer cmdStr = new StringBuffer();
              for (int i = 0; i < javacCmd.length; i++)
                  cmdStr.append(javacCmd[i]+" ");
              Report.report(1, "Executing post-compiler " + cmdStr);
          }

          try {
              Process proc = runtime.exec(javacCmd);

              InputStreamReader err = null;

              try {
                  err = new InputStreamReader(proc.getErrorStream());
                  char[] c = new char[72];
                  int len;
                  StringBuffer sb = new StringBuffer();
                  while((len = err.read(c)) > 0) {
                      sb.append(String.valueOf(c, 0, len));
                  }

                  if (sb.length() != 0) {
                      eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, sb.toString());
                  }
              }
              finally {
                  err.close();
              }

              proc.waitFor();

              if (!options.keep_output_files) {
                String[] rmCmd = new String[1+compiler.outputFiles().size()];
                rmCmd[0] = "rm";
                for (int i = 1; i < rmCmd.length; i++)
                    rmCmd[i] = javacCmd[i+2];
                runtime.exec(rmCmd);
              }

              if (proc.exitValue() > 0) {
                eq.enqueue(ErrorInfo.POST_COMPILER_ERROR,
                                 "Non-zero return code: " + proc.exitValue());
                return false;
              }
          }
          catch(Exception e) {
              eq.enqueue(ErrorInfo.POST_COMPILER_ERROR, e.getMessage());
              return false;
          }
      }
      return true;
  }

  private List explodeOptions(String[] args) throws TerminationException {
      LinkedList ll = new LinkedList();

      for (int i = 0; i < args.length; i++) {
          // special case for the @ command-line parameter
          if (args[i].startsWith("@")) {
              String fn = args[i].substring(1);
              try {
                  BufferedReader lr = new BufferedReader(new FileReader(fn));
                  LinkedList newArgs = new LinkedList();

                  while (true) {
                      String l = lr.readLine();
                      if (l == null)
                          break;

                      StringTokenizer st = new StringTokenizer(l, " ");
                      while (st.hasMoreTokens())
                          newArgs.add(st.nextToken());
                  }

                  lr.close();
                  ll.addAll(newArgs);
              }
              catch (java.io.IOException e) {
                  throw new TerminationException("cmdline parser: couldn't read args file "+fn);
              }
              continue;
          }

          ll.add(args[i]);
      }

      return ll;
  }

  public static final void main(String args[]) {
      try {
          new Main().start(args);
      }
      catch (TerminationException te) {
          if (te.getMessage() != null)
              (te.exitCode==0?System.out:System.err).println(te.getMessage());
          System.exit(te.exitCode);
      }
  }

  static final ExtensionInfo loadExtension(String ext) throws TerminationException {
    if (ext != null && ! ext.equals("")) {
      Class extClass = null;

      try {
        extClass = Class.forName(ext);
      }
      catch (ClassNotFoundException e) {
          throw new TerminationException(
            "Extension " + ext +
            " not found: could not find class " + ext + ".");
      }

      try {
        return (ExtensionInfo) extClass.newInstance();
      }
      catch (ClassCastException e) {
          throw new TerminationException(
	    ext + " is not a valid polyglot extension:" +
	    " extension class " + ext +
	    " exists but is not a subclass of ExtensionInfo");
      }
      catch (Exception e) {
          throw new TerminationException(
	           "Extension " + ext +
	           " could not be loaded: could not instantiate " + ext + ".");
      }
    }
    return null;
  }

  static private Collection timeTopics = new ArrayList(1);
  static {
      timeTopics.add("time");
  }

  static private void reportTime(String msg, int level) {
      Report.report(level, msg);
  }

  /**
   * This exception signals termination of the compiler. It should be used
   * instead of <code>System.exit</code> to allow Polyglot to be called
   * within a JVM that wasn't started specifically for Polyglot, e.g. the
   * Apache ANT framework.
   */
  public static class TerminationException extends RuntimeException {
      final public int exitCode;
      public TerminationException(String msg) {
          this(msg, 1);
      }
      public TerminationException(int exit) {
          this.exitCode = exit;
      }
      public TerminationException(String msg, int exit) {
          super(msg);
          this.exitCode = exit;
      }
  }
}
