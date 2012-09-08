package ppg;

import java.io.*;
import ppg.cmds.*;
import ppg.lex.*;
import ppg.parse.*;
import ppg.spec.*;
import ppg.util.*;

public class PPG
{
    public static final String HEADER = "ppg: ";
    public static final String DEBUG_HEADER = "ppg [debug]: ";
    public static boolean debug = false;
    public static String SYMBOL_CLASS_NAME = "sym";
    public static String OUTPUT_FILE = null;

    public static void DEBUG (String s)
    {
	if (debug)
	    System.out.println (DEBUG_HEADER + s);
    }

    public static void main (String args[])
    {
	FileInputStream fileInput;
	String filename = null;

	try
	{
	    for (int i = 0; i < args.length; i++)
	      {
		  // assume all switches begin with a dash '-'
		  if (args[i].charAt (0) == '-')
		    {
			if (args[i].equals ("-symbols"))
			  {
			      if (args.length > i)
				  SYMBOL_CLASS_NAME = args[++i];
			      else
				  throw new
				      Exception
				      ("No filename specified after -symbols");
			  }
                        else if (args[i].equals ("-o"))
                          {
                              if (args.length > i) 
                                  OUTPUT_FILE = args[++i];
                              else
				  throw new
				      Exception
				      ("No filename specified after -o");
                          }
			else	// invalid switch
			    throw new Exception ("Invalid switch: " +
						 args[i]);
		    }
		  else
		    {
			// not a switch: this must be a filename
			// but only do the 1st filename on the command line
			if (filename == null)
			    filename = args[i];
			else
			    throw new
				Exception
				("Error: multiple source files specified.");
		    }
	      }
	}
	catch (Exception e)
	{
	    System.err.println (HEADER + e.getMessage ());
	    usage ();
	}


	if (filename == null)
	  {
	      System.err.println ("Error: no filename specified.");
	      usage ();
	  }

	try
	{
	    fileInput = new FileInputStream (filename);
	}
	catch (FileNotFoundException e)
	{
	    System.out.println ("Error: " + filename + " is not found.");
	    return;
	}
	catch (ArrayIndexOutOfBoundsException e)
	{
	    System.out.println (HEADER + "Error: No file name given.");
	    return;
	}

	Lexer lex = new Lexer (fileInput, filename);

	Parser parser = new Parser (filename, lex);
	try
	{
	    parser.parse ();
	}
	catch (Exception e)
	{
	    System.out.println (HEADER + "Exception: " + e.getMessage ());
	    return;
	}
	Spec spec = (Spec) parser.getProgramNode ();

        File file = new File(filename);
	String parent = file.getParent ();
	spec.parseChain (parent == null ? "" : parent);

        PrintStream out = System.out;


	/* now we have a linked list of inheritance, namely
	 * PPG_1, PPG_2, ..., PPG_n, CUP
	 * We combine two at a time, starting from the end with the CUP spec
	 */
	try
	{
            if (OUTPUT_FILE != null) {
                out = new PrintStream(new FileOutputStream(OUTPUT_FILE));
            }

	    CUPSpec combined = spec.coalesce ();
	    CodeWriter cw = new CodeWriter (out, 72);
	    combined.unparse (cw);
	    cw.flush ();
	}
	catch (PPGError e)
	{
	    System.out.println (e.getMessage ());
	    System.exit (1);
	}
	catch (IOException e)
	{
	    System.out.println (HEADER + "exception: " + e.getMessage ());
	    System.exit (1);
	}
    }


    public static void usage ()
    {
	System.err.
	    println ("Usage: ppg [-symbols ConstClass] <input file>\nwhere:\n" +
		     "\t-c <Class>\tclass prepended to token names to pass to <func>\n"
		     + "\t<input>\ta PPG or CUP source file\n");
	System.exit (1);
    }
}
