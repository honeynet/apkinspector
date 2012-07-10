This is README file for Jad - the fast Java Decompiler.
Jad home page: http://www.geocities.com/kpdus/jad.html
Copyright 2001 Pavel Kouznetsov (kpdus@yahoo.com).

0. Please read the disclaimer on the Jad home page.

1. Installation.

Unzip jad.zip file into any appropriate directory on your hard drive.
This will create two files:

    - an executable file named 'jad.exe' (Windows *)
      or 'jad' (*n*x)

    - this README file

No other setup is required.

2. How to use Jad

To decompile a single JAVA class file 'example1.class' 
type the following:

     jad example1.class

This command creates file 'example1.jad' in the current directory.
If such file already exists Jad asks whether you want to overwrite it or not.
Option -o permits overwriting without a confirmation.

You can omit .class extension and/or use wildcards in the names of
input files.

Option -s <ext> allows to change output file extension:

     jad -sjava example1.class

This command creates file 'example1.java'. Be careful when using
options -o and -sjava together, because Jad can accidentally overwrite
your own source files.

Jad uses JAVA class name as an output file name. For example, if class
file 'example1.class' contains JAVA class 'test1' then Jad will create
file 'test1.jad' rather than 'example1.jad'. If you want to specify
your own output file name use the output redirection:

   jad -p example1.class > myexm1.java

Option -d allows you to specify another directory for output files,
which are created, by default, in the current directory. For example:

   jad -o -dtest -sjava *.class

   (or jad -o -d test -s java *.class, which has the same effect)

This command decompiles all .class files in the current directory 
and places all output files with extension .java into directory 'test'.


If you want to decompile the whole tree of JAVA classes,
use the following command:

   jad -o -r -sjava -dsrc tree/**/*.class

This command decompiles all .class files located in all 
subdirectories of 'tree' and creates output files in subdirectories
of 'src' according to package names of classes. For example, if file 
'tree/a/b/c.class' contains class 'c' from package 'a.b', then 
output file will have a name 'src/a/b/c.java'.

Note the use of the "two stars" wildcard ('**') in the previous
command. It is handled by Jad rather than the command shell, so on
UNIX the last argument should be single-quoted:

   jad -o -r -sjava -dsrc 'tree/**/*.class'


In a case you want to check the accuracy of the decompilation or just
curious, there is an option -a which tells Jad to annotate the output
with JAVA Virtual Machine bytecodes.

Jad supports the inner and anonymous classes. 
When Jad expands wildcards in the input file names, 
it automatically skips matching inner classes. 
On UNIX Jad skips inner classes if there is more than 
one class specified in the command line.
Jad looks for inner classes in the directory of their top-level
container class.

3. List of the command-line options.

Jad accepts the following options:

   -a       - annotate the output with JVM bytecodes (default: off)
   -af      - same as -a, but output fully qualified names when annotating
   -clear   - clear all prefixes, including the default ones (can be abbreviated as -cl)
   -b       - output redundant braces (e.g., if(a) { b(); }, default: off)
   -d <dir> - directory for output files (will be created when necessary)
   -dead    - try to decompile dead parts of code (if any) (default: off)
   -disass  - disassemble method bytecodes (no JAVA source generated)
   -f       - output fully qualified names for classes/fields/methods (default: off)
   -ff      - output class fields before methods (default: after methods)
   -i       - output default initializers for all non-final fields
   -l<num>  - split strings into pieces of maximum <num> chars (default: off)
   -lnc     - annotate the output with line numbers (default: off)
   -lradix<num> - display long integers using the specified radix (8, 10 or 16)
   -nl      - split strings on newline character (default: off)
   -nocast  - don't generate auxiliary casts
   -nocode  - don't generate the source code for methods
   -noconv  - don't convert Java identifiers (default: convert)
   -noctor  - suppress the empty constructors
   -nodos   - do not check for class files written in DOS mode (CR before NL, default: check)
   -nofd    - don't disambiguate fields with the same names by adding signatures to their names (default: do)
   -noinner - turn off the support of inner classes (default: on)
   -nolvt   - ignore Local Variable Table information
   -nonlb   - don't output a newline before opening brace (default: do)
   -o       - overwrite output files without confirmation (default: off)
   -p       - send decompiled code to STDOUT (e.g., for piping)
   -pi<num> - pack imports into one line after <num> imports (default: 3)
   -pv<num> - pack fields with identical types into one line (default: off)
   -pa <pfx>- prefix for all packages in generated source files
   -pc <pfx>- prefix for classes with numerical names (default: _cls)
   -pf <pfx>- prefix for fields with numerical names (default: _fld)
   -pe <pfx>- prefix for unused exception names (default: _ex)
   -pl <pfx>- prefix for locals with numerical names (default: _lcl)
   -pm <pfx>- prefix for methods with numerical names (default: _mth)
   -pp <pfx>- prefix for method parms with numerical names (default: _prm)
   -r       - restore package directory structrure
   -radix<num> - display integers using the specified radix (8, 10 or 16)
   -s <ext> - output file extension (by default '.jad')
   -safe    - generate additional casts to disambiguate methods/fields (default: off)
   -space   - output space between keyword (if/for/while/etc) and expression (default: off)
   -stat    - display the total number of processed classes/methods/fields
   -t       - use tabs instead of spaces for indentation
   -t<num>  - use <num> spaces for indentation (default: 4)
   -v       - display method names being decompiled
   -8       - convert UNICODE strings into 8-bit strings
              using the current ANSI code page (Win32 only)
   -&       - redirect STDERR to STDOUT (Win32 only)

All single-word options have three formats:

  -o    - 'reverses' value of an option
  -o+   - set value to 'true' or 'on'
  -o-   - set value to 'false' or 'off'

You can specify the options you want to be set by default in the environment variable
JAD_OPTIONS. For example:

JAD_OPTIONS=-ff+ -nonlb+ -t+ -space+
