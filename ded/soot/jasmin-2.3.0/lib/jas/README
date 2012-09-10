This is v0.3 of a simple Java bytecode assembler.


Quick Info:
-----------
  If you want to just quickly check out things, do the following.

% java scm.driver examples/hworld.jas

  This compiles an assembler script to bytecode.

% java out

  This runs the resultant bytecode, which should print the
  string "Hello World" 5 times.


  Then read the online documentation at

http://www.blackdown.org/~kbs/jas.html

More Details:
-------------

 * What is available:

  A simple java bytecode assembler that can be used either as a
  standalone scripting program or directly from java (through the jas
  package)

 * What is not available in this version:

  - Error recovery in the scripting interface
  - defining a tableswitch or lookupswitch instruction from the scripting
    interface

 * Documentation

  You can leaf through the jas API in reference/jas You can look at
  the list of available scripting functions from reference/scm. The
  bulk of what documentation exists is online from

  http://www.blackdown.org/~kbs/jas.html

  UTSL, ofcourse ;-) And documentation is mostly demand-driven,
  if there is interest I'll continue to expand it.

  * Examples

  The examples directory contains a few examples of using the
  assembler with the script and directly from java. Look at the
  README in this directory to see how to run them. Online
  documentation contains more details.

  simple.java
  simple.jas
         These are simple programs that create classes which
         don't do anything but get initialized.

  hworld.java
  hworld.jas
         These create bytecode that can be run standalone,
         which print a string a few times.

  exprcomp.java
         This is a primitive compiler that does runtime
         codegeneration and execution of arithmetic expressions.
  exprcomp.jas
         This is a primitive compiler written in jas to translate
         jas arithmetic expressions to bytecode.

  * Recompiling

  You can recompile all classes if you wish. First remove the
  jas/ and the scm/ directories under this directory. Then run the
  script compile.sh in this directory.

  You will probably want to then run the tests in the test directory
  to make sure the basic api is functional. Look at the README in this
  directory for details.


Running the scripting driver:
-----------------------------

  If you are going to use the scripting language, the driver for it is
located in the class scm.driver. The magic incantation is

% java scm.driver [path to file]

If you don't give it a file name, it will try to read from stdin.


Using code from this distribution:
----------------------------------

There is exactly one class that I *use* from the sun/* package, which
is sun.tools.java.RuntimeConstants. I know of no other reasonable way
to keep in sync with the VM.

Outside of this class, (which is not present in this distribution) you
can freely use/modify/sell/dance on with hobnailed boots any or all of
this code. If you do end up using derived code and feel disinclined to
buy me a snowboard :) all I ask is that you add me to the list of
credits.

-KB-
kbs@sbktech.org
Version created: Tue Aug 21 09:50:23 PDT 1996
