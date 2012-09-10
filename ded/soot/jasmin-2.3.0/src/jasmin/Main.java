/* --- Copyright Jonathan Meyer 1996. All rights reserved. -----------------
 > File:        jasmin/src/jasmin/Main.java
 > Purpose:     Runs Jasmin, parsing any command line arguments
 > Author:      Jonathan Meyer, 10 July 1996
 */
// Modifications Copyright (C) 2004 Ondrej Lhotak


package jasmin;

import java.io.*;
import jas.jasError;

/**
 * Main is the main entry point for Jasmin - it supplies the main()
 * method, as well as a few other useful odds and ends.
 */
public class Main {
    public static void assemble(InputStream in, OutputStream out, boolean number_lines) {
        ClassFile classFile = new ClassFile();

        try {
            InputStream inp = new BufferedInputStream(in);
            classFile.readJasmin(inp, "Jasmin", number_lines);
            inp.close();

            // if we got some errors, don't output a file - just return.
            if (classFile.errorCount() > 0) {
                System.err.println("Jasmin: Found "
                        + classFile.errorCount() + " errors");
                return;
            }

            classFile.write(out);
            out.flush();
        } catch (java.io.FileNotFoundException e) {
            System.err.println("Jasmin: file not found");
            System.exit(-1);
        } catch (jasError e) {
            classFile.report_error("JAS Error " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            classFile.report_error("Jasmin: exception - <" +
                    e.getClass().getName() + "> " + e.getMessage() +
                    ".");
            e.printStackTrace();
        }
        if (classFile.errorCount() > 0) {
            System.err.println("Jasmin: Found "
                    + classFile.errorCount() + " errors");
        }
    }

    /**
     * The Jasmin version
     */
    public static final String version = "2.3.0";

    /**
     * Called to assemble a single file.
     * @param dest_dir is the directory to place the result in.
     * @param fname is the name of the file containing the Jasmin source code.
     * @param number_lines is true if you want Jasmin to generate line numbers
     *        automatically, or false if you are generating line numbers yourself.
     */
    public static void assemble(String dest_dir, String fname,
		  		boolean number_lines) {
        File file = new File(fname);
        File out_file = null;
	ClassFile classFile = new ClassFile();

	try {
	    InputStream inp = new BufferedInputStream(new FileInputStream(fname));
            classFile.readJasmin(inp, file.getName(), number_lines);
	    inp.close();

            // if we got some errors, don't output a file - just return.
	    if (classFile.errorCount() > 0) {
                System.err.println(fname + ": Found "
				+ classFile.errorCount() + " errors");
		return;
            }

            String class_path[] = (ScannerUtils.splitClassField(
						classFile.getClassName()));
            String class_name = class_path[1];

            // determine where to place this class file
            if (class_path[0] != null) {
                String class_dir = ScannerUtils.convertChars(
					   class_path[0], "./",
                                           File.separatorChar);
                if (dest_dir != null) {
                    dest_dir = dest_dir + File.separator + class_dir;
                } else {
                    dest_dir = class_dir;
                }
            }
            if (dest_dir == null) {
                out_file = new File(class_name + ".class");
            } else {
                out_file = new File(dest_dir, class_name + ".class");

                // check that dest_dir exists

                File dest = new File(dest_dir);
                if (!dest.exists()) {
                    dest.mkdirs();
                }

                if (!dest.isDirectory()) {
                    throw new IOException("Cannot create directory");
                }
            }

            FileOutputStream outp = new FileOutputStream(out_file);
            classFile.write(outp);
	    outp.close();
            // System.out.println("Generated: " + out_file.getPath());

        } catch (java.io.FileNotFoundException e) {
            System.err.println(fname + ": file not found");
            System.exit(-1);
        } catch (jasError e) {
            classFile.report_error("JAS Error " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
  	    classFile.report_error(fname + ": exception - <" +
                              e.getClass().getName() + "> " + e.getMessage() +
                              ".");
	    e.printStackTrace();
        }
	if (classFile.errorCount() > 0) {
            System.err.println(fname + ": Found "
				+ classFile.errorCount() + " errors");
	}
    }

    public static void main(String args[]) {
        int i;
        String dest_dir = null;
        boolean debug = false;

        String files[] = new String[args.length];
        int num_files = 0;

        if (args.length == 0) {
            System.err.println("usage: jasmin [-d <directory>] [-version] <file> [<file> ...]");
            System.exit(-1);
        }

        for (i = 0; i < args.length; i++) {
            if (args[i].equals("-d")) {
                dest_dir = args[i + 1];
                i++;
            } else if (args[i].equals("-g")) {
                debug = true;
            } else if (args[i].equals("-version")) {
                System.out.println("Jasmin version: " + version);
		System.exit(0);
            } else {
                files[num_files++] = args[i];
            }
        }

        for (i = 0; i < num_files; i++) {
            assemble(dest_dir, files[i], debug);
        }
    }
};

/* --- Revision History ---------------------------------------------------
--- Jonathan Meyer, Mar 1 1997 tidied error reporting, renamed Jasmin->ClassFile
--- Jonathan Meyer, Feb 8 1997 added the assemble() method
--- Jonathan Meyer, July 24 1996 added -version flag.
*/




