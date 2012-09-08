package polyglot.types;

import polyglot.frontend.Compiler;
import polyglot.frontend.ExtensionInfo;
import polyglot.frontend.FileSource;
import polyglot.main.Report;
import polyglot.types.reflect.ClassFile;
import polyglot.types.reflect.ClassFileLoader;

/**
 * Loads class information from source files, class files, or serialized
 * class infomation from within class files. An outline of the steps is
 * given below.
 *
 * <ol>
 * <li> When the polyglot translator looks for a class by the name
 * "foo.bar.Quux" it first searches for that class in any file given
 * on the command line. If the class is found one of these files, then
 * this definition is used and the remainder of the steps are
 * skipped.
 *
 * <li>If none of these files contain the desired class, then the source
 * path is searched  next. For example, if the source extension is
 * ".jl" and the source path is "mydir:." then the translator looks
 * for files "mydir/foo/bar/Quux.jl" and "./foo/bar/Quux.jl".
 *
 * <li> Regardless of whether or not a source file is found, the translator
 * searches the classpath (defined as normal through the environment
 * and command-line options to the interpreter) for the desired class.
 *
 * <li>If no source file exists, and no class is found then an error is
 * reported (skipping the rest of the steps below).
 *
 * <li>If a source file is found, but no class, then the source file is
 * parsed. If it contains the desired class definition (which it
 * should) then that definition is used and the remainder of the steps
 * are skipped. (If it does not contain this definition, an error is
 * reported and the remainder of the steps are skipped.
 *
 * <li>If a class is found but no source file, then the class is examined
 * for jlc class type information. If the class contains no class type
 * information (this is the case if the class file was compiled from
 * raw Java source rather than jlc translated output) then this class
 * is used as the desired class definition (skipping all steps below).
 *
 * <li>(class, but no still no source) If the class does contain jlc class
 * type information, then the version number of translator used to
 * translate the source which created the given class file is compared
 * against the version of the current instantiation of the translator.
 * If the versions are compatible, then the jlc class type information
 * is used as the desired definiton. If the versions are incompatible
 * (see the documentation in Compiler.java) then an error is reported.
 * In either case, all remaining steps are skipped.
 *
 * <li>If both a suitable source file and class are found then we have a
 * choice. If the class definition does not contain jlc class type
 * information then the source file is parsed as the definition found
 * in this file is used as desired definiton and we stop here. If the
 * class does contain jlc class type information, then continue.
 *
 * <li>(source and class with jlc info) Next the last modification date of
 * the source file is compared to the last modification date of the
 * source file used to generate the class file. If the source file is
 * more recent, the it is parsed as used as the desired definition and
 * all remaining steps are skipped.
 *
 * <li>(source and class with jlc info) Next the jlc version of the class
 * and of the current translator are compared (as in 7.). If the
 * verisions are incompatible, then we use the definition from the
 * parsed source file. If the versions are compatible, then we use
 * the definition given by the jlc class type information.
 * </ol>
 * Finally, if at any point an error occurs while reading jlc class type
 * information (e.g. if this information exists but is corrupted), then
 * an error is reported.
 */
public class SourceClassResolver extends LoadedClassResolver
{
  protected Compiler compiler;
  protected ExtensionInfo ext;

  /**
   * Create a loaded class resolver.
   * @param compiler The compiler.
   * @param ext The extension to load sources for.
   * @param classpath The class path.
   * @param loader The class file loader to use.
   * @param allowRawClasses True if vanilla Java class files without
   *                        Polyglot-embedded type information should be
   *                        allowed.
   */
  public SourceClassResolver(Compiler compiler, ExtensionInfo ext,
                             String classpath, ClassFileLoader loader,
                             boolean allowRawClasses)
  {
    super(ext.typeSystem(), classpath, loader, ext.version(), allowRawClasses);
    this.compiler = compiler;
    this.ext = ext;
  }

  public boolean packageExists(String name) {
    if (super.packageExists(name)) {
        return true;
    }
    /*
    if (ext.sourceLoader().packageExists(name)) {
        return true;
    }
    */
    return false;
  }

  /**
   * Find a type by name.
   */
  public Named find(String name) throws SemanticException {
    if (Report.should_report(report_topics, 3))
	Report.report(3, "SourceCR.find(" + name + ")");

    ClassFile clazz = null;
    ClassFile encodedClazz = null;
    FileSource source = null;

    // First try the class file.
    clazz = loadFile(name);
    if (clazz != null) {
      // Check for encoded type information.
      if (clazz.encodedClassType(version.name()) != null) {
        if (Report.should_report(report_topics, 4))
          Report.report(4, "Class " + name + " has encoded type info");
        encodedClazz = clazz;
      }
    }

    if (clazz != null && ! this.allowRawClasses) {
      clazz = null;
    }

    // Now, try and find the source file.
    source = ext.sourceLoader().classSource(name);
    if (Report.should_report(report_topics, 4)) {
        if (source == null) 
            Report.report(4, "Class " + name + " not found in source file");
        else
            Report.report(4, "Class " + name + " found in source " + source);
    }

    // Don't use the raw class if the encoded class is available.
    if (encodedClazz != null) {
      if (Report.should_report(report_topics, 4))
	Report.report(4, "Not using raw class file for " + name);
      clazz = null;
    }

    // If both the source and class are available, we decide which to
    // use based on compiler compatibility and modification times.
    if (clazz != null && source != null) {
      long classModTime = clazz.rawSourceLastModified();
      long sourceModTime = source.lastModified().getTime();

      if (classModTime < sourceModTime) {
        if (Report.should_report(report_topics, 3))
	    Report.report(3, "Source file version is newer than compiled for " +
                      name + ".");
        clazz = null;
      }
      else {
        source = null;
      }
    }

    if (encodedClazz != null && source != null) {
      long classModTime = encodedClazz.sourceLastModified(version.name());
      long sourceModTime = source.lastModified().getTime();

      int comp = checkCompilerVersion(encodedClazz.compilerVersion(version.name()));

      if (classModTime < sourceModTime) {
        if (Report.should_report(report_topics, 3))
	    Report.report(3, "Source file version is newer than compiled for " +
                      name + ".");
        encodedClazz = null;
      }
      else if (comp != COMPATIBLE) {
        // Incompatible or older version, so go with the source.
        if (Report.should_report(report_topics, 3))
	    Report.report(3, "Incompatible source file version for " + name + ".");
        encodedClazz = null;
      }
    }

    if (encodedClazz != null) {
      if (Report.should_report(report_topics, 4))
	Report.report(4, "Using encoded class type for " + name);
      try {
        return getEncodedType(encodedClazz, name);
      }
      catch (BadSerializationException e) {
      	throw e;
      }
      catch (SemanticException e) {
        if (Report.should_report(report_topics, 4))
          Report.report(4, "Could not load encoded class " + name);
        encodedClazz = null;
      }
    }

    // At this point, at most one of clazz and source should be set.

    if (clazz != null) {
      if (Report.should_report(report_topics, 4))
	Report.report(4, "Using raw class file for " + name);
      return clazz.type(ts);
    }

    if (source != null) {
      if (Report.should_report(report_topics, 4))
	Report.report(4, "Using source file for " + name);
      return getTypeFromSource(source, name);
    }

    if (clazz != null && !this.allowRawClasses) {
        // We have a raw class only. We do not have the source code,
        // or encoded class information. 
        throw new SemanticException("Class \"" + name + "\" not found."
            + " A class file was found, but it did not contain appropriate" 
            + " information for the Polyglot-based compiler " + 
            ext.compilerName() + ". Try using " + ext.compilerName() 
            + " to recompile the source code.");
    }
    throw new NoClassException(name);
  }

  /**
   * Get a type from a source file.
   */
  protected Named getTypeFromSource(FileSource source, String name)
    throws SemanticException
  {
    // Compile the source file just enough to get the type information out.
    ext.readSource(source);
    
    // Even if there was an error when compiling the source file, we may
    // as well keep trying to compile the current class, as the error may
    // have been with something source depended on, that we do not.
    return ts.parsedResolver().find(name);
  }
}
